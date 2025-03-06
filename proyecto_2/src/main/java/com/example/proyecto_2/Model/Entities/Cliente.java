package com.example.proyecto_2.Model.Entities;

import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Column;
import java.io.Serializable;

@Entity
public class Cliente implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    private String nombre;
    private String apellido;
    private String Correo;

    @Column(name = "Fecha")
    @Temporal(TemporalType.DATE)
    private Date Fecha;

    public Cliente(String nombre, String apellido, Long ID, String Correo, Date Fecha){

        this.nombre = nombre;
        this.apellido = apellido;
        this.ID = ID;
        this.Correo = Correo;
        this.Fecha = Fecha;       

    }

    public String getNombre(){
        return nombre;
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getApellido(){
        return apellido;
    }

    public void setApellido(String apellido){
        this.apellido = apellido;
    }

    public Long getID(){
        return ID;
    }

    public void setID(Long ID){
        this.ID = ID;
    }

    public String getCorreo(){
        return Correo;
    }

    public void setCorreo(String Correo){
        this.Correo = Correo;
    }

    public Date getFecha(){
        return Fecha;
    }

    public void setFecha(Date Fecha){
        this.Fecha = Fecha;
    }

    public static Long getSerialVersionUID(){
        return SerialVersionUID;
    }   

    private static final Long SerialVersionUID = 1L;    


    
}
