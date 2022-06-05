package com.example.logintest2;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    TextView mname,mwelc;
    ImageView mimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
//        Toast.makeText(this, "" + currentFirebaseUser.getUid(), Toast.LENGTH_SHORT).show();
        gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc= GoogleSignIn.getClient(this,gso);

        for (UserInfo userInfo: Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getProviderData()){
            Log.d("TAG", "onCreate: "+userInfo.getEmail());
        }
       mname=findViewById(R.id.name);
       mwelc=findViewById(R.id.welc);
       mimg=findViewById(R.id.img2);
//
//        AccessToken accessToken = AccessToken.getCurrentAccessToken();
//        GraphRequest request = GraphRequest.newMeRequest(
//                accessToken,
//                new GraphRequest.GraphJSONObjectCallback() {
//                    @Override
//                    public void onCompleted(
//                            JSONObject object,
//                            GraphResponse response) {
//                        try {
//                            String fullName = object.getString("name");
//                            String url= object.getJSONObject("picture").getJSONObject("data").getString("url");
//                            Picasso.get().load(url).into(mimg);
//                            mname.setText(fullName);
//                        }catch (JSONException e){
//                            e.printStackTrace();
//                        }
//                    }
//                });
//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "id,name,link,picture.type(large)");
//        request.setParameters(parameters);
//        request.executeAsync();
        GoogleSignInAccount acc =GoogleSignIn.getLastSignedInAccount(MainActivity.this);
                if(acc != null){
                    mname.setText(acc.getEmail());
                }


    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        GoogleSignIn.getClient(this,gso).signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("remember","false");
        editor.apply();
        finish();
    }

}