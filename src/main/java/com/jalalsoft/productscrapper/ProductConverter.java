package com.jalalsoft.productscrapper;

import java.util.ArrayList;
/**
 * This converter takes a list of Products and convert them in an another format and return it in
 * @author jdeen
 *
 */
public interface ProductConverter {
	
	/**
	 * Convert list of given products into another format and return it as a string
	 * @param products whose attributes data will be converted to a different type.
	 * @return
	 */

	String convert(ArrayList<Product> products);

}