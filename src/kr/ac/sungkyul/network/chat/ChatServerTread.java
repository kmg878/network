package kr.ac.sungkyul.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

public class ChatServerTread extends Thread {
	
	private String nickname;
	private Socket socket;
	List<Writer> listWriters;
	BufferedReader bufferedReader;
		PrintWriter printWriter ;
	
	
	public ChatServerTread(Socket socket, List<Writer> listWriters){
		this.socket =socket;
		this.listWriters =listWriters;
	}

	public void run() {

		try {
			// IOStream 받아오기
			bufferedReader = new BufferedReader( 
					new InputStreamReader( socket.getInputStream(), "utf-8"));
			 printWriter = new PrintWriter(
					new OutputStreamWriter( socket.getOutputStream(), "utf-8" ), true );
			
			while (true) {
				String request = bufferedReader.readLine();
				if(request == null){
					System.out.println("클라이언트로 부터 연결 끊김");
					break;
				}
				String[] tokens = request.split(":");
				if("join".equals(tokens[0])){
					doJoin(tokens[1],printWriter);
				}else if("message".equals(tokens[0])){
					doMessage(tokens[1]);
				}else if("quit".equals(tokens[0])){
					doQuit(printWriter);break;
				}else{
					System.out.println("알 수 없는 요청");
				}
			
			
			
			
			
			}
		} catch (SocketException e) {
			consoleLog( "비정상적으로 클라이언트가 연결을 끊었습니다." + e );
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 데이터 통신 소켓 닫기
			try {
				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}
			} catch( IOException e ) {
				e.printStackTrace();
			}
		}
	}
	
	private void doQuit(Writer writer) {
		removeWriter(writer);
		String data = nickname +"님이 퇴장하였습니다" ;
		broadcast(data);
		System.out.println(nickname+"님이 퇴장하였습니다");
		
		
	}
	private void removeWriter(Writer writer){
		System.out.println("클라이언트로 부터 연결 끊김");
		
	}

	private void doMessage(String message) {
		broadcast(nickname+":"+message+"\r\n");
		System.out.println(nickname+":"+message);
		
	}

	private void consoleLog( String message ) {
		System.out.println( "[echo server thread#" + getId() + "] " + message );
	}
	private void doJoin(String nickName,Writer writer){
		this.nickname =nickName;
		String data2 = nickName +"님이 참여하였습니다";
		
		broadcast(data2);
		System.out.println(data2);
		//writer pool 에 저장
		addWriter(writer);
		//ack
		printWriter.println("join : ok");
		printWriter.flush();
	}
	
	
	
	
	private void addWriter(Writer writer){
		synchronized(listWriters){
			listWriters.add(writer);
		}
	}
	private void broadcast(String data){
		synchronized(listWriters){
			for(Writer writer : listWriters){
				PrintWriter printWriter =(PrintWriter)writer;
				printWriter.println(data);
				printWriter.flush();
			}
		}
	}

}