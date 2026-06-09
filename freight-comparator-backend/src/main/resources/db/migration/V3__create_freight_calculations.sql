CREATE TABLE freight_calculation (
    id UUID PRIMARY KEY,
    origin_zip_code VARCHAR(8) NOT NULL,
    destination_zip_code VARCHAR(8) NOT NULL,
    weight_kg NUMERIC(10, 3) NOT NULL,
    height_cm NUMERIC(10, 2) NOT NULL,
    width_cm NUMERIC(10, 2) NOT NULL,
    length_cm NUMERIC(10, 2) NOT NULL,
    declared_value NUMERIC(12, 2) NOT NULL DEFAULT 0,
    calculated_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE freight_calculation_option (
    id UUID PRIMARY KEY,
    freight_calculation_id UUID NOT NULL REFERENCES freight_calculation (id) ON DELETE CASCADE,
    carrier_code VARCHAR(40) NOT NULL,
    carrier_name VARCHAR(120) NOT NULL,
    logo_url VARCHAR(255),
    service_name VARCHAR(80) NOT NULL,
    modality VARCHAR(40) NOT NULL,
    price NUMERIC(12, 2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    delivery_days INTEGER NOT NULL,
    best_price BOOLEAN NOT NULL DEFAULT FALSE,
    fastest_delivery BOOLEAN NOT NULL DEFAULT FALSE,
    best_cost_benefit BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE INDEX idx_freight_calculation_calculated_at ON freight_calculation (calculated_at DESC);
CREATE INDEX idx_freight_calculation_option_calculation ON freight_calculation_option (freight_calculation_id);
