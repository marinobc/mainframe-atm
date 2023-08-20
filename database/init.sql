-- Crear la tabla de usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(30) UNIQUE,
    nombre VARCHAR(255) NOT NULL,
    pin INT NOT NULL,
    saldo DOUBLE NOT NULL
);

-- Crear la tabla de historico
CREATE TABLE IF NOT EXISTS historico (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT,
    tipo_operacion VARCHAR(50) NOT NULL,
    cantidad DOUBLE,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- Insertar datos de ejemplo en usuarios
INSERT INTO usuarios (id, usuario, nombre, pin, saldo) VALUES
(1, 'jperez', 'Juan Perez', 1234, 1000.0),
(2, 'aramirez', 'Ana Ramirez', 5678, 2500.0),
(3, 'cgomez', 'Carlos Gomez', 9012, 500.0),
(4, 'mtorres', 'Marta Torres', 3456, 750.0),
(5, 'lfernandez', 'Luisa Fernandez', 7890, 3000.0);

-- Insertar datos de ejemplo en historico (asumiendo que los IDs de los usuarios coinciden con los valores insertados anteriormente)
INSERT INTO historico (usuario_id, tipo_operacion, cantidad) VALUES (1, 'Deposito', 1000.0);
INSERT INTO historico (usuario_id, tipo_operacion, cantidad) VALUES (2, 'Deposito', 2500.0);
INSERT INTO historico (usuario_id, tipo_operacion, cantidad) VALUES (3, 'Deposito', 500.0);
INSERT INTO historico (usuario_id, tipo_operacion, cantidad) VALUES (4, 'Deposito', 750.0);
INSERT INTO historico (usuario_id, tipo_operacion, cantidad) VALUES (5, 'Deposito', 3000.0);


