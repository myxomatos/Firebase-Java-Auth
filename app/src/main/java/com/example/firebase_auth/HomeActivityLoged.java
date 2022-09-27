package com.example.firebase_auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.firebase_auth.adapter.ContactoAdapter;
import com.example.firebase_auth.model.Contacto;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class HomeActivityLoged extends AppCompatActivity {


    Button addContact;
    RecyclerView mRecycler;
    ContactoAdapter mAdapter;
    FirebaseFirestore mFirestore;

    TextView mailLog, phoneLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_loged);
        mailLog = findViewById(R.id.mailLog);
        phoneLog = findViewById(R.id.phoneLog);
        Button logoutLoged = findViewById(R.id.logoutLoged);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        mFirestore = FirebaseFirestore.getInstance();
        mRecycler = findViewById(R.id.recyclerView);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        assert currentUser != null;
        Query query = mFirestore.collection("contactos").whereEqualTo("createdby", currentUser.getUid());

        FirestoreRecyclerOptions<Contacto> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Contacto>().setQuery(query, Contacto.class).build();

        mAdapter = new ContactoAdapter(firestoreRecyclerOptions);
        mAdapter.notifyDataSetChanged();
        mRecycler.setAdapter(mAdapter);

        String gName = currentUser.getEmail();
        String phone = currentUser.getPhoneNumber();

        if (currentUser != null) {
            mailLog.setText(gName);
        }
        if (currentUser != null) {

            phoneLog.setText(phone);
        }



        logoutLoged.setOnClickListener(v -> logoutUser());

        addContact = findViewById(R.id.addContact);

        addContact.setOnClickListener(v -> startActivity(new Intent(HomeActivityLoged.this, AddContact.class)));

    }
    private void logoutUser() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), MainActivity.class ));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }
}