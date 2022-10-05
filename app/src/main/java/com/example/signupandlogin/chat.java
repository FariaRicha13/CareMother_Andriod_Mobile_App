package com.example.signupandlogin;

import com.google.firebase.auth.FirebaseUser;

public class chat {
   int messageType;
     String sms,UserID;
    long timestamp;

   public chat()
   {

   }

    public chat(String sms, String userID) {
        this.sms = sms;
        UserID = userID;
        //this.messageType=messageType;
    }




    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public String getUserID() {
        return UserID;
    }

    public long getTS() {
        return timestamp;
    }

    public void setTS(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }
}
