package com.example.prueba_2.entities;

public class Usuario {
    private String name;
    private String last_name;
    private String email;

    public Usuario(String name, String last_name, String email){

        this.name = name;
        this.last_name = last_name;
        this.email = email;

    }

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}