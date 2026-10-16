package com.medicore.backend.controller;

import com.medicore.backend.dto.CitaRequest;
import com.medicore.backend.dto.CitaResponse;
import com.medicore.backend.dto.ExamenRequest;
import com.medicore.backend.dto.HistoriaClinicaResponse;
import com.medicore.backend.dto.MedicoResponse;
import com.medicore.backend.dto.PacienteRequest;
import com.medicore.backend.dto.PacienteResponse;
import com.medicore.backend.dto.PrescripcionRequest;
import com.medicore.backend.facade.ClinicaFacade;
import com.medicore.backend.model.CitaMedica;
import com.medicore.backend.model.Medico;
import com.medicore.backend.model.Prescripcion;
import com.medicore.backend.model.SolicitudExamen;
import com.medicore.backend.model.Paciente;
import com.medicore.backend.service.AgendaService;
import com.medicore.backend.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clinica")
public class ClinicaController {

    private final PacienteService pacienteService;
    private final AgendaService agendaService;
    private final ClinicaFacade clinicaFacade;

    public ClinicaController(PacienteService pacienteService, AgendaService agendaService, ClinicaFacade clinicaFacade) {
        this.pacienteService = pacienteService;
        this.agendaService = agendaService;
        this.clinicaFacade = clinicaFacade;
    }

    @PostMapping("/paciente")
    public ResponseEntity<PacienteResponse> registrarPaciente(@Valid @RequestBody PacienteRequest request) {
        Paciente paciente = pacienteService.registrarPaciente(request);
        return ResponseEntity.ok(new PacienteResponse(
                paciente.getId(),
                paciente.getNombreCompleto(),
                paciente.getTipoDocumento(),
                paciente.getNumeroDocumento(),
                paciente.getFechaNacimiento(),
                paciente.getTelefono(),
                paciente.getCorreo()
        ));
    }

    @PostMapping("/cita")
    public ResponseEntity<CitaResponse> agendarCita(@Valid @RequestBody CitaRequest request) {
        CitaResponse response = clinicaFacade.agendarCita(request.pacienteId(), request.especialidad(), request.fecha());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/historia/{pacienteId}")
    public ResponseEntity<HistoriaClinicaResponse> verHistoriaCompleta(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(clinicaFacade.verHistoriaCompleta(pacienteId));
    }

    @PostMapping("/prescripcion")
    public ResponseEntity<Prescripcion> generarPrescripcion(@Valid @RequestBody PrescripcionRequest request) {
        Prescripcion prescripcion = clinicaFacade.generarPrescripcion(
                request.pacienteId(),
                request.medicamentos(),
                request.medicoResponsable(),
                request.dosis(),
                request.frecuencia(),
                request.duracion(),
                request.observaciones()
        );
        return ResponseEntity.ok(prescripcion);
    }

    @PostMapping("/laboratorio")
    public ResponseEntity<SolicitudExamen> solicitarExamenes(@Valid @RequestBody ExamenRequest request) {
        SolicitudExamen solicitud = clinicaFacade.solicitarExamenes(request.pacienteId(), request.examenes());
        return ResponseEntity.ok(solicitud);
    }

    @GetMapping("/medicos")
    public ResponseEntity<List<MedicoResponse>> consultarMedicos(@RequestParam(required = false) String especialidad) {
        List<Medico> medicos = agendaService.consultarMedicosPorEspecialidad(especialidad == null ? "" : especialidad);
        List<MedicoResponse> response = medicos.stream()
                .map(medico -> new MedicoResponse(
                        medico.getId(),
                        medico.getNombre(),
                        medico.getEspecialidad(),
                        medico.getHoraInicio(),
                        medico.getHoraFin(),
                        medico.isDatosDemostracion()))
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/citas/{pacienteId}")
    public ResponseEntity<List<CitaMedica>> consultarCitasPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(agendaService.consultarCitasPaciente(pacienteId));
    }

    @DeleteMapping("/citas/{citaId}")
    public ResponseEntity<Void> cancelarCita(@PathVariable Long citaId) {
        agendaService.cancelarCita(citaId);
        return ResponseEntity.noContent().build();
    }
}
