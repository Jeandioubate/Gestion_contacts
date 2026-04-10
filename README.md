# Agenda de contacts
Application web de gestion de contacts avec authentification simple et messagerie intégrée.

## Fonctionnalités
### Public
- Page d'accueil avec contacts fictifs
- Formulaire de connexion

### Privé (après connexion)
- **Gestion des contacts** : ajouter, modifier, supprimer
- **Organisation** : classer les contacts par catégorie (Personnel/Professionnel)
- **Recherche** : par nom ou prénom
- **Filtrage** : par catégorie
- **Messagerie** : envoyer et recevoir des messages (simulation sans email)

## Technologies utilisées
- Java 17+
- Spring Boot 3
- Spring Data JPA
- Thymeleaf
- Bootstrap 5
- MariaDB / phpMyAdmin

## Installation

### Prérequis
- Java 17 ou supérieur
- Maven
- MariaDB / phpMyAdmin

## Lancement
mvn spring-boot:run
Accéder à l'application : http://localhost:8080

## Identifiants de connexion
User : admin
MP : admin123

## Utilisation
### Gestion des contacts
1. Se connecter avec les identifiants ci-dessus
2. Cliquer sur "Mes contacts"
3. Utiliser les boutons :
   . + Nouveau contact : ajoute
   . Modifier : éditer un contact
   . Supprimer : effacer
   
### Filtres et recherche
* Catégories : cliquer sur "Personnel" ou "Professionnel"
* Recherche : saisir un nom ou prénom

### Messagerie
* Envoyer : cliquer sur envoyer
* Voir : consulter les messages reçus
* Marquer comme lu : depuis la liste des messages
