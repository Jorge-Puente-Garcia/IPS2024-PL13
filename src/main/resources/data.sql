--Inicialización de todas las tablas necesarias para empezar a trabajar
delete from Producto;
delete from Almacenero;
delete from Pedido;
delete from ProductosPedido;
delete from Localizacion;
delete from OrdenTrabajo;
delete from OrdenTrabajoProducto;
delete from Cliente;


INSERT INTO Producto (referencia, datosBasicos, precio,unidades,localizacion_id) VALUES
    ('REF001', 'Smartphone de última generación con pantalla AMOLED de 6.5 pulgadas y 128GB de almacenamiento', 699.99, 10, 1),
    ('REF002', 'Laptop ultraligera de 14 pulgadas, procesador Intel Core i7, 16GB RAM y 512GB SSD', 1199.99, 5, 2),
    ('REF003', 'Auriculares inalámbricos con cancelación de ruido, duración de batería de 24 horas', 199.99, 15, 3),
    ('REF004', 'Cámara fotográfica profesional de 24MP con lente de 50mm y estabilización óptica', 1499.99, 8, 4),
    ('REF005', 'Reloj inteligente con monitor de frecuencia cardíaca, GPS integrado y resistente al agua', 249.99, 20, 5),
    ('REF006', 'Tablet de 10 pulgadas con pantalla Full HD, 64GB de almacenamiento y batería de larga duración', 329.99, 12, 6);
	
INSERT INTO Cliente (dni, nombre, apellidos, direccion, numeroTelefono) VALUES
	('12345678A', 'Carlos', 'González Pérez', 'Calle Mayor 1, Madrid', '600123456'),
	('23456789B', 'María', 'Fernández López', 'Avenida del Sol 15, Valencia', '611234567'),
	('34567890C', 'Luis', 'Martínez Gómez', 'Calle Luna 3, Barcelona', '622345678'),
	('45678901D', 'Ana', 'Rodríguez García', 'Calle Olmo 12, Sevilla', '633456789'),
	('56789012E', 'Jorge', 'Sánchez Romero', 'Calle Jardines 7, Bilbao', '644567890'),
	('67890123F', 'Lucía', 'Jiménez Torres', 'Paseo del Prado 22, Zaragoza', '655678901'),
	('78901234G', 'Pablo', 'Hernández Díaz', 'Calle Rosas 5, Málaga', '666789012'),
	('89012345H', 'Elena', 'Muñoz Moreno', 'Calle Almendra 18, Murcia', '677890123'),
	('90123456I', 'David', 'Ruiz Ortiz', 'Calle del Río 9, Santander', '688901234'),
	('01234567J', 'Laura', 'Ramírez Gil', 'Calle Naranjo 21, Granada', '699012345');
	
INSERT INTO Localizacion (pasillo,fila, columna, estanteria, cara) VALUES
(3,1, 2, 3, 'A'),
(2,1, 3, 4, 'B'),
(5,2, 1, 1, 'A'),
(4,2, 2, 7, 'B'),
(7,3, 1, 9, 'A'),
(8,3, 2, 4, 'B');

INSERT INTO Almacenero (nombre, apellido) VALUES 
	('Juan', 'Pérez'),
	('María', 'González'),
	('Carlos', 'Sánchez'),
	('Laura', 'Martínez'),
	('Ana', 'López');
	
INSERT INTO Pedido (cliente_id, fecha, total, estado, orden_trabajo_id) VALUES 
	( 1, '2024-09-15', 150, 'Pendiente de recogida', 1),
	( 2, '2024-09-18', 299.99, 'Recogido', 2),
	( 3, '2024-09-20', 75.00, 'Pendiente de recogida', 3),
	( 4, '2024-09-22', 499.99, 'Recogido', 4),
	( 5, '2024-09-25', 250.75, 'Pendiente de recogida', 5);
	

INSERT INTO ProductosPedido (id, pedido_id, producto_id, cantidad) VALUES 
	(1, 1, 1, 2),
	(2, 1, 2, 1),
	(3, 2, 1, 3),
	(4, 2, 3, 1),
	(5, 3, 4, 5),
	(6, 3, 2, 2),
	(7, 4, 5, 1),
	(8, 5, 1, 2),
	(9, 5, 3, 3);

INSERT INTO OrdenTrabajo (fecha_creacion, estado, codigoBarrasPaquete, almacenero_id, incidencia) VALUES
	('2024-10-01', 'En recogida', 'PKG123456', 1, NULL),
	('2024-10-02', 'Pendiente de empaquetado', 'PKG654321', 2, 'Problema con el código de barras'),
	('2024-10-03', 'Empaquetado', 'PKG789012', 3, NULL),
	('2024-10-04', 'En recogida', 'PKG345678', 4, 'Producto dañado en recogida'),
	('2024-10-05', 'Pendiente de empaquetado', 'PKG901234', 5, NULL),
	('2024-10-06', 'Empaquetado', 'PKG567890', 1, 'Incidencia en empaquetado'),
	('2024-10-07', 'En recogida', 'PKG234567', 2, NULL),
	('2024-10-08', 'Pendiente de empaquetado', 'PKG876543', 3, NULL),
	('2024-10-09', 'Empaquetado', 'PKG098765', 4, 'Caja deteriorada'),
	('2024-10-10', 'En recogida', 'PKG112233', 5, NULL);
	
	
INSERT INTO OrdenTrabajoProducto (orden_trabajo_id, producto_id, cantidad) VALUES
(1, 1, 5),
(1, 2, 10),
(2, 3, 15),
(2, 4, 7),
(3, 5, 20),
(3, 6, 12),
(4, 7, 8),
(5, 8, 30),
(6, 9, 25),
(7, 10, 18);

	
	
	
	
	
	
	