package com.example.firebase_auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Phone extends AppCompatActivity {

    Button btnBckfPhone;
    EditText editText1;
    Button sendotp;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        btnBckfPhone=findViewById(R.id.btnBckfPhone);
        editText1=findViewById(R.id.input_mob_no);
        sendotp=findViewById(R.id.btnsend);
        progressBar=findViewById(R.id.probar1);

        btnBckfPhone.setOnClickListener(view -> MainActivity());
        sendotp.setOnClickListener(view -> {

            if (!editText1.getText().toString().trim().isEmpty()){
                if ((editText1.getText().toString().trim()).length()==10)
                {

                    progressBar.setVisibility(View.VISIBLE);
                    sendotp.setVisibility(View.INVISIBLE);

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+52" + editText1.getText().toString(),
                            60,
                            TimeUnit.SECONDS,
                            Phone.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                    progressBar.setVisibility(View.GONE);
                                    sendotp.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {

                                    progressBar.setVisibility(View.GONE);
                                    sendotp.setVisibility(View.VISIBLE);
                                    Toast.makeText(Phone.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                    progressBar.setVisibility(View.GONE);
                                    sendotp.setVisibility(View.VISIBLE);

                                    Intent intent=new Intent(getApplicationContext(),Phone2.class);
                                    intent.putExtra("mobile",editText1.getText().toString());
                                    intent.putExtra("backendotp",backendotp);
                                    startActivity(intent);

                                }
                            }

                    );


                }else {
                    Toast.makeText(Phone.this, "Please enter correct Number", Toast.LENGTH_SHORT).show();
                }

            }else
            {
                Toast.makeText(Phone.this, "Enter Mobile number", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void MainActivity(){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}