package com.jalalsoft.productscrapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Hello world!
 *
 */
public class ScrapperApp 
{
    public static void main( String[] args ) throws IOException
    {
 
    	String productListUrl = "";
    	if(args.length>0)
    	{
    		productListUrl = args[0];
    	}
        Properties prop = loadProps("application.properties");
        
        ProductScraper scrapper = new GroceryProductScraper(prop);
        
        ArrayList<Product> products = scrapper.getProducts(productListUrl, 3000);
        String json = new ProductsConverToJSON().convert(products);
        System.out.println(json);
        
    }
    
    public static Properties loadProps(String fileName) throws IOException
    {
    	Properties prop = new Properties();
    	try(InputStream in = ScrapperApp.class.getClass().getResourceAsStream(fileName)){
    		prop.load(in);
    	}
    	
    	return prop;
    }
}
