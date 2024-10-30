--Inicialización de todas las tablas necesarias para empezar a trabajar
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

-- Tabla Producto
INSERT INTO Producto (referencia, datosBasicos, precio, unidades, localizacion_id) VALUES 
('A001', 'Producto 1', 10, 50, 1), ('A002', 'Producto 2', 20, 30, 2), ('A003', 'Producto 3', 15, 45, 3), 
('A004', 'Producto 4', 25, 20, 4), ('A005', 'Producto 5', 18, 10, 5), ('A006', 'Producto 6', 12, 60, 6), 
('A007', 'Producto 7', 22, 33, 7), ('A008', 'Producto 8', 30, 25, 8), ('A009', 'Producto 9', 35, 55, 9), 
('A010', 'Producto 10', 40, 15, 10), ('A011', 'Producto 11', 45, 5, 11), ('A012', 'Producto 12', 50, 65, 12), 
('A013', 'Producto 13', 55, 75, 13), ('A014', 'Producto 14', 60, 35, 14), ('A015', 'Producto 15', 65, 25, 15);

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

	
	
	
	
	
	