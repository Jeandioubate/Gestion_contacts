package fr.fms.services;

import fr.fms.dto.MessageDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
//import java.util.stream.Collectors;

/**
 * Service de gestion des messages.
 * Cette classe gère le stockage et la manipulation des messages en session HTTP.
 *
 * Caractéristiques principales :
 * - Stockage en mémoire (session utilisateur) et non en base de données
 * - Structure thread-safe avec ConcurrentHashMap
 * - Organisation des messages par contact (clé = ID contact)
 * - Tri automatique des messages par date décroissante
 *
 */
@Service // Annotation Spring indiquant que cette classe est un bean de service (couche métier)
public class MessageService {
	
}