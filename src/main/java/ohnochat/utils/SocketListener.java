package ohnochat.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class SocketListener {
	private Socket sock;
	
	private InputStreamSubListener issl;
	
	private ArrayList<MessageRecievedListener> recievedListeners;
	private ArrayList<MessageSentListener> sentListeners;
	
	
	public SocketListener(Socket s) {
		recievedListeners = new ArrayList<MessageRecievedListener>();
		sentListeners = new ArrayList<MessageSentListener>();
		
		sock = s;
		
		try {
			issl = new InputStreamSubListener(s.getInputStream());
			(new Thread(issl)).start();
		} catch (IOException e) {
			throw new IllegalArgumentException("Socket is invalid");
		}
	}
	
	public void send(Object obj) {
		try {
			PrintWriter pout = new PrintWriter(sock.getOutputStream(),true);
			onMessageSent(obj.toString());
			pout.println(obj.toString());
		} catch (IOException e) {}
	}
	
	public void close() {
		issl.active = false;
	}
	
	
	public void addMessageRecievedListener(MessageRecievedListener ml) {
		recievedListeners.add(ml);
	}
	public void addMessageSentListener(MessageSentListener ml) {
		sentListeners.add(ml);
	}
	
	
	public void onMessageRecieved(String msg) {
		for(MessageRecievedListener ml : recievedListeners)
			ml.onMessageRecieved(msg);
	}
	public void onMessageSent(String msg) {
		for(MessageSentListener ml : sentListeners)
			ml.onMessageSent(msg);
	}
	
	private class InputStreamSubListener implements Runnable{
		public boolean active = true;
		private BufferedReader br;
		public InputStreamSubListener(InputStream is) {
			br =  new BufferedReader(new InputStreamReader(is));
		}
		@Override
		public void run() {
			try {Thread.sleep(220);} catch(Exception e) {}
			while(active) {
				try {
					String line = br.readLine();
					onMessageRecieved(line);
				} catch (IOException e) { 
					e.printStackTrace();
				}
			}
		}
	}
}
