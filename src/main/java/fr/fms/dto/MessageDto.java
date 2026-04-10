package fr.fms.dto;

import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) représentant un message échangé avec un contact.
 * Cette classe est utilisée pour le transfert de données entre le serveur et la vue,
 * ainsi que pour le stockage temporaire en session HTTP.
 *
 * Contrairement aux entités JPA, ce DTO n'est pas persisté en base de données.
 * Les messages sont stockés uniquement en mémoire (session HTTP).
 *
 */
public class MessageDto {

    /**
     * Identifiant unique du message.
     * Généré automatiquement via un compteur statique.
     */
    private Long id;

    /**
     * Sujet du message.
     */
    private String subject;

    /**
     * Contenu textuel du message.
     */
    private String content;

    /**
     * Nom de l'expéditeur du message.
     * Correspond à l'utilisateur connecté (session username).
     */
    private String senderName;

    /**
     * Identifiant du contact destinataire.
     * Permet d'associer le message à un contact spécifique.
     */
    private Long contactId;

    /**
     * Nom complet du contact destinataire.
     * Stocké pour un affichage rapide sans requête BDD.
     */
    private String contactName;

    /**
     * Date et heure d'envoi du message.
     * Initialisée automatiquement à la création.
     */
    private LocalDateTime sentDate;

    /**
     * Indique si le message a été lu par l'utilisateur.
     * false = non lu, true = lu.
     */
    private boolean read;

    /**
     * Compteur statique pour générer des IDs uniques et auto-incrémentés.
     * Commence à 1 et s'incrémente à chaque nouveau message.
     * Note : Le comptage reprend à 1 à chaque redémarrage de l'application.
     */
    private static long idCounter = 1;

    /**
     * Constructeur par défaut sans paramètres.
     * Nécessaire pour la sérialisation/désérialisation (session HTTP, Thymeleaf).
     */
    public MessageDto() {}

    /**
     * Constructeur pour créer un nouveau message avec tous les champs nécessaires.
     * Initialise automatiquement :
     * - sentDate : date/heure actuelle
     * - read : false (non lu par défaut)
     * - id : valeur unique auto-incrémentée
     *
     * @param subject Le sujet du message
     * @param content Le contenu du message
     * @param senderName Le nom de l'expéditeur (utilisateur connecté)
     * @param contactId L'identifiant du contact destinataire
     * @param contactName Le nom complet du contact destinataire
     */
    public MessageDto(String subject, String content, String senderName, Long contactId, String contactName) {
        this.subject = subject;
        this.content = content;
        this.senderName = senderName;
        this.contactId = contactId;
        this.contactName = contactName;
        this.sentDate = LocalDateTime.now(); // Date/heure actuelle du système
        this.read = false; // Par défaut, un nouveau message n'est pas lu
        this.id = idCounter++; // Assignation de l'ID puis incrémentation du compteur
    }
    
    // GETTERS ET SETTERS    

    /**
     * Retourne l'identifiant unique du message.
     *
     * @return L'ID du message
     */
    public Long getId() { return id; }

    /**
     * Définit l'identifiant unique du message.
     *
     * @param id Le nouvel ID du message
     */
    public void setId(Long id) { this.id = id; }

    /**
     * Retourne le sujet du message.
     *
     * @return Le sujet du message
     */
    public String getSubject() { return subject; }

    /**
     * Définit le sujet du message.
     *
     * @param subject Le nouveau sujet
     */
    public void setSubject(String subject) { this.subject = subject; }

    /**
     * Retourne le contenu du message.
     *
     * @return Le contenu textuel
     */
    public String getContent() { return content; }

    /**
     * Définit le contenu du message.
     *
     * @param content Le nouveau contenu
     */
    public void setContent(String content) { this.content = content; }

    /**
     * Retourne le nom de l'expéditeur.
     *
     * @return Le nom de l'expéditeur
     */
    public String getSenderName() { return senderName; }

    /**
     * Définit le nom de l'expéditeur.
     *
     * @param senderName Le nouveau nom de l'expéditeur
     */
    public void setSenderName(String senderName) { this.senderName = senderName; }

    /**
     * Retourne l'identifiant du contact destinataire.
     *
     * @return L'ID du contact
     */
    public Long getContactId() { return contactId; }

    /**
     * Définit l'identifiant du contact destinataire.
     *
     * @param contactId Le nouvel ID du contact
     */
    public void setContactId(Long contactId) { this.contactId = contactId; }

    /**
     * Retourne le nom complet du contact destinataire.
     *
     * @return Le nom du contact
     */
    public String getContactName() { return contactName; }

    /**
     * Définit le nom complet du contact destinataire.
     *
     * @param contactName Le nouveau nom du contact
     */
    public void setContactName(String contactName) { this.contactName = contactName; }

    /**
     * Retourne la date et l'heure d'envoi du message.
     *
     * @return La date/heure d'envoi
     */
    public LocalDateTime getSentDate() { return sentDate; }

    /**
     * Définit la date et l'heure d'envoi du message.
     *
     * @param sentDate La nouvelle date/heure
     */
    public void setSentDate(LocalDateTime sentDate) { this.sentDate = sentDate; }

    /**
     * Indique si le message a été lu.
     *
     * @return true si le message est lu, false sinon
     */
    public boolean isRead() { return read; }

    /**
     * Définit l'état de lecture du message.
     *
     * @param read true pour marquer comme lu, false pour non lu
     */
    public void setRead(boolean read) { this.read = read; }
}