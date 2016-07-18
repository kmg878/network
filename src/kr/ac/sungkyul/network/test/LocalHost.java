package kr.ac.sungkyul.network.test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LocalHost {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String hostname = inetAddress.getHostName();
			String hostAddress = inetAddress.getHostAddress();
			byte[] address = inetAddress.getAddress();
			
			
			System.out.println("Hostname "+ hostname);
			System.out.println("Host address "+hostAddress);
			for(int i=0;i<address.length;i++){
				System.out.print(address[i] & 0x000000ff);
				if(i<address.length-1){
					System.out.print(".");
				}
			}
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

	}

}
