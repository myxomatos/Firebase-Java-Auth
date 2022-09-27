package com.example.firebase_auth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {



    Button google_btn;
    Button usrpwd;
    Button phonebtn;
    Button gitbtn;

    private static final int RC_SIGN_IN = 100;
    private GoogleSignInClient googleSignInClient;

    private FirebaseAuth firebaseAuth;
    private static final String TAG = "GOOGLE_SIGN_IN_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        google_btn=findViewById(R.id.google);
        usrpwd=findViewById(R.id.usrpwd);
        phonebtn=findViewById(R.id.phonebtn);
        gitbtn=findViewById(R.id.apple);


        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();

        google_btn.setOnClickListener(v -> {
            Log.d(TAG, "onClick: being Google SignIn");
            Intent intent = googleSignInClient.getSignInIntent();
            startActivityForResult(intent, RC_SIGN_IN);
        });



        usrpwd.setOnClickListener(view -> RegisterActivity());

        phonebtn.setOnClickListener(view -> Phone());

        gitbtn.setOnClickListener(v -> {
        Intent intent = new Intent(MainActivity.this,GithubAuth.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        });


    }

    private void checkUser() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null){
            Log.d(TAG, "checkUser: Already looged in");
            startActivity(new Intent(this, HomeActivityLoged.class));
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (requestCode == RC_SIGN_IN) {
            Log.d(TAG, "onActivityResult: Google Signin intent result");
            Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = accountTask.getResult(ApiException.class);
                firebaseAuthWithGoogleAccount(account);
            }
            catch (Exception e){
                Log.d(TAG, "onActivityResult: "+e.getMessage());
            }
        }
    }

    private void firebaseAuthWithGoogleAccount(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogleAccount: being firebase auth with google account");
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential).addOnSuccessListener(authResult -> {
            Log.d(TAG, "onSuccess: Logged In");

            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                    assert firebaseUser != null;
                    String uid = firebaseUser.getUid();
            String email = firebaseUser.getEmail();

            Log.d(TAG, "onSuccess: Email"+email);
            Log.d(TAG, "onSuccess: UID"+uid);

            if (Objects.requireNonNull(authResult.getAdditionalUserInfo()).isNewUser()){
                Log.d(TAG, "onSuccess: Account Created...\n"+email);
                Toast.makeText(MainActivity.this, "Account Created...\n"+email, Toast.LENGTH_SHORT).show();
            }
            else {
                Log.d(TAG, "onSuccess: Existing User...\n"+email);
                Toast.makeText(MainActivity.this, "Existing User...\n"+email, Toast.LENGTH_SHORT).show();
            }

            startActivity(new Intent(MainActivity.this, HomeActivityLoged.class));
            finish();
        })
                .addOnFailureListener(e -> Log.d(TAG, "on Failure: Loggin failed "+e.getMessage()));
    }

    private void RegisterActivity(){
        finish();
        startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
    }

    private void Phone(){
        finish();
        startActivity(new Intent(getApplicationContext(),Phone.class));
    }

}