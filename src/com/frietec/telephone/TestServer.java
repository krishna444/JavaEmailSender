package com.frietec.telephone;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestServer extends Thread {
	private static int PORT = 2456;
	private ServerSocket serverSocket;
	private List<Socket> sockets;

	public TestServer() throws IOException {
		this.serverSocket = new ServerSocket(PORT);
		this.sockets = new ArrayList<>();
		// this.serverSocket.setSoTimeout(10000);
	}

	public void run() {
		while (true) {
			try {
				System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
				Socket server = serverSocket.accept();
				this.sockets.add(server);
				SocketOutputThread socketOutput = new SocketOutputThread(server);
				socketOutput.start();
				SocketInputThread socketIOThread = new SocketInputThread(server);
				socketIOThread.start();
				// System.out.println("Just connected to " +
				// server.getRemoteSocketAddress());
				// DataInputStream in = new
				// DataInputStream(server.getInputStream());
				// System.out.println(in.readUTF());
				// DataOutputStream out = new
				// DataOutputStream(server.getOutputStream());
				// out.writeUTF("Thank you for connecting to " +
				// server.getLocalSocketAddress() + "\nGoodbye!");
				// server.close();
			} catch (SocketTimeoutException s) {
				System.out.println("Socket timed out!");
				break;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}

	public static void main(String... args) {
		try {
			Thread t = new TestServer();
			t.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class SocketOutputThread extends Thread {
	private Socket socket;

	public SocketOutputThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {

		DataOutputStream out;
		try {
			StringBuilder stringBuilder = new StringBuilder();
			while (true) {
				int length = (int) (Math.random() * 20);
				for (int i = 1; i < length; i++) {
					stringBuilder.append(" " + randomWord((int) (Math.random() * i + 1)));
				}
				//System.out.println(stringBuilder.toString());
				out = new DataOutputStream(this.socket.getOutputStream());
				out.writeUTF(stringBuilder.toString());
				stringBuilder.delete(0, stringBuilder.length());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static String randomWord(int length) {
		Random random = new Random();
		StringBuilder word = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			word.append((char) ('a' + random.nextInt(26)));
		}

		return word.toString();
	}

}
