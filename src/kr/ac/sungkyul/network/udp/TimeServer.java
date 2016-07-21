package kr.ac.sungkyul.network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeServer {
	private static final int PORT = 1000;
	private static final int BUFFER_SIZE = 1024;

	public static void main(String[] args) {
		DatagramSocket socket = null;

		try {
			// 1.소켓 생성
			socket = new DatagramSocket(PORT);
			while (true) {
				// 2.수신대기
				System.out.println("수신대기");

				DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
				socket.receive(receivePacket); // blocking

				// 3.데이터 수신
				String message = new String(receivePacket.getData(), 0, receivePacket.getLength(),
						StandardCharsets.UTF_8);
				System.out.println("수신 : " + message);
				// 시간
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
				String data = format.format(new Date());
				if ("time".equals(message)) {
					// 4. 시간 송신
					byte[] sendData = data.getBytes(StandardCharsets.UTF_8);
					DatagramPacket sendPacket = new DatagramPacket(sendData, data.length(),
							new InetSocketAddress(receivePacket.getAddress(), receivePacket.getPort()));
					socket.send(sendPacket);
				} else {
					// 5.데이터 송신
					byte[] sendData1 = message.getBytes(StandardCharsets.UTF_8);
					DatagramPacket sendPacket1 = new DatagramPacket(sendData1, message.length(),
							new InetSocketAddress(receivePacket.getAddress(), receivePacket.getPort()));

					socket.send(sendPacket1);
				}

			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null && socket.isClosed() == false) {
				socket.close();
			}
		}

	}

}
