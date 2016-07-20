package kr.ac.sungkyul.network.chat;

import java.io.BufferedReader;
import java.io.IOException;

public class ChatClientReceiveThread extends Thread {
	private BufferedReader bufferedReader;
	
	ChatClientReceiveThread(BufferedReader bufferedReader){
		this.bufferedReader =bufferedReader;
	}

	@Override
	public void run() {
		while(true){
		String message;
		try {
			message = bufferedReader.readLine();
			System.out.println(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}
		// 받은 메세지 출력
		
		}
	}
}
