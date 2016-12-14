package research.mad.han.robaben.nl.firebasecodelab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import research.mad.han.robaben.nl.firebasecodelab.model.Message;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";
    String username = "Harry";
    DatabaseReference messages;

    MessageAdapter adapter;
    RecyclerView recyclerView;
    List<Message> messagesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        initUI();
    }

    private void initUI(){
        recyclerView = (RecyclerView) findViewById(R.id.messagesRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        final Button sendButton = (Button) findViewById(R.id.sendMessageButton);
        final EditText messageEditText = (EditText) findViewById(R.id.messageTextField);

        // provide empty list to adapter
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendMessage(String message){
        Message messageToSend = new Message(getCurrentTimeString(), username, message);
        //Create new reference calling push creates the unique key in firebase database but has no data yet
        DatabaseReference uniqueRef = messages.push();
        //Use the new reference to add the data
        uniqueRef.setValue(messageToSend);
    }

    public void watchNewMessages(){
        messages.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                messagesList.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    String timestamp = (String) snapshot.child("timestamp").getValue();
                    String name = (String) snapshot.child("username").getValue();
                    String message = (String) snapshot.child("message").getValue();
                    Message receivedMessage = new Message(timestamp,name,message);
                    messagesList.add(receivedMessage);
                }
                adapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(adapter.getItemCount()+1);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private String getCurrentTimeString(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(new Date());
    }
}
