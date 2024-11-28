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
delete from PaqueteProducto;
delete from OrdenTrabajoProductoRecogido;
delete from Vehiculo;
delete from VehiculoPaquete;
delete from OrdenTrabajoRecogidaEmpleadoDia;
delete from ProductoRecogidoEmpleadoDia;

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
INSERT INTO Producto (nombre, referencia, datosBasicos, precio, unidades, localizacion_id, id_categoria,unidadesMinimas, unidadesReposicion) VALUES
     ('Smartphone AMOLED', 'REF001', 'Smartphone de última generación con pantalla AMOLED de 6.5 pulgadas y 128GB de almacenamiento', 699.99, 10, 1, (SELECT id FROM Categoria WHERE nombre = 'Teléfonos Móviles'), 5, 10),
    ('Laptop Ultraligera', 'REF002', 'Laptop ultraligera de 14 pulgadas, procesador Intel Core i7, 16GB RAM y 512GB SSD', 1199.99, 5, 2, (SELECT id FROM Categoria WHERE nombre = 'Portátiles'), 5, 10),
    ('Auriculares Inalámbricos', 'REF003', 'Auriculares inalámbricos con cancelación de ruido, duración de batería de 24 horas', 199.99, 15, 3, (SELECT id FROM Categoria WHERE nombre = 'Televisores'), 5, 10),
    ('Cámara Fotográfica', 'REF004', 'Cámara fotográfica profesional de 24MP con lente de 50mm y estabilización óptica', 1499.99, 8, 4, (SELECT id FROM Categoria WHERE nombre = 'Televisores'), 5, 10),
    ('Reloj Inteligente', 'REF005', 'Reloj inteligente con monitor de frecuencia cardíaca, GPS integrado y resistente al agua', 249.99, 20, 5, (SELECT id FROM Categoria WHERE nombre = 'Televisores'), 5, 10),
    ('Tablet Full HD', 'REF006', 'Tablet de 10 pulgadas con pantalla Full HD, 64GB de almacenamiento y batería de larga duración', 329.99, 12, 6, (SELECT id FROM Categoria WHERE nombre = 'Tablets'), 5, 10),
    ('PC de Escritorio', 'REF007', 'PC de Escritorio Lenovo con 8GB RAM y 1TB HDD', 650.00, 7, 2, (SELECT id FROM Categoria WHERE nombre = 'Sobremesa'), 5, 10),
    ('iPhone 256GB', 'REF008', 'iPhone con 256GB de almacenamiento y cámara avanzada', 999.00, 15, 1, (SELECT id FROM Categoria WHERE nombre = 'Teléfonos Móviles'), 5, 10),
    ('Televisor 4K', 'REF009', 'Televisor Samsung de 55 pulgadas, 4K UHD', 1200.00, 4, 3, (SELECT id FROM Categoria WHERE nombre = 'Televisores'), 5, 10),
    ('Camisa Polo', 'REF010', 'Camisa Polo de algodón para hombre', 45.00, 25, 5, (SELECT id FROM Categoria WHERE nombre = 'Camisas'), 5, 10),
    ('Jeans Clásicos', 'REF011', 'Jeans Levis clásicos para hombre', 60.00, 18, 5, (SELECT id FROM Categoria WHERE nombre = 'Pantalones'), 5, 10),
    ('Zapatos Deportivos', 'REF012', 'Zapatos deportivos Nike para correr', 90.00, 20, 5, (SELECT id FROM Categoria WHERE nombre = 'Calzado'), 5, 10),
    ('Cafetera de goteo', 'REF013', 'Cafetera eléctrica de goteo, 12 tazas, con filtro reutilizable.', 49.99, 50, 1, (SELECT id FROM Categoria WHERE nombre = 'Pequeños Electrodomésticos de Cocina'), 5, 10),
    ('Tostadora de pan', 'REF014', 'Tostadora de 2 rebanadas, con control de dorado y bandeja extraíble.', 29.99, 30, 1, (SELECT id FROM Categoria WHERE nombre = 'Pequeños Electrodomésticos de Cocina'), 5, 10),
    ('Batidora de mano', 'REF015', 'Batidora de mano con 5 velocidades y accesorios de mezcla.', 35.50, 25, 1, (SELECT id FROM Categoria WHERE nombre = 'Pequeños Electrodomésticos de Cocina'), 5, 10),
    ('Aspiradora ciclónica', 'REF016', 'Aspiradora sin bolsa, potente y fácil de manejar.', 89.99, 15, 1, (SELECT id FROM Categoria WHERE nombre = 'Aspiración y Limpieza'), 5, 10),
    ('Plancha de vapor', 'REF017', 'Plancha de vapor con suela de cerámica y sistema anti-goteo.', 39.95, 40, 1, (SELECT id FROM Categoria WHERE nombre = 'Electrodomésticos de Cuidado Personal'), 5, 10),
    ('Televisor LED 50"', 'REF018', 'Televisor LED de 50 pulgadas, 4K UHD, Smart TV.', 499.99, 10, 1, (SELECT id FROM Categoria WHERE nombre = 'Televisores'), 5, 10),
    ('Laptop portátil', 'REF019', 'Laptop 15.6" con procesador i5, 8GB RAM, 256GB SSD.', 699.99, 20, 1, (SELECT id FROM Categoria WHERE nombre = 'Portátiles'), 5, 10),
    ('Smartphone Android', 'REF020', 'Smartphone de 6.5", 128GB almacenamiento, cámara triple.', 299.99, 100, 1, (SELECT id FROM Categoria WHERE nombre = 'Teléfonos Móviles'), 5, 10),
    ('Silla ergonómica', 'REF021', 'Silla de oficina ergonómica con ajuste de altura y soporte lumbar.', 149.99, 25, 1, (SELECT id FROM Categoria WHERE nombre = 'Muebles de Oficina'), 5, 10),
    ('Mesa de comedor', 'REF022', 'Mesa de comedor de madera, 6 plazas, diseño moderno.', 399.99, 10, 1, (SELECT id FROM Categoria WHERE nombre = 'Muebles de Hogar'), 5, 10);    

    
INSERT INTO Carrito (dni, referencia, cantidad, precio) VALUES
	('12345678A', 'REF001', 2, 1399.98),
	('12345678A', 'REF002', 1, 1199.99),
	('23456789B', 'REF003', 3, 599.97),
	('23456789B', 'REF004', 2, 2999.98);

-- Tabla Cliente
INSERT INTO Cliente (dni, nombre, apellidos, direccion, numeroTelefono, empresa) VALUES 
('12345678A', 'Luis', 'Pérez García', 'Calle Mayor 10, Madrid', 600123456, 0),
('87654321B', 'Ana', 'López Martín', 'Avenida Central 25, Barcelona', 654987321, 1),
('11223344C', 'Carlos', 'Sánchez Ruiz', 'Calle Luna 15, Valencia', 671234567, 0),
('22334455D', 'María', 'Gómez Fernández', 'Plaza Sol 5, Sevilla', 689654321, 1),
('33445566E', 'Elena', 'Torres Pérez', 'Calle Verde 8, Bilbao', 678987654, 0),
('44556677F', 'Javier', 'Hernández Gil', 'Calle Azul 12, Málaga', 612345678, 1),
('55667788G', 'Clara', 'Navarro López', 'Paseo del Prado 7, Zaragoza', 679876543, 0),
('66778899H', 'Pablo', 'Romero Díaz', 'Calle Naranja 19, Murcia', 634567890, 1),
('77889900I', 'Laura', 'Méndez Ortiz', 'Avenida Sur 3, Granada', 610987654, 0),
('88990011J', 'Miguel', 'Fernández Sánchez', 'Plaza Norte 1, Oviedo', 645678901, 1),
('99001122K', 'Sofía', 'Vega Morales', 'Calle Blanca 4, Pamplona', 608765432, 0),
('00112233L', 'Diego', 'Reyes Torres', 'Ronda Este 11, Salamanca', 623456789, 1),
('11223344M', 'Lucía', 'Cabrera Álvarez', 'Calle Roja 6, Toledo', 657890123, 0),
('22334455N', 'Adrián', 'Campos Romero', 'Plaza Dorada 22, Cádiz', 629876543, 1),
('33445566O', 'Isabel', 'Iglesias León', 'Calle Gris 5, Burgos', 678123456, 0);

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
(1, 1, 2), (1, 2, 3), (1, 3, 2), 
(3, 3, 1), (3, 6, 4), (3, 7, 6), 
(5, 5, 6), (5, 10, 7), (5, 11, 1), 
(7, 7, 2), (7, 14, 2), (7, 15, 9), 
(9, 9, 8), (11, 11, 9), (13, 13, 3);

INSERT INTO OrdenTrabajoProductoRecogido (orden_trabajo_id, producto_id, cantidad) VALUES 
(1, 1, 0), (1, 2, 0), (1, 3, 0), 
(3, 3, 0), (3, 6, 0), (3, 7, 0), 
(5, 5, 0), (5, 10, 0), (5, 11, 0), 
(7, 7, 0), (7, 14, 0), (7, 15, 0), 
(9, 9, 0), (11, 11, 0), (13, 13, 0),
(2, 2, 3), (2, 4, 5), (2, 5, 1), 
(4, 4, 4), (4, 8, 3), (4, 9, 2), 
(6, 6, 5), (6, 12, 5), (6, 13, 8), 
(8, 8, 7), (8, 1, 4), (10, 10, 3),
(12, 12, 10),(14, 14, 12);

INSERT INTO PaqueteProducto (orden_trabajo_id, producto_id, cantidad) VALUES 
(1, 1, 0), (1, 2, 0), (1, 3, 0), 
(3, 3, 0), (3, 6, 0), (3, 7, 0), 
(5, 5, 0), (5, 10, 0), (5, 11, 0), 
(7, 7, 0), (7, 14, 0), (7, 15, 0), 
(9, 9, 0), (11, 11, 0), (13, 13, 0),
(2, 2, 0), (2, 4, 0), (2, 5, 0), 
(4, 4, 0), (4, 8, 0), (4, 9, 0), 
(6, 6, 0), (6, 12, 0), (6, 13, 0), 
(8, 8, 0), (8, 1, 0), (10, 10, 0),
(12, 12, 0),(14, 14, 0);
	
INSERT INTO Paquete (caja_id, ordentrabajo_id, tipo) VALUES
(1, 1, 'Nacional'), (2, 2, 'Regional'),  
(3, 3, 'Nacional'), (4, 4, 'Regional'),
(5, 5, 'Nacional'), (6, 6, 'Regional'), 
(7, 7, 'Nacional'), (8, 8, 'Regional'), 
(9, 9, 'Nacional'), (10, 10, 'Regional'), 
(11, 11, 'Nacional'), (12, 12, 'Regional'),
(13, 13, 'Nacional'), (14, 14, 'Regional'),
(15, 15, 'Nacional'); 

INSERT INTO OrdenTrabajoRecogidaEmpleadoDia (almacenero_id, dia, ordenTrabajo_id) VALUES
(1, '2024-01-01', 1),
(2, '2024-01-02', 2),
(3, '2024-01-03', 3),
(4, '2024-01-04', 4),
(5, '2024-01-05', 5),
(6, '2024-01-06', 6),
(7, '2024-01-07', 7),
(8, '2024-01-08', 8),
(9, '2024-01-09', 9),
(10, '2024-01-10', 10);

INSERT INTO ProductoRecogidoEmpleadoDia (almacenero_id, dia, producto_id,cantidad) VALUES
(1, '2024-01-01', 1,3),
(2, '2024-01-02', 2,6),
(3, '2024-01-03', 3,2),
(4, '2024-01-04', 4,1),
(5, '2024-01-05', 5,9),
(6, '2024-01-06', 6,12),
(7, '2024-01-07', 7,21),
(8, '2024-01-08', 8,6),
(9, '2024-01-09', 9,88),
(10, '2024-01-10', 10,20);
