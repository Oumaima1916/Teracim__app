package com.myapp.model;

public class Directeur extends User {

    public Directeur() {
        setRole(RoleUtilisateur.DIRECTEUR);
    }

    public Directeur(Long id, String nom, String prenom,
                     String email, String motDePasse) {
        super(id, nom, prenom, email, motDePasse, RoleUtilisateur.DIRECTEUR);
    }
}
