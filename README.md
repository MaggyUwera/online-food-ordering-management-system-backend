# Online Food Ordering Management System – Backend

**Student:** Uwera Maggy
**Student ID:** 27519
**Project:** Online Food Ordering Management System

A Spring Boot REST API that lets customers order meals for home delivery. A customer places a meal order that is delivered by a courier; each courier rides one motorbike and covers several delivery zones such as Remera or Kicukiro. An order moves from PLACED to ON_THE_WAY and ends DELIVERED or CANCELLED. Once an order is delivered, reviews of the meal can be recorded.

## Technologies

Java 25, Spring Boot 4.1.1 (Spring Web MVC, Spring Data JPA, Validation), Hibernate 7, PostgreSQL, Maven (wrapper included), Postman.

## Steps to run the API

1. Install **JDK 25** and **PostgreSQL**.
2. Create the database in PostgreSQL:
   ```sql
   CREATE DATABASE food_ordering_db;
   ```
3. Open a terminal in the project folder, point `JAVA_HOME` to JDK 25 and give your PostgreSQL password (Windows PowerShell):
   ```powershell
   $env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-25.0.4.101-hotspot"
   $env:DB_PASSWORD = "your_postgres_password"
   .\mvnw.cmd spring-boot:run
   ```
   On Mac/Linux: `export DB_PASSWORD=your_postgres_password` and then `./mvnw spring-boot:run`.
4. The API runs on `http://localhost:8080`. The tables are created automatically (`spring.jpa.hibernate.ddl-auto=update`).
5. Import `online-food-ordering-management-system-midterm.postman_collection.json` into Postman. Enter the seed data with the save requests in this order: delivery zones, motorbikes, couriers, customers, meal orders, reviews.

## Domain model

| Entity | Table | Relationships |
|---|---|---|
| DeliveryZone | delivery_zone | @ManyToMany(mappedBy = "deliveryZones") couriers, marked @JsonIgnore |
| Motorbike | motorbike | none |
| Courier | courier | @ManyToOne motorbike (motorbike_id); @ManyToMany deliveryZones (join table courier_delivery_zone) |
| Customer | customer | none; phone is unique |
| MealOrder | meal_order | @ManyToOne customer (customer_id), @ManyToOne courier (courier_id); status stored as STRING |
| Review | review | @ManyToOne mealOrder (meal_order_id) |

`MealOrderStatus`: PLACED, ON_THE_WAY, DELIVERED, CANCELLED.

## Endpoints

| ID | Method | Endpoint | Type |
|---|---|---|---|
| A2 | POST / GET / PUT / DELETE | `/api/{path}/save`, `/all`, `/{id}`, `/update/{id}`, `/delete/{id}` for delivery-zones, motorbikes, couriers, customers, meal-orders | CRUD |
| B1 | GET | `/api/customers/by-last-name?lastName=uwera` | Derived |
| B2 | GET | `/api/meal-orders/by-status?status=PLACED` | Derived |
| B3 | GET | `/api/meal-orders/between?start=2026-10-01&end=2026-10-31` | Derived |
| C1 | GET | `/api/couriers/by-delivery-zone?name=remera` | JPQL |
| C2 | GET | `/api/motorbikes/free` | JPQL |
| C3 | GET | `/api/delivery-zones/unused` | JPQL |
| C4 | GET | `/api/customers/of-courier/{courierId}` | JPQL |
| D1 | POST | `/api/meal-orders/save` | Business rule + derived |
| D2 | PATCH | `/api/meal-orders/{id}/status?status=ON_THE_WAY` | Business rule |
| D3 | PUT | `/api/couriers/{courierId}/delivery-zones/{deliveryZoneId}` | Many-to-many |
| D4 | PATCH | `/api/couriers/{courierId}/motorbike?bikeNumber=4` | Business rule + derived |
| E1 | POST | `/api/reviews/save?mealOrderId=...` | New entity + rule |
| E2 | GET | `/api/reviews/customer/{phone}` | JPQL |
| E3 | DELETE | `/api/reviews/delete/{id}` and `/api/meal-orders/delete/{id}` | CRUD + derived |
| F1 | GET | `/api/meal-orders/stats/by-status` | JPQL GROUP BY |
| F2 | PATCH | `/api/meal-orders/cancel-day?courierId=...&date=2026-10-20` | JPQL UPDATE |
| Bonus | GET | `/api/dashboard/summary` | count, countBy, reuses C2 |
| Bonus | GET | `/api/meal-orders/page?page=0&size=5&sort=orderDate,desc` | Pageable |

## Seed data

4 delivery zones (Remera, Kimironko, Kicukiro, and Nyamirambo, which nobody holds), 4 motorbikes (motorbike 4 has no courier), 3 couriers, 5 customers (two with the last name Uwera; main phone 0781234567), and 10 meal orders in October and November 2026 using all four statuses.