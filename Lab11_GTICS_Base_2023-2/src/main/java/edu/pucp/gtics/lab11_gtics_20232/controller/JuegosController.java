package edu.pucp.gtics.lab11_gtics_20232.controller;

import edu.pucp.gtics.lab11_gtics_20232.Daos.DistribuidorasDAO;
import edu.pucp.gtics.lab11_gtics_20232.Daos.GenerosDAO;
import edu.pucp.gtics.lab11_gtics_20232.Daos.JuegosDAO;
import edu.pucp.gtics.lab11_gtics_20232.Daos.PlataformasDAO;
import edu.pucp.gtics.lab11_gtics_20232.entity.*;
import edu.pucp.gtics.lab11_gtics_20232.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/juegos")
public class JuegosController {

    final JuegosRepository juegosRepository;
    final PlataformasRepository plataformasRepository;
    final DistribuidorasRepository distribuidorasRepository;
    final GenerosRepository generosRepository;
    final UserRepository userRepository;
    final JuegosDAO juegosDAO;
    final GenerosDAO generosDAO;
    final PlataformasDAO plataformasDAO;
    final DistribuidorasDAO distribuidorasDAO;

    public JuegosController(JuegosRepository juegosRepository, PlataformasRepository plataformasRepository, DistribuidorasRepository distribuidorasRepository, GenerosRepository generosRepository, UserRepository userRepository, JuegosDAO juegosDAO, GenerosDAO generosDAO, PlataformasDAO plataformasDAO, DistribuidorasDAO distribuidorasDAO) {
        this.juegosRepository = juegosRepository;
        this.plataformasRepository = plataformasRepository;
        this.distribuidorasRepository = distribuidorasRepository;
        this.generosRepository = generosRepository;
        this.userRepository = userRepository;
        this.juegosDAO = juegosDAO;
        this.generosDAO = generosDAO;
        this.plataformasDAO = plataformasDAO;
        this.distribuidorasDAO = distribuidorasDAO;
    }

    @GetMapping(value = {"/list"})
    public String listaJuegos(Model model) {
        model.addAttribute("listaJuegos", juegosDAO.listaJuegos());
        return "juegos/lista";
    }

    @GetMapping("/borrar")
    public String borrarJuego(Model model, @RequestParam("id") int id, RedirectAttributes attr) {

        Juegos juegoBuscar = juegosDAO.obtenerJuegoPorId(id);

        if (juegoBuscar != null) {
            juegosDAO.borrarJuego(id);
            attr.addFlashAttribute("msg", "Producto borrado exitosamente");
        }
        return "redirect:/juegos";

    }
    @GetMapping("/nuevo")
    public String nuevoJuego(@ModelAttribute("juego") Juegos juegos, Model model){
        model.addAttribute("listaGeneros", generosDAO.listaGenero());
        model.addAttribute("listaPlataformias",plataformasDAO.listaPlataformas());
        model.addAttribute("listaDistribuidoras",distribuidorasDAO.listaDistribuidoras());
        return "juegos/editarFrm";
    }

    @PostMapping("/guardar")
    public String guardarJuegos(@ModelAttribute("juego") @Valid Juegos juegos,BindingResult bindingResult,
                                Model model,RedirectAttributes attributes){
        if(bindingResult.hasErrors()){
            model.addAttribute("listaGeneros", generosDAO.listaGenero());
            model.addAttribute("listaPlataformias",plataformasDAO.listaPlataformas());
            model.addAttribute("listaDistribuidoras",distribuidorasDAO.listaDistribuidoras());
            return "juegos/editarFrm";
        }else{
            String msg="Juego" + (juegos.getIdjuego()==0 ? "creado":"actualizado") + "exitosamente";
            attributes.addFlashAttribute("msg",msg);
            juegosDAO.guardarJuego(juegos);
            return "redirect:/juegos/lista";
        }
    }


    /*@GetMapping("/editar")
    public String editarJuegos(@RequestParam("id") int id, Model model){
        Optional<Juegos> opt = juegosRepository.findById(id);
        List<Plataformas> listaPlataformas = plataformasRepository.findAll();
        List<Distribuidoras> listaDistribuidoras = distribuidorasRepository.findAll();
        List<Generos> listaGeneros = generosRepository.findAll();
        if (opt.isPresent()){
            Juegos juego = opt.get();
            model.addAttribute("juego", juego);
            model.addAttribute("listaPlataformas", listaPlataformas);
            model.addAttribute("listaDistribuidoras", listaDistribuidoras);
            model.addAttribute("listaGeneros", listaGeneros);
            return "juegos/editarFrm";
        }else {
            return "redirect:/juegos/lista";
        }
    }

    @PostMapping("/juegos/guardar")
    public String guardarJuegos(Model model, RedirectAttributes attr, @ModelAttribute("juego") @Valid Juegos juego, BindingResult bindingResult ){
        if(bindingResult.hasErrors( )){
            List<Plataformas> listaPlataformas = plataformasRepository.findAll();
            List<Distribuidoras> listaDistribuidoras = distribuidorasRepository.findAll();
            List<Generos> listaGeneros = generosRepository.findAll();
            model.addAttribute("juego", juego);
            model.addAttribute("listaPlataformas", listaPlataformas);
            model.addAttribute("listaDistribuidoras", listaDistribuidoras);
            model.addAttribute("listaGeneros", listaGeneros);
            return "juegos/editarFrm";
        } else {
            if (juego.getIdjuego() == 0) {
                attr.addFlashAttribute("msg", "Juego creado exitosamente");
            } else {
                attr.addFlashAttribute("msg", "Juego actualizado exitosamente");
            }
            juegosRepository.save(juego);
            return "redirect:/juegos/lista";
        }
    }





    @GetMapping("/borrar")
    public String borrarDistribuidora(@RequestParam("id") int id, RedirectAttributes attr){
        Optional<Juegos> opt = juegosRepository.findById(id);
        if (opt.isPresent()) {
            juegosRepository.deleteById(id);
            attr.addFlashAttribute("msg", "Juego borrado exitosamente");

        }
        return "redirect:/juegos/lista";
    }
    @GetMapping(value = {"", "/", "/vista"})
    public String vistaJuegos (){
        return "juegos/lista";
    }

    @GetMapping("/nuevo")
    public String nuevoJuegos(Model model, @ModelAttribute("juego") Juegos juego){
        List<Plataformas> listaPlataformas = plataformasRepository.findAll();
        List<Distribuidoras> listaDistribuidoras = distribuidorasRepository.findAll();
        List<Generos> listaGeneros = generosRepository.findAll();
        model.addAttribute("listaPlataformas", listaPlataformas);
        model.addAttribute("listaDistribuidoras", listaDistribuidoras);
        model.addAttribute("listaGeneros", listaGeneros);
        return "juegos/editarFrm";
    }
    @GetMapping("/editar")
    public String editarJuegos(@RequestParam("id") int id, Model model) {
        Optional<Juegos> opt = juegosRepository.findById(id);
        List<Plataformas> listaPlataformas = plataformasRepository.findAll();
        List<Distribuidoras> listaDistribuidoras = distribuidorasRepository.findAll();
        List<Generos> listaGeneros = generosRepository.findAll();
        if (opt.isPresent()) {
            Juegos juego = opt.get();
            model.addAttribute("juego", juego);
            model.addAttribute("listaPlataformas", listaPlataformas);
            model.addAttribute("listaDistribuidoras", listaDistribuidoras);
            model.addAttribute("listaGeneros", listaGeneros);
            return "juegos/editarFrm";
        } else {
            return "redirect:/juegos/lista";
        }
    }
    @PostMapping("/guardar")
    public String guardarJuegos(Model model, RedirectAttributes attr, @ModelAttribute("juego") @Valid Juegos juego, BindingResult bindingResult ){
        if(bindingResult.hasErrors( )){
            List<Plataformas> listaPlataformas = plataformasRepository.findAll();
            List<Distribuidoras> listaDistribuidoras = distribuidorasRepository.findAll();
            List<Generos> listaGeneros = generosRepository.findAll();
            model.addAttribute("juego", juego);
            model.addAttribute("listaPlataformas", listaPlataformas);
            model.addAttribute("listaDistribuidoras", listaDistribuidoras);
            model.addAttribute("listaGeneros", listaGeneros);
            return "juegos/editarFrm";
        } else {
            if (juego.getIdjuego() == 0) {
                attr.addFlashAttribute("msg", "Juego creado exitosamente");
            } else {
                attr.addFlashAttribute("msg", "Juego actualizado exitosamente");
            }
            juegosRepository.save(juego);
            return "redirect:/juegos/lista";
        }
    }
    */


}
