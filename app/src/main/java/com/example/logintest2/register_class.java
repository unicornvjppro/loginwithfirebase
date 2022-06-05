package com.example.logintest2;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.niwattep.materialslidedatepicker.SlideDatePickerDialog;
import com.niwattep.materialslidedatepicker.SlideDatePickerDialogCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class register_class extends AppCompatActivity implements SlideDatePickerDialogCallback {
    private TextView dk;
    private EditText mhoten,mtuoi,memail,mpassword,repass;
    private ProgressBar pgbar;
    private FirebaseAuth fAuth;
    private Button btr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        mhoten=findViewById(R.id.name);
//        mtuoi=findViewById(R.id.date);
        memail=findViewById(R.id.email);
        mpassword=findViewById(R.id.password);
        repass=findViewById(R.id.repass);

        btr=findViewById(R.id.btn1);
        fAuth=FirebaseAuth.getInstance();
        pgbar=findViewById(R.id.pgb1);

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
//        mtuoi.setText(currentDate);

//        mtuoi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Calendar endDate = Calendar.getInstance();
//                endDate.set(Calendar.YEAR, 2100);
//                SlideDatePickerDialog.Builder builder = new SlideDatePickerDialog.Builder();
//                builder.setEndDate(endDate);
//                SlideDatePickerDialog dialog = builder.build();
//                dialog.show(getSupportFragmentManager(), "Dialog");
//            }
//        });

        btr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email= memail.getText().toString().trim();
                String password=mpassword.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    memail.setError("Vui lòng điền Email");
                    Log.d("loi","loi");
                }
                else if(TextUtils.isEmpty(password)){
                    mpassword.setError("Vui lòng điền mật khẩu");
                    Log.d("loi","loi");
                }
                else if(password.length()<6){
                    mpassword.setError("Mật khẩu phải có 6 kí tự");
                    Log.d("loi","loi");
                }
                else if(!repass.getText().toString().equals(password)){
                    repass.setError("Cần nhập lại đúng mật khẩu");
                    Log.d("loi","loi");
                }
                else{

                    pgbar.setVisibility(View.VISIBLE);


                    fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(register_class.this,"User create",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),Login.class);
                                intent.putExtra("username", memail.getText().toString());
                                intent.putExtra("pass", mpassword.getText().toString());
                                startActivity(intent);
                                pgbar.setVisibility(View.INVISIBLE);
                            }else{
                                pgbar.setVisibility(View.INVISIBLE);
                                Toast.makeText(register_class.this,"error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                }

            }
        });
    }


    @Override
    public void onPositiveClick(int i, int i1, int i2, Calendar calendar) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", new Locale("vi", "VN"));
        mtuoi.setText(format.format(calendar.getTime()));
    }
}
