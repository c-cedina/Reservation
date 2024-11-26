-- Création de la base de données
CREATE DATABASE reservation_db;

-- Connexion à la base de données
\c reservation_db;

-- Création de la table Availability
CREATE TABLE availability (
    id SERIAL PRIMARY KEY,                 -- Identifiant unique
    date DATE NOT NULL,                    -- Date de disponibilité
    start_time TIME NOT NULL,              -- Heure de début
    end_time TIME NOT NULL                 -- Heure de fin
);

-- Création de la table Appointment
CREATE TABLE appointment (
    id SERIAL PRIMARY KEY,                 -- Identifiant unique
    date DATE NOT NULL,                    -- Date du rendez-vous
    start_time TIME NOT NULL,              -- Heure de début
    duration INTEGER NOT NULL,             -- Durée en minutes
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
