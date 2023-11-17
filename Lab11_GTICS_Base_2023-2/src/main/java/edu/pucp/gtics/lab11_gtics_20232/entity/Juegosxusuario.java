package edu.pucp.gtics.lab11_gtics_20232;

import edu.pucp.gtics.lab11_gtics_20232.entity.Factura;
import edu.pucp.gtics.lab11_gtics_20232.entity.Juegos;
import edu.pucp.gtics.lab11_gtics_20232.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "juegosxusuario", schema = "gameshop4")
public class Juegosxusuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idjuegosxusuario", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idjuego", nullable = false)
    private Juegos idjuego;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idusuario", nullable = false)
    private User idusuario;

    @Column(name = "cantidad")
    private Integer cantidad;

    @OneToMany(mappedBy = "idjuegosxusuario")
    private Set<Factura> facturas = new LinkedHashSet<>();

}