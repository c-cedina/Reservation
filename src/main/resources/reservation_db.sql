CREATE DATABASE reservation_db;



CREATE TABLE availability (
    id SERIAL PRIMARY KEY,
    date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    is_available BOOLEAN DEFAULT TRUE
);

CREATE TABLE appointment (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    status VARCHAR(50) NOT NULL,
    availability_id INTEGER UNIQUE NOT NULL,
    CONSTRAINT fk_availability
        FOREIGN KEY (availability_id)
        REFERENCES availability (id)
        ON DELETE CASCADE
);

CREATE OR REPLACE FUNCTION set_availability_to_unavailable()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE availability
    SET is_available = FALSE
    WHERE id = NEW.availability_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER appointment_insert_trigger
AFTER INSERT ON appointment
FOR EACH ROW
EXECUTE FUNCTION set_availability_to_unavailable();

CREATE OR REPLACE FUNCTION check_availability_status()
RETURNS TRIGGER AS $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM appointment
        WHERE availability_id = OLD.availability_id
    ) THEN
        UPDATE availability
        SET is_available = TRUE
        WHERE id = OLD.availability_id;
    END IF;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER appointment_delete_trigger
AFTER DELETE ON appointment
FOR EACH ROW
EXECUTE FUNCTION check_availability_status();
    