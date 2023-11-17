package com.example.lab11_webservice.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "paises")
@Getter
@Setter
public class Paises {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idpais;

    private String iso;

    private String nombre;
}
