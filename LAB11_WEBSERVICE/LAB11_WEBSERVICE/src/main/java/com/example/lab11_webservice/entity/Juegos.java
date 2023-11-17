package com.example.lab11_webservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "juegos")
@Getter
@Setter
public class Juegos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idjuego;

    private String nombre;

    private String descripcion;

    private double precio;

    private String image;

    @ManyToOne
    @JoinColumn(name = "idplataforma")
    private Plataformas plataforma;

    @ManyToOne
    @JoinColumn(name = "iddistribuidora")
    private Distribuidoras distribuidora;

    @ManyToOne
    @JoinColumn(name = "ideditora")
    private Editoras editoras;

    @ManyToOne
    @JoinColumn(name = "idgenero")
    private Generos generos;

}