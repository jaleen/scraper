package com.jalalsoft.productscrapper;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GroceryProductScraper implements ProductScraper {

	private static final String productURLKey = "products.url";
	private static final String productPricePatternKey = "price.read.pattern";
	private static final String productLinkKey = "product.link.selector";
	private static final String productPriceKey = "product.price.selector";
	private static final String productDetailPageLinkKey = "product.detailpage.linkhref.selector";
	private static final String productDescriptionKey = "product.description.selector";

	Pattern pattern = Pattern.compile("\\d+(?:\\.\\d+)?");
	Properties properties;

	public GroceryProductScraper(Properties props) {
		properties = props;
	}

	public ArrayList<Product> getProducts(String productURL, int timeout) throws IOException {

		Validate.isTrue(productURL != null);

		Document doc = getDocument(productURL, timeout);
		Elements productElements = doc.select(".productInfo>h3>a");
		Elements prices = doc.select(".pricePerUnit");
		ArrayList<Product> products = new ArrayList<Product>();
		int count = 0;
		Product product = null;
		for (Element link : productElements) {
			String productLink = link.attr("abs:href");
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

	private String getDescription(String url, int timeout) throws IOException {
		String description = "";

		Document doc = getDocument(url, timeout);

		// "#information>productcountent htmlcontent>h3+productText"
		Elements productDescription = doc.select("productcontent > htmlcontent > h3+.productText:nth-child(2)");

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

	public float findPrice(String priceText) {
		float price = 0;

		Matcher matcher = pattern.matcher(priceText);

		if (matcher.find()) {
			price = Float.parseFloat(matcher.group());
		}

		return price;
	}

	public static void main(String args[]) throws IOException {
		String productURL = "http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/5_products.html";

		GroceryProductScraper scraper = new GroceryProductScraper(new ScrapperApp().loadProps("application.properties"));

		String priceText = "&pound3.50/unit";
		float price = scraper.findPrice(priceText);
		System.out.println(priceText + ":" + price);

		priceText = "&pound1.50/unit";
		price = scraper.findPrice(priceText);
		System.out.println(priceText + ":" + price);

	}

	public Document getDocument(String url, int timeout) throws IOException {
		Document doc = Jsoup.connect(url).timeout(timeout).get();

		return doc;
	}

}
