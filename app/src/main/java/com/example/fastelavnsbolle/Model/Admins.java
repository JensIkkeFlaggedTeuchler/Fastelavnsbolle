package com.example.fastelavnsbolle.Model;

public class Admins {

    private String navn;
    private String email;
    private String adgangskode;
    public Admins()
    {

    }

    public Admins(String navn, String email, String adgangskode) {
        this.navn = navn;
        this.email = email;
        this.adgangskode = adgangskode;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdgangskode() {
        return adgangskode;
    }

    public void setAdgangskode(String adgangskode) {
        this.adgangskode = adgangskode;
    }

}
