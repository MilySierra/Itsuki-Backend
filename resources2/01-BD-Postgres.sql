CREATE TABLE usuario(
    id SERIAL PRIMARY KEY,
    nombre varchar(100) NOT NULL,
    email varchar(30) NOT NUll,
    contrasena varchar(255) NOT NULL
);

CREATE TABLE producto(
    id Serial PRIMARY KEY,
    nombre varchar(30),
    tipo varchar (30),
    descripcion varchar(300),
    precio numeric(10,2),
    imagen varchar(100)
);

CREATE TABLE carrito(
    id Serial,
    id_usuario INTEGER,
    id_producto INTEGER,
    cantidad INTEGER DEFAULT 1,
    CONSTRAINT FK_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id),
    CONSTRAINT FK_producto FOREIGN KEY (id_producto) REFERENCES producto(id)
);