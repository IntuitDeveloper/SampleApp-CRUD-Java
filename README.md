[![Rate your Sample](views/Ratesample.png)][ss1][![Yes](views/Thumbup.png)][ss2][![No](views/Thumbdown.png)][ss3]

# SampleApp-CRUD-Java
SampleApp-CRUD-Java

<p>Welcome to the Intuit Developer's Java Sample App for CRUD operations.</p>
<p>This sample app is meant to provide working examples of how to integrate your app with the Intuit Small Business ecosystem. Specifically, this sample application demonstrates the following:</p>

<ul>
	<li>Create, Read, Query, Update, Delete, Void entities.</li>
	<li>All operations are performed using QuickBooks Java SDK.</li>
</ul>

<p>Please note that while these examples work, features not called out above are not intended to be taken and used in production business applications. In other words, this is not a seed project to be taken cart blanche and deployed to your production environment.</p>  

<p>For example, certain concerns are not addressed at all in our samples (e.g. security, privacy, scalability). In our sample apps, we strive to strike a balance between clarity, maintainability, and performance where we can. However, clarity is ultimately the most important quality in a sample app.</p>

<p>Therefore there are certain instances where we might forgo a more complicated implementation (e.g. caching a frequently used value, robust error handling, more generic domain model structure) in favor of code that is easier to read. In that light, we welcome any feedback that makes our samples apps easier to learn from.</p>

## Table of Contents

* [Requirements](#requirements)
* [First Use Instructions](#first-use-instructions)
* [Running the code](#running-the-code)
* [Project Structure](#project-structure)


## Requirements

In order to successfully run this sample app you need a few things:

1. Java 1.8
2. A [developer.intuit.com](http://developer.intuit.com) account
3. An app on [developer.intuit.com](http://developer.intuit.com) and the associated app token, consumer key, and consumer secret.
4. One sandbox company, connect the company with your app and generate the oauth tokens.
5. QuickBooks Java SDK, download from [here](https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.intuit.quickbooks-online%22) (see instructions in "Running the code" section on how to include it) 

## First Use Instructions

1. Clone the GitHub repo to your computer
2. Import the project in Eclipse or any other IDE of your choice
3. In [`config.properties`](src/main/resources/config.properties), set oauth.type as 1 or 2 depending on type of app you have. For OAuth2 apps set value as 2.
4. For OAuth2 apps, fill in the [`config.properties`](src/main/resources/config.properties) file values (realmid, oauth2.accessToken).
5. For OAuth1 apps, fill in the [`config.properties`](src/main/resources/config.properties) file values (realmId, app token, consumer key, consumer secret, access token key, access token secret). 
5. Run maven install.

Note: If you do not want to use maven, just import the project and add the jars to your project externally.

## Running the code

This app is directed to provide individual sample code for CRUD operations for various QBO entities.
Each class has a main method that can be run individually.

Steps described below is to run the class for creating a customer in Eclipse IDE.

1. Go to CustomerCreate.java in package com.intuit.developer.sampleapp.crud.entities.customer
2. Right click the file and Run as Java application
3. On the console you'll see the log being generated with the new customer id.

Follow similar steps for other classes.

Notes: 

1. The sample code has been implemented for US locale company, certain fields may not be applicable for other locales or minor version. Care should be taken to handle such scenarios separately.
2. Before running AttachableUpload sample, update the path of the pdf that you wish to upload to point to your local directory. 

## Project Structure
 **Standard Java coding structure is used for the sample app**

* Java code for CRUD operations are located under [`entities`](src/main/java/com/intuit/developer/sampleapp/crud/entities) directory for each entitiy
* Java code for Helper Classes are located under [`helper`](src/main/java/com/intuit/developer/sampleapp/crud/helper) directory for each entitiy
* Java code for QBO DataService object are located under [`qbo`](src/main/java/com/intuit/developer/sampleapp/crud/qbo) directory 
* Config files are located in the [`resources`](src/main/resources) directory

[ss1]: #
[ss2]: https://customersurveys.intuit.com/jfe/form/SV_9LWgJBcyy3NAwHc?check=Yes&checkpoint=SampleApp-CRUD-Java&pageUrl=github
[ss3]: https://customersurveys.intuit.com/jfe/form/SV_9LWgJBcyy3NAwHc?check=No&checkpoint=SampleApp-CRUD-Java&pageUrl=github
