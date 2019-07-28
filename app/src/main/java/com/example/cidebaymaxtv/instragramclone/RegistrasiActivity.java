package com.example.cidebaymaxtv.instragramclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegistrasiActivity extends AppCompatActivity {


    EditText username, fullname, email, password;
    Button registrasi;
    TextView txt_Login;


    FirebaseAuth mAuth;
    DatabaseReference reference;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);



        username = findViewById(R.id.username);
        fullname = findViewById(R.id.fullName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        registrasi = findViewById(R.id.btnRegister);
        txt_Login = findViewById(R.id.texLlogin);


    mAuth = FirebaseAuth.getInstance();


        txt_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrasiActivity.this, LoginActivity.class));

            }
        });


        registrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(RegistrasiActivity.this);
                pd.setMessage("Please wait..");
                pd.show();


                String str_username = username.getText().toString();
                String str_fullname = fullname.getText().toString();
                String str_email = email.getText().toString();
                String str_password= password.getText().toString();

                if (TextUtils.isEmpty(str_username) || TextUtils.isEmpty(str_fullname) || TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password)){
                    Toast.makeText(RegistrasiActivity.this, "All filed are required", Toast.LENGTH_SHORT).show();

                } else if (str_password.length() < 6){
                    Toast.makeText(RegistrasiActivity.this, "password mut have 6 character", Toast.LENGTH_SHORT).show();


                } else {
                    registrasi(str_username, str_fullname, str_email, str_password);

                }
            }
        });
    }

    private void registrasi (final String username, final String fullname, String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegistrasiActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            String userid = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference().child("Users  ").child(userid);

                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("username", username.toLowerCase());
                            hashMap.put("fullname", fullname);
                            hashMap.put("bio", "");
                            hashMap.put("imageurl", "https://firebasestorage.googleapis.com/v0/b/instragramclone-52860.appspot.com/o/logo.png?alt=media&token=0e7af724-f4b1-4b60-90ac-8042051d48d2");



                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()){
                                        pd.dismiss();

                                        Intent intent = new Intent(RegistrasiActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }
                            });
                        }
                        else {
                            pd.dismiss();
                            Toast.makeText(RegistrasiActivity.this, "you can't registrasi with this email or password", Toast.LENGTH_SHORT).show();
                        }




                    }
                });
    }

}
