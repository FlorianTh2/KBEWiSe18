# Java Backend Development
Repository to group the results made in the university-course "Komponentenbasiert Entwicklung" (eng: component-based development). The goal was to build step by step a backend to retrieve data for `User`, `Song` and a `Songlist`.

Collaborator: [RespectableRuessel](https://github.com/RespectableRuessel)

## Learned
  - Reflection as a base for Framworks
    - ./runMeRunner
  - Postgres-DB
  - self-maintained authentication
  - Framworks in the following setup:
 
| Specification  | Implementation | Folder/Project |
| ------------- | ------------- |--------------|
| Servlet-Container-API| Tomcat9|-|
| Servlet-API|HttpServlet| ./echoServlet|
| Jax-RS  | Jersey, RestEasy  | ./contactsJAXRS|
| CDI, -> DependencyBinder with Inject  | Weld, -> HK2 (here) |./contactsJAXRS|
| JPA | Hibernate| ./songsRX|
|"spring"| spring-Boot|-|


## Prerequisites
 - Tomcat with the environment-variable: CATALINA_HOME
 - mvn
 - psql (only for one project)

## Setup
 - ./runMeRunner
    - mvn clean package; start resulting jar with java -jar path/to/jar
 - ./echoServlet
    - mvn clean package; move resulting .war to tomcat
 - ./contactsJAXRS
    - mvn clean package; move resulting .war to tomcat
 - ./songsRX
    - mvn clean package; move resulting .war to tomcat
## TL;DR
--
## Build with
- Maven - Dependency-Manager
## Acknowledgements
 - thanks to my awesome collaborator: [RespectableRuessel](https://github.com/RespectableRuessel)

