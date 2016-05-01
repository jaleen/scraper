package com.jalalsoft.productscrapper;

import java.io.IOException;
import java.util.ArrayList;

/**
 * It crawls the provided url and retrieves the product details and then convert these in ArrayList<Product>
 * @author jdeen
 */
public interface ProductScraper {

	/**
	 * Retrieve the products from the given url and return in them in list of product objects
	 * @param productURL from where we need to retrieve the Products
	 * @param timeout is connectivity timeout in millisecs. Actual page download time would be much higher. 
	 * @return list of product objects found on the page. It will return an empty list if no product is found
	 * @throws IOException if connectivity or page download face network or related issues
	 */
	ArrayList<Product> getProducts(String productURL, int timeout) throws IOException;

}
