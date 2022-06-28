package com.aplicatie.spintm;

//user --: nume, email, parola - 3 variabile care apar in baza de date
public class User {
    public String nume;
    public String email;
    public String password;

    public User() {
//necesar pentru a nu obtine eroare in Firebase
    }

    public User (String nume, String email, String password) {
        this.nume = nume;
        this.email = email;
        this.password = password;
    }
}
