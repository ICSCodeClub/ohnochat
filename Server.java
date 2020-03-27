

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public abstract class Server extends Thread{	
	private ArrayList<Client> clients;
	private ArrayList<String> messages;
	private final int port;
	
	
	public Server (int port) {
		this.port = port;
		
		clients = new ArrayList<Client>();
		messages = new ArrayList<String>();
		
		this.start();
	}
	
	boolean active = true;
	public void run() {
		active = true;
		ServerSocket newCon = null;
		try {
			newCon = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
			kill();
		}
		while(active) {
			try {
				//newCon.accept();
				Client c = new Client(this, newCon, newCon.accept());
				clients.add(c);
				c.start();
			}
			catch(Exception e) {
			}
		}
	}
	public void kill() {
		active = false;
		for(Client c : clients)
			c.kill();
	}
	
	public void addToMessages(Client origin, String msg) {
		messages.add(msg);
		onMessageRecieved(origin, msg);
		System.out.println(msg);
	}
	public List<Client> getClients(){
		return clients;
	}
	public List<String> getAllMessages(){
		return messages;
	}
	public abstract void onMessageRecieved(Client origin, String msg);
}
class Client extends Thread {
	public Server parent;
	private ServerSocket server;
	private Socket soc;
	
	private BufferedReader br;
	private PrintStream ps;
	
	public Client(Server dad, ServerSocket ss, Socket sssss) throws IOException {
		parent = dad;
		server = ss;
		soc = sssss;
		
		br = new BufferedReader(new InputStreamReader(soc.getInputStream()));
		ps = new PrintStream(soc.getOutputStream());
	}
	
	boolean active = true;
	public void run() {
		while(active) {
			try {
				String line = br.readLine();
				parent.addToMessages(this, line);
			} catch (IOException e) {
				kill();
			}
			
		}
	}
	public PrintStream getPrintStream() {
		return ps;
	}
	public void kill() {
		active = false;
		try {
			server.close();
			soc.close();
		} catch (IOException e) {}
	}
	
}
