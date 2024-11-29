-- Création de la base de données
CREATE DATABASE reservation_db;

-- Création de la table Availability
CREATE TABLE availability (
    id SERIAL PRIMARY KEY,                 -- Identifiant unique
    date DATE NOT NULL,                    -- Date de disponibilité
    start_time TIME NOT NULL,              -- Heure de début
    end_time TIME NOT NULL,                -- Heure de fin
    is_available BOOLEAN DEFAULT TRUE      -- Indicateur de disponibilité
);

-- Création de la table Appointment
CREATE TABLE appointment (
    id SERIAL PRIMARY KEY,                 -- Identifiant unique
    name VARCHAR(255) NOT NULL,            -- Nom de la personne
    email VARCHAR(255) NOT NULL,           -- Email de la personne
    phone VARCHAR(20),                     -- Numéro de téléphone (facultatif)
    status VARCHAR(50) NOT NULL,           -- État du rendez-vous (PENDING, VALIDATED, CONFIRMED)
    availability_id INTEGER NOT NULL,      -- ID de la disponibilité associée
    CONSTRAINT fk_availability
        FOREIGN KEY (availability_id)
        REFERENCES availability (id)
        ON DELETE CASCADE                  -- Supprime les rendez-vous associés si la disponibilité est supprimée
);

-- Fonction pour marquer une disponibilité comme indisponible lors de l'ajout d'un rendez-vous
CREATE OR REPLACE FUNCTION set_availability_to_unavailable()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE availability
    SET is_available = FALSE
    WHERE id = NEW.availability_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger pour exécuter la fonction après un ajout dans la table Appointment
CREATE TRIGGER appointment_insert_trigger
AFTER INSERT ON appointment
FOR EACH ROW
EXECUTE FUNCTION set_availability_to_unavailable();

-- Fonction pour vérifier si une disponibilité peut redevenir disponible après suppression d'un rendez-vous
CREATE OR REPLACE FUNCTION check_availability_status()
RETURNS TRIGGER AS $$
BEGIN
    -- Vérifie si d'autres rendez-vous existent pour cette disponibilité
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

-- Trigger pour exécuter la fonction après une suppression dans la table Appointment
CREATE TRIGGER appointment_delete_trigger
AFTER DELETE ON appointment
FOR EACH ROW
EXECUTE FUNCTION check_availability_status();
