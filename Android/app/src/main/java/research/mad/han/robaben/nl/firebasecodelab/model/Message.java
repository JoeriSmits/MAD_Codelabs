package research.mad.han.robaben.nl.firebasecodelab.model;

/**
 * Created by rob on 7-12-16.
 */

public class Message {
    public Message(String timestamp, String username, String message){
        this.timestamp = timestamp;
        this.username = username;
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String timestamp;
    private String username;
    private String message;
}
