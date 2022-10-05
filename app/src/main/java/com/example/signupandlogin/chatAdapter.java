package com.example.signupandlogin;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;

public class chatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    ArrayList<chat> list;
    public static final int MESSAGE_TYPE_IN = 1;
    public static final int MESSAGE_TYPE_OUT = 2;

    public chatAdapter( ArrayList<chat> list,Context context) { // you can pass other parameters in constructor
        this.context = context;
        this.list = list;
    }

    private class MessageInViewHolder extends RecyclerView.ViewHolder {

        TextView messageTV,dateTV;
        MessageInViewHolder(final View itemView) {
            super(itemView);
            messageTV = itemView.findViewById(R.id.seconduserText);
            //  dateTV = itemView.findViewById(R.id.date_text);
        }
        void bind(int position) {
            chat messageModel = list.get(position);
            messageTV.setText(messageModel.getSms());
            //   dateTV.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(messageModel.getTimestamp()));
        }
    }
    private class MessageOutViewHolder extends RecyclerView.ViewHolder {

        TextView messageTV,dateTV;
        MessageOutViewHolder(final View itemView) {
            super(itemView);
            messageTV = itemView.findViewById(R.id.firstuserText);
            //dateTV = itemView.findViewById(R.id.date_text);
        }
        void bind(int position) {
            chat messageModel = list.get(position);

            messageTV.setText(messageModel.sms);
            // dateTV.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(messageModel.getTimestamp()));
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MESSAGE_TYPE_IN) {
            return new MessageInViewHolder(LayoutInflater.from(context).inflate(R.layout.singleview_sms, parent, false));
        }
        return new MessageOutViewHolder(LayoutInflater.from(context).inflate(R.layout.oppview_sms, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        chat messageModel = list.get(position);
        if(holder.getClass()==MessageInViewHolder.class) {
            ((MessageInViewHolder) holder).messageTV.setText(messageModel.sms);
        } else {
            ((MessageOutViewHolder) holder).messageTV.setText(messageModel.sms);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    @Override
    public int getItemViewType(int position) {

        if(list.get(position).getUserID().equals(FirebaseAuth.getInstance().getUid()))
        {
            return  MESSAGE_TYPE_IN ;
        }
        else
        {
            return MESSAGE_TYPE_OUT;
        }
    }

}

