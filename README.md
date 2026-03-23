# Préstamos Personales Service — Proyecto de Aprendizaje

## Descripción
Microservicio de ejemplo desarrollado con fines formativos durante un período 
de prácticas. **No contiene datos, credenciales ni información perteneciente 
a ninguna empresa.**

Simula la gestión de préstamos personales en un entorno bancario ficticio,
incluyendo evaluación de riesgo crediticio contra un sistema legado SOAP
y cálculo de cuadro de amortización con fórmula francesa.

## Stack tecnológico
- Java 17 + Spring Boot 3.2.x
- Arquitectura Hexagonal + DDD (agregados, value objects, puertos)
- Apache Camel 4.4.0 — orquestación con reintentos y Dead Letter Channel
- Apache CXF 4.0.4 — cliente SOAP generado desde WSDL simulado
- JPA + H2 en memoria
- JUnit 5 + Mockito

## Estructura del proyecto
```
com.cajarural.prestamos
├── domain/          → modelo de negocio puro, sin dependencias externas
│   ├── model/       → agregado Prestamo, value objects, enums
│   └── port/        → interfaces de entrada y salida
├── application/     → casos de uso
└── infrastructure/  → adaptadores REST, JPA y SOAP/Camel
```

## Endpoints
| Método | URL | Descripción |
|--------|-----|-------------|
| POST | `/prestamos` | Solicitar un nuevo préstamo |
| GET | `/prestamos/{id}/amortizacion` | Obtener cuadro de amortización |

## Ejemplo de uso
**POST /prestamos**
```json
{
    "nifTitular": "12345678A",
    "importe": "10000.00",
    "plazoMeses": 24,
    "tasaInteresAnual": 5.5
}
```

## Aclaraciones importantes
Este proyecto es únicamente un ejercicio de aprendizaje personal.
- El WSDL es simulado y no corresponde a ningún sistema real
- La URL del sistema legado apunta a un entorno local inexistente
- No se han utilizado datos, contratos ni arquitecturas reales de ninguna empresa
- La base de datos es H2 en memoria, sin persistencia real
- Los NIFs y datos usados en los tests son completamente ficticios

## Cómo ejecutar
```bash
# Generar clases desde el WSDL
mvn generate-sources

# Ejecutar la aplicación
mvn spring-boot:run

# Ejecutar los tests
mvn test

# Consola H2 (con la app corriendo)
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:prestamosdb
```


