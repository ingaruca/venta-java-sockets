package consulta;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {
	
	private static Producto objProducto;
	private static ArrayList<Producto> catalogoProductos;

	public static void main(String[] args) {
		
		int precio, cantidad, subTotal;
		double total;
		String _codigo, _cantidad, _tipoCliente;
		
		ServerSocket server = null;
		
		try {
			
			server = new ServerSocket(5051);
			System.out.println(".:: SERVIDOR CON CONEXIÓN ::.");
			
			
			/* Obtener código enviado por el cliente -- Inicio */
			Socket socket = server.accept();
			InputStream entrada = socket.getInputStream();
			DataInputStream dis = new DataInputStream(entrada);
			_codigo = dis.readUTF();			
			System.out.println("El producto seleccionado es: " + nombreProducto(_codigo));
			/* Obtener código enviado por el cliente -- Fin */
			
			
			/* Obtener la cantidad enviado por el cliente -- Inicio */
			Socket socket2 = server.accept();
			InputStream entrada2 = socket2.getInputStream();
			DataInputStream dis2 = new DataInputStream(entrada2);
			_cantidad = dis2.readUTF();			
			System.out.println("La cantidad ha adquirir es: " + _cantidad);
			/* Obtener la cantidad enviado por el cliente -- Fin */
			
			
			/* Obtener el tipo de cliente -- Inicio */
			Socket socket3 = server.accept();
			InputStream entrada3 = socket3.getInputStream();
			DataInputStream dis3 = new DataInputStream(entrada3);
			_tipoCliente = dis3.readUTF();
			/* Obtener el tipo de cliente -- Fin */
			
			
			/* Calcular monto Total -- Inicio */
			precio = precioProducto(_codigo);
			cantidad = Integer.parseInt(_cantidad);
			subTotal = precio * cantidad;
			total = calcularTotal(_tipoCliente, subTotal);			
			/* Calcular monto Total -- Fin */
			
			
			/* Respuesta al cliente -- Inicio */
			Socket socket4 = new Socket("localhost", 5052);
			OutputStream salida = socket4.getOutputStream();
			DataOutputStream dos = new DataOutputStream(salida);
			dos.writeUTF("El importe total a pagar es: " + total);			
			/* Respuesta al cliente -- Fin */
			
			
			dos.close();
			socket.close();
			socket2.close();
			socket3.close();
			
		} catch (IOException e) {
			System.out.println(e.getMessage().toString());
		}

	}
	
	private static void llenarDatos() {
		
		catalogoProductos = new ArrayList<Producto>();
		
		objProducto  = new Producto("A100", "Samsung UHD 50", 2700);
		catalogoProductos.add(objProducto);
		
		objProducto  = new Producto("A200", "Play Station 4 Pro", 1800);
		catalogoProductos.add(objProducto);
		
		objProducto  = new Producto("A300", "Iphone X 64GB", 3500);
		catalogoProductos.add(objProducto);
		
		objProducto  = new Producto("A400", "Macbook Pro 13 256GB", 4850);
		catalogoProductos.add(objProducto);
		
	}
	
	private static String nombreProducto(String codigo) {
		
		String producto = "";
		String cod = "";
		
		llenarDatos();
		
		for (int i = 0; i < catalogoProductos.size(); i++) {
			cod = catalogoProductos.get(i).getCodigo();
			if (cod.equals(codigo)) {
				producto = catalogoProductos.get(i).getNombre();
				break;
			}
		}
				
		return producto;
		
	}
	
	private static int precioProducto(String codigo) {
		
		int precio = 0;
		String cod = "";
		
		llenarDatos();
		
		for (int i = 0; i < catalogoProductos.size(); i++) {
			cod = catalogoProductos.get(i).getCodigo();
			if (cod.equals(codigo)) {
				precio = catalogoProductos.get(i).getPrecio();
				break;
			}
		}
		
		return precio;
		
	}
	
	private static double calcularTotal(String tipoCliente, int subTotal) {
		
		double total = 0;
		
		switch (tipoCliente) {
		case "1":
			total = subTotal - (subTotal * 0.02);
			break;
		case "2":
			total = subTotal - (subTotal * 0.03);
			break;
		case "3":
			total = subTotal - (subTotal * 0.01);
			break;
		default:
			total = subTotal;
			break;
		}
		
		return total;
		
	}

}
