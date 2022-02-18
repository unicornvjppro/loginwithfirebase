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

public class register_class extends AppCompatActivity {
    private TextView dk;
    private EditText mhoten,mtuoi,memail,mpassword;
    private ProgressBar pgbar;
    private FirebaseAuth fAuth;
    private Button btr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        mhoten=findViewById(R.id.edt1);
        mtuoi=findViewById(R.id.edt2);
        memail=findViewById(R.id.edt3);
        mpassword=findViewById(R.id.edt4);

        btr=findViewById(R.id.btn1);
        fAuth=FirebaseAuth.getInstance();
        pgbar=findViewById(R.id.pgb1);

        if(fAuth.getCurrentUser() !=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }


        btr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email= memail.getText().toString().trim();
                String password=mpassword.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    memail.setError("Vui lòng điền Email");
                    Log.d("loi","loi");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mpassword.setError("Vui lòng điền mật khẩu");
                    Log.d("loi","loi");
                    return;
                }
                if(password.length()<6){
                    mpassword.setError("Mật khẩu phải có 6 kí tự");
                    Log.d("loi","loi");
                    return;
                }
                pgbar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(register_class.this,"User create",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(register_class.this,"error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });
    }

}
