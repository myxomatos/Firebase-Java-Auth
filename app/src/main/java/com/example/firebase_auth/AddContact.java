package com.example.firebase_auth;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

public class AddContact extends AppCompatActivity {

    Button backFContact;
    Button btn_cAdd;
    EditText cName, cNumber, cRelation;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        mFirestore = FirebaseFirestore.getInstance();

        backFContact = findViewById(R.id.btnBckfCon);
        btn_cAdd = findViewById(R.id.btn_cAdd);
        cName = findViewById(R.id.cName);
        cNumber = findViewById(R.id.cNumber);
        cRelation = findViewById(R.id.cRelation);

        btn_cAdd.setOnClickListener(v -> {
            String nameCon = cName.getText().toString().trim();
            String numberCon = cNumber.getText().toString().trim();
            String relationCon = cRelation.getText().toString().trim();

            if (nameCon.isEmpty() && numberCon.isEmpty() && relationCon.isEmpty()){
                Toast.makeText(getApplicationContext(),"Add information required", Toast.LENGTH_SHORT).show();
            }else{
                postContact(nameCon,numberCon,relationCon);
            }
        });

        backFContact.setOnClickListener(v -> HomeActivityLoged());

    }

    private void postContact(String nameCon, String numberCon, String relationCon) {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        Map<String, Object> map = new HashMap<>();
        map.put("name", nameCon);
        map.put("number", numberCon);
        map.put("relation", relationCon);
        assert currentUser != null;
        map.put("createdby", currentUser.getUid());


        mFirestore.collection("contactos").add(map).addOnSuccessListener(documentReference -> {
            Toast.makeText(getApplicationContext(),"Contacto Creado", Toast.LENGTH_SHORT).show();
            finish();
        }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_SHORT).show());
    }

    private void HomeActivityLoged(){
        startActivity(new Intent(getApplicationContext(), HomeActivityLoged.class));
        finish();
    }


}