INSERT INTO product(name, price) VALUES ('Soup', 0.65);
INSERT INTO product(name, price) VALUES ('Bread', 0.80);
INSERT INTO product(name, price) VALUES ('Milk', 1.30);
INSERT INTO product(name, price) VALUES ('Apples', 1.00);

INSERT INTO offer(product_name, description, discount, rule) VALUES ('Apples', 'Apples 10% off', 0.10, null);
INSERT INTO offer(product_name, description, discount, rule) VALUES ('Bread', 'Buy 2 soups and get bread half price', 0.50, 'getQuantity("Soup") >= 2');