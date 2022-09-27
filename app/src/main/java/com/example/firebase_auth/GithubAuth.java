package com.example.firebase_auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.OAuthProvider;

import java.util.ArrayList;
import java.util.List;

public class GithubAuth extends AppCompatActivity {

    EditText txtLogGit;
    Button btnLoginGit;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_auth);

        txtLogGit=findViewById(R.id.txtLogGit);
        btnLoginGit=findViewById(R.id.btnLoginGit);
        mAuth=FirebaseAuth.getInstance();

        btnLoginGit.setOnClickListener(v -> {
            String email = txtLogGit.getText().toString();
            if(!email.matches(emailPattern)){
                Toast.makeText(GithubAuth.this,"Enter a proper Email",Toast.LENGTH_SHORT).show();
            }else {
                OAuthProvider.Builder provider = OAuthProvider.newBuilder("github.com");
                provider.addCustomParameter("login", email);
                List<String> scopes =
                        new ArrayList<String>() {
                            {
                                add("user:email");
                            }
                        };
                provider.setScopes(scopes);
                Task<AuthResult> pendingResultTask = mAuth.getPendingAuthResult();
                if (pendingResultTask != null) {
                    // There's something already here! Finish the sign-in for your user.
                    pendingResultTask
                            .addOnSuccessListener(
                                    authResult -> {
                                        // User is signed in.
                                        // IdP data available in
                                        // authResult.getAdditionalUserInfo().getProfile().
                                        // The OAuth access token can also be retrieved:
                                        // authResult.getCredential().getAccessToken().
                                    })
                            .addOnFailureListener(
                                    e -> Toast.makeText(GithubAuth.this,""+e.getMessage(),Toast.LENGTH_SHORT).show());
                } else {
                    mAuth
                            .startActivityForSignInWithProvider(/* activity= */ GithubAuth.this, provider.build())
                            .addOnSuccessListener(
                                    authResult -> HomeActivityLoged())
                            .addOnFailureListener(
                                    e -> Toast.makeText(GithubAuth.this,""+e.getMessage(),Toast.LENGTH_SHORT).show());
                }
            }
        });
    }
    private void HomeActivityLoged(){
        Intent intent = new Intent(getApplicationContext(), HomeActivityLoged.class);
        startActivity(intent);
        finish();
    }
}