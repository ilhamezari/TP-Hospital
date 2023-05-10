package ma.enset.hospital.Web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.enset.hospital.repository.patientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import ma.enset.hospital.entities.Patient;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {

 private patientRepository patientRep;

@GetMapping("/user/index")
 public String index(Model model,
                     @RequestParam(name="page", defaultValue = "0") int p,
                     @RequestParam(name="size", defaultValue = "4") int s,
                     @RequestParam(name="keyword", defaultValue = "") String ky
                     ){
 Page<Patient> pagePatients=patientRep.findByNomContains(ky,PageRequest.of(p,s));
model.addAttribute("listPatients",pagePatients.getContent());
 model.addAttribute("pages",new int[pagePatients.getTotalPages()]);
 model.addAttribute("currentPage",p);
 model.addAttribute("keyword",ky);
 return "patients";}
 @GetMapping("/admin/delete")
 @PreAuthorize("hasRole('ROLE_ADMIN')")
public String delete(Long id, String keyword, int page){
 patientRep.deleteById(id);
 return "redirect:/user/index?page="+page+"&keyword="+keyword;
}

@GetMapping("/")
 public String home()
{ return "redirect:/user/index";}
 @GetMapping("/patients")
 @ResponseBody
 public List<Patient> patientList()
 {return patientRep.findAll();}

@GetMapping("/admin/formPatients")
@PreAuthorize("hasRole('ROLE_ADMIN')")
 public String formPatients(Model model){
 model.addAttribute("patient",new Patient());
 return "formPatients";

 }
 @PostMapping(path="/admin/save")
 @PreAuthorize("hasRole('ROLE_ADMIN')")
 public String save(Model model, @Valid Patient patient, BindingResult bindingResult,
                    @RequestParam(defaultValue = "0") int page,
                   @RequestParam(defaultValue = "") String keyword){
 if(bindingResult.hasErrors()) return "formPatients";
 patientRep.save(patient);
 return "redirect:/user/index?page="+page+"&keyword="+keyword;
 }

 @GetMapping("/admin/editPatients")
 @PreAuthorize("hasRole('ROLE_ADMIN')")
 public String editPatients(Model model, Long id, String keyword, int page){
 Patient patient= patientRep.findById(id).orElse(null);
 if (patient==null) throw new RuntimeException("Patient introuvable");
  model.addAttribute("patient",patient);
  model.addAttribute("page",page);
  model.addAttribute("keyword",keyword);
  return "editPatients";
 }
}

