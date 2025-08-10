-- Borrar tablas si existen
DROP TABLE IF EXISTS recursos CASCADE;
DROP TABLE IF EXISTS tareas CASCADE;
DROP TABLE IF EXISTS proyectos CASCADE;
DROP TABLE IF EXISTS usuarios CASCADE;
DROP TABLE IF EXISTS log_errores CASCADE;

-- Tabla usuarios
CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    rol VARCHAR(30) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla proyectos
CREATE TABLE proyectos (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    descripcion VARCHAR(500),
    fecha_inicio DATE,
    fecha_fin DATE,
    estado VARCHAR(20) NOT NULL DEFAULT 'Planeado',
    porcentaje INT NOT NULL DEFAULT 0,
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla tareas
CREATE TABLE tareas (
    id SERIAL PRIMARY KEY,
    proyecto_id INT NOT NULL REFERENCES proyectos(id) ON DELETE CASCADE,
    asignado_a INT REFERENCES usuarios(id),
    titulo VARCHAR(120) NOT NULL,
    descripcion VARCHAR(500),
    prioridad VARCHAR(10) NOT NULL DEFAULT 'Media', -- Baja|Media|Alta
    estado VARCHAR(20) NOT NULL DEFAULT 'Pendiente', -- Pendiente|En Progreso|Completada
    avance INT NOT NULL DEFAULT 0,
    fecha_vencimiento DATE,
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla recursos
CREATE TABLE recursos (
    id SERIAL PRIMARY KEY,
    tarea_id INT NOT NULL REFERENCES tareas(id) ON DELETE CASCADE,
    nombre_archivo VARCHAR(200) NOT NULL,
    url VARCHAR(400),
    agregado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla log_errores
CREATE TABLE log_errores (
    id SERIAL PRIMARY KEY,
    origen VARCHAR(120) NOT NULL,
    mensaje TEXT NOT NULL,
    traza TEXT,
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Datos iniciales (usuarios)
INSERT INTO usuarios (nombre, email, rol, password_hash) VALUES
('Administrador', 'admin@demo.com', 'ADMIN', 'admin123'),
('Usuario', 'user@demo.com', 'USER', 'user123');

-- Datos iniciales (proyectos)
INSERT INTO proyectos (nombre, descripcion, estado, porcentaje) VALUES
('Lanzamiento Web', 'Sitio corporativo', 'En Progreso', 35),
('App Móvil', 'Aplicación Android', 'Planeado', 0);

-- Datos iniciales (tareas)
INSERT INTO tareas (proyecto_id, asignado_a, titulo, descripcion, prioridad, estado, avance)
VALUES
(1, 1, 'Diseño UI', 'Pantallas principales', 'Alta', 'En Progreso', 50),
(1, 2, 'Configurar CI', 'Pipeline en GitHub Actions', 'Media', 'Pendiente', 0),
(2, NULL, 'Levantamiento de Reqs', 'Reunión con cliente', 'Alta', 'Pendiente', 0);
