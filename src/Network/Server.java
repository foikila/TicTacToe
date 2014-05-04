package Network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

public class Server {
	public static int port = 444;
	public static String ip;
	public static ServerSocket server;

	public static ArrayList<Socket> listSockets = new ArrayList<Socket>();
	public static ArrayList<Package> listData = new ArrayList<Package>();

	private static Runnable accept = new Runnable() {
		@Override
		public void run() {
			// starts the send thread
			new Thread(send).start();
			// Starts the receive thread
			new Thread(receive).start();

			while (true) {
				try {
					// starts listening to the server socket
					Socket socket = server.accept();
					// Opens the stream from the socket
					ObjectInputStream ois = new ObjectInputStream(
							socket.getInputStream());
					// Adds the socket to
					listSockets.add(socket);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	};

	private static Runnable send = new Runnable() {
		@Override
		public void run() {
			while (true) {
				ObjectOutputStream oos;
				// makes sure that I don't start to many
				for (int i = 0; i < listSockets.size(); i++) {
					try {
						// gets the output stream
						oos = new ObjectOutputStream(listSockets.get(i)
								.getOutputStream());
						// writes to the stream
						oos.writeObject(listData);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}
	};

	private static Runnable receive = new Runnable() {
		@Override
		public void run() {
			ObjectInputStream ois;
			while (true) {
				for (int i = 0; i < listSockets.size(); i++) {
					try {
						ois = new ObjectInputStream(listSockets.get(i)
								.getInputStream());

						Package p = (Package) ois.readObject();
						System.out.println("Got new data: " + p.toString());
						listData.set(i, p);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	};

	public static void main(String[] args) {
		Date d = new Date();
		System.out.println("Welcome human\nServer has now started..... \n" + d);
		try {
			server = new ServerSocket(port);
			// starts to listen
			new Thread(accept).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
