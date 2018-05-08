# Bioserenity Cloud Technical Test

This applications answers to the requirements as they are described in bioserenity_cloud_data_test.pdf

Based on a mosaic.in file, the application generates a mosaic.out file.

## Getting Started

This project relies on Maven with Java and Spring Boot.

## Prerequisites

You need to have a [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/index.html) and [Maven 3.2+](https://maven.apache.org/download.cgi) in order to use this application.


## Build & Tests & Execution
Apart from Spring Boot's default dependencies, only commons-io dependency is added to the pom.xml file.
Tests have been implemented in a way that they ensure if non valid input file is provided to the application, it will not start. The format of the output file is also tested in order to ensure it fits to the description requirements.
In order to build your project and run all tests execute
```
mvn clean install
```

Execution can now be triggered using the following commands :
```
java -jar target/bioserenity-test-1.jar
```

A successful execution will print logs similar to the following:
```
Total score is :49032. Maximum score is 50000. Scoring percentage is 98.06%
Program executed in 255ms
```
Logs are configured to INFO level per default.

You will then find the mosaic.out file under the path 
```
/src/main/resources/mosaic.out
```
Rebuild your project to clean old output files.

## Versioning

pom.xml is used for versioning.


## Authors
Emmanuel Le Rider


## License

BioSerenity
