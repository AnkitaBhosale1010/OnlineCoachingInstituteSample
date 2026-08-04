package com.example.onlinecoachingapp.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.onlinecoachingapp.session.SessionManager;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.model.Message;
import java.util.List;

public class MessageAdapter
        extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private Context context;
    private List<Message> messageList;

    public MessageAdapter(Context context,
                          List<Message> messageList) {

        this.context = context;
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_chat_message,
                        parent,
                        false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        Message message = messageList.get(position);

        holder.txtSender.setText(
                message.getSender().getName());

        holder.txtMessage.setText(
                message.getMessage());

        holder.txtTime.setText(
                message.getSentAt());

        SessionManager session =
                new SessionManager(context);

        Long myId = session.getUserId();

        if (message.getSender().getUserId().equals(myId)) {

            holder.chatBubble.setBackgroundResource(
                    R.drawable.bg_message_right);

            ((LinearLayout) holder.chatBubble.getParent())
                    .setGravity(Gravity.END);

        } else {

            holder.chatBubble.setBackgroundResource(
                    R.drawable.bg_message_left);

            ((LinearLayout) holder.chatBubble.getParent())
                    .setGravity(Gravity.START);

        }

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    static class ViewHolder
            extends RecyclerView.ViewHolder {

        TextView txtSender;
        TextView txtMessage;
        TextView txtTime;
        LinearLayout chatBubble;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtSender = itemView.findViewById(R.id.txtSender);
            txtMessage = itemView.findViewById(R.id.txtMessage);
            txtTime = itemView.findViewById(R.id.txtTime);
            chatBubble = itemView.findViewById(R.id.chatBubble);
        }
    }
}