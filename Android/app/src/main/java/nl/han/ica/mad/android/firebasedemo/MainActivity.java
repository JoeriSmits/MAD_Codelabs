package nl.han.ica.mad.android.firebasedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import nl.han.ica.mad.android.firebasedemo.model.Message;
import nl.han.ica.mad.android.firebasedemo.util.Utilities;

public class MainActivity extends AppCompatActivity {

    private String username = "Harry";

    private DatabaseReference messages;
    private MessageAdapter adapter;
    private RecyclerView recyclerView;
    private List<Message> messagesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        messages = database.getReference("messages");

        recyclerView = (RecyclerView) findViewById(R.id.messagesRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        final Button sendButton = (Button) findViewById(R.id.sendMessageButton);
        final EditText messageEditText = (EditText) findViewById(R.id.messageTextField);

        adapter = new MessageAdapter(messagesList, username, getApplicationContext());
        recyclerView.setAdapter(adapter);
        watchNewMessages();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(messageEditText.getText().toString());
                messageEditText.setText("");
            }
        });
    }

    @Override
    protected void onDestroy() {
        recyclerView = null;
        messages.removeEventListener(listener);
        adapter = null;
        recyclerView = null;
    }

    public void sendMessage(String message) {
        Message messageToSend = new Message(Utilities.getCurrentTimeString(), username, message);
        DatabaseReference uniqueRef = messages.push();
        uniqueRef.setValue(messageToSend);
    }

    public void watchNewMessages() {
        messages.addValueEventListener(listener);
    }

    private ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            messagesList.clear();
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                String timestamp = (String) snapshot.child("timestamp").getValue();
                String name = (String) snapshot.child("username").getValue();
                String message = (String) snapshot.child("message").getValue();
                Message receivedMessage = new Message(timestamp, name, message);
                messagesList.add(receivedMessage);
            }
            adapter.notifyDataSetChanged();
            recyclerView.scrollToPosition(adapter.getItemCount() + 1);
        }

        @Override
        public void onCancelled(DatabaseError error) {
            Log.w(this.getClass().getName(), "Failed to read value.", error.toException());
        }
    };
}
