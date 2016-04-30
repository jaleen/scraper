package com.jalalsoft.productscrapper;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.Assert.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

public class RetrieveProductsTest {

	String productURL;
	GroceryProductScraper groceryProductScraper = null;
	private static final String productURLKey = "products.url";
	Properties props = null;
	int timeout;

	@Before
	public void setUp() throws Exception {
		
		props = new ScrapperApp().loadProps("/test.properties");

		productURL = props.getProperty(productURLKey);
		//productURL = "http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/5_products.html";
		groceryProductScraper = new GroceryProductScraper(props);
		timeout = 3000;
	}

	@Test(timeout = 3000)
	public void whenURLisCorrect_ThenShouldConnectAndDownloadDocument() throws IOException {
		String url = "http://www.google.com";
		Document document = groceryProductScraper.getDocument(url, timeout);

		assertNotNull(document);
	}

	@Test(expected = IllegalArgumentException.class, timeout = 100)
	public void whenURLisIncorrect_ThenShouldTimeout() throws IOException {
		String url = "Incorrect url.";
		groceryProductScraper.getDocument(url, timeout);

		fail("should have thrown the exception for bad url.");
	}

	@Test
	public void whenProdcutsAvailable_thenShouldFindProducts() throws IOException {

		ArrayList<Product> products = groceryProductScraper.getProducts(productURL, timeout);

		assertNotNull(products);
		assertEquals("Product count is incorrect.", 7, products.size());

		Product product = products.get(0);

		assertNotNull(product);

		String expectedTitle = "Sainsbury's Apricot Ripe & Ready x5";
		float expectedUnitPrice = 3.50f;
		String actualTitle = product.getTitle();
		float actualUnitPrice = product.getUnitPrice();
		String expectedPageSize = "38.0kb";
		
		assertEquals("Product title is incorrect", expectedTitle, actualTitle);
		assertEquals("Product price is incorrect", expectedUnitPrice, actualUnitPrice,0.01);
		assertEquals(expectedPageSize, product.getSize());
		
		product = products.get(1);

		assertNotNull(product);

		expectedTitle = "Sainsbury's Avocado Ripe & Ready XL Loose 300g";
		expectedUnitPrice = 1.50f;
		actualTitle = product.getTitle();
		actualUnitPrice = product.getUnitPrice();
		
		assertEquals("Product title is incorrect", expectedTitle, actualTitle);
		assertEquals("Product price is incorrect", expectedUnitPrice, actualUnitPrice,0.01);
		assertEquals(expectedPageSize, product.getSize());

	}
	
	@Test
	public void whenProductsAvailable_ThenShouldCreateJSONFormat() throws IOException
	{
		ArrayList<Product> products = groceryProductScraper.getProducts(productURL, timeout);

		ProductsConverToJSON convertor = new ProductsConverToJSON();
		
		String json = convertor.convert(products);
		
		System.out.println(json);
		assertNotNull(json);
	
	}

}
