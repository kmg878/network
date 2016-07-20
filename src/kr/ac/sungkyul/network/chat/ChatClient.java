package kr.ac.sungkyul.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class ChatClient {
	private static final String SERVER_IP = "220.67.115.227";
	private static final int SERVER_PORT = 3000;

	public static void main(String[] args) {
		Socket socket = null;
		Scanner scanner = null;
		BufferedReader bufferedReader;
		PrintWriter printWriter;

		try {

			// 키보드 연결
			scanner = new Scanner(System.in);

			// 소켓 생성
			socket = new Socket();

			// 서버연결
			InetSocketAddress serverSocketAddress = new InetSocketAddress(SERVER_IP, SERVER_PORT);
			socket.connect(serverSocketAddress);

			// IOStream 받아오기
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
			printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true);

			
				// 5. join 프로토콜
				System.out.print("닉네임>>");
				String nickname = scanner.nextLine();
				printWriter.println("join:" + nickname);
				printWriter.flush();

				// 6. ChatClientReceiveThread 시작
				ChatClientReceiveThread thread = new ChatClientReceiveThread(bufferedReader);
				thread.start();

				// 7. 키보드 입력 처리
				while( true ) {
				      System.out.print( ">>" );
				      String input = scanner.nextLine();
								
				      if( "quit".equals( input ) == true ) {
				          // 8. quit 프로토콜 처리
				          break;
				      } else {
				          // 9. 메시지 처리 
				    	  System.out.println(nickname+":"+input);
				    	  printWriter.println("message:"+input );
				    	  printWriter.flush();
				      }
				   }

			
		} catch (SocketException e) {
			System.out.println("[client] 비정상적으로 서버로 부터 연결이 끊어졌습니다." + e);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 소켓 닫기
				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}

				// 키보드 닫기
				if (scanner != null) {
					scanner.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
