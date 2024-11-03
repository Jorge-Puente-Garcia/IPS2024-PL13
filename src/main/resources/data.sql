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
delete from Paquete;

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

-- Inserta categorías principales primero
INSERT INTO Categoria (nombre, id_padre) VALUES
    ('Electrónica', NULL),
    ('Ropa', NULL),
    ('Electrodomésticos', NULL),
    ('Muebles', NULL);

-- Inserta subcategorías
INSERT INTO Categoria (nombre, id_padre) VALUES
    ('Computadoras', (SELECT id FROM Categoria WHERE nombre = 'Electrónica')),
    ('Teléfonos Móviles', (SELECT id FROM Categoria WHERE nombre = 'Electrónica')),
    ('Televisores', (SELECT id FROM Categoria WHERE nombre = 'Electrónica')),
    ('Tablets', (SELECT id FROM Categoria WHERE nombre = 'Electrónica')),    
    ('Camisas', (SELECT id FROM Categoria WHERE nombre = 'Ropa')),
    ('Pantalones', (SELECT id FROM Categoria WHERE nombre = 'Ropa')),
    ('Calzado', (SELECT id FROM Categoria WHERE nombre = 'Ropa')),
    ('Pequeños Electrodomésticos de Cocina', (SELECT id FROM Categoria WHERE nombre = 'Electrodomésticos')),
    ('Electrodomésticos de Cuidado Personal', (SELECT id FROM Categoria WHERE nombre = 'Electrodomésticos')),
    ('Aspiración y Limpieza', (SELECT id FROM Categoria WHERE nombre = 'Electrodomésticos')),
    ('Muebles de Oficina', (SELECT id FROM Categoria WHERE nombre = 'Muebles')),
    ('Muebles de Hogar', (SELECT id FROM Categoria WHERE nombre = 'Muebles'));
    
--Inserta subcategoría de subcategoria    
INSERT INTO Categoria (nombre, id_padre) VALUES
	('Portátiles', (SELECT id FROM Categoria WHERE nombre = 'Computadoras')),
    ('Sobremesa', (SELECT id FROM Categoria WHERE nombre = 'Computadoras'));
    
-- Inserta productos
INSERT INTO Producto (nombre, referencia, datosBasicos, precio, unidades, localizacion_id, id_categoria) VALUES
    ('Smartphone AMOLED', 'REF001', 'Smartphone de última generación con pantalla AMOLED de 6.5 pulgadas y 128GB de almacenamiento', 699.99, 10, 1, (SELECT id FROM Categoria WHERE nombre = 'Teléfonos Móviles')),
    ('Laptop Ultraligera', 'REF002', 'Laptop ultraligera de 14 pulgadas, procesador Intel Core i7, 16GB RAM y 512GB SSD', 1199.99, 5, 2, (SELECT id FROM Categoria WHERE nombre = 'Portátiles')),
    ('Auriculares Inalámbricos', 'REF003', 'Auriculares inalámbricos con cancelación de ruido, duración de batería de 24 horas', 199.99, 15, 3, (SELECT id FROM Categoria WHERE nombre = 'Televisores')),
    ('Cámara Fotográfica', 'REF004', 'Cámara fotográfica profesional de 24MP con lente de 50mm y estabilización óptica', 1499.99, 8, 4, (SELECT id FROM Categoria WHERE nombre = 'Televisores')),
    ('Reloj Inteligente', 'REF005', 'Reloj inteligente con monitor de frecuencia cardíaca, GPS integrado y resistente al agua', 249.99, 20, 5, (SELECT id FROM Categoria WHERE nombre = 'Televisores')),
    ('Tablet Full HD', 'REF006', 'Tablet de 10 pulgadas con pantalla Full HD, 64GB de almacenamiento y batería de larga duración', 329.99, 12, 6, (SELECT id FROM Categoria WHERE nombre = 'Tablets')),
    ('PC de Escritorio', 'REF007', 'PC de Escritorio Lenovo con 8GB RAM y 1TB HDD', 650.00, 7, 2, (SELECT id FROM Categoria WHERE nombre = 'Sobremesa')),
    ('iPhone 256GB', 'REF008', 'iPhone con 256GB de almacenamiento y cámara avanzada', 999.00, 15, 1, (SELECT id FROM Categoria WHERE nombre = 'Teléfonos Móviles')),
    ('Televisor 4K', 'REF009', 'Televisor Samsung de 55 pulgadas, 4K UHD', 1200.00, 4, 3, (SELECT id FROM Categoria WHERE nombre = 'Televisores')),
    ('Camisa Polo', 'REF010', 'Camisa Polo de algodón para hombre', 45.00, 25, 5, (SELECT id FROM Categoria WHERE nombre = 'Camisas')),
    ('Jeans Clásicos', 'REF011', 'Jeans Levis clásicos para hombre', 60.00, 18, 5, (SELECT id FROM Categoria WHERE nombre = 'Pantalones')),
    ('Zapatos Deportivos', 'REF012', 'Zapatos deportivos Nike para correr', 90.00, 20, 5, (SELECT id FROM Categoria WHERE nombre = 'Calzado')),
    ('Cafetera de goteo', 'REF013', 'Cafetera eléctrica de goteo, 12 tazas, con filtro reutilizable.', 49.99, 50, 1, (SELECT id FROM Categoria WHERE nombre = 'Pequeños Electrodomésticos de Cocina')),
    ('Tostadora de pan', 'REF014', 'Tostadora de 2 rebanadas, con control de dorado y bandeja extraíble.', 29.99, 30, 1, (SELECT id FROM Categoria WHERE nombre = 'Pequeños Electrodomésticos de Cocina')),
    ('Batidora de mano', 'REF015', 'Batidora de mano con 5 velocidades y accesorios de mezcla.', 35.50, 25, 1, (SELECT id FROM Categoria WHERE nombre = 'Pequeños Electrodomésticos de Cocina')),
    ('Aspiradora ciclónica', 'REF016', 'Aspiradora sin bolsa, potente y fácil de manejar.', 89.99, 15, 1, (SELECT id FROM Categoria WHERE nombre = 'Aspiración y Limpieza')),
    ('Plancha de vapor', 'REF017', 'Plancha de vapor con suela de cerámica y sistema anti-goteo.', 39.95, 40, 1, (SELECT id FROM Categoria WHERE nombre = 'Electrodomésticos de Cuidado Personal')),
    ('Televisor LED 50"', 'REF018', 'Televisor LED de 50 pulgadas, 4K UHD, Smart TV.', 499.99, 10, 1, (SELECT id FROM Categoria WHERE nombre = 'Televisores')),
    ('Laptop portátil', 'REF019', 'Laptop 15.6" con procesador i5, 8GB RAM, 256GB SSD.', 699.99, 20, 1, (SELECT id FROM Categoria WHERE nombre = 'Portátiles')),
    ('Smartphone Android', 'REF020', 'Smartphone de 6.5", 128GB almacenamiento, cámara triple.', 299.99, 100, 1, (SELECT id FROM Categoria WHERE nombre = 'Teléfonos Móviles')),
    ('Silla ergonómica', 'REF021', 'Silla de oficina ergonómica con ajuste de altura y soporte lumbar.', 149.99, 25, 1, (SELECT id FROM Categoria WHERE nombre = 'Muebles de Oficina')),
    ('Mesa de comedor', 'REF022', 'Mesa de comedor de madera, 6 plazas, diseño moderno.', 399.99, 10, 1, (SELECT id FROM Categoria WHERE nombre = 'Muebles de Hogar'));
    

    
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
('2024-01-01', 'Pendiente de recogida', 1, NULL), ('2024-01-02', 'Pendiente de empaquetado', 2, NULL), 
('2024-01-03', 'Pendiente de recogida', 3, 'Producto dañado'), ('2024-01-04', 'Pendiente de empaquetado', 4, NULL), 
('2024-01-05', 'Pendiente de recogida', 5, 'Retraso en entrega'), ('2024-01-06', 'Pendiente de empaquetado', 6, NULL), 
('2024-01-07', 'Pendiente de recogida', 7, NULL), ('2024-01-08', 'Pendiente de empaquetado', 8, 'Producto faltante'), 
('2024-01-09', 'Pendiente de recogida', 9, NULL), ('2024-01-10', 'Pendiente de empaquetado', 10, NULL), 
('2024-01-11', 'Pendiente de recogida', 11, 'Producto dañado'), ('2024-01-12', 'Pendiente de empaquetado', 12, NULL), 
('2024-01-13', 'Pendiente de recogida', 13, 'Error en cantidad'), ('2024-01-14', 'Pendiente de empaquetado', 14, NULL), 
('2024-01-15', 'Pendiente de recogida', 15, NULL);

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
(13, 13, 10), (14, 14, 4), (15, 15, 12), (15,14,2), (15,13,2);

-- Tabla OrdenTrabajoProducto
INSERT INTO OrdenTrabajoProducto (orden_trabajo_id, producto_id, cantidad) VALUES 
(1, 1, 2), (2, 2, 3), (3, 3, 1), (4, 4, 4), (5, 5, 6), (6, 6, 5), 
(7, 7, 2), (8, 8, 7), (9, 9, 8), (10, 10, 3), (11, 11, 9), (12, 12, 10), 
(13, 13, 3), (14, 14, 12), (15, 15, 4),(1, 2, 3), (1, 3, 2), (2, 4, 5), (2, 5, 1), (3, 6, 4), (3, 7, 6), 
(4, 8, 3), (4, 9, 2), (5, 10, 7), (5, 11, 1), (6, 12, 5), (6, 13, 8), 
(7, 14, 2), (7, 15, 9), (8, 1, 4),(1, 1, 1), (1, 1, 3), (1, 1, 4), (1, 1, 5), (1, 1, 6),
(1, 1, 7), (1, 1, 8), (1, 1, 9), (1, 1, 10), (1, 1, 11),
(1, 1, 12), (1, 1, 13), (1, 1, 14), (1, 1, 15), (1, 2, 1),
(1, 2, 2), (1, 2, 4), (1, 2, 5), (1, 2, 6), (1, 2, 7),
(1, 2, 8), (1, 2, 9), (1, 2, 10), (1, 2, 11), (1, 2, 12),
(1, 2, 13), (1, 2, 14), (1, 2, 15), (1, 3, 1), (1, 3, 3);

	
