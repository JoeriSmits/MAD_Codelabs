package nl.han.ica.mad.android.firebasedemo;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import nl.han.ica.mad.android.firebasedemo.model.Message;

/**
 * Provides a RecyclerView Adapter to display messages fetched from the FireBase reference.
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<Message> messages;
    private String currentUser;
    private Context context;

    /**
     * Constructor to instantiate a new MessageAdapter.
     *
     * @param messages The list of messages fetched by the {@link com.google.firebase.database.DatabaseReference FireBase database reference}
     * @param currentUser The current logged in user
     * @param context The context of this MessageAdapter (i.e. {@link nl.han.ica.mad.android.firebasedemo.MainActivity})
     */
    MessageAdapter(List<Message> messages, String currentUser, Context context) {
        this.context = context;
        this.messages = messages;
        this.currentUser = currentUser;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View messageView = inflater.inflate(R.layout.single_message, parent, false);
        return new ViewHolder(messageView);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.username.setText(String.format(context.getResources().getString(R.string.sender_name), message.username));
        holder.message.setText(message.message);

        if (currentUser.equals(message.username)) {
            holder.container.setBackgroundColor(context.getColor(R.color.messageBackgroundUser));
            holder.wrapper.setGravity(Gravity.END);
        } else {
            holder.container.setBackgroundColor(context.getColor(R.color.messageBackground));
            holder.wrapper.setGravity(Gravity.START);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView message;
        TextView username;
        LinearLayout container;
        LinearLayout wrapper;

        ViewHolder(View itemView) {
            super(itemView);
            message = (TextView) itemView.findViewById(R.id.message);
            username = (TextView) itemView.findViewById(R.id.username);
            container = (LinearLayout) itemView.findViewById(R.id.messageContainer);
            wrapper = (LinearLayout) itemView.findViewById(R.id.messageWrapper);
        }
    }
}
