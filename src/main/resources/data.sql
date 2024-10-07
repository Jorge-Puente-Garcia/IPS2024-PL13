--Inicialización de todas las tablas necesarias para empezar a trabajar
delete from Producto;
delete from Almacenero;
delete from Pedido;
delete from ProductosPedido;
	
INSERT INTO Producto (referencia, datosBasicos, precio) VALUES
    ('REF001', 'Smartphone de última generación con pantalla AMOLED de 6.5 pulgadas y 128GB de almacenamiento', 699.99),
    ('REF002', 'Laptop ultraligera de 14 pulgadas, procesador Intel Core i7, 16GB RAM y 512GB SSD', 1199.99),
    ('REF003', 'Auriculares inalámbricos con cancelación de ruido, duración de batería de 24 horas', 199.99),
    ('REF004', 'Cámara fotográfica profesional de 24MP con lente de 50mm y estabilización óptica', 1499.99),
    ('REF005', 'Reloj inteligente con monitor de frecuencia cardíaca, GPS integrado y resistente al agua', 249.99),
    ('REF006', 'Tablet de 10 pulgadas con pantalla Full HD, 64GB de almacenamiento y batería de larga duración', 329.99),
    ('REF007', 'Consola de videojuegos de última generación con 1TB de almacenamiento y mando inalámbrico', 499.99),
    ('REF008', 'Impresora multifunción con conexión Wi-Fi y sistema de tinta continua', 149.99),
    ('REF009', 'Altavoz portátil Bluetooth con sonido envolvente y resistente al agua', 89.99),
    ('REF010', 'Cafetera de cápsulas con función automática y depósito de agua de 1 litro', 99.99);
	
INSERT INTO Cliente (dni, nombre, apellidos) VALUES
	('12345678A', 'Carlos', 'González Pérez'),
	('23456789B', 'María', 'Fernández López'),
	('34567890C', 'Luis', 'Martínez Gómez'),
	('45678901D', 'Ana', 'Rodríguez García'),
	('56789012E', 'Jorge', 'Sánchez Romero'),
	('67890123F', 'Lucía', 'Jiménez Torres'),
	('78901234G', 'Pablo', 'Hernández Díaz'),
	('89012345H', 'Elena', 'Muñoz Moreno'),
	('90123456I', 'David', 'Ruiz Ortiz'),
	('01234567J', 'Laura', 'Ramírez Gil');

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
	
	
	
	
	
	
	
	
	
	