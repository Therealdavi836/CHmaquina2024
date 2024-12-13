# CHMAQUINA V2024

## Descripción del Proyecto
CHMAQUINA es un simulador gráfico de un computador ficticio con un procesador elemental y memoria principal. Permite ejecutar programas escritos en un pseudo-lenguaje llamado CHMAQUINA, diseñado para practicar conceptos de sistemas operativos y planificación de procesos.

## Funcionalidades Principales
1. **Simulación de Memoria y Procesador**:
   - Memoria configurable con hasta `1000*Z + 100` posiciones (donde `Z` es el último dígito de la cédula del usuario).
   - Simulación de un acumulador para operaciones aritméticas y lógicas.

2. **Soporte para Programas CHMAQUINA**:
   - Carga de programas desde archivos `.ch`.
   - Chequeo de sintaxis con reportes de errores.
   - Ejecución en modalidad continua o paso a paso.

3. **Planificación de Procesos**:
   - Métodos soportados: FCFS, Round-Robin, SJF (expropiativo/no expropiativo) y por prioridad.
   - Soporte para múltiples programas con gestión de memoria y cola circular.

4. **Administración de Memoria**:
   - Métodos soportados: particiones fijas, variables y paginación de un nivel.
   - Visualización de mapas de memoria y tablas de procesos.

5. **Interfaz Gráfica**:
   - Carga, edición y ejecución de programas.
   - Visualización del estado del sistema y cambios de contexto.

## Requisitos del Proyecto
1. **Manual Técnico y de Usuario**:
   - Descripción técnica con diagramas y código.
   - Guía de instalación y uso.

2. **Bitácora Semanal**:
   - Registro semanal de avances en un blog.
   - Penalización del 10% en la nota por semana sin bitácora.

3. **Fases del Proyecto**:
   - **Fase A**: Interfaz gráfica, carga, chequeo de sintaxis y ejecución.
   - **Fase B**: Planificación de procesos.
   - **Fase C**: Administración de memoria.
-Presentado al docente Carlos Hernan Gomez Gomez.

## Ejemplos de Programas
### Factorial
Calcula el factorial de un número.
```ch
nueva unidad I 1
nueva m I 5
nueva respuesta I 1
nueva intermedia I 0
...
retorne 0

