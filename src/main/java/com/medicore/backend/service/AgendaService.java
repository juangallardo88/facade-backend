package com.medicore.backend.service;

import com.medicore.backend.exception.BusinessException;
import com.medicore.backend.model.CitaMedica;
import com.medicore.backend.model.Medico;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class AgendaService {

    private final Map<Long, Medico> medicos = new ConcurrentHashMap<>();
    private final Map<Long, CitaMedica> citas = new ConcurrentHashMap<>();
    private final AtomicLong medicoSequence = new AtomicLong(1L);
    private final AtomicLong citaSequence = new AtomicLong(1L);

    public AgendaService() {
        seedMedicosDemostracion();
    }

    private void seedMedicosDemostracion() {
        registrarMedico(new Medico(medicoSequence.getAndIncrement(), "Dra. Ana Gómez", "Medicina general", LocalTime.of(8, 0), LocalTime.of(17, 0), true));
        registrarMedico(new Medico(medicoSequence.getAndIncrement(), "Dr. Luis Moreno", "Medicina general", LocalTime.of(8, 30), LocalTime.of(18, 0), true));
        registrarMedico(new Medico(medicoSequence.getAndIncrement(), "Dra. Sara Rojas", "Pediatría", LocalTime.of(9, 0), LocalTime.of(16, 0), true));
        registrarMedico(new Medico(medicoSequence.getAndIncrement(), "Dr. Carlos Vega", "Pediatría", LocalTime.of(10, 0), LocalTime.of(15, 0), true));
        registrarMedico(new Medico(medicoSequence.getAndIncrement(), "Dra. Elena Ruiz", "Dermatología", LocalTime.of(8, 0), LocalTime.of(14, 0), true));
    }

    private void registrarMedico(Medico medico) {
        medicos.put(medico.getId(), medico);
    }

    public List<Medico> consultarMedicosPorEspecialidad(String especialidad) {
        String especialidadNormalizada = especialidad == null ? "" : especialidad.trim();
        return medicos.values().stream()
                .filter(medico -> especialidadNormalizada.isBlank()
                        || medico.getEspecialidad().equalsIgnoreCase(especialidadNormalizada))
                .sorted(Comparator.comparing(Medico::getId))
                .toList();
    }

    public List<LocalTime> consultarHorariosDisponibles(Long medicoId, LocalDate fecha) {
        Medico medico = medicos.get(medicoId);
        if (medico == null) {
            throw new BusinessException("El médico indicado no existe");
        }

        List<LocalTime> horarios = new ArrayList<>();
        LocalTime horaActual = medico.getHoraInicio();
        while (!horaActual.isAfter(medico.getHoraFin().minusMinutes(30))) {
            LocalDateTime fechaHora = LocalDateTime.of(fecha, horaActual);
            if (!existeCitaReservada(medicoId, fechaHora)) {
                horarios.add(horaActual);
            }
            horaActual = horaActual.plusMinutes(30);
        }
        return horarios;
    }

    public CitaMedica agendarCita(Long pacienteId, String especialidad, LocalDateTime fecha) {
        validarFechaCita(fecha);
        List<Medico> medicosEspecialidad = consultarMedicosPorEspecialidad(especialidad);
        if (medicosEspecialidad.isEmpty()) {
            throw new BusinessException("No existen médicos disponibles para la especialidad solicitada");
        }

        Medico medicoDisponible = medicosEspecialidad.stream()
                .filter(medico -> !existeCitaReservada(medico.getId(), fecha))
                .findFirst()
                .orElseThrow(() -> new BusinessException("El horario seleccionado no está disponible para esa especialidad"));

        Long citaId = citaSequence.getAndIncrement();
        CitaMedica cita = new CitaMedica(
                citaId,
                pacienteId,
                medicoDisponible.getId(),
                medicoDisponible.getNombre(),
                medicoDisponible.getEspecialidad(),
                fecha,
                "CONFIRMADA",
                "Recordatorio: llegue 10 minutos antes de la cita y lleve su documento de identidad."
        );

        citas.put(citaId, cita);
        return cita;
    }

    public List<CitaMedica> consultarCitasPaciente(Long pacienteId) {
        return citas.values().stream()
                .filter(cita -> cita.getPacienteId().equals(pacienteId))
                .sorted(Comparator.comparing(CitaMedica::getFechaHora))
                .toList();
    }

    public List<CitaMedica> consultarCitasAnteriores(Long pacienteId) {
        LocalDateTime ahora = LocalDateTime.now();
        return consultarCitasPaciente(pacienteId).stream()
                .filter(cita -> cita.getFechaHora().isBefore(ahora))
                .toList();
    }

    public void cancelarCita(Long citaId) {
        if (!citas.containsKey(citaId)) {
            throw new BusinessException("La cita indicada no existe");
        }
        citas.remove(citaId);
    }

    public void validarFechaCita(LocalDateTime fecha) {
        if (fecha == null) {
            throw new BusinessException("La fecha de la cita es obligatoria");
        }
        if (fecha.isBefore(LocalDateTime.now())) {
            throw new BusinessException("La fecha de la cita no puede ser anterior a la fecha actual");
        }
    }

    public boolean existeCitaReservada(Long medicoId, LocalDateTime fechaHora) {
        return citas.values().stream()
                .anyMatch(cita -> cita.getMedicoId().equals(medicoId) && cita.getFechaHora().equals(fechaHora));
    }
}
