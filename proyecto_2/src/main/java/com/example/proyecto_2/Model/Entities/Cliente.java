package com.example.proyecto_2.Model.Entities;

//import java.util.Date;
import java.io.Serializable;
import java.sql.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
//import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Cliente implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String correo;

    @Column(name = "Fecha")
    @Temporal(TemporalType.DATE)
    private Date Fecha;

    public Cliente(){

    }

    public Cliente(Long ID, String nombre, String apellido, String correo, Date Fecha){

        this.nombre = nombre;
        this.apellido = apellido;
        this.id = ID;
        this.correo = correo;
        this.Fecha = Fecha;       

    }

    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getApellido() {
        return apellido;
    }


    public void setApellido(String apellido) {
        this.apellido = apellido;
    }


    public String getCorreo() {
        return correo;
    }


    public void setCorreo(String email) {
        this.correo = email;
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
