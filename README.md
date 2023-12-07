# Practice 2: Data Incorporation Into the Architecture System

## University of Las Palmas de Gran Canaria
### School of Computer Engineering
#### Bachelor's Degree in Data Science and Engineering
##### Development of Applications for Data Science

---

## Description

This project connects to the web service: https://openweathermap.org/api. This service is an API that contains weather data for any location worldwide. The application calls the API every 6 hours to obtain the weather forecast for the next 5 days on the 8 Canary Islands, specifically at noon. Additionally, this application establishes a connection with an ActiveMQ broker to send the weather data obtained from the previous API (temperature, probability of precipitation, humidity, clouds, and wind speed). This is done by the "prediction-provider" module.

The "event-store-builder" module is designed to connect the application to the broker's topic containing the weather data. It creates a durable subscriber to that topic. The software then receives the data stored in the topic and creates a file inside a directory with the received information.

### Execution

To execute the application, in the "prediction-provider" module, the user must add their personal apiKey as arguments to connect with the openweathermap API, along with the URL to connect with the broker and the name of the topic they want to subscribe to. In the "event-store-builder" module, the user must add the URL to connect with the broker, the name of the topic, and the path of the directory where the information will be stored as arguments, in that precise order.

---


## Resources

  This project has been developed entirely using IntelliJ IDEA. IntelliJ IDEA is an Integrated Development Environment (IDE) created by JetBrains. JetBrains is a company that develops tools and solutions for software developers. IntelliJ IDEA is specifically designed for Java application development, although it also offers support for other programming languages such as Kotlin, Groovy, Scala, and more.

Throughout the project's development, the Git version control system has been used. Git is a fundamental tool for tracking changes in source code during software project development. All project versions are stored in the 'master' branch.

Regarding the procedure, the application establishes the connection with the API through the Jsoup library. Jsoup is a Java library that provides operations for working with HTML and XML. It allows for the extraction and manipulation of data, which can be conveniently used for our needs. 

This application relies in the broker concept, in particular in ActiveMQ. ActiveMQ is an open-source messaging system based on the Java Message Service (JMS) protocol. It operates as a message broker, enabling asynchronous communication between applications in a distributed network.
  
### Tools 

- [Git](https://git-scm.com/): Version control system for tracking changes in source code during software project development.
- [Jsoup](https://jsoup.org/): Java library for working with HTML and XML, facilitating data extraction and manipulation
- [ActiveMQ]( https://activemq.apache.org): Open source, multi-protocol, Java-based message broker

---
## Design

This project has been developed considering the Model-View-Controller (MVC) design pattern and its derivatives. The MVC pattern helps achieve high-level software, avoiding interdependence between its layers and promoting a high degree of modularity and cohesion.

In general terms, the MVC pattern divides the application into three main components:

Model: Represents the logic and data of the application.
View: Is responsible for the presentation and user interface.
Controller: Handles user interactions and coordinates actions between the model and the view.
Derivatives of the MVC pattern, such as MVVM (Model-View-ViewModel) and MVP (Model-View-Presenter), adapt the basic approach to address different needs and development contexts.

Below is the abstraction of the project through class diagram. These diagrams has been used as a guide throughout the development of the application.

![image](https://github.com/AlejandroDelToroAcosta/Practica1.0/assets/145200194/89f9aef5-82a8-463d-8f34-2f5903b1d03b)



---
## Index

1. [Description](#description)
   - [Execution](#execution)
2. [Resources](#resources)
   - [Tools](#tools)
3. [Design](#design)
  
