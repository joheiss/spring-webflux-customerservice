DROP TABLE IF EXISTS portfolio_items;
DROP TABLE IF EXISTS trading_customers;

CREATE TABLE trading_customers (
      id SERIAL PRIMARY KEY,
      name VARCHAR(50),
      balance int
);

CREATE TABLE portfolio_items (
    id SERIAL PRIMARY KEY,
    customer_id int,
    ticker VARCHAR(10),
    quantity int,
    foreign key (customer_id) references trading_customers(id)
);

insert into trading_customers(name, balance)
values
    ('Sam', 10000),
    ('Mike', 10000),
    ('John', 10000);