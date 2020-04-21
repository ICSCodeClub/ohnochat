package ohnochat;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import ohnochat.utils.EchoServer;
import ohnochat.utils.MessageRecievedListener;
import ohnochat.utils.SocketListener;

//hooolllyy shiiit OhnoChat was soooo baaaad i need to rewrite it
public class OhnoChatTheRewrite implements Closeable{
	private JFrame frmOhnochat;
	private JTextField typedMessage;
	private JTextPane messages;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OhnoChatTheRewrite window = new OhnoChatTheRewrite();
					window.frmOhnochat.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws UnknownHostException 
	 */
	public OhnoChatTheRewrite() throws UnknownHostException {
		boolean isServer = JOptionPane.showConfirmDialog(null,"Do you want to host the chat?\nYes - Act as server\nNo - Act as client","Want to host a chat?",JOptionPane.YES_NO_OPTION) == 0;
		String ip = InetAddress.getLocalHost().toString();
		if(!isServer) {
			ip = JOptionPane.showInputDialog("Please enter the ip address");
			//check ip
			try {InetAddress.getByName(ip);}
			catch(UnknownHostException e) {
				JOptionPane.showMessageDialog(null,"Invalid or Unreachable IP");
				return;
			}
		}
		int port = 6969; //haha, it never gets old
		try {port = Integer.parseInt(JOptionPane.showInputDialog("Please enter the port number"));}
		catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(null,"Enter a real port next time, retard");
			return;
		}
		
		initialize(isServer ? "127.0.0.1" : ip, port, isServer);
	}
	
	private EchoServer server;
	private SocketListener sl;
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String ip, int port, boolean isServer) {
		//create new echo server
		if(isServer) server = new EchoServer(port);
		
		sl = SocketListener.fromNewSocket(ip, port);
		if(sl == null) {JOptionPane.showMessageDialog(null,"Hey man, how about giving me a *valid* ip+port? Thx"); return;}
		
		//add the incoming message listener
		sl.addMessageRecievedListener(new MessageRecievedListener() {
			public void onMessageRecieved(String msg) {
				if(msg != null && !msg.equalsIgnoreCase("null"))
				appendMessage(msg);
			}
		});
		
		//initialize the GUI
		frmOhnochat = new JFrame();
		frmOhnochat.setTitle("OhnoChat");
		frmOhnochat.setBounds(100, 100, 450, 570);
		frmOhnochat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmOhnochat.getContentPane().setLayout(new BorderLayout(0, 0));
		frmOhnochat.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.out.println("Closing");
				close();
			}
		});
		
		
		JPanel sendMessagePanel = new JPanel();
		frmOhnochat.getContentPane().add(sendMessagePanel, BorderLayout.NORTH);
		
		typedMessage = new JTextField();
		typedMessage.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar() == '\n') sendMessage();
			}
		});
		sendMessagePanel.add(typedMessage);
		typedMessage.setColumns(30);
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMessage();
			}
		});
		sendMessagePanel.add(btnSend);
		
		JScrollPane bodyPane = new JScrollPane();
		frmOhnochat.getContentPane().add(bodyPane, BorderLayout.CENTER);
		
		messages = new JTextPane();
		messages.setEditable(false);
		bodyPane.setViewportView(messages);
	}
	
	/**
	 * Sends the current contents of the message box
	 */
	private void sendMessage() {
		String msg = typedMessage.getText();
		if(msg.isBlank()) return;
		
		if(sl != null) sl.send(msg);
		typedMessage.setText("");
		
		//now add our message to the message log
		appendMessage("Me: "+msg);
	}
	
	/**
	 * Easily appends text
	 * @param msg The Message
	 */
	private void appendMessage(String msg) {
		messages.setText(msg+"\n"+messages.getText());
	}
	/**
	 * Disposes of all client sockets and stuff. DOES NOT CLOSE WINDOW
	 */
	public void close() {
		if(server != null) server.close();
		if(sl != null) sl.close();
	}
}
