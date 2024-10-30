--Inicialización de todas las tablas necesarias para empezar a trabajar
delete from Categoria;
delete from Producto;
delete from Almacenero;
delete from Pedido;
delete from ProductosPedido;
delete from Localizacion;
delete from OrdenTrabajo;
delete from OrdenTrabajoProducto;
delete from Cliente;

INSERT INTO Localizacion (pasillo, estanteria, posicion, altura) VALUES
    (1, 'Izquierda', 5, 2),
    (1, 'Derecha', 3, 4),
    (2, 'Izquierda', 7, 1),
    (2, 'Derecha', 6, 3),
    (3, 'Izquierda', 2, 5),
    (3, 'Derecha', 4, 2),
    (4, 'Izquierda', 1, 4),
    (4, 'Derecha', 8, 1),
    (5, 'Izquierda', 3, 3),
    (5, 'Derecha', 5, 2),
    (6, 'Izquierda', 6, 5),
    (6, 'Derecha', 7, 3),
    (7, 'Izquierda', 4, 1),
    (7, 'Derecha', 2, 4),
    (8, 'Izquierda', 5, 2);

INSERT INTO Categoria (nombre, id_padre) VALUES
    ('Electrónica', NULL),
    ('Ropa', NULL),
    ('Computadoras', 1),
    ('Teléfonos Móviles', 1),
    ('Televisores', 1),
    ('Tablets', 1),
    ('Portátiles', 3),
    ('Sobremesa', 3),
    ('Camisas', 2),
    ('Pantalones', 2),
    ('Calzado', 2);
    
INSERT INTO Producto (referencia, datosBasicos, precio, unidades, localizacion_id, id_categoria) VALUES
    ('REF001', 'Smartphone de última generación con pantalla AMOLED de 6.5 pulgadas y 128GB de almacenamiento', 699.99, 10, 1, 4),
    ('REF002', 'Laptop ultraligera de 14 pulgadas, procesador Intel Core i7, 16GB RAM y 512GB SSD', 1199.99, 5, 2, 7),
    ('REF003', 'Auriculares inalámbricos con cancelación de ruido, duración de batería de 24 horas', 199.99, 15, 3, 1),
    ('REF004', 'Cámara fotográfica profesional de 24MP con lente de 50mm y estabilización óptica', 1499.99, 8, 4, 1),
    ('REF005', 'Reloj inteligente con monitor de frecuencia cardíaca, GPS integrado y resistente al agua', 249.99, 20, 5, 1),
    ('REF006', 'Tablet de 10 pulgadas con pantalla Full HD, 64GB de almacenamiento y batería de larga duración', 329.99, 12, 6, 6),
    ('REF007', 'PC de Escritorio Lenovo con 8GB RAM y 1TB HDD', 650.00, 7, 2, 8),
    ('REF008', 'iPhone con 256GB de almacenamiento y cámara avanzada', 999.00, 15, 1, 4),
    ('REF009', 'Televisor Samsung de 55 pulgadas, 4K UHD', 1200.00, 4, 3, 5),
    ('REF010', 'Camisa Polo de algodón para hombre', 45.00, 25, 5, 9),
    ('REF011', 'Jeans Levis clásicos para hombre', 60.00, 18, 5, 10),
    ('REF012', 'Zapatos deportivos Nike para correr', 90.00, 20, 5, 11);
    
INSERT INTO Carrito (dni, referencia, cantidad, precio) VALUES
	('12345678A', 'REF001', 2, 1399.98),
	('12345678A', 'REF002', 1, 1199.99),
	('23456789B', 'REF003', 3, 599.97),
	('23456789B', 'REF004', 2, 2999.98);

-- Tabla Cliente
INSERT INTO Cliente (dni, nombre, apellidos, direccion, numeroTelefono) VALUES 
('12345678A', 'Juan', 'Perez', 'Calle Falsa 123', 600123456), ('23456789B', 'Maria', 'Lopez', 'Av. Siempreviva 742', 600234567), 
('34567890C', 'Carlos', 'Garcia', 'Calle Larga 321', 600345678), ('45678901D', 'Laura', 'Martinez', 'Plaza Mayor 45', 600456789), 
('56789012E', 'Ana', 'Hernandez', 'Calle Real 67', 600567890), ('67890123F', 'Jorge', 'Fernandez', 'Av. Libertad 89', 600678901), 
('78901234G', 'Lucia', 'Gomez', 'Calle Central 101', 600789012), ('89012345H', 'Pedro', 'Diaz', 'Av. San Martin 111', 600890123), 
('90123456I', 'Marta', 'Sanchez', 'Calle Norte 9', 600901234), ('01234567J', 'Luis', 'Romero', 'Av. Sur 15', 600012345), 
('11234567K', 'Isabel', 'Vega', 'Calle del Sol 3', 600112345), ('21234567L', 'Pablo', 'Jimenez', 'Av. del Mar 20', 600212345), 
('31234567M', 'Sofia', 'Ruiz', 'Calle de la Luna 8', 600312345), ('41234567N', 'Manuel', 'Torres', 'Av. del Norte 2', 600412345), 
('51234567O', 'Sara', 'Morales', 'Calle del Este 14', 600512345);

-- Tabla Almacenero
INSERT INTO Almacenero (nombre, apellido) VALUES 
('Miguel', 'Ramirez'), ('Cristina', 'Mendez'), ('Sergio', 'Ortiz'), ('Monica', 'Castro'), 
('Raul', 'Nunez'), ('Elena', 'Rojas'), ('Oscar', 'Martinez'), ('Paula', 'Guzman'), 
('Fernando', 'Santos'), ('Rosa', 'Navarro'), ('Ivan', 'Garcia'), ('Andrea', 'Pascual'), 
('Javier', 'Moreno'), ('Veronica', 'Reyes'), ('Eduardo', 'Soto');

-- Tabla OrdenTrabajo
INSERT INTO OrdenTrabajo (fecha_creacion, estado, almacenero_id, incidencia) VALUES 
('2024-01-01', 'En recogida', 1, NULL), ('2024-01-02', 'Pendiente de empaquetado', 2, NULL), 
('2024-01-03', 'En recogida', 3, 'Producto dañado'), ('2024-01-04', 'Pendiente de empaquetado', 4, NULL), 
('2024-01-05', 'En recogida', 5, 'Retraso en entrega'), ('2024-01-06', 'Pendiente de empaquetado', 6, NULL), 
('2024-01-07', 'En recogida', 7, NULL), ('2024-01-08', 'Pendiente de empaquetado', 8, 'Producto faltante'), 
('2024-01-09', 'En recogida', 9, NULL), ('2024-01-10', 'Pendiente de empaquetado', 10, NULL), 
('2024-01-11', 'En recogida', 11, 'Producto dañado'), ('2024-01-12', 'Pendiente de empaquetado', 12, NULL), 
('2024-01-13', 'En recogida', 13, 'Error en cantidad'), ('2024-01-14', 'Pendiente de empaquetado', 14, NULL), 
('2024-01-15', 'En recogida', 15, NULL);

-- Tabla Pedido
INSERT INTO Pedido (cliente_id, fecha, total, estado, orden_trabajo_id) VALUES 
(1, '2024-01-01', 100.00, 'Pendiente de recogida', 1), (2, '2024-01-02', 150.00, 'Recogido', 2), 
(3, '2024-01-03', 200.00, 'Pendiente de recogida', 3), (4, '2024-01-04', 250.00, 'Recogido', 4), 
(5, '2024-01-05', 300.00, 'Pendiente de recogida', 5), (6, '2024-01-06', 120.00, 'Recogido', 6), 
(7, '2024-01-07', 180.00, 'Pendiente de recogida', 7), (8, '2024-01-08', 220.00, 'Recogido', 8), 
(9, '2024-01-09', 270.00, 'Pendiente de recogida', 9), (10, '2024-01-10', 350.00, 'Recogido', 10), 
(11, '2024-01-11', 110.00, 'Pendiente de recogida', 11), (12, '2024-01-12', 175.00, 'Recogido', 12), 
(13, '2024-01-13', 210.00, 'Pendiente de recogida', 13), (14, '2024-01-14', 310.00, 'Recogido', 14), 
(15, '2024-01-15', 400.00, 'Pendiente de recogida', 15);

-- Tabla ProductosPedido
INSERT INTO ProductosPedido (pedido_id, producto_id, cantidad) VALUES 
(1, 1, 5), (2, 2, 10), (3, 3, 7), (4, 4, 8), (5, 5, 3), (6, 6, 6), 
(7, 7, 4), (8, 8, 9), (9, 9, 2), (10, 10, 11), (11, 11, 5), (12, 12, 7), 
(13, 13, 10), (14, 14, 4), (15, 15, 12);

-- Tabla OrdenTrabajoProducto
INSERT INTO OrdenTrabajoProducto (orden_trabajo_id, producto_id, cantidad) VALUES 
(1, 1, 2), (2, 2, 3), (3, 3, 1), (4, 4, 4), (5, 5, 6), (6, 6, 5), 
(7, 7, 2), (8, 8, 7), (9, 9, 8), (10, 10, 3), (11, 11, 9), (12, 12, 10), 
(13, 13, 3), (14, 14, 12), (15, 15, 4),(1, 2, 3), (1, 3, 2), (2, 4, 5), (2, 5, 1), (3, 6, 4), (3, 7, 6), 
(4, 8, 3), (4, 9, 2), (5, 10, 7), (5, 11, 1), (6, 12, 5), (6, 13, 8), 
(7, 14, 2), (7, 15, 9), (8, 1, 4);

	
