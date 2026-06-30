CREATE TABLE products (

    id BIGSERIAL PRIMARY KEY,

    name VARCHAR(150) NOT NULL,

    sku VARCHAR(50) UNIQUE NOT NULL,

    barcode VARCHAR(100) UNIQUE,

    description TEXT,

    selling_price DECIMAL(10,2) NOT NULL,

    cost_price DECIMAL(10,2) NOT NULL,

    quantity INTEGER NOT NULL DEFAULT 0,

    minimum_stock INTEGER NOT NULL DEFAULT 5,

    status BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP

);