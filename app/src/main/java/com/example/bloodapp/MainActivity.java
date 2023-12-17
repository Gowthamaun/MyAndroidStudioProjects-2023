package com.example.bloodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.bloodapp.Adapter.UserAdapter;
import com.example.bloodapp.Model.User;
import com.example.bloodapp.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;


    private List<User> list;

    private UserAdapter adapter;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });

        list=new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference("user");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot data:snapshot.getChildren()) {
                    User user=data.getValue(User.class);
                    list.add(user);
                    adapter=new UserAdapter(MainActivity.this,list);
                    binding.recyclerView.setAdapter(adapter);
                    binding.editText.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void filter(String txt)
    {
        ArrayList<User> filterList=new ArrayList<>();
        for (User user:list) {
            if(user.getEmail().toLowerCase().contains(txt.toLowerCase()))
            {
                filterList.add(user);
            }
        }
        adapter.FilterList(filterList);
    }
}