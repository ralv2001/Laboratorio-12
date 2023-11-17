package edu.pucp.gtics.lab11_gtics_20232.controller;

import edu.pucp.gtics.lab11_gtics_20232.Daos.DistribuidorasDAO;
import edu.pucp.gtics.lab11_gtics_20232.Daos.PaisesDAO;
import edu.pucp.gtics.lab11_gtics_20232.entity.Distribuidoras;
import edu.pucp.gtics.lab11_gtics_20232.entity.Juegos;
import edu.pucp.gtics.lab11_gtics_20232.entity.Paises;
import edu.pucp.gtics.lab11_gtics_20232.repository.DistribuidorasRepository;
import edu.pucp.gtics.lab11_gtics_20232.repository.PaisesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/distribuidoras")

public class DistribuidorasController {

    @Autowired
    DistribuidorasRepository distribuidorasRepository;

    @Autowired
    PaisesRepository paisesRepository;
    @Autowired
    DistribuidorasDAO distribuidorasDAO;
    @Autowired
    PaisesDAO paisesDAO;

    @GetMapping(value = {"/lista"})
    public String listaDistribuidoras (Model model){
        List<Distribuidoras> listadistribuidoras = distribuidorasRepository.findAll(Sort.by("nombre"));
        model.addAttribute("listadistribuidoras", listadistribuidoras);
        return "distribuidoras/lista";
    }

    @GetMapping("/editar")
    public String editarDistribuidoras(@RequestParam("id") int id, Model model){
        Optional<Distribuidoras> opt = distribuidorasRepository.findById(id);
        List<Paises> listaPaises = paisesRepository.findAll();
        if (opt.isPresent()){
            Distribuidoras distribuidora = opt.get();
            model.addAttribute("distribuidora", distribuidora);
            model.addAttribute("listaPaises", listaPaises);
            return "distribuidoras/editarFrm";
        }else {
            return "redirect:/distribuidoras/lista";
        }

    }

    @GetMapping("/nuevo")
    public String nuevaDistribuidora(Model model, @ModelAttribute("distribuidora") Distribuidoras distribuidora){
        List<Paises> listaPaises = paisesRepository.findAll();
        model.addAttribute("listaPaises", listaPaises);
        return "distribuidoras/editarFrm";
    }

    @PostMapping("/guardar")
    public String guardarDistribuidora(Model model, RedirectAttributes attr, @ModelAttribute("distribuidora") @Valid Distribuidoras distribuidora , BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<Paises> listaPaises = paisesRepository.findAll();
            model.addAttribute("distribuidora", distribuidora);
            model.addAttribute("listaPaises", listaPaises);
            return "distribuidoras/editarFrm";
        } else {
            if (distribuidora.getIddistribuidora() == 0) {
                attr.addFlashAttribute("msg", "Distribuidora creada exitosamente");
            } else {
                attr.addFlashAttribute("msg", "Distribuidora actualizada exitosamente");
            }
            distribuidorasRepository.save(distribuidora);
            return "redirect:/distribuidoras/lista";
        }
    }

    @GetMapping("/borrar")
    public String borrarDistribuidora(@RequestParam("id") int id){
        Optional<Distribuidoras> opt = distribuidorasRepository.findById(id);
        if (opt.isPresent()) {
            distribuidorasRepository.deleteById(id);
        }
        return "redirect:/distribuidoras/lista";
    }




    /*
        @GetMapping(value = {"/lista"})
    public String listaDistribuidoras (Model model){
        model.addAttribute("listaDistribuidoras",distribuidorasDAO.listaDistribuidoras());
        return "distribuidoras/lista";
    }


    @GetMapping(value = {"", "/", "/vista"})
    public String vistaJuegos ( ...){

    }

    @GetMapping("/nuevo")
    public String nuevoDistribuidora(@ModelAttribute("distribuidoras") Juegos juegos, Model model){
        model.addAttribute("listaPaises", paisesDAO.listaPaises());
        return "juegos/editarFrm";
    }

    @PostMapping("/guardar")
    public String guardarDistribuidora(@ModelAttribute("distribuidoras") @Valid Distribuidoras distribuidoras,BindingResult bindingResult,
                                Model model,RedirectAttributes attributes){
        if(bindingResult.hasErrors()){
            model.addAttribute("listaPaises", paisesDAO.listaPaises());
            return "distribuidoras/editarFrm";
        }else{
            String msg="Distribuidora" + (distribuidoras.getIddistribuidora()==0 ? "creada":"actualizado") + "exitosamente";
            attributes.addFlashAttribute("msg",msg);
            distribuidorasDAO.guardarDistribuidora(distribuidoras);
            return "redirect:/distribuidoras/lista";
        }
    }
    @GetMapping("/editar")
    public String editarDistribuidora(@ModelAttribute("distribuidoras") Distribuidoras distribuidoras,
                                      Model model, @RequestParam("id") int id) {

        Distribuidoras distribuidorasBuscar = distribuidorasDAO.obtenerDistribuidoraPorId(id);

        if (distribuidorasBuscar != null) {
            distribuidoras=distribuidorasBuscar;
            model.addAttribute("distribuidoras", distribuidoras);
            model.addAttribute("listaPaises",paisesDAO.listaPaises());
            return "distribuidoras/editarFrm";
        } else {
            return "redirect:/distribuidoras";
        }
    }

    @GetMapping("/borrar")
    public String borrarDistribuidora(Model model, @RequestParam("id") int id, RedirectAttributes attr) {

        Distribuidoras distribuidorasBuscar = distribuidorasDAO.obtenerDistribuidoraPorId(id);

        if (distribuidorasBuscar != null) {
            distribuidorasDAO.borrarDistribuidora(id);
            attr.addFlashAttribute("msg", "Distribuidora borrada exitosamente");
        }
        return "redirect:/distribuidoras";

    }
*/


}
