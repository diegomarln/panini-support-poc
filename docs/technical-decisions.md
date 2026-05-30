# Decisiones técnicas - Panini Support PoC

## 1. Propósito técnico
Este documento describe las decisiones técnicas que sostienen la prueba de
concepto móvil de Panini Support. Está dirigido al equipo de ingeniería de
Panini que recibe el handoff técnico y continuará la construcción de la
solución móvil para la gestión de tickets internos relacionados con
proveedores, distribución del álbum oficial de la Copa Mundial FIFA 2026,
faltantes de inventario, errores logísticos y coordinación entre puntos de
venta.

## 2. Arquitectura móvil
La aplicación está construida con Kotlin sobre Android. La interfaz se
implementa con Jetpack Compose y Material 3, sin layouts XML.

- Kotlin: lenguaje oficial recomendado por Google para Android moderno.
  Aporta seguridad frente a nulos, sintaxis concisa y un ecosistema maduro
  de librerías para que el equipo móvil de Panini mantenga la solución a
  largo plazo.
- Jetpack Compose: toolkit declarativo que reduce el costo de
  mantenimiento de pantallas como el listado, el detalle y el formulario
  de creación de tickets, y facilita iterar conforme el equipo móvil
  agregue nuevos flujos.
- Material 3: provee componentes y tipografía estándar que mantienen una
  experiencia coherente sin invertir tiempo en un sistema de diseño
  propio.
- Navigation Compose: centraliza el grafo de navegación en
  `ui/navigation/AppNavGraph.kt`, manteniendo las pantallas independientes
  del controlador de navegación.

## 3. Separación de responsabilidades
La solución sigue el patrón MVVM, ajustado al estilo declarativo de
Compose:

- UI (`ui/.../*Screen.kt`): pantallas mayormente sin estado. Reciben un
  `UiState` y callbacks (por ejemplo, `onCreateTicket` o
  `onStatusSelected`) y solo renderizan. Esto simplifica las pruebas y
  mantiene la lógica fuera del árbol de Composables.
- ViewModel (`ui/.../*ViewModel.kt`): mantiene el estado de cada pantalla
  con `MutableStateFlow<UiState>` y expone una vista `StateFlow` solo
  lectura. Procesa eventos del usuario, valida formularios y traduce
  acciones a llamadas del repositorio.
- Repositorio (`domain/repository/TicketRepository.kt`): contrato del
  dominio sobre tickets. Expone `tickets: StateFlow<List<Ticket>>`,
  `events: SharedFlow<TicketEvent>`, `getTicketById`, `createTicket`,
  `updateTicketStatus` y `updateTicketPriority`.
- Datos mock (`data/mock/MockTicketDataSource.kt`): proporciona la lista
  inicial de tickets realistas para esta primera versión.
- Networking (`data/remote/`, `core/network/`): capa preparada para la
  futura integración con el backend.

## 4. Datos mock e integración futura
La fuente activa de tickets es `InMemoryTicketRepository`. Mantiene una
lista priorizada en memoria a partir de `MockTicketDataSource` y expone
los cambios mediante `StateFlow`. La decisión de comenzar con datos mock
permite validar el flujo de soporte interno (creación, listado, detalle,
actualización de prioridad y de estado) sin depender de la disponibilidad
de un backend.

Los modelos del dominio (`Ticket`, `TicketCategory`, `TicketPriority`,
`TicketStatus`) son independientes de la capa de red, lo que permitirá
sustituir la implementación de `TicketRepository` por una versión que
consuma el backend real sin reescribir la UI ni los ViewModels.

## 5. Networking preparado
La capa de red existe en `data/remote/` y `core/network/`:

- `PaniniSupportApiService`: interfaz Retrofit con los cinco endpoints
  alineados al contrato `contracts/tickets-api.yaml`.
- DTOs: `TicketDto`, `CreateTicketRequestDto`, `UpdateTicketStatusRequestDto`
  y `UpdateTicketPriorityRequestDto`.
- Mapper: `TicketDtoMapper` traduce entre DTO y modelo de dominio usando
  valores de API estables (por ejemplo `point_of_sale`, `in_progress`).
- `NetworkModule`: configura Retrofit con un `BASE_URL` placeholder, Gson
  como conversor y `HttpLoggingInterceptor` para observabilidad básica
  cuando se integre el backend.

Esta capa está aislada y todavía no se invoca desde la UI ni desde el
repositorio activo. Cuando el equipo de backend de Panini exponga la API
real, una nueva implementación de `TicketRepository` (por ejemplo
`RemoteTicketRepository`) podrá consumirla sin afectar las pantallas ya
construidas.

## 6. Decisiones fuera de alcance
Las siguientes tecnologías se omitieron de manera consciente para entregar
una prueba de concepto empresarial acotada, rápida de iterar y fácil de
mantener por el equipo técnico de Panini. Son decisiones de control de
alcance, no limitaciones impuestas.

- Sin Room: la persistencia local no aporta valor al flujo de soporte
  interno en esta fase. Los tickets viven en memoria; la persistencia
  real se definirá cuando exista backend y se establezcan los criterios
  de uso offline.
- Sin Firebase: los servicios de analytics, crashlytics, remote config y
  autenticación se incorporarán cuando Panini defina su plataforma de
  servicios.
- Sin Hilt: la inyección manual a través de `AppContainer` y
  `TicketViewModelFactory` es suficiente para el grafo actual. Adoptar
  un framework de inyección antes de tener el alcance funcional
  definido agregaría complejidad sin beneficio inmediato.
- Sin autenticación real: el flujo de tickets que se valida en esta
  prueba de concepto no requiere un proveedor de identidad para
  demostrarse. La autenticación se integrará cuando exista backend y se
  decida el proveedor.
- Sin persistencia entre sesiones: al reiniciar la aplicación, los datos
  vuelven al estado de los tickets mock. Es coherente con el alcance de
  validación interna de esta fase.

## 7. Cómo continuar la solución
Pasos recomendados para el equipo técnico de Panini cuando inicie la
integración real con el backend:

1. Implementar un `RemoteTicketRepository` que use `PaniniSupportApiService`
   y reutilice `TicketDtoMapper`, manteniendo la firma de
   `TicketRepository` para no afectar a los ViewModels existentes.
2. Cambiar la instanciación del repositorio en `core/di/AppContainer.kt`
   para usar la nueva implementación; `TicketViewModelFactory` no
   requiere cambios.
3. Declarar `INTERNET` en `AndroidManifest.xml` y, si el backend de
   desarrollo se expone en HTTP, configurar `network_security_config.xml`.
4. Reemplazar el `BASE_URL` placeholder en `NetworkModule` por la URL
   real del backend de Panini Support.
5. Evolucionar `FeatureFlags` hacia configuración remota cuando Panini
   estandarice una plataforma de remote config.
6. Definir un proveedor de identidad y reemplazar el login simulado por
   el flujo real, conservando la separación entre pantalla, ViewModel y
   repositorio.

Esta hoja de ruta mantiene la UI y los ViewModels intactos: los cambios se
concentran en la capa de datos y en la configuración, gracias a la
separación descrita en este documento.
