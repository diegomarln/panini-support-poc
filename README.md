# Panini Support PoC

## Descripción general

Panini Support PoC es una prueba de concepto móvil para centralizar la gestión de tickets internos relacionados con la operación del álbum oficial de la Copa Mundial FIFA 2026.

La solución permite establecer una base Android clara para registrar, consultar y actualizar solicitudes de soporte asociadas con proveedores, distribución de paquetes, faltantes de inventario, errores logísticos y coordinación entre puntos de venta.

El proyecto está diseñado para ser simple, mantenible y preparado para que otros ingenieros puedan continuar la integración con un backend real en una fase posterior.

## Contexto operativo

Durante la operación de distribución del álbum, las incidencias pueden generarse desde distintos puntos de venta, proveedores o equipos internos. Cuando estos reportes se manejan mediante correos, hojas de cálculo o mensajes informales, se vuelve más difícil dar seguimiento, evitar duplicidades y priorizar los casos críticos.

Esta aplicación móvil propone un flujo centralizado de tickets para mejorar la visibilidad operativa y facilitar la continuidad técnica del producto.

## Estructura del repositorio

```text
panini-support-poc/
├── app/          Proyecto Android con Kotlin y Jetpack Compose
├── contracts/    Contratos de API en formato YAML/OpenAPI
├── docs/         Documentación técnica de handoff
├── video/        Enlace al video demo
├── README.md
└── .gitignore
```

## Tecnologías utilizadas

- Kotlin
- Android Studio
- Jetpack Compose
- Material 3
- Navigation
- MVVM
- StateFlow
- Retrofit
- DTOs
- Mock data

## Funcionalidades consideradas

La solución móvil contempla los siguientes flujos principales:

- Autenticación simulada.
- Listado de tickets de soporte.
- Visualización de detalle de ticket.
- Creación de tickets.
- Actualización de estado.
- Manejo básico de prioridades.
- Actualización reactiva del listado.
- Feature Flags para habilitar o deshabilitar funcionalidades durante validaciones internas.
- Estructura de networking preparada para integración futura con backend.

## Ejecución del proyecto

El proyecto Android se encuentra dentro de la carpeta `/app`.

Para compilarlo desde PowerShell:

```powershell
cd app
.\gradlew.bat assembleDebug
```

También puede abrirse la carpeta `/app` directamente desde Android Studio.

## Documentación técnica

La documentación técnica del proyecto se encuentra en `/docs`:

- `technical-decisions.md`: decisiones técnicas principales.
- `event-driven-flow.md`: explicación del flujo reactivo de tickets.
- `feature-flags.md`: explicación de las Feature Flags.

## Contratos de API

Los contratos de integración se ubican en `/contracts`.

El archivo principal es:

```text
contracts/tickets-api.yaml
```

Este contrato define la base para la futura integración entre la aplicación móvil y el backend de gestión de tickets.

## Video demo

El enlace al video demo se documenta en:

```text
video/demo-link.md
```
