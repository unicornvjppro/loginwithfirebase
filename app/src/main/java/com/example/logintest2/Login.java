package com.example.logintest2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText mEmail,mPassword;
    TextView mLabel,msignup;
    Button msignin;
    private ProgressBar pgbar;

    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mEmail=findViewById(R.id.txtmail);
        mPassword=findViewById(R.id.txtpw);
        msignup=findViewById(R.id.txtpro);
        msignin=findViewById(R.id.button);
        mLabel=findViewById(R.id.textView);
        pgbar=findViewById(R.id.pgb1);

        msignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email= mEmail.getText().toString().trim();
                String password=mPassword.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Vui lòng điền Email");
                    Log.d("loi","loi");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Vui lòng điền mật khẩu");
                    Log.d("loi","loi");
                    return;
                }
                if(password.length()<6){
                    mPassword.setError("Mật khẩu phải có 6 kí tự");
                    Log.d("loi","loi");
                    return;
                }

                pgbar.setVisibility(View.VISIBLE);
                fAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this,"Login thành công",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                        }else{
                            Toast.makeText(Login.this,"Lỗi"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        msignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),register_class.class));
                pgbar.setVisibility(View.INVISIBLE);
            }
        });

    }
}
