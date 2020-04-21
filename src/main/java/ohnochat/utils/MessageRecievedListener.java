package ohnochat.utils;

public interface MessageRecievedListener {
	/**
     * Invoked when a message is sent
     * @param msg The Message received 
     */
    public void onMessageRecieved(String msg);
}
