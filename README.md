# Gestor de Proyectos — Java Swing + Ant + SQL Server

Aplicación de escritorio (Swing) con CRUD de **Proyectos**, **Tareas** y **Usuarios**, 
seguimiento de avance y registro de errores en base de datos. Preparada para Ant.

## Requisitos
- Java 17 (o 11)
- Ant
- SQL Server (Express sirve)
- Driver JDBC de SQL Server en `lib/sqljdbc42.jar` (copiarlo a esa ruta)
- Crear BD ejecutando `scripts/database.sql`

## Configuración
Editar `resources/config.properties`:
```
db.url=jdbc:sqlserver://localhost:1433;databaseName=gestor_proyectos;encrypt=false
db.user=sa
db.pass=1123206
```

> Ajusta usuario/contraseña/puerto según tu entorno.

## Ejecutar
```bash
ant run            # compila y ejecuta
ant jar            # genera dist/GestorProyectosSwingAnt.jar
```

## Estructura
- `src/com/utn/gestor/...` código fuente (modelo/dao/servicio/ui)
- `resources/config.properties` configuración DB
- `scripts/database.sql` creación de tablas y datos de prueba
- `build.xml` script Ant

## Despliegue en la nube (ideas)
- Empaqueta con JAR y publicar en un VPS o en escritorio remoto.
- Alternativa web: portar lógica a Spring Boot + Thymeleaf y publicar en Render/Azure Web App.

## Credenciales demo
- Usuarios insertados en `scripts/database.sql` (admin/admin123, user/user123).

## Logs de error
Errores se guardan en tabla `log_errores` con traza, controlador y fecha.
