CREATE TABLE purchases (
    id BIGSERIAL PRIMARY KEY,

    supplier_id BIGINT NOT NULL,

    invoice_number VARCHAR(100) UNIQUE,

    purchase_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    total_amount DECIMAL(12,2) NOT NULL,

    status VARCHAR(30) NOT NULL DEFAULT 'COMPLETED',

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP,

    CONSTRAINT fk_purchase_supplier
        FOREIGN KEY (supplier_id)
        REFERENCES suppliers(id)
);