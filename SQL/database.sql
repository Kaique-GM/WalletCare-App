-- Create a new database named 'walletcaresql'
CREATE DATABASE walletcaresql;

-- Select the 'walletcaresql' database for use
use walletcaresql;

-- Create the 'users' table to store user information
CREATE TABLE users( 
    id INT AUTO_INCREMENT PRIMARY KEY, -- 'id' is an auto-incrementing primary key for the user
    username VARCHAR(50) NOT NULL UNIQUE, -- 'username' is a unique identifier for the user
    password_hash VARCHAR(255) NOT NULL -- 'password_hash' stores the user's hashed password
);

-- Create the 'months' table to store month data for each user
CREATE TABLE months (
    id_month INT AUTO_INCREMENT PRIMARY KEY, -- 'id_month' is an auto-incrementing primary key
    month_name VARCHAR(20) NOT NULL UNIQUE, -- 'month_name' stores the name of the month (e.g., 'January')
    yr INT NOT NULL, -- 'yr' stores the year (e.g., 2024)
    total_balance DECIMAL(10, 2), -- 'total_balance' stores the total balance for the month
    id_user INT NOT NULL, -- 'id_user' is the foreign key referencing the user associated with the month
    FOREIGN KEY (id_user) REFERENCES users(id) -- Link 'id_user' to the 'users' table
);

-- Create the 'categories' table to store category names for incomes and expenses
CREATE TABLE categories (
    id_category INT AUTO_INCREMENT PRIMARY KEY, -- 'id_category' is an auto-incrementing primary key
    category_name VARCHAR(20) NOT NULL UNIQUE -- 'category_name' stores the name of the category (e.g., 'Income', 'Expense Fixed', 'Expense Variable')
);

-- Create the 'incomes' table to store income entries
CREATE TABLE incomes (
    id_income INT AUTO_INCREMENT PRIMARY KEY, -- 'id_income' is an auto-incrementing primary key
    description_i VARCHAR(50) NOT NULL, -- 'description_i' stores a description for the income (e.g., 'Salary')
    value_i DECIMAL(10, 2), -- 'value_i' stores the value of the income
    id_month INT NOT NULL, -- 'id_month' is a foreign key referencing the 'months' table
    id_category INT NOT NULL, -- 'id_category' is a foreign key referencing the 'categories' table
    FOREIGN KEY (id_month) REFERENCES months(id_month), -- Link 'id_month' to the 'months' table
    FOREIGN KEY (id_category) REFERENCES categories(id_category) -- Link 'id_category' to the 'categories' table
);

-- Create the 'expenses' table to store expense entries
CREATE TABLE expenses (
    id_expenses INT AUTO_INCREMENT PRIMARY KEY, -- 'id_expenses' is an auto-incrementing primary key
    description_e VARCHAR(50) NOT NULL, -- 'description_e' stores a description for the expense (e.g., 'Rent')
    value_e DECIMAL(10, 2), -- 'value_e' stores the value of the expense
    id_month INT NOT NULL, -- 'id_month' is a foreign key referencing the 'months' table
    id_category INT NOT NULL, -- 'id_category' is a foreign key referencing the 'categories' table
    FOREIGN KEY (id_month) REFERENCES months(id_month), -- Link 'id_month' to the 'months' table
    FOREIGN KEY (id_category) REFERENCES categories(id_category) -- Link 'id_category' to the 'categories' table
);

-- Insert the categories into the 'categories' table
INSERT INTO categories (category_name) 
VALUES ('Income'); -- 'Income' category for income entries

-- Insert the 'Expense Fixed' category into the 'categories' table
INSERT INTO categories (category_name) 
VALUES ('Expense Fixed'); -- 'Expense Fixed' category for fixed expenses (e.g., rent, utilities)

-- Insert the 'Expense Variable' category into the 'categories' table
INSERT INTO categories (category_name) 
VALUES ('Expense Variable'); -- 'Expense Variable' category for variable expenses (e.g., transport, entertainment)

    
