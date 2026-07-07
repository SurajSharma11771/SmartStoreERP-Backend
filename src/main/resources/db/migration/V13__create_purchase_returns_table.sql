CREATE TABLE purchase_returns (
    id BIGSERIAL PRIMARY KEY,

    purchase_id BIGINT NOT NULL,

    product_id BIGINT NOT NULL,

    quantity INTEGER NOT NULL,

    reason VARCHAR(255),

    return_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_purchase_return_purchase
        FOREIGN KEY (purchase_id)
        REFERENCES purchases(id),

    CONSTRAINT fk_purchase_return_product
        FOREIGN KEY (product_id)
        REFERENCES products(id)
);