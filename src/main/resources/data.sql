-- DEV data
INSERT INTO my_schema.price_tracking (id, created_at, active, link, product_name, desired_price) VALUES ('1', '2025-01-19 19:54:28.026342+00', 'true', 'https://www.arket.com/en_eur/women/jackets-coats/product.shawl-collar-wool-jacket-grey.1195460001.html', 'Shawl-Collar Wool Jacket', '50.0');
INSERT INTO my_schema.price_tracking (id, created_at, active, link, product_name, desired_price) VALUES ('2', '2025-01-19 19:54:29.026342+00', 'false', 'https://www.arket.com/en_eur/women/tops/product.lily-lightweight-t-shirt-black.0630665001.html', 'LILY Lightweight T-Shirt', '5.00');

INSERT INTO my_schema.notification_settings (id, email) VALUES ('1', 'dario.mauri9@gmail.com');

-- Adjust the auto-increment value to avoid collisions
ALTER TABLE my_schema.price_tracking ALTER COLUMN id RESTART WITH 3;
