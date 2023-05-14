package com.example.firestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class InsertActivity extends AppCompatActivity {

    EditText ed_name, ed_age;
    Button btn_save;
    FirebaseFirestore db;

    Map<String, Object> user
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        ed_age = findViewById(R.id.ed_age);
        ed_name = findViewById(R.id.ed_name);
        btn_save = findViewById(R.id.btn);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String age = ed_age.getText().toString();
                String name = ed_name.getText().toString();

                user = new HashMap<>();
                user.put("name",name);
                user.put("age", age);

                insertTofirestore();

            }
        });
    }

    private void insertTofirestore() {
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(InsertActivity.this, "Successed", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(InsertActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                    }
                });


    }
}