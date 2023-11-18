# Práctica 1: Captura de Datos a partir de Fuentes Externas

## Universidad de Las Palmas de Gran Canaria
### Escuela de Ingeniería Informática
#### Grado en Ciencia e Ingeniería de Datos
##### Desarrollo de Aplicaciones para Ciencia de Datos

---

## Descripción

  Este proyecto se conecta con el servicio web: https://openweathermap.org/api , este servicio es una API que contiene datos meteorológicos de cualquier lugar del mundo. Esta práctica llama a la API cada 6 horas para 
  obtener la predicción meteorológica de los próximos 5 días en las 8 Islas Canarias, concretamente a las 12 del mediodía.

  La aplicación se conecta con la API,una vez establecida la conexión, nuestra aplicación toma los datos meteorológicos que le interesan( temperatura, probabilidad de precipitaciones, la humedad,
  nubes y velocidad del viento)a continuación, guarda estos datos y crea una base de datos con 8 tablas(una para cada isla) e inserta en cada una de estas tablas los datos correspondientes para cada isla.
### Ejecución

Para ejecutar la aplicación, se debe proporcionar como argumento la apikey personal para la API de OpenWeatherMap. El usuario también deberá añadir la ruta en la que se le creará la base de datos del parte meteorológico, en la línea 18 de la clase SqliteWeatherStore.

---


## Recursos Utilizados

  Este proyecto ha sido desarrollado en su totalidad mediante InteliJ IDEA. IntelliJ IDEA es un entorno de desarrollo integrado (IDE, por sus siglas en inglés) creado por JetBrains. 
  JetBrains es una empresa que desarrolla herramientas y soluciones para desarrolladores de software. 
  IntelliJ IDEA está diseñado específicamente para el desarrollo de aplicaciones en Java, aunque también ofrece soporte para otros lenguajes de programación como Kotlin, Groovy, Scala y más.

  A lo largo del desarrollo del proyecto se ha usado el sistema de control de versiones Git. Git es
  una herramienta fundamental para el seguimiento de cambios en el código fuente durante el desarrollo de proyectos de software. Todas las versiones del proyecto se encuentran en la rama 'master'.

  En cuanto al procedimiento, la aplicación establece la conexión con la API a través de la librería Jsoup. Jsoup es una libreria Java que proporciona operaciones para trabajar con HTML y XML. 
  Permite extraer y manipular datos, que podrán ser utilizados convenientemente para nuestras necesidades.
  Por otro lado, la aplicación se apoya en la interfaz JDBC(Java Database Connectivity) para todo lo que tiene que ver con la creación y el manejo de la base de datos y sus tablas. JDBC es es una API 
  de Java que proporciona un conjunto de clases e interfaces para permitir que las aplicaciones Java se conecten y accedan a bases de datos. 
  JDBC facilita la interacción entre programas escritos en Java y una amplia variedad de sistemas de gestión de bases de datos.
  
### Herramientas y Tecnologías

- [Git](https://git-scm.com/): Sistema de control de versiones para el seguimiento de cambios en el código fuente durante el desarrollo de proyectos de software.
- [Jsoup](https://jsoup.org/): Librería Java para trabajar con HTML y XML, facilitando la extracción y manipulación de datos.
- [JDBC (Java Database Connectivity)](https://docs.oracle.com/javase/tutorial/jdbc/): API de Java para la conexión y acceso a bases de datos.

---
## Diseño

Este proyecto ha sido desarrollado teniendo en cuenta el patrón de diseño MVC (Modelo-Vista-Controlador) y sus derivados. El patrón MVC ayuda a obtener un software de alto nivel, evitando la interdependencia entre sus capas y promoviendo un alto grado de modularidad y cohesión.

En términos generales, el patrón MVC divide la aplicación en tres componentes principales:

- **Modelo:** Representa la lógica y los datos de la aplicación.
- **Vista:** Es responsable de la presentación y la interfaz de usuario.
- **Controlador:** Maneja las interacciones del usuario y coordina las acciones entre el modelo y la vista.

Los derivados del patrón MVC, como MVVM (Modelo-Vista-ViewModel) y MVP (Modelo-Vista-Presentador), adaptan el enfoque básico para abordar diferentes necesidades y contextos de desarrollo.

A continucacón se muestra la abstracción del proyecto a través de un diagrama de clases. Este diagrama ha sido usado como guía durante todo el desarrollo de la aplicación.

![image](https://github.com/AlejandroDelToroAcosta/Practica1.0/assets/145200194/89f9aef5-82a8-463d-8f34-2f5903b1d03b)



---
## Índice

1. [Descripción](#descripción)
   - [Ejecución](#ejecución)
2. [Recursos Utilizados](#recursos-utilizados)
   - [Herramientas y Tecnologías](#herramientas-y-tecnologías)
3. [Diseño](#diseño)
  
