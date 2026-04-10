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
	
	/**
     * Clé de session utilisée pour stocker la map des messages.
     * Cette constante permet d'accéder uniformément aux messages dans la session.
     */
    private static final String SESSION_MESSAGES_KEY = "user_messages";

    /**
     * Récupère la map des messages stockée en session.
     * La structure est : Map&lt;Long, List&lt;MessageDto&gt;&gt;
     * - Clé : ID du contact
     * - Valeur : Liste des messages associés à ce contact
     *
     * Si la map n'existe pas encore en session, elle est créée automatiquement.
     *
     * @param session La session HTTP contenant les données utilisateur
     * @return La map des messages (jamais null)
     */
    @SuppressWarnings("unchecked") // Supprime l'avertissement de cast non vérifié pour la map générique
    private Map<Long, List<MessageDto>> getMessagesMap(HttpSession session) {
        // Récupère la map depuis la session en effectuant un cast vers le type générique
        Map<Long, List<MessageDto>> messagesMap =
            (Map<Long, List<MessageDto>>) session.getAttribute(SESSION_MESSAGES_KEY);

        // Si la map n'existe pas encore en session
        if (messagesMap == null) {
            // Crée une nouvelle map thread-safe (ConcurrentHashMap)
            messagesMap = new ConcurrentHashMap<>();
            // La stocke en session pour une utilisation ultérieure
            session.setAttribute(SESSION_MESSAGES_KEY, messagesMap);
        }
        return messagesMap;
    }
    
    /**
     * Envoie un message à un contact.
     * Le message est créé avec une date automatique et un ID unique,
     * puis ajouté à la liste des messages du contact.
     *
     * @param subject Le sujet du message
     * @param content Le contenu du message
     * @param senderName Le nom de l'expéditeur (utilisateur connecté)
     * @param contactId L'identifiant du contact destinataire
     * @param contactName Le nom complet du contact destinataire
     * @param session La session HTTP pour stocker le message
     */
    // Envoyer un message
    public void sendMessage(String subject, String content, String senderName,
                           Long contactId, String contactName, HttpSession session) {
        // Crée un nouveau DTO de message avec les informations fournies
        MessageDto message = new MessageDto(subject, content, senderName, contactId, contactName);

        // Récupère la map des messages depuis la session
        Map<Long, List<MessageDto>> messagesMap = getMessagesMap(session);

        //
        // computeIfAbsent : Si aucune liste n'existe pour ce contactId, en crée une nouvelle (ArrayList)
        // Puis ajoute le message à la liste existante ou nouvellement créée
        messagesMap.computeIfAbsent(contactId, k -> new ArrayList<>()).add(message);
    }
    
    /**
     * Récupère tous les messages d'un contact spécifique.
     * Les messages sont triés par date décroissante (les plus récents en premier).
     *
     * @param contactId L'identifiant du contact
     * @param session La session HTTP contenant les messages
     * @return La liste des messages du contact (vide si aucun message)
     */
    // Récupérer les messages d'un contact
    public List<MessageDto> getMessagesForContact(Long contactId, HttpSession session) {
        // Récupère la map des messages depuis la session
        Map<Long, List<MessageDto>> messagesMap = getMessagesMap(session);

        // Récupère la liste des messages pour le contact, ou une liste vide par défaut
        List<MessageDto> messages = messagesMap.getOrDefault(contactId, new ArrayList<>());

        // Trie les messages par date décroissante (du plus récent au plus ancien)
        // m2.getSentDate().compareTo(m1.getSentDate()) : inverse l'ordre naturel
        messages.sort((m1, m2) -> m2.getSentDate().compareTo(m1.getSentDate()));

        return messages;
    }
    
    /**
     * Compte le nombre de messages non lus pour un contact.
     *
     * @param contactId L'identifiant du contact
     * @param session La session HTTP contenant les messages
     * @return Le nombre de messages non lus (long pour compatibilité avec Stream.count())
     */
    // Compter les messages non lus pour un contact
    public long countUnreadMessages(Long contactId, HttpSession session) {
        // Récupère tous les messages du contact, puis filtre ceux qui ne sont pas lus, puis compte
        return getMessagesForContact(contactId, session).stream()
            .filter(m -> !m.isRead()) // Garde uniquement les messages où read == false
            .count(); // Compte le nombre d'éléments restants
    }
    
    /**
     * Marque un message spécifique comme lu.
     * Parcourt la liste des messages du contact jusqu'à trouver le message correspondant
     * à l'ID fourni, puis modifie son attribut read à true.
     *
     * @param contactId L'identifiant du contact associé au message
     * @param messageId L'identifiant du message à marquer comme lu
     * @param session La session HTTP contenant les messages
     */
    // Marquer un message comme lu
    public void markAsRead(Long contactId, Long messageId, HttpSession session) {
        // Récupère la map des messages depuis la session
        Map<Long, List<MessageDto>> messagesMap = getMessagesMap(session);

        // Récupère la liste des messages pour le contact spécifié
        List<MessageDto> messages = messagesMap.get(contactId);

        // Si une liste existe pour ce contact
        if (messages != null) {
            // Parcourt la liste pour trouver le message avec l'ID correspondant
            for (MessageDto message : messages) {
                if (message.getId().equals(messageId)) {
                    message.setRead(true); // Marque le message comme lu
                    break; // Sort de la boucle une fois trouvé (optimisation)
                }
            }
        }
    }
    
    /**
     * Supprime tous les messages associés à un contact.
     * Méthode optionnelle (peut être utilisée lors de la suppression d'un contact).
     *
     * @param contactId L'identifiant du contact dont les messages doivent être supprimés
     * @param session La session HTTP contenant les messages
     */
    // Supprimer les messages d'un contact (optionnel)
    public void deleteMessagesForContact(Long contactId, HttpSession session) {
        // Récupère la map des messages depuis la session
        Map<Long, List<MessageDto>> messagesMap = getMessagesMap(session);

        // Supprime l'entrée correspondant au contactId (supprime toute la liste des messages)
        messagesMap.remove(contactId);
    }
	
}