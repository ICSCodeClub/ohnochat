package ohnochat;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import ohnochat.utils.EchoServer;
@Test
public class Test {
	public static void main(String[] args) throws UnknownHostException, IOException {
		EchoServer es = new EchoServer(6969); //ha ha it's funny b/c 69
		Socket client = new Socket("127.0.0.1",6969);
	}
}
