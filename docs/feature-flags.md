# Feature Flags - Panini Support PoC

La solución móvil incluye un conjunto pequeño de feature flags que permiten
al equipo técnico de Panini activar o desactivar capacidades concretas del
flujo de soporte interno sin tener que reescribir múltiples pantallas. Este
documento describe los flags previstos, su alcance actual y su evolución
prevista para futuras fases del producto.

## Flags definidos
- `ticketCreationEnabled`: controla la creación de tickets de soporte.
  Cuando está desactivado, las acciones de creación quedan ocultas o
  bloqueadas en la UI. Es útil cuando Panini necesita congelar el ingreso
  de nuevos casos durante una ventana operativa específica.
- `ticketPriorityUpdateEnabled`: controla el cambio de prioridad de un
  ticket existente. Permite habilitar la repriorización solo cuando el
  equipo interno está listo para gestionarla en un momento determinado de
  la operación.
- `inventoryCategoryVisible`: controla la visibilidad de la categoría de
  inventario en la UI. Permite mostrar u ocultar esta vista mientras el
  equipo de ingeniería ajusta su contenido o mientras Panini valida los
  datos de inventario antes de exponerlos.

## Alcance en esta prueba de concepto
- Los flags son locales y estáticos. Viven dentro de la aplicación como
  constantes o como un pequeño objeto de configuración en memoria.
- No hay configuración remota en esta primera fase. El objetivo es que la
  solución móvil ofrezca un mecanismo claro de control de funcionalidades
  sin agregar dependencias externas todavía.

## Evolución prevista
- En fases posteriores estos mismos flags pueden migrar a una configuración
  remota: un endpoint del backend de Panini o un proveedor de remote
  config. La forma en que la UI consume los flags no cambia, solo la
  fuente desde la que se obtienen.
- Esto permitirá al equipo técnico de Panini activar o desactivar
  capacidades en producción sin requerir una nueva publicación de la
  aplicación.

## Valor para el negocio
Los feature flags permiten a Panini habilitar o desactivar capacidades
durante la validación interna de la solución móvil sin reescribir
múltiples pantallas ni bloquear el avance del equipo móvil. Es un
mecanismo de control operativo de bajo costo que se alinea con la
estrategia de entrega incremental de la prueba de concepto y con su
integración futura con backend.
