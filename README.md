# ING - City - ATMs 
Java web application provide a REST API to create a list of ING ATMs in a given Dutch
city and return a well formed JSON response
The web application should invoke an external service to gather a super set of the
data: https://www.ing.nl/api/locator/atms/


## Business assumption covered by exist code:

1) 3rd party API is return bad format we exclude the bad part at start to not stop, but this is decision should take and raise a flag.
2) If search for City not exist in the 3rd party response, return error message with response not found
3) Other 3rd party Errors out of our scope.
4) No cash happen in the application.

## Provided API:

1) Get city atms which return all city available atms
```
curl -X GET --header 'Accept: application/json' 'http://localhost:8060/atms/all/CITY_NAME'
```
2) Get city atms size which return count of all city available atms
```
curl -X GET --header 'Accept: application/json' 'http://localhost:8060/atms/size/CITY_NAME'
```
3) Get city atms pagination
```
curl -X GET --header 'Accept: application/json' 'http://localhost:8060/atms/CITY_NAME?pageNumber=1&pageSize=10'
```

**System solution:**

1) Get city name from path variable parameter. 
2) Send Rest request to external service using rest template.
3) Remove wrong start from the response.
4) Convert adapted response to list.
5) Using streams API filter returned list based on city name.
6) Return response to user.
7) Code coverage is 76% classes, 60% lines due to configuration classes.
8) POM support war, jar embedded tomcat, jar embedded jetty.

**Swagger UI:**
```
localhost:8060/swagger-ui.html
```
provide easy way to test for development

**User UI:**
```
Login page jar case: http://localhost:8060/index.html
Login page war case: http://localhost:8060/ing-atms/index.html
User name: admin
Password: admin
```

**Future Add**

1) Use Apache Camel instead of Rest-template.
2) Can set each deployment configuration war, jar tomcat, jar Jetty on separate branches.
3) Update Wrapper according to 3rd party API change.

## Development requirement:

1) JDK 8
2) MySql

## Development hint:
For change between jar and war packaging
change in pom comments for packing and server

## Deployment requirement:

**Executable Jar contain embedded server**

1.1) JRE 8
1.2) MySql

**War on tomcat server**

2.1) JRE 8
2.2) MySql
2.3) Tomcat server

## Deployment

**Executable Jar contain embedded server**

1.1)change properties file [file next jar (application.properties)] data for DB username and password as per your sql connection
```
spring.datasource.url= in case you need
spring.datasource.username=
spring.datasource.password=
```
1.2) run war through command

```java -jar ing-atms-1.0.jar```

**War on tomcat server**

2.1)Open war as rar and navigate to application.properties [/WEB_INF/classes/application.properties] and change below setting
```
spring.datasource.url= in case you need
spring.datasource.username=
spring.datasource.password=
```
2.2) Save the changes
2.3) Change the name of war to be [ing-atms]
2.4) Deploy war on tomcat
2.5) Access links as before but replace
```
[machine IP]= with your machine IP in case not local host
[port number]8060= with your server port ex: 8080
/=/ing-atms/

EX API: http://localhost:8080/ing-atms/atms/all/CITY_NAME
EX home page: http://localhost:8080/ing-atms/index.html
```

## Logging

1) Log file create next to jar.
