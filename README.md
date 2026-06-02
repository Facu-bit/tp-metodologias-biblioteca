# Feria de Emprendedores - TP Final Metodologías de Sistemas II

## Integrantes
- Fredes Facundo
- Ludi Sofia
- Vazques Nahiara

## Como ejecutar el programa
Abrir el proyecto en IntelliJ IDEA y correr la clase Main.java.

## Como correr los tests
Clic derecho sobre GestorFeriaTest.java → Run GestorFeriaTest.

## Decisiones tecnicas
- Se identificaron y corrigieron 4 code smells (nombres cripticos, metodos con multiples responsabilidades, campos publicos y codigo duplicado)
- Se aplicaron los principios SRP y OCP
- Se implemento el patron Observer para desacoplar las notificaciones de la logica de negocio
- Se agregaron 8 tests unitarios con JUnit 5 y Mockito
- Se aplico TDD para agregar el metodo buscarEmprendedorPorId()
- Se configuro CI con GitHub Actions para correr los tests automaticamente en cada push