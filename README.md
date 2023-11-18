# Practice 1: Data Capture from External Sources

## University of Las Palmas de Gran Canaria
### School of Computer Engineering
#### Bachelor's Degree in Data Science and Engineering
##### Development of Applications for Data Science

---

## Description

 This project connects to the web service: https://openweathermap.org/api. This service is an API that contains weather data for any location worldwide. This practice calls the API every 6 hours to obtain the weather forecast for the next 5 days on the 8 Canary Islands, specifically at noon.

The application connects to the API, and once the connection is established, it retrieves the relevant weather data (temperature, probability of precipitation, humidity, clouds, and wind speed). Subsequently, it saves this data and creates a database with 8 tables (one for each island) and inserts the corresponding data for each island into these tables.
### Execution

To execute the application, the user must provide their personal API key for the OpenWeatherMap API as an argument. The user should also add the path where the weather report database will be created on line 18 of the SqliteWeatherStore class.

---


## Resources

  This project has been developed entirely using IntelliJ IDEA. IntelliJ IDEA is an Integrated Development Environment (IDE) created by JetBrains. JetBrains is a company that develops tools and solutions for software developers. IntelliJ IDEA is specifically designed for Java application development, although it also offers support for other programming languages such as Kotlin, Groovy, Scala, and more.

Throughout the project's development, the Git version control system has been used. Git is a fundamental tool for tracking changes in source code during software project development. All project versions are stored in the 'master' branch.

Regarding the procedure, the application establishes the connection with the API through the Jsoup library. Jsoup is a Java library that provides operations for working with HTML and XML. It allows for the extraction and manipulation of data, which can be conveniently used for our needs. Additionally, the application relies on the JDBC (Java Database Connectivity) interface for everything related to the creation and management of the database and its tables. JDBC is an API for Java that provides a set of classes and interfaces to allow Java applications to connect to and access databases. JDBC facilitates interaction between programs written in Java and a wide variety of database management systems.
  
### Tools 

- [Git](https://git-scm.com/): Version control system for tracking changes in source code during software project development.
- [Jsoup](https://jsoup.org/): Java library for working with HTML and XML, facilitating data extraction and manipulation
- [JDBC (Java Database Connectivity)](https://docs.oracle.com/javase/tutorial/jdbc/): Java API for connecting to and accessing databases.

---
## Design

This project has been developed considering the Model-View-Controller (MVC) design pattern and its derivatives. The MVC pattern helps achieve high-level software, avoiding interdependence between its layers and promoting a high degree of modularity and cohesion.

In general terms, the MVC pattern divides the application into three main components:

Model: Represents the logic and data of the application.
View: Is responsible for the presentation and user interface.
Controller: Handles user interactions and coordinates actions between the model and the view.
Derivatives of the MVC pattern, such as MVVM (Model-View-ViewModel) and MVP (Model-View-Presenter), adapt the basic approach to address different needs and development contexts.

Below is the abstraction of the project through a class diagram. This diagram has been used as a guide throughout the development of the application.

![image](https://github.com/AlejandroDelToroAcosta/Practica1.0/assets/145200194/89f9aef5-82a8-463d-8f34-2f5903b1d03b)



---
## Index

1. [Description](#description)
   - [Execution](#execution)
2. [Resources](#resources)
   - [Tools](#tools)
3. [Design](#design)
  
