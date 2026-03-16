package com.vet.vetweb;

import com.vet.vetweb.paciente.Dueno;
import com.vet.vetweb.paciente.Mascota;
import com.vet.vetweb.paciente.Paciente;
import com.vet.vetweb.paciente.PacienteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PublicController {

  private final PacienteService pacienteService;

  public PublicController(PacienteService pacienteService) {
    this.pacienteService = pacienteService;
  }

  @GetMapping("/")
  public String landing() {
    return "pages/landing";
  }

  @GetMapping("/login")
  public String login(Authentication authentication) {
    if (authentication != null && authentication.isAuthenticated()) {
      return "redirect:/panel";
    }
    return "views/login";
  }

  @GetMapping("/panel")
  public String panel() {
    return "pages/panel";
  }

  @GetMapping("panel/pacientes/registrar")
  public String registerPatient() {
    return "pages/pacientes/registrar";
  }

  @PostMapping("panel/pacientes/registrar")
  public String submitRegisterPatient(
      @RequestParam String mascotaNombre,
      @RequestParam String mascotaEspecie,
      @RequestParam(required = false) String mascotaRaza,
      @RequestParam(required = false) Integer mascotaEdad,
      @RequestParam String duenoNombre,
      @RequestParam String duenoRun,
      @RequestParam(required = false) String duenoFechaNacimiento,
      RedirectAttributes redirectAttributes
  ) {
    try {
      Mascota mascota = new Mascota(mascotaNombre, mascotaEspecie, mascotaRaza, mascotaEdad);
      Dueno dueno = new Dueno(duenoNombre, duenoRun, duenoFechaNacimiento);
      pacienteService.registrar(new Paciente(mascota, dueno));
      redirectAttributes.addFlashAttribute("notificacionTipo", "success");
      redirectAttributes.addFlashAttribute("notificacionMensaje", "El paciente fue registrado correctamente.");
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("notificacionTipo", "error");
      redirectAttributes.addFlashAttribute("notificacionMensaje", "No se pudo registrar el paciente. Intenta nuevamente.");
    }
    return "redirect:/panel/pacientes/registrar";
  }

  @GetMapping("/panel/pacientes")
  public String getPatient(Model model) {
      model.addAttribute("pacientes", pacienteService.listar());
      return "pages/pacientes/ver-todos";
  }
  
}
