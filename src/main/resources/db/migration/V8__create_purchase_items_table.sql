CREATE TABLE purchase_items (
    id BIGSERIAL PRIMARY KEY,

    purchase_id BIGINT NOT NULL,

    product_id BIGINT NOT NULL,

    quantity INTEGER NOT NULL,

    purchase_price DECIMAL(10,2) NOT NULL,

    total_price DECIMAL(12,2) NOT NULL,

    CONSTRAINT fk_purchase_item_purchase
        FOREIGN KEY (purchase_id)
        REFERENCES purchases(id),

    CONSTRAINT fk_purchase_item_product
        FOREIGN KEY (product_id)
        REFERENCES products(id)
);