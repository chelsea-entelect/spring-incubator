CREATE TABLE IF NOT EXISTS bookings (
                                        id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                        customer_id VARCHAR(50) NOT NULL,
    flight_id VARCHAR(50) NOT NULL,
    reference_number VARCHAR(50) NOT NULL
    );