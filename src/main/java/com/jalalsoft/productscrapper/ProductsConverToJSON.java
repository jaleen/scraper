package com.jalalsoft.productscrapper;

import java.util.ArrayList;

import com.google.gson.Gson;

public class ProductsConverToJSON {

	public String convert(ArrayList<Product> products) {

		String json = null; 
		json = new Gson().toJson(new ProductList(products));
		return json;
	}

}
