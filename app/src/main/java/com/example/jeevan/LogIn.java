package com.example.jeevan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LogIn extends AppCompatActivity {
    //Creating Object of Edittext and Button Fields...
    EditText loginEmail,loginPassword;
    Button loginButton;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        if(FirebaseAuth.getInstance().getCurrentUser() != null)
        {
            //later changed to HomeActivity.class now testing****I**with Home_copy_Activity.class
            startActivity(new Intent(this,Home_screen_Activity.class));
            this.finish();
        }
        //Casting objects into xml
        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginButton);



        //getting state of firebase
        firebaseAuth = FirebaseAuth.getInstance();
        //Applying onClickListener to loginButton..
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginEmail.getText().toString().trim();
                String Password = loginPassword.getText().toString().trim();
                //Checking if fields are empty then show some Toast message..
                if(email.isEmpty()){
                    Toast.makeText(LogIn.this,"Please Enter email",Toast.LENGTH_SHORT).show();
                    //loginEmail.setError("Please Enter Email");
                    //loginEmail.requestFocus();
                }
                else if(Password.isEmpty()){
                    Toast.makeText(LogIn.this,"Enter Password",Toast.LENGTH_SHORT).show();
                   // loginPassword.setText("Enter Password");
                  // loginPassword.requestFocus();
                }
                //Resolved... login error...???
                if((!email.isEmpty() && !Password.isEmpty())){


                    firebaseAuth.signInWithEmailAndPassword(email, Password)
                            .addOnCompleteListener(LogIn.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(getApplicationContext(), Home.class));
                                        Toast.makeText(LogIn.this, "Welcome", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(LogIn.this, "Login Failed", Toast.LENGTH_SHORT).show();

                                    }

                                }
                            });

                }
            }
        });
    }







    public void text_signupForm(View view) {
        startActivity(new Intent(getApplicationContext(),SignUp.class));
    }
    //when create forgot password page otp the remain to link...****
    public void textForgotPassword(View view) {
        startActivity(new Intent(getApplicationContext(),SignUp.class));
    }
}
