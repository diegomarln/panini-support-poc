# Panini Support PoC

## Descripción general
Prueba de concepto móvil desarrollada para Panini con el objetivo de
centralizar el flujo de tickets de soporte interno. La solución móvil se
enfoca en registrar, listar y priorizar incidencias operativas reportadas
por el equipo interno, y está diseñada para ser simple, mantenible y lista
para una integración futura con backend.

## Contexto operativo
Panini gestiona la distribución y el soporte operativo del álbum oficial de
la Copa Mundial de la FIFA 2026. Durante esta operación surgen incidencias
relacionadas con proveedores, distribución de paquetes, faltantes de
inventario, errores logísticos y coordinación con puntos de venta. Esta
prueba de concepto entrega al equipo técnico de Panini una base móvil sobre
la cual escalar la gestión de tickets en futuras fases del producto.

## Estructura del repositorio
```
panini-support-poc/
- app/         Proyecto Android (Kotlin + Jetpack Compose) de la solución móvil
- contracts/   Contratos YAML/OpenAPI para la integración futura con backend
- docs/        Documentación de handoff técnico para el equipo de ingeniería
- video/       Enlace al video demo final de la prueba de concepto
- README.md
- .gitignore
```

## Tecnologías utilizadas
- Kotlin
- Jetpack Compose
- MVVM, para separar el estado de la UI del renderizado
- Retrofit y DTOs, preparados para la integración futura con backend
- Datos mock en memoria, para esta primera prueba de concepto

## Alcance actual
Este bloque inicial establece la estructura del repositorio, el proyecto
Android base dentro de `/app` y la documentación de handoff técnico. Las
pantallas de creación, listado y priorización de tickets, así como la vista
de inventario por categoría, se incorporarán en bloques posteriores siguiendo
los lineamientos descritos en `/docs`.

## Cómo ejecutar el proyecto
El proyecto Android vive dentro de `/app`. El comando de build se debe
ejecutar desde esa carpeta:

```
cd app
.\gradlew.bat assembleDebug
```

## Validación inicial
El build inicial de Android se validó correctamente con `assembleDebug`,
obteniendo `BUILD SUCCESSFUL`. Esto confirma que el entorno Gradle del
proyecto está correctamente configurado para que el equipo móvil continúe
construyendo sobre esta base.

## Estado actual
Bloque 0 completado: estructura del repositorio creada, proyecto Android
base inicializado y documentación de handoff técnico publicada. La
implementación funcional del flujo de tickets se entregará en los siguientes
bloques.
