package com.example.signupandlogin;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Mholder extends RecyclerView.ViewHolder {
    View view;
    public Mholder(@NonNull View itemView) {
        super(itemView);

        view = itemView;
    }

    public void setView(Context context, String Phone, String DOB, String Height, String Weight)
    {
        TextView mphn = view.findViewById(R.id.phn_no);
        TextView mdob = view.findViewById(R.id.dob);
        TextView mheight = view.findViewById(R.id.height);
        TextView mweight = view.findViewById(R.id.weight);
       // TextView mweight = view.findViewById(R.id.weight);

        mphn.setText(Phone);
        mdob.setText(DOB);
        mheight.setText(Height);
        mweight.setText(Weight);


    }
}
