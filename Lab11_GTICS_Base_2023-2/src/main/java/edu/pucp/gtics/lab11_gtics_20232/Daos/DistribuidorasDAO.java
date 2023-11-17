package edu.pucp.gtics.lab11_gtics_20232.Daos;

import edu.pucp.gtics.lab11_gtics_20232.dto.DistribuidorasDto;
import edu.pucp.gtics.lab11_gtics_20232.dto.JuegosDto;
import edu.pucp.gtics.lab11_gtics_20232.entity.Distribuidoras;
import edu.pucp.gtics.lab11_gtics_20232.entity.Generos;
import edu.pucp.gtics.lab11_gtics_20232.entity.Juegos;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DistribuidorasDAO {

    public List<Distribuidoras> listaDistribuidoras() {
        List<Distribuidoras> lista = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplateBuilder()
                .basicAuthentication("elarios@pucp.pe", "123456")
                .build();

        String endPoint = "http://localhost:8080/distribuidoras";

        ResponseEntity<Distribuidoras[]> responseEntity = restTemplate.getForEntity(endPoint, Distribuidoras[].class);

        if(responseEntity.getStatusCode().is2xxSuccessful()){
            Distribuidoras[] body = responseEntity.getBody();
            lista = Arrays.asList(body);
        }

        return lista;

    }

    public void guardarDistribuidora(Distribuidoras distribuidoras) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String endPoint = "http://localhost:8080/distribuidoras";
        HttpEntity<Distribuidoras> httpEntity = new HttpEntity<>(distribuidoras, headers);

        RestTemplate restTemplate = new RestTemplateBuilder()
                .basicAuthentication("elarios@pucp.pe", "123456")
                .build();
        if (distribuidoras.getIddistribuidora() > 0) {
            restTemplate.put(endPoint, httpEntity, Distribuidoras.class);
        } else {
            restTemplate.postForEntity(endPoint, httpEntity, Distribuidoras.class);
        }

    }

    public Distribuidoras obtenerDistribuidoraPorId(int id){
        Distribuidoras distribuidoras = null;

        RestTemplate restTemplate = new RestTemplateBuilder()
                .basicAuthentication("elarios@pucp.pe", "123456")
                .build();

        String url = "http://localhost:8080/distribuidoras" + id;

        ResponseEntity<DistribuidorasDto> forEntity = restTemplate.getForEntity(url, DistribuidorasDto.class);

        if(forEntity.getStatusCode().is2xxSuccessful()){
            DistribuidorasDto distribuidorasDto = forEntity.getBody();
            distribuidoras = distribuidorasDto.getDistribuidoras();
        }

        return distribuidoras;
    }


    public void borrarDistribuidora(int id){
        RestTemplate restTemplate = new RestTemplateBuilder()
                .basicAuthentication("elarios@pucp.pe", "123456")
                .build();
        restTemplate.delete("http://localhost:8080/distribuidoras"+id);
    }
}
