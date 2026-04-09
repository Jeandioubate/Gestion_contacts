package fr.fms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import fr.fms.entities.Category;

/**
 * Interface repository pour l'entité Category.
 * Cette interface étend JpaRepository et fournit automatiquement les méthodes CRUD standard ainsi que la possibilité d'ajouter
 * des méthodes de requête personnalisées.
 *
 * Spring Data JPA génère automatiquement l'implémentation de cette interface au moment de l'exécution.
 *
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Recherche une catégorie par son nom.
     * Spring Data JPA analyse le nom de la méthode et génère automatiquement la requête JPQL correspondante.
     * 
     *
     * @param name Le nom de la catégorie à rechercher
     * @return La catégorie trouvée, ou null si aucune catégorie ne correspond au nom spécifié
     */
    Category findByName(String name);
}