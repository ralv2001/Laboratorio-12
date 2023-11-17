package com.example.lab11_webservice.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "editoras")
@Getter
@Setter
public class Editoras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ideditora;

    private String nombre;

    private String descripcion;

}
