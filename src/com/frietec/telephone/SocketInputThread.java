package com.frietec.telephone;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketInputThread extends Thread {
	private Socket socket;

	public SocketInputThread(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try {
			while (true) {
				DataInputStream in = new DataInputStream(this.socket.getInputStream());
				System.out.println(in.readUTF());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
