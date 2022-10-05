package com.example.signupandlogin;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatAct3 extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    EditText insms;
    TextView user,status;
    Button send;
    String otheruser,otherUserName,otherUserStatus;
    DatabaseReference mUserref,smsref,otheruseref;
    FirebaseAuth fAuth;
    FirebaseUser mUser1;
    String mUser;
    String senderroom,recieverroom;
    private RecyclerView msglist;
    TextView t1,t2;
    ArrayList<chat>chats=new ArrayList<>();
    chatAdapter chatAdapter1= new chatAdapter(chats,this);

    chat model=new chat();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_act3);
        recyclerView = findViewById(R.id.recycler_gchat);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(chatAdapter1);
        insms=findViewById(R.id.edit);
        user=findViewById(R.id.username);


        send=findViewById(R.id.button_gchat_send);
        otheruser= getIntent().getStringExtra("userkey");
        // recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fAuth=FirebaseAuth.getInstance();
        mUser1=fAuth.getCurrentUser();
        mUser=mUser1.getUid();
        mUserref= FirebaseDatabase.getInstance().getReference().child("User");
        otheruseref= FirebaseDatabase.getInstance().getReference("caretaker");
        smsref= FirebaseDatabase.getInstance().getReference().child("cmmsg");
        senderroom= mUser+otheruser;
        recieverroom= otheruser+mUser;

        LoadOtherUser();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMS();
            }
        })
        ;
        loadmsg();




    }

    private void loadmsg() {
        smsref.child(senderroom).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chats.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren())
                {
                    chat model = snapshot1.getValue(chat.class);

                    chats.add(model);

                }
                chatAdapter1.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendSMS() {

        String sms=insms.getText().toString();
        int msgtyp = 1;

        chat model=new chat(sms,mUser);
        model.messageType=msgtyp;
        //model.setTS(new Date().getTime());
        Log.wtf("hell","hell");
        if(sms.isEmpty())
        {
            Toast.makeText(ChatAct3.this,"Please Write Something",Toast.LENGTH_SHORT).show();
        }
        else
        {

            smsref.child(senderroom).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    smsref.child(recieverroom).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Log.wtf("he;;p","jello");
                            Log.wtf("model", String.valueOf(model));
                            Toast.makeText(ChatAct3.this,"Message Sent",Toast.LENGTH_SHORT).show();
                            insms.setText(null);
                        }
                    });
                }


            });
        }
    }




    private void LoadOtherUser() {
        otheruseref.child(otheruser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    otherUserName=snapshot.child("name").getValue().toString();
                    user.setText(otherUserName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ChatAct3.this,""+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }
}