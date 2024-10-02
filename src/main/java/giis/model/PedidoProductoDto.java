package giis.model;

public class PedidoProductoDto {
	
		/*
		 * fecha DATE NOT NULL,
		 *  total DECIMAL(10, 2) NOT NULL,
		 *  estado TEXT NOT NULL, orden_trabajo_id INTEGER,
		 */
	    private int pedido_id;
		private int producto_id;
		private int cantidad;
		private double precio;
		
		public PedidoProductoDto(int pedido_id, int producto_id, int cantidad,double precio) {
			this.pedido_id=pedido_id;
			this.producto_id=producto_id;
			this.cantidad=cantidad;
			this.precio=precio;
		}

		@Override
		public String toString() {
			return "PedidoProductoDto [pedido_id=" + pedido_id + ", producto_id=" + producto_id + ", cantidad="
					+ cantidad + ", precio=" + precio + "]";
		}

		public int getPedido_id() {
			return pedido_id;
		}

		public void setPedido_id(int pedido_id) {
			this.pedido_id = pedido_id;
		}

		public int getProducto_id() {
			return producto_id;
		}

		public void setProducto_id(int producto_id) {
			this.producto_id = producto_id;
		}

		public int getCantidad() {
			return cantidad;
		}

		public void setCantidad(int cantidad) {
			this.cantidad = cantidad;
		}

		public double getPrecio() {
			return precio;
		}

		public void setPrecio(double precio) {
			this.precio = precio;
		}
	

		

		
}
