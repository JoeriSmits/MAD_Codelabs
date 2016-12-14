package research.mad.han.robaben.nl.firebasecodelab;

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

import research.mad.han.robaben.nl.firebasecodelab.model.Message;

/**
 * Created by rob on 7-12-16.
 */

class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private List<Message> messages;
    private String currentUser;
    private Context context;

    public MessageAdapter(List<Message> messages, String currentUser, Context context){
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
        holder.username.setText("@" + message.getUsername());
        holder.message.setText(message.getMessage());

        if(currentUser.equals(message.getUsername())){
            holder.container.setBackgroundColor(context.getColor(R.color.messageBackgroundUser));
            holder.wrapper.setGravity(Gravity.END);
        }
        else {
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
            message = (TextView)itemView.findViewById(R.id.message);
            username = (TextView)itemView.findViewById(R.id.username);
            container = (LinearLayout)itemView.findViewById(R.id.messageContainer);
            wrapper = (LinearLayout)itemView.findViewById(R.id.messageWrapper);
        }
    }
}
