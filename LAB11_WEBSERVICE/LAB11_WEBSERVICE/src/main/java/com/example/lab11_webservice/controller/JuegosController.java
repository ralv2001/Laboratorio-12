package com.example.lab11_webservice.controller;

import com.example.lab11_webservice.entity.Juegos;
import com.example.lab11_webservice.repository.JuegosRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/juegos")
public class JuegosController {

    final JuegosRepository juegosRepository;

    public JuegosController(JuegosRepository juegosRepository) {
        this.juegosRepository = juegosRepository;
    }

    //LISTAR
    @GetMapping(value = {"/list"})
    public List<Juegos> listajuegos() {
        return juegosRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<HashMap<String, Object>> buscarProducto(@PathVariable("id") String idStr) {
        try {
            int id = Integer.parseInt(idStr);
            Optional<Juegos> byId = juegosRepository.findById(id);
            HashMap<String, Object> respuesta = new HashMap<>();
            if (byId.isPresent()) {
                respuesta.put("result", "ok");
                respuesta.put("juego", byId.get());
            } else {
                respuesta.put("result", "no existe");
            }
            return ResponseEntity.ok(respuesta);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // CREAR /juegos y /juegos/
    @PostMapping(value = {"", "/"}, produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
    public ResponseEntity<HashMap<String, Object>> guardarProducto(
            @RequestBody Juegos juegos,
            @RequestParam(value = "fetchId", required = false) boolean fetchId) {

        HashMap<String, Object> responseJson = new HashMap<>();

        juegosRepository.save(juegos);
        if (fetchId) {
            responseJson.put("id", juegos.getIdjuego());
        }
        responseJson.put("estado", "creado");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseJson);
    }


    // ACTUALIZAR
    @PutMapping(value = {"", "/"}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
    public ResponseEntity<HashMap<String, Object>> actualizar(Juegos juegosRecibido) {

        HashMap<String, Object> rpta = new HashMap<>();

        if (juegosRecibido.getIdjuego() != null && juegosRecibido.getIdjuego() > 0) {

            Optional<Juegos> byId = juegosRepository.findById(juegosRecibido.getIdjuego());
            if (byId.isPresent()) {
                Juegos juegosFromDb = byId.get();

                if (juegosRecibido.getNombre() != null)
                    juegosFromDb.setNombre(juegosRecibido.getNombre());

                if (juegosRecibido.getDescripcion() != null)
                    juegosFromDb.setDescripcion(juegosRecibido.getDescripcion());

                if (juegosRecibido.getPrecio() != 0)
                    juegosFromDb.setPrecio(juegosRecibido.getPrecio());

                if (juegosRecibido.getImage() != null)
                    juegosFromDb.setImage(juegosRecibido.getImage());

                if (juegosRecibido.getPlataforma() != null)
                    juegosFromDb.setPlataforma(juegosRecibido.getPlataforma());

                if (juegosRecibido.getDistribuidora() != null)
                    juegosFromDb.setDistribuidora(juegosRecibido.getDistribuidora());

                if (juegosRecibido.getGeneros() != null)
                    juegosFromDb.setGeneros(juegosRecibido.getGeneros());

                juegosRepository.save(juegosFromDb);
                rpta.put("result", "ok");
                return ResponseEntity.ok(rpta);
            } else {
                rpta.put("result", "error");
                rpta.put("msg", "El ID del juego enviado no existe");
                return ResponseEntity.badRequest().body(rpta);
            }
        } else {
            rpta.put("result", "error");
            rpta.put("msg", "debe enviar un distribuidora con ID");
            return ResponseEntity.badRequest().body(rpta);
        }
    }




    // /Product?id
    @DeleteMapping("")
    public ResponseEntity<HashMap<String, Object>> borrar(@RequestParam("id") String idStr){

        try{
            int id = Integer.parseInt(idStr);

            HashMap<String, Object> rpta = new HashMap<>();

            Optional<Juegos> byId = juegosRepository.findById(id);
            if(byId.isPresent()){
                juegosRepository.deleteById(id);
                rpta.put("result","ok");
            }else{
                rpta.put("result","no ok");
                rpta.put("msg","el ID enviado no existe");
            }

            return ResponseEntity.ok(rpta);
        }catch (NumberFormatException e){
            return ResponseEntity.badRequest().body(null);
        }
    }




    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap<String, String>> gestionException(HttpServletRequest request) {
        HashMap<String, String> responseMap = new HashMap<>();
        if (request.getMethod().equals("POST") || request.getMethod().equals("PUT")) {
            responseMap.put("estado", "error");
            responseMap.put("msg", "Debe enviar un distribuidora");
        }
        return ResponseEntity.badRequest().body(responseMap);
    }


}
