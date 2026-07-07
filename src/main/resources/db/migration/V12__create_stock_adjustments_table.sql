CREATE TABLE stock_adjustments (
    id BIGSERIAL PRIMARY KEY,

    product_id BIGINT NOT NULL,

    adjustment_type VARCHAR(20) NOT NULL,

    quantity INTEGER NOT NULL,

    reason VARCHAR(255),

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_stock_adjustment_product
        FOREIGN KEY (product_id)
        REFERENCES products(id)
);