package com.jalalsoft.productscrapper;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class ProductList {

	@SerializedName("results")
	private ArrayList<Product> products;

	private String total;

	ProductList(ArrayList<Product> products) {
		this.products = products;
		total = calculateTotal(products);

	}

	private String calculateTotal(ArrayList<Product> products) {
		double currentTotal = 0;
		if (products == null) {
			return "";
		}
		for (Product product : products) {
			currentTotal += product.getUnitPrice();
		}
		return String.format("%.2f", currentTotal);
	}

	public String getTotal() {
		return calculateTotal(products);
	}

}
