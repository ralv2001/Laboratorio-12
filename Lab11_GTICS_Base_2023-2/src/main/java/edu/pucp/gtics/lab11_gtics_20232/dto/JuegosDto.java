package edu.pucp.gtics.lab11_gtics_20232.dto;

import edu.pucp.gtics.lab11_gtics_20232.entity.Juegos;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JuegosDto {
    private String result;
    private String msg;
    private Juegos juegos;
}
