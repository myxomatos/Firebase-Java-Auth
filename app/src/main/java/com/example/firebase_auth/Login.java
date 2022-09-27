package com.example.firebase_auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;

    Button btnBckfLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnBckfLog=findViewById(R.id.btnBckfLog);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            finish();
            return;
        }

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v -> authenticateUser());

        btnBckfLog.setOnClickListener(view -> RegisterActivity());
    }
    private void authenticateUser() {
        EditText txtLogEmail = findViewById(R.id.txtLogGit);
        EditText txtLogPwd = findViewById(R.id.txtLogPwd);

        String email = txtLogEmail.getText().toString();
        String password = txtLogPwd.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()){
                HomeActivityLoged();
            } else {
                Toast.makeText(Login.this, "The account does not exist, Please register", Toast.LENGTH_LONG).show();
            }
        });

    }
    private void RegisterActivity(){
        finish();
        Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
        startActivity(intent);
    }
    private void HomeActivityLoged(){
        Intent intent = new Intent(getApplicationContext(), HomeActivityLoged.class);
        startActivity(intent);
        finish();
    }
}