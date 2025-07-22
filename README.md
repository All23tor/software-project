# SueldoMinimo App
+ Torres Ara Alberto Gabriel
+ Meza Pareja Arthur Patrick
+ Diaz Vasquez Esdras Amado
+ Rivera Cruz Diego Benjamin

### Propósito
---
Desarrollar una aplicación móvil que conecte a consumidores con negocios locales de comida en zonas urbanas y periurbanas de Arequipa, con el fin de mejorar el acceso a opciones alimenticias económicas y saludables, fortalecer la economía barrial y promover el empleo independiente mediante repartidores.

### Funcionalidades
---
#### Usuario
+ Activar su cuenta
+ Desactivar su cuenta
+ Actualizar datos personales
+ Puede ser Administrador, Repartidor, Cliente
#### Restaurante
+ Representa un negocio con platos y horarios
+ Actualizar horario `actualizarHorario()`
+ Verificar si está abierto `estaAbiertoAHora()`

+ Activar disponibilidad `estaDisponible()`

#### Pedido
+ Añadir ítems `addItem()`
+ Cambiar estado `actualizarEstado()`
+ Cancelar pedido `cancelar()`
+ Calcular total `calcularMontoTotal()`

Principios SOLID APLICADOS


🔹 1. S – Principio de Responsabilidad Única (SRP)
    CLASE USUARIO

        @Entity
    @Table(name = "usuarios")
    public class Usuario {
    private String nombre;
    private String email;
    private String telefono;
    private boolean activo;

    public void activarCuenta() {
        this.activo = true;
        logger.info(() -> "Cuenta de usuario activada.")
    }

    public void desactivarCuenta() {
        this.activo = false;
        logger.info(() -> "Cuenta de usuario desactivada.");
    }
🔹 2. O – Principio de Abierto/Cerrado (OCP)
    
    @Inheritance(strategy = InheritanceType.JOINED)
    public class Usuario {
    ...
    }
    
    // Subclase ejemplo:
    @Entity
    public class Cliente extends Usuario {
    private String preferenciasEntrega;
    }

🔹 3. L – Principio de Sustitución de Liskov (LSP)
    
    public class Usuario {
    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("El email es invalido.");
        }
        this.email = email;
        }
    }

    // Esto funcionará igual si Cliente extiende Usuario:
    Usuario u = new Cliente();
    u.setEmail("ejemplo@correo.com");
![Prototipo](Prototipo.png)
![Diagrama](Diagrama.png)