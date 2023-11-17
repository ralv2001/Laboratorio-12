package edu.pucp.gtics.lab11_gtics_20232.entity;

import edu.pucp.gtics.lab11_gtics_20232.Juegosxusuario;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "factura", schema = "gameshop4")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idfactura", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "fechaEnvio", nullable = false, length = 50)
    private String fechaEnvio;

    @Size(max = 50)
    @NotNull
    @Column(name = "tarjeta", nullable = false, length = 50)
    private String tarjeta;

    @Size(max = 5)
    @NotNull
    @Column(name = "codigoVerificacion", nullable = false, length = 5)
    private String codigoVerificacion;

    @Size(max = 500)
    @NotNull
    @Column(name = "direccion", nullable = false, length = 500)
    private String direccion;

    @NotNull
    @Column(name = "monto", nullable = false)
    private Float monto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idjuegosxusuario")
    private Juegosxusuario idjuegosxusuario;

}