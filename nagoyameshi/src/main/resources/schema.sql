
CREATE TABLE IF NOT EXISTS categories (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

 CREATE TABLE IF NOT EXISTS users (
     id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
     full_name VARCHAR(50) NOT NULL,
     kana VARCHAR(50) NOT NULL,
     email VARCHAR(255) NOT NULL UNIQUE,
     enabled BOOLEAN NOT NULL,
     password VARCHAR(255) NOT NULL,   
     paid_license BOOLEAN NOT NULL,
     admin BOOLEAN NOT NULL, 
     created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
     updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
     subscription_id VARCHAR(255) 
 );
 
 CREATE TABLE IF NOT EXISTS restaurants (
     id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
     venue_name VARCHAR(50) NOT NULL,  
     image_name VARCHAR(50) NOT NULL,
     description VARCHAR(255) NOT NULL,
     postal_code VARCHAR(50) NOT NULL,
     address VARCHAR(255) NOT NULL,
     opening_hour INT NOT NULL,
     closing_hour INT NOT NULL,
     reservation_capacity INT NOT NULL,
     budget_range INT NOT NULL,
     created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
     updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  
     category_id INT NOT NULL,
     FOREIGN KEY (category_id) REFERENCES categories (id)  
 );
 
 CREATE TABLE IF NOT EXISTS verification_tokens (
     id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
     user_id INT NOT NULL UNIQUE,
     token VARCHAR(255) NOT NULL,        
     created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
     updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
     FOREIGN KEY (user_id) REFERENCES users (id) 
 );
 
  CREATE TABLE IF NOT EXISTS reservations (
     id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
     user_id INT NOT NULL,
     restaurant_id INT NOT NULL,
     reservation_datetime DATETIME NOT NULL,
     reservation_count INT NOT NULL,
     created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
     updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
     FOREIGN KEY (user_id) REFERENCES users (id),
     FOREIGN KEY (restaurant_id) REFERENCES restaurants (id)
 );
 
   CREATE TABLE IF NOT EXISTS reviews (
     id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
     restaurant_id INT NOT NULL,
     user_id INT NOT NULL,
     star_id INT NOT NULL,
     comment VARCHAR(1000) NOT NULL,
     created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
     updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
     FOREIGN KEY (restaurant_id) REFERENCES restaurants (id),
     FOREIGN KEY (user_id) REFERENCES users (id)
 );
 
   CREATE TABLE IF NOT EXISTS favorites (
     id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
     restaurant_id INT NOT NULL,
     user_id INT NOT NULL,
     created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
     updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
     FOREIGN KEY (restaurant_id) REFERENCES restaurants (id),
     FOREIGN KEY (user_id) REFERENCES users (id)
 );
