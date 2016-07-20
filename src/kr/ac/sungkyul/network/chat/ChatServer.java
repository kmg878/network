package kr.ac.sungkyul.network.chat;


import java.io.IOException;
import java.io.Writer;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;;

public class ChatServer {
	private static final int SERVER_PORT = 3000;

	public static void main(String[] args) {
		

		ServerSocket serverSocket = null;
		
		List<Writer> listWriters = new ArrayList<Writer>();
		

		try {
			// 서버 소켓 생성
			serverSocket = new ServerSocket();

			// 바인딩
			InetAddress inetAddress = InetAddress.getLocalHost();
			String localHostAddress = inetAddress.getHostAddress();
			InetSocketAddress inetSocketAddress = new InetSocketAddress(localHostAddress, SERVER_PORT);
			serverSocket.bind(inetSocketAddress);
			System.out.println("[echo server] binding " + localHostAddress + ":" + SERVER_PORT);

			while (true) {
				// accept: 연결 요청 대기
				Socket socket = serverSocket.accept();

				ChatServerTread thread = new ChatServerTread(socket, listWriters);
				thread.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 서버 소켓 닫기
				if (serverSocket != null && serverSocket.isClosed() == false) {
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	

}
