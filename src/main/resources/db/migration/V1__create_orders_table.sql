CREATE TABLE orders
(
    id                 BIGINT                           NOT NULL AUTO_INCREMENT,
    public_id          BINARY(16)   NOT NULL,
    organization_id    BINARY(16)   NOT NULL,
    external_reference VARCHAR(100) COLLATE utf8mb4_bin NOT NULL,
    status             VARCHAR(30)                      NOT NULL,
    received_at        DATETIME(6)  NOT NULL,

    CONSTRAINT pk_orders
        PRIMARY KEY (id),

    CONSTRAINT uk_orders_public_id
        UNIQUE (public_id),

    CONSTRAINT uk_orders_organization_external_reference
        UNIQUE (organization_id, external_reference)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE INDEX idx_orders_organization_status
    ON orders (organization_id, status);

CREATE INDEX idx_orders_received_at
    ON orders (received_at);