package com.example.lab11_webservice.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "plataformas")
@Getter
@Setter
public class Plataformas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idplataforma;

    private String nombre;

    private String descripcion;


}
