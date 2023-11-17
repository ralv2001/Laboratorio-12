package edu.pucp.gtics.lab11_gtics_20232.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
@Entity
@Table(name = "editoras")
public class Editoras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(value = 0, message = "Editora no puede estar vacío")
    private
    int idgeditora;
    private String nombre;
    private String descripcion;
}
