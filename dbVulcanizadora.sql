CREATE DATABASE IF NOT EXISTS dbVulcanizadora
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_general_ci;

-- Seleccionar la base de datos para trabajar en ella
USE dbVulcanizadora;

-- Creación de la tabla Producto
CREATE TABLE Producto (
    id_producto INTEGER PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(60) NOT NULL,
    tipo VARCHAR(50),
    marca VARCHAR(50),
    precio DECIMAL(10, 2),
    stock INTEGER DEFAULT 0
);

-- Añadir columna hay_stock a la tabla Producto
ALTER TABLE Producto
    ADD COLUMN hay_stock BOOLEAN DEFAULT FALSE;

-- Añadir columna fecha_registro a la tabla Producto
ALTER TABLE Producto
    ADD COLUMN fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- Insertar datos en la tabla Producto
INSERT INTO Producto (nombre, tipo, marca, precio, stock, hay_stock, fecha_registro)
VALUES
    ('Llanta Rin 15', 'Llanta', 'Michelin', 120.50, 10, TRUE, DATE('2025-01-05')),
    ('Llanta Rin 17', 'Llanta', 'Goodyear', 150.75, 5, TRUE, DATE('2025-02-12')),
    ('Batería 12V', 'Batería', 'Bosch', 95.00, 8, TRUE, DATE('2025-03-20')),
    ('Filtro de aceite', 'Filtro', 'Mann', 12.99, 20, TRUE, DATE('2025-04-01')),
    ('Bujías (set de 4)', 'Bujía', 'NGK', 35.50, 15, TRUE, DATE('2025-05-17')),
    ('Líquido de frenos', 'Fluido', 'Bosch', 18.75, 12, TRUE, DATE('2025-06-23')),
    ('Filtro de aire', 'Filtro', 'Fram', 9.99, 25, TRUE, DATE('2025-07-08')),
    ('Amortiguador delantero', 'Suspensión', 'Monroe', 85.20, 7, TRUE, DATE('2025-08-19')),
    ('Llanta Rin 16', 'Llanta', 'Pirelli', 140.00, 0, FALSE, DATE('2025-09-27')),
    ('Aceite de motor', 'Aceite', 'Castrol', 25.00, 0, FALSE, DATE('2025-10-30'));

-- Seleccionar todos los productos
SELECT * FROM Producto;

-- Opcional: Actualizar el estado de stock basándose en la cantidad en stock
UPDATE Producto
SET hay_stock = stock > 0
WHERE id_producto > 0;

-- Seleccionar productos con formato de fecha
SELECT
    nombre,
    tipo,
    marca,
    precio,
    stock,
    hay_stock,
    DATE_FORMAT(fecha_registro, '%d-%m-%Y') AS fecha_registro_formateada
FROM
    Producto;

-- Creación de la tabla Cliente
CREATE TABLE Cliente (
    idCliente INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    numeroTelefono VARCHAR(20),
    correo VARCHAR(100),
    direccion VARCHAR(255),
    fechaNacimiento DATE,
    sexo VARCHAR(20),
    tipoDocumento VARCHAR(50),
    numeroDocumento VARCHAR(50) UNIQUE NOT NULL,
    estado BOOLEAN NOT NULL DEFAULT TRUE, -- TRUE para activo, FALSE para inactivo (eliminación lógica)
    fechaRegistro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insertar el primer cliente
INSERT INTO Cliente (nombre, apellido, numeroTelefono, correo, direccion, fechaNacimiento, sexo, tipoDocumento, numeroDocumento, estado)
VALUES ('Carlos', 'López', '999888777', 'carlos.lopez@email.com', 'Avenida Principal 456, Lima', '1985-12-20', 'Masculino', 'DNI', '78901234B', TRUE);

-- Seleccionar todos los clientes
SELECT * FROM Cliente;

-- Seleccionar clientes inactivos
SELECT * FROM Cliente
WHERE estado = FALSE;

-- Mostrar columnas de la tabla Cliente
SHOW COLUMNS FROM Cliente;

-- Creación de la tabla Servicio
CREATE TABLE servicio (
    id_servicio INTEGER PRIMARY KEY AUTO_INCREMENT,
    tipoServicio VARCHAR(50) NOT NULL,
    descripcion TEXT, -- TEXT es más común para MySQL para textos largos.
    comentario TEXT,
    urgencia VARCHAR(20),
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    estado_vehiculo BOOLEAN NOT NULL,
    fecha DATE,
    precio DECIMAL(10, 2) NOT NULL
);
