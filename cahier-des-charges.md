# Cahier des Charges : Application de Réservation de Rendez-Vous

---

## 1. Introduction
L’objectif est de développer une application de réservation de rendez-vous en ligne où les utilisateurs peuvent réserver un créneau horaire sans créer de compte. Une fois un rendez-vous pris, l'utilisateur recevra une notification par email ou SMS pour valider sa demande. Après validation, une confirmation de rendez-vous sera envoyée.

---

## 2. Objectifs
- Permettre à un utilisateur de réserver un créneau horaire disponible sans créer de compte.
- Envoyer des notifications automatisées par email ou SMS :
  1. **Message de validation** : Envoyé à l'utilisateur pour confirmer la demande de rendez-vous.
  2. **Message de confirmation** : Envoyé une fois le rendez-vous validé.
- Gérer les créneaux horaires pour éviter les conflits.
- Assurer que chaque rendez-vous respecte une **durée définie**, qui sera prise en compte dans la gestion des disponibilités.

---

## 3. Périmètre fonctionnel

### 3.1. Fonctionnalités principales
1. **Réservation de rendez-vous**
   - L'utilisateur choisit un créneau horaire disponible.
   - Les informations collectées incluent :
     - Nom complet.
     - Email ou numéro de téléphone.
     - Date et heure du rendez-vous.
     - **Durée du rendez-vous** (en minutes).
   - Un email ou SMS est envoyé pour valider la réservation.

2. **Validation des demandes**
   - L'utilisateur valide le rendez-vous via un lien dans l'email ou le SMS.
   - Après validation, un email ou SMS de confirmation est envoyé.

3. **Gestion des disponibilités**
   - Les administrateurs peuvent ajouter, modifier ou supprimer des créneaux disponibles.
   - Les rendez-vous ne peuvent être pris que dans les créneaux définis et en respectant la **durée des rendez-vous**.

---

## 4. Spécifications techniques

### 4.1. Technologies
- **Backend** : Spring Boot
  - API REST pour la réservation et la gestion des disponibilités.
- **Base de données** : PostgreSQL (conteneurisée avec Docker).
- **Email** : Service SMTP (ex. SendGrid, Mailgun, ou JavaMail).
- **SMS** : Intégration avec un fournisseur comme Twilio (facultatif).

### 4.2. Flux des emails/SMS
1. **Réservation** :
   - Un email/SMS de validation est envoyé avec un lien unique.
2. **Validation** :
   - Une fois validée, un email/SMS de confirmation est envoyé.
3. **Annulation** (facultatif) :
   - L'utilisateur peut annuler une réservation via un lien.

---

## 5. Structure de l’application

### 5.1. Modèle relationnel
1. **Availability**
   - `id` (Long) : Identifiant unique.
   - `date` (LocalDate) : Date de disponibilité.
   - `startTime` (LocalTime) : Heure de début.
   - `endTime` (LocalTime) : Heure de fin.

2. **Appointment**
   - `id` (Long) : Identifiant unique.
   - `date` (LocalDate) : Date du rendez-vous.
   - `startTime` (LocalTime) : Heure de début.
   - **`duration` (int)** : Durée en minutes.
   - `name` (String) : Nom complet du client.
   - `email` (String) : Email du client.
   - `phone` (String) : Numéro de téléphone (facultatif).
   - `status` (String) : État du rendez-vous (`PENDING`, `VALIDATED`, `CONFIRMED`).

---

### 5.2. Flux utilisateur

1. **Consultation des créneaux disponibles** :
   - L'utilisateur consulte les disponibilités pour un jour donné.
2. **Prise de rendez-vous** :
   - L'utilisateur sélectionne un créneau et fournit ses informations, y compris la **durée souhaitée**.
   - Le système vérifie que le créneau est disponible pour la durée spécifiée.
   - Un email/SMS de validation est envoyé avec un lien unique.
3. **Validation du rendez-vous** :
   - L'utilisateur clique sur le lien de validation.
   - L'état du rendez-vous passe de `PENDING` à `VALIDATED`.
   - Un email/SMS de confirmation est envoyé.
4. **Notification d'annulation** (facultatif) :
   - Un lien d'annulation est inclus dans les emails.

---

## 6. Contraintes et non-fonctionnel

### 6.1. Performances
- L'application doit gérer au moins 100 demandes de rendez-vous par jour.

### 6.2. Sécurité
- Les liens de validation doivent être uniques et sécurisés (tokens signés).
- Les données personnelles doivent être stockées de manière sécurisée (conformité GDPR).

### 6.3. Résilience
- Les emails/SMS doivent être réessayés en cas d'échec (configuré côté fournisseur).

---

## 7. Livrables
1. **API REST pour la réservation et la gestion des créneaux.**
2. **Base de données PostgreSQL avec script d’initialisation.**
3. **Système de notification (email/SMS).**
4. **Documentation Swagger/OpenAPI.**

---

## Exemple technique

### 1. Table `Availability`
```java
@Entity
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    @OneToMany(mappedBy = "availability")
    private List<Appointment> appointments;
}
