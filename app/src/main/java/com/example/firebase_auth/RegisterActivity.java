package com.example.firebase_auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    Button btnBckfReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirestore = FirebaseFirestore.getInstance();

        btnBckfReg=findViewById(R.id.btnBckfReg);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            finish();
            return;
        }

        Button btnRegister = findViewById(R.id.btnSignin);
        btnRegister.setOnClickListener(view -> registerUser());

        TextView textSwitchToLogin = findViewById(R.id.lnkLogin);
        textSwitchToLogin.setOnClickListener(v -> switchToLogin());

        btnBckfReg.setOnClickListener(view -> MainActivity());

    }

    private void registerUser() {
        EditText etFullName = findViewById(R.id.txtName);
        EditText etRegisterEmail = findViewById(R.id.txtEmail) ;
        EditText etRegisterPassword = findViewById(R.id.txtPwd);

        String fullName = etFullName.getText().toString();
        String email = etRegisterEmail.getText().toString();
        String password = etRegisterPassword.getText().toString();

        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {

                    Map<String, Object> map = new HashMap<>();
                    map.put("name", fullName);
                    map.put("email", email);

                    if (task.isSuccessful()) {

                        mFirestore.collection("users").document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).set(map).addOnSuccessListener(documentReference -> {
                            Toast.makeText(getApplicationContext(),"Usuario Creado", Toast.LENGTH_SHORT).show();
                            finish();
                        }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_SHORT).show());
                        HomeActivityLoged();
                    } else {
                        Toast.makeText(RegisterActivity.this, "The account already exists",
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void MainActivity(){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    private void HomeActivityLoged(){

        startActivity(new Intent(getApplicationContext(), HomeActivityLoged.class));
        finish();
    }
    private void switchToLogin(){
        startActivity(new Intent(getApplicationContext(), Login.class ));
        finish();
    }
}