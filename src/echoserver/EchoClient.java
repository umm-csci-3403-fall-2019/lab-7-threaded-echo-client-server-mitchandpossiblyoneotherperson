package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;

public class EchoClient {
	public static final int PORT_NUMBER = 6013;

	public static void main(String[] args) throws IOException {
		EchoClient client = new EchoClient();
		client.start();
	}

	private void start() throws IOException {
		Socket socket = new Socket("localhost", PORT_NUMBER);
		InputStream socketInputStream = socket.getInputStream();
		OutputStream socketOutputStream = socket.getOutputStream();

		Thread keyboardRead = new Thread(() ->{
		try {
			int line;
			while ((line = System.in.read()) != -1) {
				socketOutputStream.write(line);
				int newLine = socketInputStream.read();
				//System.out.write(newLine);
			}
			socket.shutdownOutput();
			System.out.flush();
			socket.close();
		}
		catch (ConnectException ce) {
			System.out.println("We were unable to connect to the host");
			System.out.println("You should make sure the server is running.");
		} catch (IOException ioe) {
			System.out.println("We caught an unexpected exception");
			System.err.println(ioe);
		}
	});

		keyboardRead.start();



	}

}