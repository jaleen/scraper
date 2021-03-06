package com.jalalsoft.productscrapper;

import java.util.ArrayList;

import com.google.gson.Gson;
/**
 * This class uses Gson to convert the Product objects list to JSON format. Products data is wrapped in an 
 * array named results. Also prices of all products in list are summed and saved in total attribute. Following 
 * is a sample JSON which is generated by this method. 
 *  {
 *  "results":[
 *  {
 *  "title":"Sainsbury's Avocado, Ripe & Ready x2",
 *  "size": "90.6kb",
 *  "unit_price": 1.80,
 *  "description": "Great to eat now - refrigerate at home 1 of 5 a day 1
 *  avocado counts as 1 of your 5..."
 *  },
 *  {
 *  "title":"Sainsbury's Avocado, Ripe & Ready x4",
 *  "size": "87kb",
 *  "unit_price": 2.00,
 *  "description": "Great to eat now - refrigerate at home 1 of 5 a day 1
 *  avocado counts as 1 of your 5..."
 *  }
 *  ],
 *  "total": 3.80
 *  } 
 * @author jdeen
 *
 */
public class ProductsConverToJSON implements ProductConverter {

	/**
	 * Convert list of given products into JSON format and return it as a string. see above for further details
	 * @param products whose attributes data will be converted to JSON type.
	 * @return String which represents JSON format
	 * 
	 * @see com.jalalsoft.productscrapper.ProductsConverToJSON
	 */
	public String convert(ArrayList<Product> products) {

		String json = null; 
		json = new Gson().toJson(new ProductList(products));
		return json;
	}
}
