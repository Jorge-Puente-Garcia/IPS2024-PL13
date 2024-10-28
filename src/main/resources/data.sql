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

INSERT INTO OrdenTrabajo (fecha_creacion, estado, almacenero_id, incidencia) VALUES
	('2024-10-01', 'En recogida', 1, NULL),
	('2024-10-02', 'Pendiente de empaquetado', 2, 'Problema con el código de barras'),
	('2024-10-03', 'En recogida', 3, NULL),
	('2024-10-04', 'En recogida', 4, 'Producto dañado en recogida'),
	('2024-10-05', 'Pendiente de empaquetado', 5, NULL),
	('2024-10-06', 'En recogida',  1, 'Incidencia en empaquetado'),
	('2024-10-07', 'En recogida',  2, NULL),
	('2024-10-08', 'Pendiente de empaquetado', 3, NULL),
	('2024-10-09', 'En recogida', 4, 'Caja deteriorada'),
	('2024-10-10', 'En recogida',  5, NULL);
	
	
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