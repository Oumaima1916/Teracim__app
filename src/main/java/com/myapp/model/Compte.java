package com.myapp.model;

import java.time.LocalDate;

public class Compte {
    private String nom, role, email, username, statut, lastActive;
    private LocalDate dateJoined;

    public Compte(String nom, String role, String email, String username, String statut, LocalDate dateJoined, String lastActive) {
        this.nom = nom; this.role = role; this.email = email;
        this.username = username; this.statut = statut;
        this.dateJoined = dateJoined; this.lastActive = lastActive;
    }


    public String getNom() { return nom; }
    public String getRole() { return role; }
    public String getEmail() { return email; }
    public String getUsername() { return username; }
    public String getStatut() { return statut; }
    public LocalDate getDateJoined() { return dateJoined; }
    public String getLastActive() { return lastActive; }
}