package com.example.lab11_webservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "distribuidoras")
@Getter
@Setter
public class Distribuidoras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer iddistribuidora;

    private String nombre;

    private String descripcion;

    private int fundacion;

    private String web;

    @ManyToOne
    @JoinColumn(name = "idsede")
    private Paises paises;

}
