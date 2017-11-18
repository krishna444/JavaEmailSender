package com.frietec.telephone;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestClient {
	public TestClient() {

	}

	public static void main(String... args) throws UnknownHostException, IOException {
		//
		// Socket clientSocket = new Socket("localhost", 2456);
		// while (true) {
		// DataInputStream in = new
		// DataInputStream(clientSocket.getInputStream());
		// System.out.println(in.readUTF());
		// }

		TestClient client = new TestClient();
		// System.out.println(client.reverseSentence(" THis is tgest "));
		int[] A = { 1, 1, 1, 1, -1, 1, 1, 1 };
		System.out.println(client.longestAlternateSlice(A));
	}

	public String reverseSentence(String S) {
		String[] strings = S.split(" ");
		String reversed = "";

		for (String string : strings) {
			reversed += reverse(string);
			reversed += " ";
		}
		return reversed.trim();

	}

	public int longestAlternateSlice(int[] A) {
		if (A.length <= 1)
			return A.length;
		int longest = 0;
		int previous = check(A[0]);
		int count = 1;
		for (int i = 1; i < A.length; i++) {
			int sign = check(A[i]);
			if (sign == 0) {
				count += 1;
				if (previous == 1) {
					previous = -1;
				} else if (previous == -1) {
					previous = 1;
				} else {
					previous = 0;
				}

			} else if (sign == 1) {
				if (previous == -1 || previous == 0) {
					count += 1;
				} else {
					count = 1;
				}
				previous = sign;

			} else {
				if (previous == 1 || previous == 0) {
					count += 1;
				} else {
					count = 1;
				}
				previous = sign;

			}
			if (count > longest) {
				longest = count;
			}
		}
		return longest;
	}

	private int check(int A) {
		if (A > 0)
			return 1;
		if (A < 0)
			return -1;
		return 0;
	}

	private String reverse(String s) {
		char[] chars = s.toCharArray();
		char[] reversed = new char[chars.length];
		for (int i = 0; i < chars.length; i++) {
			reversed[i] = chars[chars.length - 1 - i];
		}
		return new String(reversed);
	}

}
