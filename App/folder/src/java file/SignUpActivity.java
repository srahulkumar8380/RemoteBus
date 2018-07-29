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
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.acl.Owner;

public class SignUpActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseRef;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;
    private String mUser;
    private EditText name,email,pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mDatabase=FirebaseDatabase.getInstance();
        mDatabaseRef=mDatabase.getReference().child("User");
        mAuth=FirebaseAuth.getInstance();

        Button signUp=findViewById(R.id.signup_id);
        name=findViewById(R.id.sign_username);
        email=findViewById(R.id.signup_email);
        pass=findViewById(R.id.signup_password);
        mDialog=new ProgressDialog(this);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewAccount();
            }


        });

    }

    private void createNewAccount() {
       final String mName=name.getText().toString();
       final String mEmail=email.getText().toString();
       final String mPass=pass.getText().toString();

       if (!TextUtils.isEmpty(mName)&& !TextUtils.isEmpty(mEmail) && !TextUtils.isEmpty(mPass)){
            mDialog.setMessage("Creating Account");
            mDialog.show();
            mAuth.createUserWithEmailAndPassword(mEmail,mPass)
                    .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                              if (authResult!=null){
                                  String userId=mAuth.getCurrentUser().getUid();
                                  DatabaseReference currentDb=mDatabaseRef.child(userId).child("Account");
                                  currentDb.child("Name").setValue(mName);
                                  currentDb.child("Email").setValue(mEmail);
                                  currentDb.child("Password").setValue(mPass);
                                  mDialog.dismiss();

                                  //send User to Login  Page
                                  Intent intent=new Intent(SignUpActivity.this, HomeActivity.class);
                                  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                  startActivity(intent);
                              }
                        }
                    });
       }
       else {
           Toast.makeText(SignUpActivity.this,"Password Not Match",Toast.LENGTH_SHORT).show();
       }

    }
}
