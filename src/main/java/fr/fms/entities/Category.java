package fr.fms.entities;

import jakarta.persistence.*;
import java.util.*;

/**
 * Classe entité représentant une catégorie dans l'application.
 * Une catégorie permet de regrouper et classer les contacts.
 * Cette classe est mappée à une table dans la base de données via JPA.
 *
 */
@Entity // Annotation JPA indiquant que cette classe est une entité persistante
public class Category {

    /**
     * Identifiant unique de la catégorie.
     * Généré automatiquement par la base de données.
     */
    @Id // Annotation JPA indiquant que ce champ est la clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Stratégie d'auto-incrémentation pour la génération de l'ID
    private Long id;

    /**
     * Nom de la catégorie 
     */
    private String name;

    /**
     * Collection des contacts appartenant à cette catégorie.
     * Relation One-to-Many : une catégorie peut contenir plusieurs contacts.
     * mappedBy = "category" indique que la relation est gérée par l'attribut 'category' dans la classe Contact.
     * C'est une relation bidirectionnelle où Category est le côté inverse (non propriétaire).
     */
    @OneToMany(mappedBy = "category") // Annotation JPA définissant une relation un-à-plusieurs
    private Collection<Contact> contacts;

    /**
     * Constructeur par défaut sans paramètres.
     * Nécessaire pour JPA et la sérialisation.
     */
    public Category() {}

    /**
     * Constructeur pour créer une catégorie avec un nom.
     *
     * @param name Le nom de la catégorie
     */
    public Category(String name) {
        this.name = name;
    }

    // Méthodes getters et setters

    /**
     * Retourne l'identifiant de la catégorie.
     *
     * @return L'ID de la catégorie
     */
    public Long getId() {
        return id;
    }

    /**
     * Définit l'identifiant de la catégorie.
     *
     * @param id Le nouvel ID de la catégorie
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retourne le nom de la catégorie.
     *
     * @return Le nom de la catégorie
     */
    public String getName() {
        return name;
    }

    /**
     * Définit le nom de la catégorie.
     *
     * @param name Le nouveau nom de la catégorie
     */
    public void setName(String name) {
        this.name = name;
    }
}