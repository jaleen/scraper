package com.jalalsoft.productscrapper;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class ProductListTest {

	ProductList productList;

	@Before
	public void setUp() {

	}

	@Test
	public void when0Products_thenTotalShouldbe0() {
		ArrayList<Product> products = new ArrayList<Product>();
		productList = new ProductList(products);

		String actualTotal = productList.getTotal();
		String expectedTotal = "0.00";
		assertEquals("Total should be zero when there are no products", expectedTotal, actualTotal);
	}

	@Test
	public void when1Products_thenTotalShouldbeSameAsProductPrice() {
		ArrayList<Product> products = new ArrayList<Product>();
		Product product = new Product("Avocado", "nice fruit", 12.98f, "12kb");
		products.add(product);
		productList = new ProductList(products);

		String actualTotal = productList.getTotal();
		String expectedTotal = "12.98";
		assertEquals("Total should be same as price of product when there is one product in list", expectedTotal, actualTotal);
	}

	@Test
	public void whenThreAreFewProducts_thenTotalShouldbeSumOfAllProductPrices() {
		ArrayList<Product> products = new ArrayList<Product>();
		Product product = new Product("Avocado", "nice fruit", 12.08f, "12kb");
		products.add(product);
		product = new Product("Avocado1", "nice fruit again", 10.08f, "12kb");
		products.add(product);
		product = new Product("Avocado2", "nice fruit yet again", 5.2f, "12kb");
		products.add(product);
		productList = new ProductList(products);

		String actualTotal = productList.getTotal();
		String expectedTotal = "27.36";
		assertEquals("Total should be same as price of product when there is one product in list", expectedTotal, actualTotal);
	}
	
	@Test
	public void whenTotalIsInSingleFraction_thenTotalShouldContainSecondFractionAsZero() {
		ArrayList<Product> products = new ArrayList<Product>();
		Product product = new Product("Avocado", "nice fruit", 12.10f, "12kb");
		products.add(product);
		product = new Product("Avocado1", "nice fruit again", 10.2f, "12kb");
		products.add(product);
		product = new Product("Avocado2", "nice fruit yet again", 5.2f, "12kb");
		products.add(product);
		productList = new ProductList(products);

		String actualTotal = productList.getTotal();
		String expectedTotal = "27.50";
		assertEquals("Total should have two precision points even if last one is zero", expectedTotal, actualTotal);
	}

}
