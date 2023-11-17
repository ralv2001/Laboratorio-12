package com.example.lab11_webservice.controller;

import com.example.lab11_webservice.entity.Distribuidoras;
import com.example.lab11_webservice.entity.Juegos;
import com.example.lab11_webservice.repository.DistribuidorasRepository;
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
@RequestMapping("/distribuidoras")
public class DistribuidorasController {

    final DistribuidorasRepository distribuidorasRepository;

    public DistribuidorasController(DistribuidorasRepository distribuidorasRepository) {
        this.distribuidorasRepository = distribuidorasRepository;
    }

    @GetMapping(value = {"/list", ""})
    public List<Distribuidoras> listadistribuidoras() {
        return distribuidorasRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<HashMap<String, Object>> buscarDistribuidoras(@PathVariable("id") String idStr) {
        try {
            int id = Integer.parseInt(idStr);
            Optional<Distribuidoras> byId = distribuidorasRepository.findById(id);
            HashMap<String, Object> respuesta = new HashMap<>();
            if (byId.isPresent()) {
                respuesta.put("result", "ok");
                respuesta.put("distribuidoras", byId.get());
            } else {
                respuesta.put("result", "no existe");
            }
            return ResponseEntity.ok(respuesta);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    // CREAR /distribuidoras y /distribuidoras/
    @PostMapping(value = {"", "/"}, produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
    public ResponseEntity<HashMap<String, Object>> guardarProducto(
            @RequestBody Distribuidoras distribuidoras,
            @RequestParam(value = "fetchId", required = false) boolean fetchId) {

        HashMap<String, Object> responseJson = new HashMap<>();

        distribuidorasRepository.save(distribuidoras);
        if (fetchId) {
            responseJson.put("id", distribuidoras.getIddistribuidora());
        }
        responseJson.put("estado", "creado");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseJson);
    }


    // ACTUALIZAR
    @PutMapping(value = {"", "/"}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
    public ResponseEntity<HashMap<String, Object>> actualizar(Distribuidoras distribuidorasRecibido) {

        HashMap<String, Object> rpta = new HashMap<>();

        if (distribuidorasRecibido.getIddistribuidora() != null && distribuidorasRecibido.getIddistribuidora() > 0) {

            Optional<Distribuidoras> byId = distribuidorasRepository.findById(distribuidorasRecibido.getIddistribuidora());
            if (byId.isPresent()) {
                Distribuidoras distribuidorasFromDb = byId.get();

                if (distribuidorasRecibido.getNombre() != null)
                    distribuidorasFromDb.setNombre(distribuidorasRecibido.getNombre());

                if (distribuidorasRecibido.getDescripcion() != null)
                    distribuidorasFromDb.setDescripcion(distribuidorasRecibido.getDescripcion());

                if (distribuidorasRecibido.getFundacion() != 0)
                    distribuidorasFromDb.setFundacion(distribuidorasRecibido.getFundacion());

                if (distribuidorasRecibido.getWeb() != null)
                    distribuidorasFromDb.setWeb(distribuidorasRecibido.getWeb());

                if (distribuidorasRecibido.getPaises() != null)
                    distribuidorasFromDb.setPaises(distribuidorasRecibido.getPaises());



                distribuidorasRepository.save(distribuidorasFromDb);
                rpta.put("result", "ok");
                return ResponseEntity.ok(rpta);
            } else {
                rpta.put("result", "error");
                rpta.put("msg", "El ID del distribuidora enviado no existe");
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

            Optional<Distribuidoras> byId = distribuidorasRepository.findById(id);
            if(byId.isPresent()){
                distribuidorasRepository.deleteById(id);
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
