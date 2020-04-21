package ohnochat.utils;

public interface MessageSentListener {
	/**
     * Invoked when a message is sent
     * @param msg The Message received 
     */
    public void onMessageSent(String msg);
}
