
---

# Restro PoS Application


Welcome to **Restro PoS**, a user-friendly Point of Sale (PoS) application designed for restaurants. This application helps manage inventory, process sales, and generate detailed sales reports, all while securely storing data in a local database.

## Features

### 1. User Login
To access the application, users must log in with their credentials. This ensures that only authorized personnel can manage inventory, process sales, and view reports.

<img width="896" alt="Screenshot 2024-08-30 at 3 44 02 PM" src="https://github.com/user-attachments/assets/cbb71a4e-2732-40c8-9fbd-d5b1e62170c2">


### 2. Inventory Management
Manage your restaurant's inventory with ease. The Inventory Management section allows you to:
- **Add New Products**: Include new items to your inventory.
- **Update Product Details**: Modify existing product information. (to be implemented)
- **Track Inventory Levels**: Monitor stock levels to prevent shortages. (to be implemented)

<img width="1496" alt="Screenshot 2024-08-30 at 3 46 03 PM" src="https://github.com/user-attachments/assets/a6cdcb02-7b05-4a01-a615-6617bf06e954">
<img width="1496" alt="Screenshot 2024-08-30 at 3 46 10 PM" src="https://github.com/user-attachments/assets/3fb2c0a2-1b39-4a81-bf6d-aac411b08f0b">


### 3. Sell Section
This section is where the selling happens! You can:
- **Add Items to Sell**: Select items from the inventory for sale.
- **Apply Discounts**: Include discounts on applicable items.
- **Print Bills**: Generate and print a bill for the customer.


<img width="1496" alt="Screenshot 2024-08-30 at 3 47 46 PM" src="https://github.com/user-attachments/assets/39f1257b-245c-4bec-a36e-0e8679ef9bfe">

<img width="1496" alt="Screenshot 2024-08-30 at 3 48 31 PM" src="https://github.com/user-attachments/assets/3a1c6948-fdc5-4ca5-90e7-9b33cfdd1657">


### 4. Sell Report
Generate detailed sales reports over a selected time span. This feature allows you to:
- **View Sales Data**: See all transactions within the specified time period.
- **Analyze Trends**: Use the report to identify sales trends. (to be implemented)
- **Export Reports**: Export the report for record-keeping or further analysis. (to be implemented)

<img width="1496" alt="Screenshot 2024-08-30 at 3 49 14 PM" src="https://github.com/user-attachments/assets/48aa5c41-8aa8-4aaa-9315-177c0fff712c">


## Local Database

The Restro PoS application connects to a local database to securely store user credentials, product details, and sales data. The following tables are used:

- **ADMIN**: Stores user credentials.
  - `USERNAME`: The username for login.
  - `PASSWORD`: The encrypted password for login.

- **Product**: Stores information about products in the inventory.
  - `ID`: Unique identifier for each product.
  - `name`: Name of the product.
  - `rate`: Price per unit of the product.

- **SELL**: Stores details about each sale transaction.
  - `ID`: Unique identifier for each sale.
  - `ITEM_QTY`: Quantity of the items sold.
  - `DISCOUNT`: Any discount applied to the sale.
  - `AMOUNT`: Total amount after applying discounts.
  - `PAYMENT`: Payment method used for the sale (e.g., Cash, Card).
  - `DATE`: Date of the sale.
  - `TIME`: Time of the sale.

The local database ensures that all data is securely stored and easily retrievable for generating reports and managing inventory.

## Installation

### Prerequisites
- Java 17 or higher
- JavaFX 17 or higher
- Maven (optional, if you want to build from source)
- A local database (e.g., MySQL, SQLite) configured and connected

### Steps
1. **Clone the repository:**
   ```bash
   git clone https://github.com/yourusername/restro-pos.git
   cd restro-pos
   ```

2. **Configure the Database:**
   Set up your local database with the required tables (`ADMIN`, `Product`, `SELL`). Make sure your application is configured to connect to this database.

3. **Run the application:**
   If you have Maven installed, you can run the application directly from the terminal:
   ```bash
   mvn javafx:run
   ```
   Alternatively, you can build the project and run the JAR file:
   ```bash
   mvn clean package
   java -jar target/restro-pos-1.0.jar
   ```

## Usage
1. **Login** with your credentials.
2. **Manage Inventory** through the Inventory Management section.
3. **Process Sales** in the Sell section and print bills.
4. **Generate Reports** for any time span to analyze your sales performance.

## Contributing
If you'd like to contribute, please fork the repository and use a feature branch. Pull requests are welcome.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact
For more information or queries, feel free to contact us at [Linked In](https://www.linkedin.com/in/somsuvra-ganguly-67b95a12b/).

---
