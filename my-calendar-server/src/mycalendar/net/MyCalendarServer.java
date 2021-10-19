package mycalendar.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import mycalendar.config.Settings;

public class MyCalendarServer implements Runnable {

	private ServerSocket server = null;
	private ExecutorService executor = null;
	
	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			try {
				System.out.println("[SERVER] Waiting for new connections..");
				Socket socket = server.accept();
				System.out.println("[SERVER] New connection from " + socket.getInetAddress());
				executor.submit(new UserHandler(socket));
			} catch (IOException e) {
				System.err.println("[SERVER] Error in run():");
				e.printStackTrace();
				server = null;
				return;
			}
		}
	} // run
	
	
	private static MyCalendarServer instance = null;

	public static MyCalendarServer getInstance() {
		if (instance == null)
			instance = new MyCalendarServer();
		return instance;
	}
	
	private MyCalendarServer() {
		try {
			server = new ServerSocket(Settings.PORT);
			executor = Executors.newCachedThreadPool();
			System.out.println("[SERVER] Server started. Port: " + Settings.PORT);
			new Thread(this).start();
		} catch (IOException e) {
			System.out.println("[SERVER] Connection error:");
			e.printStackTrace();
		}
	}
	
} // class MyCalendarServer
