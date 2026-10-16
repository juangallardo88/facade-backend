package com.medicore.backend.facade;

import com.medicore.backend.dto.CitaResponse;
import com.medicore.backend.dto.HistoriaClinicaResponse;
import com.medicore.backend.model.CitaMedica;
import com.medicore.backend.model.HistoriaClinica;
import com.medicore.backend.model.Prescripcion;
import com.medicore.backend.model.SolicitudExamen;
import com.medicore.backend.service.AgendaService;
import com.medicore.backend.service.HistoriaClinicaService;
import com.medicore.backend.service.LaboratorioService;
import com.medicore.backend.service.PacienteService;
import com.medicore.backend.service.PrescripcionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClinicaFacade {

    private final PacienteService pacienteService;
    private final AgendaService agendaService;
    private final HistoriaClinicaService historiaClinicaService;
    private final PrescripcionService prescripcionService;
    private final LaboratorioService laboratorioService;

    public ClinicaFacade(PacienteService pacienteService,
                         AgendaService agendaService,
                         HistoriaClinicaService historiaClinicaService,
                         PrescripcionService prescripcionService,
                         LaboratorioService laboratorioService) {
        this.pacienteService = pacienteService;
        this.agendaService = agendaService;
        this.historiaClinicaService = historiaClinicaService;
        this.prescripcionService = prescripcionService;
        this.laboratorioService = laboratorioService;
    }

    public CitaResponse agendarCita(Long pacienteId, String especialidad, LocalDateTime fecha) {
        pacienteService.validarPaciente(pacienteId);
        CitaMedica cita = agendaService.agendarCita(pacienteId, especialidad, fecha);

        HistoriaClinica historia = historiaClinicaService.obtenerHistoriaPaciente(pacienteId);
        historia.registrarCita("Cita agendada en " + cita.getEspecialidad() + " con " + cita.getMedicoNombre());

        return new CitaResponse(
                cita.getId(),
                cita.getPacienteId(),
                cita.getMedicoId(),
                cita.getMedicoNombre(),
                cita.getEspecialidad(),
                cita.getFechaHora(),
                cita.getEstado(),
                cita.getRecordatorio()
        );
    }

    public HistoriaClinicaResponse verHistoriaCompleta(Long pacienteId) {
        pacienteService.validarPaciente(pacienteId);

        HistoriaClinica historia = historiaClinicaService.consolidarHistoria(pacienteId);
        List<String> prescripciones = prescripcionService.consultarPorPaciente(pacienteId).stream()
                .map(prescripcion -> prescripcion.getMedicamentos() + " | dosis: " + prescripcion.getDosis() + " | frecuencia: " + prescripcion.getFrecuencia())
                .toList();
        List<String> examenDetalle = laboratorioService.consultarSolicitudesPorPaciente(pacienteId).stream()
                .flatMap(solicitud -> solicitud.getExamenes().stream())
                .toList();

        return new HistoriaClinicaResponse(
                pacienteId,
                historia.getDiagnosticos(),
                historia.getAlergias(),
                historia.getHistorialCitas(),
                prescripciones,
                examenDetalle
        );
    }

    public Prescripcion generarPrescripcion(Long pacienteId, List<String> medicamentos) {
        return generarPrescripcion(pacienteId, medicamentos, "Médico general de demostración", "1 tableta cada 12 horas",
                "Cada 12 horas", "7 días", "Prescripción simulada para fines académicos");
    }

    public Prescripcion generarPrescripcion(Long pacienteId, List<String> medicamentos, String medicoResponsable,
                                           String dosis, String frecuencia, String duracion, String observaciones) {
        pacienteService.validarPaciente(pacienteId);
        List<String> alergias = historiaClinicaService.consultarAlergias(pacienteId);
        prescripcionService.validarAlergias(pacienteId, medicamentos, alergias);

        Prescripcion prescripcion = prescripcionService.generarPrescripcion(
                pacienteId,
                medicoResponsable,
                medicamentos,
                dosis,
                frecuencia,
                duracion,
                observaciones
        );

        HistoriaClinica historia = historiaClinicaService.obtenerHistoriaPaciente(pacienteId);
        historia.registrarDiagnostico("Prescripción generada: " + String.join(", ", medicamentos));
        return prescripcion;
    }

    public SolicitudExamen solicitarExamenes(Long pacienteId, List<String> examenes) {
        pacienteService.validarPaciente(pacienteId);
        SolicitudExamen solicitud = laboratorioService.registrarSolicitud(pacienteId, examenes);

        HistoriaClinica historia = historiaClinicaService.obtenerHistoriaPaciente(pacienteId);
        historia.registrarDiagnostico("Solicitud de exámenes: " + String.join(", ", examenes));
        return solicitud;
    }
}
