package fr.fms.entities;

import java.io.Serializable;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Classe entité représentant un contact dans l'application.
 * Cette classe est mappée à une table dans la base de données via JPA.
 * Elle implémente Serializable pour permettre la sérialisation des objets Contact.
 *
 */
@Entity // Annotation JPA indiquant que cette classe est une entité persistante
@Data // Annotation Lombok générant automatiquement getters, setters, toString, equals et hashCode
@NoArgsConstructor // Annotation Lombok générant un constructeur sans paramètres
@AllArgsConstructor // Annotation Lombok générant un constructeur avec tous les paramètres
@ToString // Annotation Lombok générant une méthode toString
public class Contact implements Serializable {

    /**
     * Identifiant unique de version pour la sérialisation.
     * Permet de garantir la compatibilité lors de la désérialisation.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identifiant unique du contact.
     * Généré automatiquement par la base de données.
     */
    @Id // Annotation JPA indiquant que ce champ est la clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Stratégie d'auto-incrémentation pour la génération de l'ID
    private Long id;

    /**
     * Nom de famille du contact.
     * Ce champ est obligatoire et ne peut pas être vide.
     */
    @NotEmpty(message = "Le nom est obligatoire") // Validation : le champ ne doit pas être null ou vide
    private String lastName;

    /**
     * Prénom du contact.
     * Ce champ est obligatoire et ne peut pas être vide.
     */
    @NotEmpty(message = "Le prénom est obligatoire") // Validation : le champ ne doit pas être null ou vide
    private String firstName;

    /**
     * Adresse email du contact.
     * Ce champ est obligatoire et ne peut pas être vide.
     */
    @NotEmpty(message = "L'email est obligatoire") // Validation : le champ ne doit pas être null ou vide
    private String email;

    /**
     * Numéro de téléphone du contact.
     * Ce champ est obligatoire et ne peut pas être vide.
     */
    @NotEmpty(message = "Le téléphone est obligatoire") // Validation : le champ ne doit pas être null ou vide
    private String phoneNumber;

    /**
     * Adresse postale du contact.
     * Ce champ est obligatoire et ne peut pas être vide.
     */
    @NotEmpty(message = "L'adresse est obligatoire") // Validation : le champ ne doit pas être null ou vide
    private String address;

    /**
     * Catégorie associée au contact.
     * Relation Many-to-One : plusieurs contacts peuvent appartenir à une même catégorie.
     * La clé étrangère "category_id" dans la table contact fait référence à la table category.
     */
    @ManyToOne // Annotation JPA définissant une relation plusieurs-à-un
    @JoinColumn(name = "category_id") // Spécifie le nom de la colonne de clé étrangère dans la table contact
    private Category category;

    /**
     * Constructeur pour créer un contact sans catégorie.
     *
     * @param lastName Le nom de famille du contact
     * @param firstName Le prénom du contact
     * @param email L'adresse email du contact
     * @param phoneNumber Le numéro de téléphone du contact
     * @param address L'adresse postale du contact
     */
    public Contact(String lastName, String firstName, String email, String phoneNumber, String address) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    // Méthodes getters et setters

    /**
     * Retourne l'identifiant du contact.
     *
     * @return L'ID du contact
     */
    public Long getId() {
        return id;
    }

    /**
     * Définit l'identifiant du contact.
     *
     * @param id Le nouvel ID du contact
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retourne le nom de famille du contact.
     *
     * @return Le nom de famille
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Définit le nom de famille du contact.
     *
     * @param lastName Le nouveau nom de famille
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Retourne le prénom du contact.
     *
     * @return Le prénom
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Définit le prénom du contact.
     *
     * @param firstName Le nouveau prénom
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Retourne l'adresse email du contact.
     *
     * @return L'email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Définit l'adresse email du contact.
     *
     * @param email Le nouvel email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retourne le numéro de téléphone du contact.
     *
     * @return Le numéro de téléphone
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Définit le numéro de téléphone du contact.
     *
     * @param phoneNumber Le nouveau numéro de téléphone
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Retourne l'adresse postale du contact.
     *
     * @return L'adresse
     */
    public String getAddress() {
        return address;
    }

    /**
     * Définit l'adresse postale du contact.
     *
     * @param address La nouvelle adresse
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Retourne la catégorie du contact.
     *
     * @return La catégorie associée
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Définit la catégorie du contact.
     *
     * @param category La nouvelle catégorie
     */
    public void setCategory(Category category) {
        this.category = category;
    }
}