package com.example.firestore;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.recyclerview.MyAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class viewtActivity extends AppCompatActivity {

    MyAdapter ma;
    ArrayList<User> users;
    RecyclerView rv;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewt);

        rv = findViewById(R.id.recyclerView);
        users = new ArrayList<>();

        db = FirebaseFirestore.getInstance();
        ma = new MyAdapter(this, users);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);

        rv.setLayoutManager(lm);
        rv.setAdapter(ma);

        viewDataFromFirestore();
    }

    private void viewDataFromFirestore() {
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                User user = document.toObject(User.class);
                                users.add(user);

                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                        ma.notifyDataSetChanged();
                    }
                });

    }
}
