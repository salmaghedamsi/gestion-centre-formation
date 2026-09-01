# Gestion Centre de Formation

Application web de gestion d'un centre de formation, permettant de gérer les formations, les formateurs, les candidats/stagiaires et les sessions.

## Fonctionnalités

- Gestion des formations (ajout, modification, suppression, consultation)
- Gestion des formateurs
- Gestion des élèves
- Gestion des sessions de formation
- Inscription des élèves aux formations


## Stack technique

**Backend**
- Java
- Spring Boot
- Spring Data JPA
- MySQL

**Frontend**
- Angular
- TypeScript
- HTML / CSS

## Architecture du projet

```
gestion-centre-formation/
├── Backend/     # API REST Spring Boot
└── Frontend/    # Application Angular
```
## Stack technique

**Backend**
Java v 21.0.11
Spring Framework (web / webmvc / orm)	7.0.8
Spring Security	7.1.0
Spring Data JPA / Commons	4.1.0
Hibernate ORM	7.4.1.Final
Tomcat embarqué	11.0.22
MySQL Connector/J	9.7.0

**Frontend**
Angular CLI: 20.3.28
Node: 24.13.0


## Installation et lancement

### 1. Cloner le dépôt

```bash
git clone https://github.com/salmaghedamsi/gestion-centre-formation.git
cd gestion-centre-formation
```

### 2. Configuration de la base de données


```properties
spring.datasource.url=jdbc:mysql://localhost:3306/gestion_centreformation
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### 3. Lancer le Backend

```bash
cd Backend
mvn spring-boot:run
```

Le serveur démarre sur `http://localhost:8083`.

### 4. Lancer le Frontend

```bash
cd Frontend
npm install
ng serve
```

L'application est ensuite accessible sur `http://localhost:4200`.


## Captures d'écran

_À ajouter : quelques captures de l'interface pour illustrer le projet._

## Auteur

Développé par [salmaghedamsi](https://github.com/salmaghedamsi)

## Licence

Ce projet est distribué sous licence MIT (ou à préciser selon ton choix).
