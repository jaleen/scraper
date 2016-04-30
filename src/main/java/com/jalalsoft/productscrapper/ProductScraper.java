package com.jalalsoft.productscrapper;

import java.io.IOException;
import java.util.ArrayList;

public interface ProductScraper {

	ArrayList<Product> getProducts(String productURL, int timeout) throws IOException;

}
