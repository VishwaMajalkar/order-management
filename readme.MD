Order Management System
API Specifications:

    You can access swagger page here: http://localhost:9001/order/swagger-ui.html#/

Installing Application

mvn clean install

Run Application

mvn spring-boot:run

Main Application

Class: com.vish.example.order.main.OrderManagementApplication

Model Entity

Class: com.vish.example.order.beans.OrderBook

Controller

package containing class with REST end-points for clients to interact with Order Management System

Class: com.vish.example.order.controller.OrderController

Running the Unit tests

mvn test

Data Persistence 
 
Gemfire Cache Region - "OrderBook"

