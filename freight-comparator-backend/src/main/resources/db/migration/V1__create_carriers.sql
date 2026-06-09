CREATE TABLE carriers (
    id UUID PRIMARY KEY,
    code VARCHAR(40) NOT NULL UNIQUE,
    name VARCHAR(120) NOT NULL,
    logo_url VARCHAR(255),
    active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE INDEX idx_carriers_active ON carriers (active);
