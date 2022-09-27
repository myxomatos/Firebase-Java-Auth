package com.example.firebase_auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Phone2 extends AppCompatActivity {
    EditText et1, et2, et3, et4, et5, et6;
    Button btnsubmit;
    String getbackendotp;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone2);

        et1 = findViewById(R.id.inputotp1);
        et2 = findViewById(R.id.inputotp2);
        et3 = findViewById(R.id.inputotp3);
        et4 = findViewById(R.id.inputotp4);
        et5 = findViewById(R.id.inputotp5);
        et6 = findViewById(R.id.inputotp6);

        progressBar = findViewById(R.id.probar2);


        //get mobile number from mainActivty to this
        TextView textView = findViewById(R.id.txtmobileno);
        textView.setText(String.format(
                "+52-%S", getIntent().getStringExtra("mobile")
        ));

        getbackendotp = getIntent().getStringExtra("backendotp");


        btnsubmit = findViewById(R.id.btnsubmit);

        btnsubmit.setOnClickListener(view -> {

            if (!et1.getText().toString().trim().isEmpty() && !et2.getText().toString().trim().isEmpty()
                    && !et3.getText().toString().trim().isEmpty()
                    && !et4.getText().toString().trim().isEmpty()
                    && !et5.getText().toString().trim().isEmpty()
                    && !et6.getText().toString().trim().isEmpty()) {

                // marging user's input in a string
                String getuserotp = et1.getText().toString() +
                        et2.getText().toString() +
                        et3.getText().toString() +
                        et4.getText().toString() +
                        et5.getText().toString() +
                        et6.getText().toString();

                if (getbackendotp != null) {

                    progressBar.setVisibility(View.VISIBLE);
                    btnsubmit.setVisibility(View.INVISIBLE);

                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(getbackendotp, getuserotp);
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(task -> {

                                progressBar.setVisibility(View.GONE);
                                btnsubmit.setVisibility(View.VISIBLE);

                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(getApplicationContext(), HomeActivityLoged.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(Phone2.this, "Enter correct OTP", Toast.LENGTH_SHORT).show();
                                }
                            });


                } else {
                    Toast.makeText(Phone2.this, "Please check internet", Toast.LENGTH_SHORT).show();
                }

                //Toast.makeText(Phone2.this, "OTP Verify", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Phone2.this, "Please fill all number", Toast.LENGTH_SHORT).show();
            }


            // movenumtonext();



        });

        findViewById(R.id.sendotp_again).setOnClickListener(view -> PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+52" + getIntent().getStringExtra("mobile"),
                60,
                TimeUnit.SECONDS,
                Phone2.this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {


                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {


                        Toast.makeText(Phone2.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String newbackendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {


                        getbackendotp = newbackendotp;
                        Toast.makeText(Phone2.this, "OTP Send Sucessfuly", Toast.LENGTH_SHORT).show();


                    }
                }

        ));

        movenumtonext(); //move num to next

    }

    private void movenumtonext() {



        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!charSequence.toString().trim().isEmpty()) {
                    et2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!charSequence.toString().trim().isEmpty()) {
                    et3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!charSequence.toString().trim().isEmpty()) {
                    et4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!charSequence.toString().trim().isEmpty()) {
                    et5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!charSequence.toString().trim().isEmpty()) {
                    et6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}