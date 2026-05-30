# Feature Flags - Panini Support PoC

## 1. Propósito
Los Feature Flags permiten al equipo técnico de Panini activar o
desactivar capacidades concretas del flujo de soporte interno sin
reescribir múltiples pantallas y sin reconstruir la aplicación. Son un
mecanismo de control operativo de bajo costo para ajustar el
comportamiento durante la validación interna de la solución móvil.

## 2. Flags implementados
La aplicación expone tres flags locales:

- `ticketCreationEnabled`: controla la creación de nuevos tickets.
- `ticketPriorityUpdateEnabled`: controla la actualización de prioridad
  de tickets existentes.
- `inventoryCategoryVisible`: controla la visibilidad de la categoría
  Inventory en la UI.

Los tres valores por defecto son `true`, por lo que el comportamiento
estándar de la aplicación es el flujo completo. Para experimentar con
escenarios de desactivación, basta con cambiar el valor en un único
archivo.

## 3. ticketCreationEnabled
Cuando es `true`, `TicketListScreen` muestra el botón "Crear ticket",
que navega al formulario de creación.

Cuando es `false`, la pantalla oculta el botón y muestra un mensaje en
español informando que la creación de tickets está deshabilitada durante
la validación interna. Además, `AppNavGraph` ignora la navegación hacia
`CreateTicket` si la lambda de creación se invoca con el flag
desactivado, manteniendo defensa en profundidad.

## 4. ticketPriorityUpdateEnabled
Cuando es `true`, `TicketDetailScreen` muestra los chips de prioridad
que permiten escalar o desescalar el ticket.

Cuando es `false`, los chips se ocultan, se mantiene visible la línea
"Prioridad actual: <nivel>" y se muestra un mensaje en español indicando
que la actualización de prioridad está deshabilitada durante la
validación interna. El flujo de cambio de estado permanece operativo en
ambos casos.

## 5. inventoryCategoryVisible
Cuando es `true`, el listado muestra todos los tickets y el formulario
de creación ofrece todas las categorías.

Cuando es `false`:

- `TicketListScreen` filtra los tickets de categoría Inventory antes de
  renderizar la `LazyColumn`.
- `CreateTicketScreen` excluye Inventory de los chips de categoría
  disponibles.

Este flag permite ocultar el área de inventario temporalmente, por
ejemplo durante una ventana en la que el equipo de inventario está
conciliando datos y Panini prefiere no exponer esa categoría desde la
solución móvil.

## 6. Ubicación técnica
La definición vive en `app/.../core/featureflag/FeatureFlags.kt`. Es un
`object` con propiedades `val` booleanas. `AppNavGraph` lee los flags y
los pasa como parámetros explícitos a las pantallas afectadas,
manteniendo las pantallas mayormente sin estado y los flags concentrados
en un único punto de configuración.

## 7. Evolución futura
- Configuración remota: los mismos flags pueden trasladarse a un
  servicio de configuración remoto administrado por Panini, por ejemplo
  un endpoint de configuración expuesto por el backend de soporte
  interno. La forma en que la UI los consume no cambia; solo cambia la
  fuente desde donde se obtienen.
- Modelo de exposición: si la lista de flags crece, conviene reemplazar
  el `object` por una clase con `StateFlow<FeatureFlagsSnapshot>` para
  que la UI recomponga automáticamente cuando un flag remoto cambie en
  caliente.
- Catálogo dinámico: cuando exista backend, podría añadirse un endpoint
  dedicado para que el equipo técnico de Panini gestione los flags sin
  publicar una nueva versión de la aplicación.

El valor de negocio principal es que Panini pueda habilitar o desactivar
capacidades durante la validación interna de la solución móvil sin
intervenir múltiples pantallas, alineando el ritmo de entrega del
producto con la estrategia operativa del equipo de ingeniería.
