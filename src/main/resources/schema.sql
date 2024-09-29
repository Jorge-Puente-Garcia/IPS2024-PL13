drop table Producto;
drop table Cliente;
drop table PedidoProducto;
drop table Pedido;
drop table Carrito;
drop table CarritoProducto;
drop table Almacenero;
drop table OrdenTrabajo;
drop table OrdenTrabajoProducto;
drop table Incidencia;

CREATE TABLE Producto (id int PRIMARY KEY, referencia TEXT NOT NULL, datosBasicos TEXT NOT NULL); 
CREATE TABLE Cliente (id TEXT PRIMARY KEY,dni TEXT NOT NULL, nombre TEXT NOT NULL, apellidos TEXT NOT NULL, UNIQUE(dni));
CREATE TABLE PedidoProducto (id INTEGER PRIMARY KEY, pedido_id INTEGER NOT NULL, producto_id INTEGER NOT NULL, cantidad INTEGER NOT NULL, precio DECIMAL(10, 2) NOT NULL, FOREIGN KEY (pedido_id) REFERENCES Pedido(id), FOREIGN KEY (producto_id) REFERENCES Producto(id));
CREATE TABLE Pedido (id INTEGER PRIMARY KEY, cliente_dni TEXT NOT NULL, fecha DATE NOT NULL, total DECIMAL(10, 2) NOT NULL, estado TEXT NOT NULL, orden_trabajo_id INTEGER, FOREIGN KEY (cliente_dni) REFERENCES Cliente(dni), FOREIGN KEY (orden_trabajo_id) REFERENCES OrdenTrabajo(id));
CREATE TABLE Carrito (id INTEGER PRIMARY KEY, cliente_dni TEXT NOT NULL, fecha_creacion DATE NOT NULL, FOREIGN KEY (cliente_dni) REFERENCES Cliente(dni));
CREATE TABLE CarritoProducto (id INTEGER PRIMARY KEY, carrito_id INTEGER NOT NULL, producto_id INTEGER NOT NULL, cantidad INTEGER NOT NULL, FOREIGN KEY (carrito_id) REFERENCES Carrito(id), FOREIGN KEY (producto_id) REFERENCES Producto(id));
CREATE TABLE Almacenero (id INTEGER PRIMARY KEY, nombre TEXT NOT NULL, apellido TEXT NOT NULL);
CREATE TABLE OrdenTrabajo (id INTEGER PRIMARY KEY, fecha_creacion DATE NOT NULL, estado TEXT NOT NULL, almacenero_id INTEGER NOT NULL, incidencia TEXT, FOREIGN KEY (almacenero_id) REFERENCES Almacenero(id));
CREATE TABLE OrdenTrabajoProducto (id INTEGER PRIMARY KEY, orden_trabajo_id INTEGER NOT NULL, producto_id INTEGER NOT NULL, cantidad INTEGER NOT NULL, FOREIGN KEY (orden_trabajo_id) REFERENCES OrdenTrabajo(id), FOREIGN KEY (producto_id) REFERENCES Producto(id));
CREATE TABLE Incidencia (id INTEGER PRIMARY KEY, orden_trabajo_id INTEGER NOT NULL, descripcion TEXT NOT NULL, fecha DATE NOT NULL, FOREIGN KEY (orden_trabajo_id) REFERENCES OrdenTrabajo(id));



