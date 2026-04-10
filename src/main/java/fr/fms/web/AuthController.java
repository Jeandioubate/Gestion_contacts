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

    
    
}