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
    
    /**
     * Affiche la page d'accueil avec la liste des contacts.
     * Permet le filtrage par catégorie et la recherche par mot-clé.
     *
     * @param model Le modèle Spring pour passer les données à la vue
     * @param categoryId ID de la catégorie pour le filtrage 
     * @param keyword Mot-clé pour la recherche 
     * @param session La session HTTP pour vérifier l'authentification
     * @return Le nom de la vue "contacts" ou redirection vers login si non authentifié
     */
    @GetMapping("/index") // Gère les requêtes HTTP GET vers l'URL "/index"
    public String index(Model model,
                       @RequestParam(name="categoryId", required=false) Long categoryId, // Paramètre optionnel pour filtrer par catégorie
                       @RequestParam(name="keyword", defaultValue="") String keyword, // Paramètre optionnel pour la recherche textuelle
                       HttpSession session) {

        // Vérification de l'authentification avant d'afficher le contenu
        if (!isAuthenticated(session)) {
            return "redirect:/login"; // Redirection vers la page de connexion
        }

        // Récupère tous les contacts de la base de données
        List<Contact> allContacts = contactRepository.findAll();

        // Initialise la liste filtrée avec tous les contacts
        List<Contact> filteredContacts = allContacts;

        // Filtrage par catégorie si un categoryId est fourni
        if (categoryId != null) {
            filteredContacts = filteredContacts.stream() // Convertit en stream pour le filtrage
                .filter(c -> c.getCategory() != null && c.getCategory().getId().equals(categoryId)) // Garde uniquement les contacts de la catégorie spécifiée
                .collect(Collectors.toList()); // Reconvertit en liste
        }

        // Filtrage par mot-clé (recherche insensible à la casse dans nom et prénom)
        if (!keyword.isEmpty()) {
            filteredContacts = filteredContacts.stream()
                .filter(c -> c.getLastName().toLowerCase().contains(keyword.toLowerCase()) || // Vérifie si le nom contient le mot-clé
                            c.getFirstName().toLowerCase().contains(keyword.toLowerCase())) // Vérifie si le prénom contient le mot-clé
                .collect(Collectors.toList());
        }

        // Ajoute les attributs au modèle pour la vue
        model.addAttribute("listContact", filteredContacts); // Liste des contacts filtrés
        model.addAttribute("categories", categoryRepository.findAll()); // Toutes les catégories pour le menu déroulant
        model.addAttribute("selectedCategoryId", categoryId); // ID de la catégorie sélectionnée
        model.addAttribute("keyword", keyword); // Mot-clé de recherche
        model.addAttribute("username", session.getAttribute("username")); // Nom d'utilisateur connecté

        return "contacts"; // Retourne le nom de la vue Thymeleaf (contacts.html)
    }
    
    /**
     * Supprime un contact par son ID.
     * Après suppression, redirige vers l'index en conservant les paramètres de filtrage.
     *
     * @param id ID du contact à supprimer
     * @param categoryId ID de la catégorie pour conserver le filtre (optionnel)
     * @param keyword Mot-clé pour conserver la recherche (optionnel)
     * @param session La session HTTP pour vérifier l'authentification
     * @return Redirection vers l'index avec les paramètres conservés
     */
    @GetMapping("/delete") // Gère les requêtes HTTP GET vers l'URL "/delete"
    public String delete(@RequestParam Long id, // ID du contact à supprimer
                        @RequestParam(name="categoryId", required=false) Long categoryId, // Paramètre optionnel pour le filtre
                        @RequestParam(name="keyword", defaultValue="") String keyword, // Paramètre optionnel pour la recherche
                        HttpSession session) {

        // Vérification de l'authentification
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }

        // Supprime le contact de la base de données
        contactRepository.deleteById(id);

        // Construction de l'URL de redirection avec les paramètres conservés
        String redirect = "redirect:/index"; // URL de base
        if (categoryId != null) {
            redirect += "?categoryId=" + categoryId; // Ajoute le paramètre categoryId
            if (!keyword.isEmpty()) {
                redirect += "&keyword=" + keyword; // Ajoute le paramètre keyword
            }
        } else if (!keyword.isEmpty()) {
            redirect += "?keyword=" + keyword; // Ajoute seulement le mot-clé
        }

        return redirect; // Retourne la redirection
    }
    
    /**
     * Affiche le formulaire d'ajout d'un nouveau contact.
     *
     * @param model Le modèle Spring pour passer les données à la vue
     * @param session La session HTTP pour vérifier l'authentification
     * @return Le nom de la vue "contact" ou redirection vers login
     */
    @GetMapping("/contact") // Gère les requêtes HTTP GET vers l'URL "/contact"
    public String contactForm(Model model, HttpSession session) {
        // Vérification de l'authentification
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }

        // Ajoute un nouvel objet Contact vide au modèle
        model.addAttribute("contact", new Contact());
        // Ajoute toutes les catégories pour le menu déroulant
        model.addAttribute("categories", categoryRepository.findAll());
        // Indique qu'il s'agit d'un ajout (et non d'une modification)
        model.addAttribute("isEdit", false);
        return "contact"; // Retourne la vue contact.html
    }
    
    
}