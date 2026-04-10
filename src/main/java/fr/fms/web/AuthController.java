package fr.fms.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

/**
 * Contrôleur d'authentification pour la gestion de la connexion et déconnexion des utilisateurs.
 * Gère les pages d'accueil, de connexion et de déconnexion.
 *
 * Note: L'authentification est basique avec des identifiants hardcodés.
 * Dans une application réelle, il faudrait utiliser Spring Security et une base de données.
 *
 */
@Controller // Annotation indiquant que cette classe est un contrôleur Spring MVC
public class AuthController {
	
	/**
     * Nom d'utilisateur valide pour l'authentification.
     * 
     */
    private static final String VALID_USERNAME = "admin"; // Identifiant prédéfini

    /**
     * Mot de passe valide pour l'authentification.
     * 
     */
    private static final String VALID_PASSWORD = "admin123"; // Mot de passe prédéfini

    /**
     * Affiche la page de connexion.
     * Gère la requête GET vers l'URL "/login".
     *
     * @return Le nom de la vue Thymeleaf "login" (login.html)
     */
    @GetMapping("/login") // Gère les requêtes HTTP GET vers l'URL "/login"
    public String loginPage() {
        return "login"; // Retourne la page de formulaire de connexion
    }

    /**
     * Traite la tentative de connexion de l'utilisateur.
     * Gère la requête POST vers l'URL "/login".
     * Vérifie les identifiants et crée une session si l'authentification est réussie.
     *
     * @param username Le nom d'utilisateur soumis via le formulaire
     * @param password Le mot de passe soumis via le formulaire
     * @param session La session HTTP pour stocker les informations de l'utilisateur connecté
     * @param model Le modèle Spring pour passer les messages d'erreur à la vue
     * @return Redirection vers l'index si authentification réussie, sinon retour à la page de connexion avec erreur
     */
    @PostMapping("/login") // Gère les requêtes HTTP POST vers l'URL "/login"
    public String login(@RequestParam String username, // Récupère le paramètre "username" du formulaire
                       @RequestParam String password, // Récupère le paramètre "password" du formulaire
                       HttpSession session, // Session HTTP pour maintenir l'état de connexion
                       Model model) { // Modèle pour transmettre des données à la vue

        // Vérifie si les identifiants correspondent aux valeurs prédéfinies
        if (VALID_USERNAME.equals(username) && VALID_PASSWORD.equals(password)) {
            // Authentification réussie
            session.setAttribute("loggedUser", true);  // Stocke un booléen indiquant que l'utilisateur est connecté
            session.setAttribute("username", username);  // Stocke le nom d'utilisateur pour l'affichage 
            return "redirect:/index"; // Redirige vers la page d'accueil des contacts
        }
        
     // Authentification échouée
        model.addAttribute("error", "Identifiants incorrects"); // Ajoute un message d'erreur au modèle
        return "login"; // Retourne à la page de connexion pour réessaye        
    }
    
}
    

    
