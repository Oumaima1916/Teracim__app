package com.myapp.model;

public class Secretaire extends User {

    public Secretaire() {
        setRole(RoleUtilisateur.SECRETAIRE);
    }

    public Secretaire(Long id, String nom, String prenom,
                      String email, String motDePasse) {
        super(id, nom, prenom, email, motDePasse, RoleUtilisateur.SECRETAIRE);
    }
}
