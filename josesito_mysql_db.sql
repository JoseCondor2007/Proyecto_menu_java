
CREATE DATABASE IF NOT EXISTS JosesitoEnterprise;

USE JosesitoEnterprise;

CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,            
    nombre VARCHAR(50) NOT NULL,                   
    apellidos VARCHAR(50) NOT NULL,               
    email VARCHAR(100) UNIQUE NOT NULL,            
    telefono VARCHAR(20),                          
    direccion VARCHAR(255),                        
    password VARCHAR(255) NOT NULL,                
    recibir_promociones BOOLEAN DEFAULT TRUE,      
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
);

CREATE TABLE IF NOT EXISTS servicios_registrados (
    id INT AUTO_INCREMENT PRIMARY KEY,             
    fecha DATE NOT NULL,                          
    vehiculo VARCHAR(100) NOT NULL,                
    descripcion TEXT,                             
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

select * from usuarios;
select * from servicios_registrados;
