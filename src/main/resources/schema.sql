drop table Categoria;
drop table Producto;
drop table Cliente;
drop table Carrito; 
drop table ProductosPedido;
drop table Pedido;
drop table Almacenero;
drop table OrdenTrabajo;
drop table OrdenTrabajoProducto;
drop table OrdenTrabajoProductoRecogido;
drop table Incidencia;
drop table Localizacion;
drop table Caja;
drop table Paquete;
drop table Albaran;
drop table AlbaranPedido;
drop table PaqueteProducto;
drop table VehiculoPaquete;
drop table Vehiculo;


CREATE TABLE Categoria (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre TEXT NOT NULL,
    id_padre INTEGER NULL,
    FOREIGN KEY (id_padre) REFERENCES categoria(id) ON DELETE CASCADE
);
CREATE TABLE Producto (id INTEGER PRIMARY KEY AUTOINCREMENT,nombre TEXT NOT NULL,referencia TEXT NOT NULL UNIQUE, datosBasicos TEXT NOT NULL, precio INTEGER NOT NULL, unidades INTEGER NOT NULL, localizacion_id INTEGER,id_categoria INTEGER NOT NULL, unidadesMinimas INTEGER , unidadesReposicion INTEGER , FOREIGN KEY (localizacion_id) REFERENCES Localizacion(id));
CREATE TABLE Cliente (id INTEGER PRIMARY KEY AUTOINCREMENT,dni TEXT NOT NULL, nombre TEXT NOT NULL, apellidos TEXT NOT NULL,direccion TEXT,numeroTelefono INTEGER,empresa BOOLEAN, UNIQUE(dni));
CREATE TABLE Carrito (id INTEGER PRIMARY KEY AUTOINCREMENT, dni TEXT NOT NULL, referencia TEXT NOT NULL, cantidad INTEGER NOT NULL, precio INTEGER NOT NULL, FOREIGN KEY (referencia) REFERENCES Producto(referencia),FOREIGN KEY (dni) REFERENCES Cliente(dni));
CREATE TABLE ProductosPedido (id INTEGER PRIMARY KEY AUTOINCREMENT, pedido_id INTEGER NOT NULL, producto_id INTEGER NOT NULL, cantidad INTEGER NOT NULL, FOREIGN KEY (pedido_id) REFERENCES Pedido(id), FOREIGN KEY (producto_id) REFERENCES Producto(id));
CREATE TABLE Pedido (id INTEGER PRIMARY KEY AUTOINCREMENT, cliente_id int NOT NULL, fecha DATE NOT NULL, total DECIMAL(10, 2) NOT NULL, estado TEXT CHECK (estado IN ('Pendiente de recogida', 'Recogido','PendienteDePago','En empaquetado')) NOT NULL, orden_trabajo_id INTEGER, FOREIGN KEY (cliente_id) REFERENCES Cliente(id), FOREIGN KEY (orden_trabajo_id) REFERENCES OrdenTrabajo(id));
CREATE TABLE Almacenero (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT NOT NULL, apellido TEXT NOT NULL);
CREATE TABLE OrdenTrabajo (id INTEGER PRIMARY KEY AUTOINCREMENT, fecha_creacion DATE NOT NULL, estado TEXT CHECK(estado IN ('Pendiente de recogida','En recogida', 'Pendiente de empaquetado','En empaquetado','Empaquetado')),almacenero_id INTEGER NOT NULL, incidencia TEXT, FOREIGN KEY (almacenero_id) REFERENCES Almacenero(id));
CREATE TABLE OrdenTrabajoProducto (id INTEGER PRIMARY KEY AUTOINCREMENT, orden_trabajo_id INTEGER NOT NULL, producto_id INTEGER NOT NULL, cantidad INTEGER NOT NULL, FOREIGN KEY (orden_trabajo_id) REFERENCES OrdenTrabajo(id), FOREIGN KEY (producto_id) REFERENCES Producto(id));
CREATE TABLE OrdenTrabajoProductoRecogido (id INTEGER PRIMARY KEY AUTOINCREMENT, orden_trabajo_id INTEGER NOT NULL, producto_id INTEGER NOT NULL, cantidad INTEGER NOT NULL, FOREIGN KEY (orden_trabajo_id) REFERENCES OrdenTrabajo(id), FOREIGN KEY (producto_id) REFERENCES Producto(id));
CREATE TABLE PaqueteProducto (id INTEGER PRIMARY KEY AUTOINCREMENT, orden_trabajo_id INTEGER NOT NULL, producto_id INTEGER NOT NULL, cantidad INTEGER NOT NULL, FOREIGN KEY (orden_trabajo_id) REFERENCES OrdenTrabajo(id), FOREIGN KEY (producto_id) REFERENCES Producto(id));
CREATE TABLE Incidencia (id INTEGER PRIMARY KEY AUTOINCREMENT, orden_trabajo_id INTEGER NOT NULL, descripcion TEXT NOT NULL, fecha DATE NOT NULL, FOREIGN KEY (orden_trabajo_id) REFERENCES OrdenTrabajo(id));
CREATE TABLE Localizacion (id INTEGER PRIMARY KEY AUTOINCREMENT, pasillo INTEGER NOT NULL, estanteria TEXT CHECK(estanteria IN ('Izquierda', 'Derecha')),posicion integer not null, altura integer not null);
CREATE TABLE Caja(id INTEGER PRIMARY KEY AUTOINCREMENT);
CREATE TABLE Paquete(id INTEGER PRIMARY KEY AUTOINCREMENT, caja_id integer not null, ordentrabajo_id integer not null,tipo TEXT CHECK(tipo IN ('Regional','Nacional')), FOREIGN KEY (caja_id) REFERENCES Caja(id),FOREIGN KEY (ordentrabajo_id) REFERENCES OrdenTrabajo(id));
CREATE TABLE AlbaranPedido(id INTEGER PRIMARY KEY AUTOINCREMENT, id_Albaran integer not null, id_Pedido integer not null, FOREIGN KEY (id_Albaran) REFERENCES Albaran(id), FOREIGN KEY (id_Pedido) REFERENCES Pedido(id));
CREATE TABLE Albaran(id INTEGER PRIMARY KEY AUTOINCREMENT);
CREATE TABLE VehiculoPaquete(id INTEGER PRIMARY KEY AUTOINCREMENT, vehiculo_id INTEGER, paquete_id INTEGER,FOREIGN KEY (vehiculo_id) REFERENCES Vehiculo(id), FOREIGN KEY (paquete_id) REFERENCES Paquete(id));
CREATE TABLE Vehiculo(id INTEGER PRIMARY KEY AUTOINCREMENT, matricula TEXT NOT NULL, tipo TEXT CHECK(tipo IN ('Regional','Nacional')));
