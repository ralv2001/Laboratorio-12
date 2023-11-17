package edu.pucp.gtics.lab11_gtics_20232.Daos;

import edu.pucp.gtics.lab11_gtics_20232.entity.Generos;
import edu.pucp.gtics.lab11_gtics_20232.entity.Paises;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class PaisesDAO {

    public List<Paises> listaPaises(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Paises[]> response = restTemplate.getForEntity(
                "http://localhost:8080/paises", Paises[].class);

        return Arrays.asList(response.getBody());
    }

}
