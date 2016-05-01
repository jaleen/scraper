package com.jalalsoft.productscrapper;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/* (non-Javadoc)
 * @see com.jalalsoft.productscrapper.ProductScraper
 */
/**
 * This class retrieves the products from a provided url. To look for the product attributes it needs to run
 * the css selectors to find the attributes. Example css selectors for each attributes are provided in application.properties
 * file. 
 * products.url is used as default url and is retrieved from application.properties file if this was null in arguments
 * price.read.pattern is reg expression to find price per unit float value from the string like £1.89/unit. it returns 1.89
 * product.price.selector will retrieve the prices from page. It assumes that prices order will be same as of product order. i.e. if css selector for product
 * selection selects the product a as first product in the list the price selector should select the price of product a as first price
 * product.detailpage.linkhref.selector this selector will select the follow on link to the product detail page
 * product.description.selector will retrieve the description of the product from product detail page which opens using link found using product.detailpage.linkhref.selector 
 *  
 * @author jdeen
 *
 */
public class GroceryProductScraper implements ProductScraper {

	private static final String productURLKey = "products.url";
	private static final String productPricePatternKey = "price.read.pattern";
	private static final String productLinkKey = "product.link.selector";
	private static final String productPriceKey = "product.price.selector";
	private static final String productDetailPageLinkKey = "product.detailpage.linkhref.selector";
	private static final String productDescriptionKey = "product.description.selector";

	Pattern pattern;
	Properties properties;

	/**
	 * @param props should contain css selector key values specified above
	 */
	public GroceryProductScraper(Properties props) {
		this.properties = props;
		pattern = Pattern.compile(props.getProperty(productPricePatternKey));
	}

	/* (non-Javadoc)
	 * @see com.jalalsoft.productscrapper.ProductScraper#getProducts(java.lang.String, int)
	 */
	public ArrayList<Product> getProducts(String productURL, int timeout) throws IOException {


		if(productURL==null || productURL.equals(""))
		{
			productURL= properties.getProperty(productURLKey);
		}
		Document doc = getDocument(productURL, timeout);
		Elements productElements = doc.select(properties.getProperty(productLinkKey));
		Elements prices = doc.select(properties.getProperty(productPriceKey));
		ArrayList<Product> products = new ArrayList<Product>();
		int count = 0;
		Product product = null;
		for (Element link : productElements) {
			String productLink = link.attr(properties.getProperty(productDetailPageLinkKey));
			String title = link.text();
			String pricesText = prices.get(count++).text();
			float unitPrice = findPrice(pricesText);
			String description = getDescription(productLink, timeout);
			String size = getSize(productLink);
			product = new Product(title, description, unitPrice, size);
			products.add(product);

		}

		return products;

	}

	/**
	 * Retrieve the description of the product from the provided url
	 * @param url
	 * @param timeout is connection timeout
	 * @return description of product if found
	 * @throws IOException if there are any connectivity or related issues
	 */
	private String getDescription(String url, int timeout) throws IOException {
		String description = "";

		Document doc = getDocument(url, timeout);
		Elements productDescription = doc.select(properties.getProperty(productDescriptionKey));
		Element descriptionNode = productDescription.get(0);

		if (descriptionNode != null) {
			description = descriptionNode.text();
		}

		return description;

	}

	private String getSize(String link) throws IOException {
		URL url = new URL(link);
		float size = (url.openConnection().getContentLength()) / 1024;

		return String.format("%.1f", size) + "kb";
	}

	// TODO this method shouldn't be public. To make it testable and save time, this is made public for now
	/**
	 * look for a floating numbers and retrieve the first floating number found. If there are multiple found then it will use the first occurrence.
	 * @param priceText
	 * @return
	 */
	public float findPrice(String priceText) {
		float price = 0;

		Matcher matcher = pattern.matcher(priceText);
		if (matcher.find()) {
			price = Float.parseFloat(matcher.group());
		}

		return price;
	}

	// TODO this doesn't need to be public or even doesn't need to be in this class. Re-factor it to improve design. Left it here to save time for now.
	/**
	 * This uses JSoup to retrieve the html document. JSoup is chosen for its tolerance to html format errors and good support for css selectors. 
	 * It's also simpler to use as compared to other alternatives
	 * @param url of target page
	 * @param timeout is connection timeout
	 * @return document
	 * @throws IOException
	 */
	public Document getDocument(String url, int timeout) throws IOException {
		
		Document doc = Jsoup.connect(url).timeout(timeout).get();

		return doc;
	}

}
