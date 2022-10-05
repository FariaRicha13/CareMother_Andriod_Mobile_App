package com.example.signupandlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import static android.widget.Toast.makeText;

public class doctorlogin extends AppCompatActivity {
    GoogleSignInClient mGoogleSignInClient;
    String username;
    private static int RC_SIGN_IN=100;
    private Button buttonsignin;
    private FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference ref;
    EditText memail,mpassword;
    FirebaseAuth fAuth,mAuth;
    ProgressDialog mload;
    private TextView signuplink;
    //String personName, personGivenName,personFamilyName,personEmail

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctorlogin);
        buttonsignin=findViewById(R.id.buttonlogin);
        memail=findViewById(R.id.email);
        mpassword=findViewById(R.id.password);

        signuplink=findViewById(R.id.signup);
        fAuth=FirebaseAuth.getInstance();










        buttonsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  email=memail.getText().toString().trim();
                String password=mpassword.getText().toString().trim();
                if(TextUtils.isEmpty(email))
                {  memail.setError("Email is Required");

                }

                if(TextUtils.isEmpty(password))
                {  mpassword.setError("Password is Required");
                    // memail.requestFocus();
                    ///   return;
                }
                if(password.length()<6)
                {  mpassword.setError("Password must be 6 or more character long");

                }


                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {



                            Toast.makeText(doctorlogin.this, "Logged in Successfully!!", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(getApplicationContext(),doctor.class));

                        }



                        else{
                            Toast.makeText(doctorlogin.this, "Error Logging In"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                //signIn();
            }
        });
        signuplink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openSignup();
            }
        });


    }





    public void openSignup(){
        Intent intent = new Intent(this, doctor_login.class);
        startActivity(intent);
    }


}

