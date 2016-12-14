package nl.han.ica.mad.android.firebasedemo.model;

public class Message {

    public String timestamp;
    public String username;
    public String message;

    public Message(String timestamp, String username, String message) {
        this.timestamp = timestamp;
        this.username = username;
        this.message = message;
    }
}
