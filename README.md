#Introduction
This is a quick test to scrape a webpage and download its products and then print the product details in the JSON format. It uses Junit and Java 1.8. It also uses JSoup for scraping the webpage. [JSoup](https://jsoup.org/) is selected for its ease of use, it's tolerance for html structure errors and it's good support for css selectors. Implementation assumes certain structure of html. If html structure is changed then you have to provide the new css selectors in application.properties file which is currently located in  resources folder. These selectors are explained in javadoc of GroceryProductScraper.java

#How to Build and Execute
Code is tested with Java 1.8.0_73 and Maven 3.3.9. Other similar recent versions are likely to work. After cloning the respository use following commands. 

$mvn clean package
and then
$java -jar target\scrapper-0.0.1-SNAPSHOT.jar

or 
java -jar target\scrapper-0.0.1-SNAPSHOT.jar {fully qualified url to the products page}

You can copy paste the JSON into this [JSON Viewer](http://jsonviewer.stack.hu/) for ease of use.

