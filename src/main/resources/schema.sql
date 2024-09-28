--Primero se deben borrar todas las tablas (de detalle a maestro) y lugo anyadirlas (de maestro a detalle)
--(en este caso en cada aplicacion se usa solo una tabla, por lo que no hace falta)

--Para giis.demo.tkrun:
drop table Carreras;
drop table Producto;
drop table Cliente;
drop table PedidoProducto;
drop table ;

create table Carreras (id int primary key not null, inicio date not null, fin date not null, fecha date not null, descr varchar(32), check(inicio<=fin), check(fin<fecha));
create table Producto (id int PRIMARY KEY, TEXT NOT NULL, datosBasicos TEXT NOT NULL); 
CREATE TABLE Cliente (dni TEXT PRIMARY KEY, nombre TEXT NOT NULL, apellidos TEXT NOT NULL);
CREATE TABLE PedidoProducto (id INTEGER PRIMARY KEY, cliente_dni TEXT NOT NULL, producto_id INTEGER NOT NULL, cantidad INTEGER NOT NULL, precio DECIMAL(10, 2) NOT NULL, FOREIGN KEY (cliente_dni) REFERENCES Cliente(dni), FOREIGN KEY (producto_id) REFERENCES Producto(id));