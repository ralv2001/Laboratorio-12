package edu.pucp.gtics.lab11_gtics_20232.Daos;

import edu.pucp.gtics.lab11_gtics_20232.dto.JuegosDto;
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
public class JuegosDAO {

    public List<Juegos> listaJuegos() {
        List<Juegos> lista = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplateBuilder()
                .basicAuthentication("elarios@pucp.pe", "123456")
                .build();

        String endPoint = "http://localhost:8080/juegos/list";

        ResponseEntity<Juegos[]> responseEntity = restTemplate.getForEntity(endPoint, Juegos[].class);

        if(responseEntity.getStatusCode().is2xxSuccessful()){
           Juegos[] body = responseEntity.getBody();
            lista = Arrays.asList(body);
        }

        return lista;

    }

    public void guardarJuego(Juegos juegos) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String endPoint = "http://localhost:8080/juegos";
        HttpEntity<Juegos> httpEntity = new HttpEntity<>(juegos, headers);

        RestTemplate restTemplate = new RestTemplateBuilder()
                .basicAuthentication("elarios@pucp.pe", "123456")
                .build();

        if (juegos.getIdjuego() > 0) {
            restTemplate.put(endPoint, httpEntity, Juegos.class);
        } else {
            restTemplate.postForEntity(endPoint, httpEntity, Juegos.class);
        }

    }
    public Juegos obtenerJuegoPorId(int id){
        Juegos juegos = null;

        RestTemplate restTemplate = new RestTemplateBuilder()
                .basicAuthentication("elarios@pucp.pe", "123456")
                .build();

        String url = "http://localhost:8080/juegos/" + id;

        ResponseEntity<JuegosDto> forEntity = restTemplate.getForEntity(url, JuegosDto.class);

        if(forEntity.getStatusCode().is2xxSuccessful()){
            JuegosDto juegosDto = forEntity.getBody();
            juegos = juegosDto.getJuegos();
        }

        return juegos;
    }


    public void borrarJuego(int id){
        RestTemplate restTemplate = new RestTemplateBuilder()
                .basicAuthentication("elarios@pucp.pe", "123456")
                .build();
        restTemplate.delete("http://localhost:8080/juegos"+id);
    }


}
