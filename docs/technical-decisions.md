# Decisiones técnicas - Panini Support PoC

Este documento resume las decisiones técnicas que guían la primera prueba
de concepto móvil de Panini Support. Está dirigido al equipo de ingeniería
interno de Panini que recibirá el handoff técnico y continuará la
construcción de la solución móvil en fases posteriores. Las decisiones
están alineadas al flujo de soporte interno de Panini sobre incidencias de
proveedores, distribución de paquetes, faltantes de inventario y
coordinación con puntos de venta.

## Lenguaje y UI: Kotlin y Jetpack Compose
- Kotlin es el lenguaje oficial recomendado por Google para desarrollo
  Android moderno. Aporta seguridad frente a nulos, sintaxis concisa y un
  ecosistema maduro de librerías que el equipo móvil de Panini podrá
  mantener a largo plazo.
- Jetpack Compose es el toolkit de UI declarativo de Android. Permite
  construir pantallas reactivas con menos código que el sistema XML
  tradicional, lo cual reduce el costo de mantenimiento del flujo de
  tickets y facilita iterar sobre nuevas vistas conforme el negocio
  agregue requerimientos de soporte interno.

## Arquitectura: MVVM
- MVVM separa el estado de la UI (en los ViewModels) del renderizado (en
  los Composables). Esto permite probar la lógica del flujo de tickets de
  forma aislada y mantiene las pantallas enfocadas únicamente en mostrar
  datos y emitir eventos de usuario, como crear un ticket o cambiar su
  prioridad.
- La separación facilita que distintas personas del equipo móvil trabajen
  en paralelo sobre UI y lógica de negocio sin pisarse el trabajo, algo
  importante para mantener un ritmo de entrega sostenible en futuras
  fases del producto.

## Datos: mock en memoria para esta primera PoC
- En esta primera prueba de concepto los tickets de incidencias logísticas
  y las categorías de inventario viven en memoria mediante datos mock. El
  objetivo es validar el flujo de soporte interno (creación, listado,
  cambio de prioridad y vista de inventario) sin bloquear la entrega
  móvil por la disponibilidad de un backend.
- Esta decisión es un control de alcance deliberado para entregar una
  prueba de concepto rápida y mantenible que el equipo técnico de Panini
  pueda evolucionar, no una limitación de diseño.

## Preparación para integración futura: Retrofit, DTOs y API Service
- Aunque todavía no existe un backend conectado, la solución móvil se
  estructura para incorporar Retrofit, DTOs y una capa de API Service.
  Esto permite que, cuando el equipo de backend de Panini exponga los
  endpoints reales de tickets, los mismos ViewModels puedan consumir el
  servicio remoto sin reescribir la UI.
- Los DTOs y el contrato inicial en `/contracts/tickets-api.yaml` actúan
  como punto de acuerdo entre el equipo móvil y el equipo de backend
  durante la integración futura.

## Fuera de alcance en esta primera PoC
Las siguientes tecnologías están excluidas conscientemente para mantener
un alcance acotado, una entrega rápida y un código mantenible por el
equipo técnico de Panini. No son limitaciones impuestas, sino decisiones
de control de alcance para una prueba de concepto empresarial.

- Sin Room: la persistencia local no aporta valor al flujo de soporte
  interno en esta fase. Los tickets viven en memoria y se reconstruyen al
  abrir la aplicación. La persistencia real se evaluará cuando exista
  backend.
- Sin Firebase: los servicios de Firebase (analytics, crashlytics, remote
  config, autenticación) se incorporarán en una fase posterior, cuando
  Panini decida la plataforma definitiva de servicios de backend.
- Sin Hilt: la inyección de dependencias manual es suficiente para el
  grafo de objetos pequeño de esta prueba de concepto. Adoptar Hilt antes
  de tener el alcance funcional definido agregaría complejidad sin
  beneficio inmediato.
- Sin autenticación real: el flujo de tickets que se valida en esta PoC
  (proveedores, distribución, inventario, incidencias logísticas y
  puntos de venta) no requiere un sistema de login para demostrar la
  experiencia de soporte interno. La autenticación se integrará cuando
  exista backend y un proveedor de identidad definido por Panini.
