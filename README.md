# MediCore Backend

Backend del proyecto universitario MediCore, desarrollado con Java 21, Spring Boot 3.4 y patrón Facade.

## Características

- API REST para gestión médica
- Patrón Facade central: `ClinicaFacade`
- Subsistemas para pacientes, agenda, historia clínica, prescripciones y laboratorio
- Swagger/OpenAPI habilitado en `/swagger-ui.html`
- Actuator habilitado en `/actuator/health`

## Ejecución

```bash
./mvnw spring-boot:run
```

O con Maven instalado:

```bash
mvn spring-boot:run
```

## Endpoints principales

- `POST /api/clinica/paciente`
- `POST /api/clinica/cita`
- `GET /api/clinica/historia/{pacienteId}`
- `POST /api/clinica/prescripcion`
- `POST /api/clinica/laboratorio`
- `GET /api/clinica/medicos?especialidad=`

## Base de datos

La aplicación está preparada para PostgreSQL. Si no se define la variable de entorno `MEDICORE_DB_URL`, arranca con H2 en memoria para pruebas locales.
