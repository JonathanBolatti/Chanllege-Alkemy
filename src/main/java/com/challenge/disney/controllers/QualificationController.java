package com.challenge.disney.controllers;

import com.challenge.disney.entities.Qualification;
import com.challenge.disney.exception.ErrorService;
import com.challenge.disney.services.QualificationService;
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
@RequestMapping("qualification")
public class QualificationController {
	
	@Autowired
	private QualificationService qualificationService;
	
	 //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/list")
    public String listarCalificaciones(Model model,@RequestParam(required = false) String q) {
        if (q != null) {
            model.addAttribute("calificaciones", qualificationService.listAll(q));
        }else{
            model.addAttribute("calificaciones", qualificationService.listAll());
        }
        return "qualification-list";
    }

    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/form")
    public String crearCalificacion(Model model, @RequestParam(required = false) String id) {
        if (id != null) {
            Optional<Qualification> optional = qualificationService.findById(id);
            if (optional.isPresent()) {
                model.addAttribute("calificacion",optional.get());
            }else {
            return "redirect:/calificacion/list";
            } 
        }else{
           model.addAttribute("calificacion",new Qualification()); 
        }
        return "qualification-form";
    }

    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/save")
    public String guardarCalificacion(RedirectAttributes redirectAttributes,Qualification qualification) {
        
        try {
            qualificationService.save(qualification);
            redirectAttributes.addFlashAttribute("success", "Calificacion guardada con exito");  
        } catch (ErrorService ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());        }
        return "redirect:/qualification/form";
    }

    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/delete")
    public String delete(@RequestParam(required = true) String id) {
        qualificationService.deleteById(id);
        return "redirect:/qualification/list";
    }
	
	
}
