package edu.pucp.gtics.lab11_gtics_20232.entity;

import edu.pucp.gtics.lab11_gtics_20232.entity.Juegos;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "editoras", schema = "gameshop4")
public class Editora {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ideditora", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Column(name = "nombre", length = 50)
    private String nombre;

    @Size(max = 200)
    @Column(name = "descripcion", length = 200)
    private String descripcion;

    @OneToMany(mappedBy = "ideditora")
    private Set<Juegos> juegos = new LinkedHashSet<>();

}