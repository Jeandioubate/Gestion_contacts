package fr.fms.dao;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import fr.fms.entities.Contact;

/**
 * Interface repository pour l'entité Contact.
 * Cette interface étend JpaRepository et fournit automatiquement les méthodes CRUD standard
 * ainsi que des méthodes de requête personnalisées
 * pour rechercher des contacts selon différents critères.
 *
 * Spring Data JPA génère automatiquement l'implémentation de cette interface au moment de l'exécution.
 *
 */
public interface ContactRepository extends JpaRepository<Contact, Long> {

    /**
     * Recherche tous les contacts appartenant à une catégorie spécifique par le nom de la catégorie.
     *
     */
    List<Contact> findByCategory_Name(String name);

    /**
     * Recherche un contact spécifique en utilisant tous ses champs d'identification.
     * Cette méthode permet de trouver un contact existant avant une éventuelle mise à jour
     * ou de vérifier si un contact est déjà présent dans la base de données.
     *
     */
    List<Contact> findByLastNameAndFirstNameAndEmailAndPhoneNumberAndAddress(
            String lastName, String firstName, String email, String phoneNumber, String address);

    /**
     * Recherche des contacts par nom ou prénom (recherche partielle insensible à la casse).
     *
     */
    List<Contact> findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCase(String lastName, String firstName);

    /**
     * Recherche des contacts par catégorie ET par nom ou prénom (recherche partielle insensible à la casse).
     * Cette méthode combine deux conditions sur la catégorie avec une condition OR sur le nom et le prénom.
     * 
     */
    List<Contact> findByCategory_IdAndLastNameContainingIgnoreCaseOrCategory_IdAndFirstNameContainingIgnoreCase(
            Long categoryId1, String lastName, Long categoryId2, String firstName);

    /**
     * Recherche tous les contacts appartenant à une catégorie spécifique par l'ID de la catégorie.
     * 
     */
    List<Contact> findByCategory_Id(Long categoryId);

}

