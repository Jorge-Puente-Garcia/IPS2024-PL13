drop table Producto;
drop table Cliente;
drop table ProductosPedido;
drop table Pedido;
drop table Almacenero;
drop table OrdenTrabajo;
drop table OrdenTrabajoProducto;
drop table Incidencia;
drop table Localizacion;

CREATE TABLE Producto (id INTEGER PRIMARY KEY AUTOINCREMENT,referencia TEXT NOT NULL UNIQUE, datosBasicos TEXT NOT NULL, precio INTEGER NOT NULL,unidades INTEGER NOT NULL,localizacion_id INTEGER,FOREIGN KEY (localizacion_id) REFERENCES Localizacion(id)););
CREATE TABLE Cliente (id INTEGER PRIMARY KEY AUTOINCREMENT,dni TEXT NOT NULL, nombre TEXT NOT NULL, apellidos TEXT NOT NULL, UNIQUE(dni));
CREATE TABLE ProductosPedido (id INTEGER PRIMARY KEY AUTOINCREMENT, pedido_id INTEGER NOT NULL, producto_id INTEGER NOT NULL, cantidad INTEGER NOT NULL, FOREIGN KEY (pedido_id) REFERENCES Pedido(id), FOREIGN KEY (producto_id) REFERENCES Producto(id));
CREATE TABLE Pedido (id INTEGER PRIMARY KEY AUTOINCREMENT, cliente_id int NOT NULL, fecha DATE NOT NULL, total DECIMAL(10, 2) NOT NULL, estado TEXT CHECK (estado IN ('Pendiente de recogida', 'Recogido')) NOT NULL, orden_trabajo_id INTEGER, FOREIGN KEY (cliente_id) REFERENCES Cliente(id), FOREIGN KEY (orden_trabajo_id) REFERENCES OrdenTrabajo(id));
CREATE TABLE Almacenero (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT NOT NULL, apellido TEXT NOT NULL);
CREATE TABLE OrdenTrabajo (id INTEGER PRIMARY KEY AUTOINCREMENT, fecha_creacion DATE NOT NULL, estado TEXT CHECK(estado IN ('En recogida', 'Pendiente de empaquetado', 'Empaquetado')), almacenero_id INTEGER NOT NULL, incidencia TEXT, FOREIGN KEY (almacenero_id) REFERENCES Almacenero(id));
CREATE TABLE OrdenTrabajoProducto (id INTEGER PRIMARY KEY AUTOINCREMENT, orden_trabajo_id INTEGER NOT NULL, producto_id INTEGER NOT NULL, cantidad INTEGER NOT NULL, FOREIGN KEY (orden_trabajo_id) REFERENCES OrdenTrabajo(id), FOREIGN KEY (producto_id) REFERENCES Producto(id));
CREATE TABLE Incidencia (id INTEGER PRIMARY KEY AUTOINCREMENT, orden_trabajo_id INTEGER NOT NULL, descripcion TEXT NOT NULL, fecha DATE NOT NULL, FOREIGN KEY (orden_trabajo_id) REFERENCES OrdenTrabajo(id));
CREATE TABLE Localizacion (id INTEGER PRIMARY KEY AUTOINCREMENT, pasillo INTEGER NOT NULL,fila INTEGER NOT NULL, columna INTEGER NOT NULL, estanteria INTEGER NOT NULL, cara CHAR NOT NULL);





