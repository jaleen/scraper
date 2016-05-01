#Introduction
This maven based project is a quick test to scrape a webpage and download any products listed on the page. After downloading  html it looks for product details and print on system console in JSON format. It uses Junit and Java 1.8. It also uses JSoup for scraping the webpage. [JSoup](https://jsoup.org/) is selected over otehr options for its ease of use, it's tolerance to errors in html structure and it's good support for css selectors. If html structure is changed then you can provide the new css selectors in application.properties file which is currently located in resources folder. These selectors are explained in javadoc of GroceryProductScraper.java

#How to Build and Execute
Code is tested with Java 1.8.0_73 and Maven 3.3.9. Other similar recent versions are likely to work. After cloning the respository, you can use following commands: 

		$mvn clean package
	
and then:

		$java -jar target\scrapper-0.0.1-SNAPSHOT.jar

or: 

		$java -jar target\crapper-0.0.1-SNAPSHOT.jar > products.json

You can provide different url for different environments using this:

		java -jar target\scrapper-0.0.1-SNAPSHOT.jar {fully qualified url to the products page}

You can copy paste the JSON into this [JSON Viewer](http://jsonviewer.stack.hu/) for ease of use.

