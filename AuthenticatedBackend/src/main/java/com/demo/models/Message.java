package com.demo.models;

import jakarta.persistence.*;


import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MessageType type;

    private String contenu;

    @ManyToOne
    private User emetteur;

    @ManyToMany
    private Set<User> recepteurs = new HashSet<>();

    // Ajoutez d'autres champs si nécessaire (date de création, date de modification, etc.)

    // Constructeurs, Getters and Setters

    public Message() {
        // Constructeur par défaut nécessaire pour JPA
    }

    public Message(MessageType type, String contenu, User emetteur) {
        this.type = type;
        this.contenu = contenu;
        this.emetteur = emetteur;
    }

    // Ajoutez d'autres méthodes si nécessaire

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public User getEmetteur() {
        return emetteur;
    }

    public void setEmetteur(User emetteur) {
        this.emetteur = emetteur;
    }

    public Set<User> getRecepteurs() {
        return recepteurs;
    }

    public void setRecepteurs(Set<User> recepteurs) {
        this.recepteurs = recepteurs;
    }

    public void addRecepteur(User recepteur) {
        this.recepteurs.add(recepteur);
    }
}