package fr.fms.web;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import fr.fms.dao.ContactRepository;
import fr.fms.dao.CategoryRepository;
import fr.fms.entities.Category;
import fr.fms.entities.Contact;
import jakarta.servlet.http.HttpSession;





/**
 * Contrôleur Spring MVC pour la gestion des contacts et des messages.
 * Gère toutes les requêtes HTTP liées aux contacts (CRUD, recherche, filtrage)
 * ainsi que l'envoi et la consultation de messages associés aux contacts.
 *
 */
@Controller // Annotation indiquant que cette classe est un contrôleur Spring MVC
public class ContactController {

    /**
     * Repository pour l'accès aux données des contacts (injection automatique par Spring)
     */
    @Autowired // Injection automatique du bean ContactRepository par Spring
    private ContactRepository contactRepository;

    /**
     * Repository pour l'accès aux données des catégories (injection automatique par Spring)
     */
    @Autowired // Injection automatique du bean CategoryRepository par Spring
    private CategoryRepository categoryRepository;
    
    /**
     * Vérifie si l'utilisateur est authentifié en session.
     * Méthode utilitaire privée appelée au début de chaque méthode publique.
     *
     * @param session La session HTTP contenant les informations de l'utilisateur
     * @return true si l'utilisateur est authentifié, false sinon
     */
    private boolean isAuthenticated(HttpSession session) {
        // Vérifie la présence de l'attribut "loggedUser" et qu'il est à true
        return session.getAttribute("loggedUser") != null && (boolean) session.getAttribute("loggedUser");
    }
    
    
}