# ALDIWAN — Restaurant Management System

A desktop restaurant management application built with **Java Swing** and **MySQL**, developed as an Object-Oriented Programming project. ALDIWAN handles staff login, order taking, delivery, credit card payments, and manager analytics — all through a NetBeans GUI project.

## Features

- **Role-based login** — Managers and staff (`user`) log in separately and land on different screens.
- **Manager Dashboard** (`Dash2`) — Live charts (bar & pie, via JFreeChart) for staff counts, payment-type breakdowns, and order categories, plus staff (person) record management: add, update, delete.
- **Menu & Ordering** (`Menu`) — Browse the food menu with images, build an order, and choose a payment type: Cash, Credit, or Takeaway.
- **Delivery / Takeaway** (`DeliveryForm`) — Capture customer name, address, phone, delivery fees, and amount due.
- **Credit Payments** (`CreditForm`) — Record card payments (card number, name on card, expiry date) linked to a customer and staff member.
- **MySQL-backed persistence** — All customers, menu items, orders, payments, staff, and takeaway records are stored in the `aldiwandb` database.

## Project Structure

```
ALDIWAN6/
├── src/aldiwan/          # Java source (.java) and NetBeans form (.form) files
│   ├── LoginFrame.java   # Entry point — authentication
│   ├── Dash2.java        # Manager dashboard & analytics
│   ├── Menu.java         # Ordering screen
│   ├── DeliveryForm.java # Takeaway/delivery details
│   ├── CreditForm.java   # Credit card payment form
│   ├── DBConnection.java # MySQL connection settings
│   ├── PersonClass.java / SessionPerson.java  # Logged-in user session model
│   └── images/           # In-app UI images
├── Images/ & imagesSmall/ # Menu item photos (full-size & thumbnails)
├── build/                 # Compiled classes (NetBeans build output)
├── dist/                  # Packaged runnable JAR + bundled libraries
├── nbproject/             # NetBeans project configuration
├── aldiwandb.sql          # MySQL database schema + seed data
└── build.xml              # Ant build script
```

## Tech Stack

| Layer      | Technology                                  |
|------------|----------------------------------------------|
| UI         | Java Swing (NetBeans GUI Builder)             |
| Charts     | [JFreeChart](https://www.jfree.org/jfreechart/) |
| Database   | MySQL 8 (`aldiwandb`)                         |
| Connector  | MySQL Connector/J                             |
| Build      | Apache Ant / NetBeans project                 |

## Getting Started

### Prerequisites
- Java JDK 8+
- MySQL Server
- NetBeans IDE (recommended) or Apache Ant

### 1. Set up the database
```bash
mysql -u root -p < ALDIWAN6/aldiwandb.sql
```
This creates the `aldiwandb` database with all tables (`person`, `customer`, `menu_items`, `orders`, `paycredit`, `takeaway`) and sample seed data.

### 2. Configure the connection
Update the credentials in `ALDIWAN6/src/aldiwan/DBConnection.java` to match your local MySQL setup:
```java
public static final String URL = "jdbc:mysql://localhost:3306/aldiwandb";
public static final String DBUser = "root";
public static final String DBPassword = "your_password";
```

### 3. Run the project
**Option A — NetBeans:** Open the `ALDIWAN6` folder as a project and run it.

**Option B — Command line:**
```bash
cd ALDIWAN6
ant run
```

**Option C — Prebuilt JAR:**
```bash
java -jar ALDIWAN6/dist/ALDIWAN.jar
```

## Sample Login

The seeded database includes sample accounts you can log in with (see `person` table in `aldiwandb.sql`) — one `Manager` role account and several `user` role accounts.

## License

This project was created for educational purposes as part of an Object-Oriented Programming course.
