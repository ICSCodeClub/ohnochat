

public class EchoServer extends Server {

	public EchoServer(int port) {
		super(port);
	}
	
	public void onMessageRecieved(Client origin, String msg) {
		for(Client c : super.getClients())
			if(!c.equals(origin))
				c.getPrintStream().println(msg);
	}

}
