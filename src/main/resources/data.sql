--Inicialización de todas las tablas necesarias para empezar a trabajar
delete from carreras;
delete from Producto;
delete from Almacenero;
	
INSERT INTO Producto (referencia, datosBasicos) VALUES
	('REF001', 'Smartphone de última generación con pantalla AMOLED de 6.5 pulgadas y 128GB de almacenamiento'),
	('REF002', 'Laptop ultraligera de 14 pulgadas, procesador Intel Core i7, 16GB RAM y 512GB SSD'),
	('REF003', 'Auriculares inalámbricos con cancelación de ruido, duración de batería de 24 horas'),
	('REF004', 'Cámara fotográfica profesional de 24MP con lente de 50mm y estabilización óptica'),
	('REF005', 'Reloj inteligente con monitor de frecuencia cardíaca, GPS integrado y resistente al agua'),
	('REF006', 'Tablet de 10 pulgadas con pantalla Full HD, 64GB de almacenamiento y batería de larga duración'),
	('REF007', 'Consola de videojuegos de última generación con 1TB de almacenamiento y mando inalámbrico'),
	('REF008', 'Impresora multifunción con conexión Wi-Fi y sistema de tinta continua'),
	('REF009', 'Altavoz portátil Bluetooth con sonido envolvente y resistente al agua'),
	('REF010', 'Cafetera de cápsulas con función automática y depósito de agua de 1 litro');
	
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