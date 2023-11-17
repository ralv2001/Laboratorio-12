package edu.pucp.gtics.lab11_gtics_20232.dto;

import edu.pucp.gtics.lab11_gtics_20232.entity.Distribuidoras;
import edu.pucp.gtics.lab11_gtics_20232.entity.Juegos;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DistribuidorasDto {
    private String result;
    private String msg;
    private Distribuidoras distribuidoras;
}
