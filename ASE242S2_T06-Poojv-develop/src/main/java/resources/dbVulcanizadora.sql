USE dbVulcanizadora;
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
DROP TABLE Cliente;
-- Insertar el primer cliente
INSERT INTO Cliente (nombre, apellido, numeroTelefono, correo, direccion, fechaNacimiento, sexo, tipoDocumento, numeroDocumento, estado )
VALUES ('Carlos', 'López', '999888777', 'carlos.lopez@email.com', 'Avenida Principal 456, Lima', '1985-12-20', 'Masculino', 'DNI', '78901234B', 1, 1);
SELECT * FROM Cliente;

SELECT * FROM Cliente
WHERE estado = FALSE;

ALTER TABLE Cliente DROP COLUMN recibirNotificaciones;

SHOW COLUMNS FROM Cliente;