CREATE TABLE products
(
    id            SERIAL      NOT NULL,
    internal_code VARCHAR(10) NOT NULL UNIQUE,
    name          VARCHAR     NOT NULL,
    CONSTRAINT product_pk PRIMARY KEY (id)
);

CREATE TABLE organizations
(
    id              SERIAL      NOT NULL,
    name            VARCHAR     NOT NULL,
    inn             VARCHAR(10) NOT NULL,
    payment_account VARCHAR(10) NOT NULL,
    CONSTRAINT organization_pk PRIMARY KEY (id),
    CONSTRAINT check_inn CHECK
        (inn LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
    CONSTRAINT check_payment_account CHECK
        (payment_account LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]')
);