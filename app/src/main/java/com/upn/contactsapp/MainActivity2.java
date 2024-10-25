package com.upn.contactsapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ContentInfoCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.upn.contactsapp.entities.Contact;

import java.util.List;
import java.util.UUID;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        EditText setText = findViewById(R.id.editTextText);
        Button btn = findViewById(R.id.buttondatabase);
        btn.setOnClickListener(view -> {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("N00286557");
            DatabaseReference table =  myRef.child("contacts");

           // Contact c1 = new Contact("Luis", "12345");
           // c1.id = 1;
           // Contact c2 = new Contact("Luis", "12345");
           // c2.id = 2;

           // table.child(String.valueOf(c1.id)).setValue(c1);
           // table.child(String.valueOf(c2.id)).setValue(c2);
            String name = setText.getText().toString();

            Contact c3 = new Contact(name, "123456");
            c3.uuid = UUID.randomUUID().toString();
            table.child(c3.uuid).setValue(c3);

        });
    }


}