package consulta;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Cliente {
	
	public static void main(String[] args) {
		
		ServerSocket server = null;
		
		try {
			
			server = new ServerSocket(5052);
			System.out.println("Ingrese la información necesaria");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			String codigo, cantidad, tipoCliente;
			
			/* Enviar al servidor el código de producto a consultar -- Inicio */
			codigo = br.readLine();
			Socket socket = new Socket("localhost", 5051);
			OutputStream salida = socket.getOutputStream();
			DataOutputStream dos = new DataOutputStream(salida);
			dos.writeUTF(codigo);
			/* Enviar al servidor el código de producto a consultar -- Fin */
			
			
			/* Enviar al servidor la cantidad del producto a adquirir -- Inicio */
			cantidad = br.readLine();
			Socket socket2 = new Socket("localhost", 5051);
			OutputStream salida2 = socket2.getOutputStream();
			DataOutputStream dos2 = new DataOutputStream(salida2);
			dos2.writeUTF(cantidad);
			/* Enviar al servidor la cantidad del producto a adquirir -- Fin */
			
			
			/* Enviar al servidor el tipo de cliente para aplicar descuento -- Inicio */
			tipoCliente = br.readLine();
			Socket socket3 = new Socket("localhost", 5051);
			OutputStream salida3 = socket3.getOutputStream();
			DataOutputStream dos3 = new DataOutputStream(salida3);
			dos3.writeUTF(tipoCliente);
			/* Enviar al servidor el tipo de cliente para aplicar descuento -- Fin */
			
			
			/* Respuesta del servidor con el importe total a pagar -- Inicio */
			Socket socket4 = server.accept();
			InputStream entrada = socket4.getInputStream();
			DataInputStream dis = new DataInputStream(entrada);
			System.out.println(dis.readUTF());
			/* Respuesta del servidor con el importe total a pagar -- Fin */
			
		} catch (Exception e) {
			
		}

	}

}
