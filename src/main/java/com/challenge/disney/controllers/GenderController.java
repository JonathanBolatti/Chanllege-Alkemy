package com.challenge.disney.controllers;

import com.challenge.disney.entities.Gender;
import com.challenge.disney.exception.ErrorService;
import com.challenge.disney.services.GenderService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Jonathan
 */
@Controller
@RequestMapping("/gender")
public class GenderController {
	
	@Autowired GenderService genderService; 
	
	//@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/list")
    public String listarGeneroes(Model model,@RequestParam(required = false) String query) {
        if (query != null) {
            model.addAttribute("generos", genderService.findAllByQ(query));
        }else{
            model.addAttribute("generos", genderService.listAll());
        }
        return "gender-list.html";
    }

    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/form")
    public String crearGenero(Model model, @RequestParam(required = false) String id) {
        if (id != null) {
            Optional<Gender> optional = genderService.findById(id);
            if (optional.isPresent()) {
                model.addAttribute("genero",optional.get());
            }else {
            return "redirect:/genero/list";
            } 
        }else{
           model.addAttribute("genero",new Gender()); 
        }
        return "gender-form";
    }

    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/save")
    public String guardarGenero(RedirectAttributes redirectAttributes, Gender gender) {
        
        try {
            genderService.save(gender);
            redirectAttributes.addFlashAttribute("success", "Genero guardado con exito");  
        } catch (ErrorService ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());        }
        return "redirect:/gender/form";
    }

    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/delete")
    public String delete(@RequestParam(required = true) String id) {
        genderService.deleteById(id);
        return "redirect:/gender/list";
    }
}
	

