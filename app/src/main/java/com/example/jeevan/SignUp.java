package com.example.jeevan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class SignUp extends AppCompatActivity {
    //Creating Objects of Edittext ,Button , TextView..
    EditText signupFullName,signupEmail,signupPassword,signupConfirmPassword,signupPhoneNumber;
    Button signupButton;
    TextView signupTextalreadyAccount;
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //Creating objects and casting them to xml..
        signupFullName = findViewById(R.id.signupFullName);
        signupEmail = findViewById(R.id.signupEmail);
        signupPassword = findViewById(R.id.signupPassword);
        signupConfirmPassword = findViewById(R.id.signupConfirmPassword);
        signupPhoneNumber = findViewById(R.id.signupPhoneNumber);
        signupButton = findViewById(R.id.signupButton);
        signupTextalreadyAccount = findViewById(R.id.signupTextalreadyAccount);
        //getting instance of firebase..
        firebaseAuth = FirebaseAuth.getInstance();
        //getting instance of database...
        databaseReference = FirebaseDatabase.getInstance().getReference("Jeevan");


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fullName = signupFullName.getText().toString().trim();
                final String email = signupEmail.getText().toString().trim();
                String password =  signupPassword.getText().toString().trim();
                String confirmPassword = signupConfirmPassword.getText().toString().trim();
                final String phoneNumber = signupPhoneNumber.getText().toString().trim();

                if(TextUtils.isEmpty(fullName)){
                    Toast.makeText(SignUp.this,"Please Enter Name",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(SignUp.this,"Please Enter Email",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(SignUp.this,"Please Enter Password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.length()<8){
                    Toast.makeText(SignUp.this,"Password too short",Toast.LENGTH_SHORT).show();
                }

                if(TextUtils.isEmpty(confirmPassword)){
                    Toast.makeText(SignUp.this,"Password doesn't match",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(phoneNumber)){
                    Toast.makeText(SignUp.this,"Enter Contact details",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.equals(confirmPassword)){
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        //startActivity(new Intent(getApplicationContext(),LogIn.class));
                                       // Toast.makeText(SignUp.this,"Signup Successfully",Toast.LENGTH_SHORT).show();
                                        //Database information storing..
                                        AddingDatabaseHelper information = new AddingDatabaseHelper(
                                              fullName,
                                              email,
                                                phoneNumber
                                        );
                                        FirebaseDatabase.getInstance().getReference("Jeevan")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(SignUp.this,"Registration completed",Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getApplicationContext(),LogIn.class));
                                            }
                                        });


                                    } else {
                                       Toast.makeText(SignUp.this,"Authentication failed",Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });



                }



            }
        });




    }

    public void text_already_have_account_Login(View view) {
        startActivity(new Intent(getApplicationContext(),LogIn.class));
    }
}
