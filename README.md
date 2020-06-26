# Code Challenge

Java backend API which provides flights information between the cities of Oporto and Lisbon and vice-versa from the companies TAP and Ryanair.

## **Installation**

### **Requirements**

* [Java 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
* [Maven 3.6.3](https://archive.apache.org/dist/maven/maven-3/3.6.3/binaries/)
* [Lombok](https://projectlombok.org/download)
* [MongoDB](https://www.mongodb.com/)

### **Usage**

Open the project using an IDE of your preference (eg.: IntelliJ or Eclipse) and run the project and wait for completion. Once all depencecies are downloaded and the code is built, open an web browser or an app which makes HTTP GET requests (eg.: Postman) and access the following endpoint in order to make a request:

```bash
http://localhost:8080/flight 
```

See below the **optional** parameters available to send:

Parameters | Values | Requirements
--- | --- | ---
dest | OPO,OPORTO,PORTO,LIS,LISBON,LISBOA  | Not case sensitive
dateFrom | Any date | dd/MM/yyyy
dateTo | Any date | dd/MM/yyyy

### **Testing**

* [SoapUI](https://www.soapui.org/downloads/soapui/)

Path to find the project used to mock external API "/src/test/resources/_SoapUI/Flight-Project-soapui-project.xml".

### **MongoDB Configuration**

* **Host**: localhost
* **Default MongoDB port**: 27017
* **Default MongoDB database to connect**: test

## **Logic**

The logic behind the API can be explained through the following structured layers:

### **Flight Controller**

A controller which receives the user request, save it on a database (MongoDB) and delegates to a service responsible for validating all the information provided.

### **Flight Service**

This service will be responsible for validating the received parameters, if available. In case of invalid parameters it will return to the Controller an exception with an appropriate message. Otherwise, it will delegate the request to another layer that will process the request and return the response accordingly.

### **Processor Request**

This layer will interpretate the parameters and set a structured request to a third party flight API, then receive a response with all data provided and parse that into a list of flights, which will be analyzed and structured back again in a final object to be returned with the final response. In case the third party flight API returns any errors, that will be propagated to the previous layers in order to set an appropriate response.

### **Validations**

Some of the validation scenarios that are contemplated on this API are:

* Destination different of Lisbon or Porto
* Past date
* Date to before date from
* Date format

### **API Assumptions**

* In case no date is provided the API will assume the current date
* In case no destination is provided the API will request both destinations (Lisbon and Porto)

### **API Plus**

This API exposes two other endpoints:

* List all the records of the requests in database: http://localhost:8080/flight/request
* Delete all the records of the requests from database: http://localhost:8080/flight/request/delete

## **License**

[MIT](https://choosealicense.com/licenses/mit/)

