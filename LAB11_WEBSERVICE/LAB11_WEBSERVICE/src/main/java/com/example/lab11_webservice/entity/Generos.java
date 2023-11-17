package com.example.lab11_webservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "generos")
@Getter
@Setter
public class Generos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idgenero;

    private String nombre;

    private String descripcion;

}
