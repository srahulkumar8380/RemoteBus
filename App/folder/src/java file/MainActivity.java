package com.hackthon.srahulkumar.remotebus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mUsers;
    private EditText user,pass;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button login=findViewById(R.id.login_buttonId);
        Button signup=findViewById(R.id.login_createAccount);
  dialog=new ProgressDialog(this);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SignUpActivity.class));
            }
        });
        user=findViewById(R.id.signup_email);
        pass=findViewById(R.id.signup_password);
        mAuth=FirebaseAuth.getInstance();


        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mUsers=firebaseAuth.getCurrentUser();
                if (mUsers!=null){
                    Toast.makeText(MainActivity.this,"Welcome back",Toast.LENGTH_LONG).show();

                }
                else {
                    Toast.makeText(MainActivity.this,"Not Sign In",Toast.LENGTH_LONG).show();
                }
            }
        };
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setMessage("Logging...");
                dialog.show();
                 if (!TextUtils.isEmpty(user.getText()) && !TextUtils.isEmpty(pass.getText())){
                       String username=user.getText().toString();
                       String password=pass.getText().toString();
                       loginMethod(username,password);
                 }
                 else {
                     user.setError("Invalid Email");
                     pass.setError("Invalid Password");
                 }
            }
        });

    }

    private void loginMethod(String username, String password) {
        mAuth.signInWithEmailAndPassword(username,password)
               .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            startActivity(new Intent(MainActivity.this,HomeActivity.class));
                            dialog.dismiss();
                        }   else {
                            Toast.makeText(MainActivity.this,"Incorrect",Toast.LENGTH_SHORT).show();
                        }
                   }
               });

    }
}
