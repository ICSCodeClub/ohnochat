package ohnochat;

import java.util.concurrent.TimeUnit;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import ohnochat.EchoServer;
import ohnochat.utils.MessageRecievedListener;
import ohnochat.utils.MessageSentListener;
import ohnochat.utils.SocketListener;

public class Test {
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		EchoServer es = new EchoServer(6969); //ha ha it's funny b/c 69
		Socket client = new Socket("127.0.0.1",6969);
		
		SocketListener sl = new SocketListener(client);
		
		//add an easy listener for new recieved messages
		sl.addMessageRecievedListener(new MessageRecievedListener() {
			public void onMessageRecieved(String msg) {
				System.out.println("Recieved Message: "+msg);
			}
		});
		
		//you can even listen for sent messages
		sl.addMessageSentListener(new MessageSentListener() {
			public void onMessageSent(String msg) {
				System.out.println("Sent Message: "+msg);
			}
		});
		
		//sleep
		TimeUnit.NANOSECONDS.sleep(1500);
		
		//send an example message. NOTE THAT you do not recieve messages you send with the EchoServer
		sl.send("Who is joe");
	}
}
