package com.medicore.backend;

import com.medicore.backend.dto.CitaResponse;
import com.medicore.backend.exception.BusinessException;
import com.medicore.backend.model.CitaMedica;
import com.medicore.backend.model.HistoriaClinica;
import com.medicore.backend.service.AgendaService;
import com.medicore.backend.service.HistoriaClinicaService;
import com.medicore.backend.service.LaboratorioService;
import com.medicore.backend.service.PacienteService;
import com.medicore.backend.service.PrescripcionService;
import com.medicore.backend.facade.ClinicaFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClinicaFacadeTest {

    @Mock
    private PacienteService pacienteService;

    @Mock
    private AgendaService agendaService;

    @Mock
    private HistoriaClinicaService historiaClinicaService;

    @Mock
    private PrescripcionService prescripcionService;

    @Mock
    private LaboratorioService laboratorioService;

    @InjectMocks
    private ClinicaFacade clinicaFacade;

    @Test
    void agendarCitaDebeCoordinarPacienteYAgenda() {
        LocalDateTime fecha = LocalDateTime.of(2030, 1, 15, 9, 0);
        CitaMedica cita = new CitaMedica(1L, 1L, 10L, "Dra. Ana Gómez", "Medicina general", fecha, "CONFIRMADA",
                "Recordatorio");

        when(agendaService.agendarCita(1L, "Medicina general", fecha)).thenReturn(cita);
        when(historiaClinicaService.obtenerHistoriaPaciente(1L)).thenReturn(new HistoriaClinica(1L));

        CitaResponse response = clinicaFacade.agendarCita(1L, "Medicina general", fecha);

        assertNotNull(response);
        assertEquals("Dra. Ana Gómez", response.medicoNombre());
        assertEquals("Medicina general", response.especialidad());
        verify(pacienteService).validarPaciente(1L);
        verify(agendaService).agendarCita(1L, "Medicina general", fecha);
    }

    @Test
    void generarPrescripcionDebeRechazarMedicamentosConAlergias() {
        doNothing().when(pacienteService).validarPaciente(1L);
        when(historiaClinicaService.consultarAlergias(1L)).thenReturn(List.of("Penicilina"));
        doThrow(new BusinessException("Se detectó una alergia relacionada con el medicamento 'Penicilina'. No se puede continuar con la prescripción."))
                .when(prescripcionService).validarAlergias(eq(1L), eq(List.of("Penicilina")), eq(List.of("Penicilina")));

        BusinessException exception = assertThrows(BusinessException.class,
                () -> clinicaFacade.generarPrescripcion(1L, List.of("Penicilina")));

        assertTrue(exception.getMessage().contains("alergia"));
        verify(prescripcionService).validarAlergias(eq(1L), eq(List.of("Penicilina")), eq(List.of("Penicilina")));
    }
}
