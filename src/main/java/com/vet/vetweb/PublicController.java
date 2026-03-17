package com.vet.vetweb;

import com.vet.vetweb.agenda.Cita;
import com.vet.vetweb.agenda.CitaService;
import com.vet.vetweb.agenda.VeterinarioService;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Controller
public class PublicController {

  private final PacienteService pacienteService;
  private final VeterinarioService veterinarioService;
  private final CitaService citaService;

  public PublicController(PacienteService pacienteService, VeterinarioService veterinarioService, CitaService citaService) {
    this.pacienteService = pacienteService;
    this.veterinarioService = veterinarioService;
    this.citaService = citaService;
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

  @GetMapping("/panel/agenda/solicitar")
  public String solicitarAgenda(
      @RequestParam(required = false, defaultValue = "1") int paso,
      @RequestParam(required = false) String nombre,
      @RequestParam(required = false) String run,
      @RequestParam(required = false) String mascotaNombre,
      @RequestParam(required = false) String duenoRun,
      @RequestParam(required = false) String especialidad,
      @RequestParam(required = false) String fecha,
      @RequestParam(required = false) String vetNombre,
      @RequestParam(required = false) String hora,
      Model model
  ) {
      model.addAttribute("paso", paso);

      if (paso == 1) {
          model.addAttribute("nombre", nombre);
          model.addAttribute("run", run);
          if ((nombre != null && !nombre.isBlank()) || (run != null && !run.isBlank())) {
              model.addAttribute("resultados", pacienteService.buscar(nombre, run));
              model.addAttribute("buscado", true);
          }
      } else if (paso == 2) {
          model.addAttribute("mascotaNombre", mascotaNombre);
          model.addAttribute("duenoRun", duenoRun);
          pacienteService.buscarPorNombreYRun(mascotaNombre, duenoRun)
              .ifPresent(p -> model.addAttribute("mascotaSeleccionada", p));
          model.addAttribute("especialidad", especialidad);
          model.addAttribute("fecha", fecha);
          if (especialidad != null && !especialidad.isBlank() && fecha != null && !fecha.isBlank()) {
              model.addAttribute("veterinarios", veterinarioService.buscarDisponibles(especialidad, LocalDate.parse(fecha)));
              model.addAttribute("buscadoVet", true);
          }
      } else if (paso == 3) {
          model.addAttribute("mascotaNombre", mascotaNombre);
          model.addAttribute("duenoRun", duenoRun);
          model.addAttribute("especialidad", especialidad);
          model.addAttribute("fecha", fecha);
          model.addAttribute("vetNombre", vetNombre);
          model.addAttribute("hora", hora);
          pacienteService.buscarPorNombreYRun(mascotaNombre, duenoRun)
              .ifPresent(p -> model.addAttribute("mascotaSeleccionada", p));
      }

      return "pages/agenda/solicitar-agenda";
  }

  @PostMapping("/panel/agenda/confirmar")
  public String confirmarCita(
      @RequestParam String mascotaNombre,
      @RequestParam String duenoRun,
      @RequestParam String veterinarioNombre,
      @RequestParam String especialidad,
      @RequestParam String fecha,
      @RequestParam String hora,
      RedirectAttributes redirectAttributes
  ) {
      citaService.agendar(new Cita(
          mascotaNombre,
          duenoRun,
          veterinarioNombre,
          especialidad,
          LocalDate.parse(fecha),
          LocalTime.parse(hora)
      ));
      redirectAttributes.addFlashAttribute("notificacionTipo", "success");
      redirectAttributes.addFlashAttribute("notificacionMensaje", "Cita agendada correctamente para " + mascotaNombre + " el " + fecha + " a las " + hora + ".");
      return "redirect:/panel/agenda/solicitar";
  }

  @GetMapping("/panel/agenda/hoy")
  public String agendaHoy(Authentication authentication, Model model) {
      DateTimeFormatter fmt = DateTimeFormatter.ofPattern("EEEE d 'de' MMMM yyyy", Locale.of("es", "CL"));
      model.addAttribute("fechaHoy", LocalDate.now().format(fmt));

      boolean esVet = authentication.getAuthorities().stream()
          .anyMatch(a -> a.getAuthority().equals(Role.VET.authority()));

      if (esVet) {
          veterinarioService.buscarPorUsername(authentication.getName())
              .ifPresentOrElse(
                  vet -> model.addAttribute("citas", citaService.listarHoyPorVet(vet.nombre())),
                  ()  -> model.addAttribute("citas", java.util.List.of())
              );
          model.addAttribute("soloMiAgenda", true);
      } else {
          model.addAttribute("citas", citaService.listarHoy());
          model.addAttribute("soloMiAgenda", false);
      }

      return "pages/agenda/agenda-hoy";
  }
}
