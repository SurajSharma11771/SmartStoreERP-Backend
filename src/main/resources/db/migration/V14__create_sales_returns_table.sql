CREATE TABLE sales_returns (
    id BIGSERIAL PRIMARY KEY,

    sale_id BIGINT NOT NULL,

    product_id BIGINT NOT NULL,

    quantity INTEGER NOT NULL,

    reason VARCHAR(255),

    return_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_sales_return_sale
        FOREIGN KEY (sale_id)
        REFERENCES sales(id),

    CONSTRAINT fk_sales_return_product
        FOREIGN KEY (product_id)
        REFERENCES products(id)
);