package com.projects.patients_app.web;

import com.projects.patients_app.entities.Patient;
import com.projects.patients_app.repositories.PatientRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PatientController {
    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("index")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "5") int size,
                        @RequestParam(name = "keyword", defaultValue = "")String keyword) {
        Page<Patient> patientList = patientRepository.findByFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCase(keyword, keyword, PageRequest.of(page,size));
        model.addAttribute("PatientsList", patientList);
        model.addAttribute("pages", new int[patientList.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "patients";
    }

    @GetMapping("deletePatient")
    public String deletePatient(@RequestParam(name = "id") Long id, String keyword, int page) {
        patientRepository.deleteById(id);
        return "redirect:/index?page="+page+"&keyword="+keyword;
    }
    @GetMapping("formPatient")
    public String formPatient(Model model){
        model.addAttribute("patient", new Patient());
        return "savePatient";
    }
    @PostMapping("savePatient")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String savePatient(@Valid /*@ModelAttribute("patient")*/ Patient patient, BindingResult bindingResult){
        System.out.println("saving patient " + patient.toString());
        if(bindingResult.hasErrors()) return "savePatient";
        System.out.println("saving patient " + patient.toString());
        patientRepository.save(patient);
        return "savePatient";
    }
//@PostMapping("/savePatient")
//public String savePatient(@Valid @ModelAttribute("patient") Patient patient, BindingResult bindingResult) {
//    if (bindingResult.hasErrors()) {
//        System.out.println("Validation errors: " + bindingResult.getAllErrors());
//        return "savePatient";
//    }
//    System.out.println("Saving patient: " + patient.toString());
//    patientRepository.save(patient);
//    return "redirect:/formPatient";  // redirect to a new page or the same form
//}

    @GetMapping("editPatient")
    public String editPatient(@RequestParam(name = "id") Long id, Model model){
        Patient patient = patientRepository.findById(id).orElse(null);
        if(patient==null) return "redirect:/index";
        model.addAttribute("patient", patient);
        return "editPatient";
    }
    @GetMapping("")
    public String home(){
        return "redirect:/index";
    }
}