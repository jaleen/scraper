package com.jalalsoft.productscrapper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

	@Expose
	private String title;
	@Expose
	private String size;
	
	@Expose
	@SerializedName("unit_price")
	private float unitPrice;
	@Expose
	private String description;

	public Product(String title, String description, float unitPrice, String size) {
		this.size = size;
		this.title = title;
		this.unitPrice = unitPrice;
	}

	
	public String getTitle() {
		return title;
	}

	
	public String getSize() {
		return size;
	}

	public float getUnitPrice() {
		return unitPrice;
	}

	public String getDescription() {
		return description;
	}

}
