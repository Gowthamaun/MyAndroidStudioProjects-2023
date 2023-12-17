package com.example.bloodapp.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.bloodapp.MainActivity;
import com.example.bloodapp.Model.User;
import com.example.bloodapp.R;
import com.example.bloodapp.databinding.ActivityRegistrationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {
    private ActivityRegistrationBinding binding;

    private String name,email,password,phone,blood;

    private DatabaseReference databaseReference;

    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String[] items=new String[]{"Select Blood Group","A+","A-","B+","B-","AB+","AB-","O+","O-"};
        binding.BloodGroupsSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,items));

        binding.BloodGroupsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                blood=binding.BloodGroupsSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=binding.InputName.getText().toString();
                phone=binding.InputNumber.getText().toString();
                email=binding.InputEmail.getText().toString();
                password=binding.InputPassword.getText().toString();

                if(name.isEmpty())
                {
                    binding.InputName.setError("Please Enter your Name");
                    binding.InputName.requestFocus();
                }
                else if(phone.isEmpty())
                {
                    binding.InputNumber.setError("Please Enter your Number");
                    binding.InputNumber.requestFocus();
                }
                else if(email.isEmpty())
                {
                    binding.InputEmail.setError("Please Enter your Email");
                    binding.InputEmail.requestFocus();
                }
                else if(password.isEmpty())
                {
                    binding.InputPassword.setError("Please Enter your Password");
                    binding.InputPassword.requestFocus();
                }
                else if(blood.equals("Select Blood Group"))
                {
                    Toast.makeText(RegistrationActivity.this,"Choose your Blood Group",Toast.LENGTH_SHORT).show();
                }
                else {
                    auth=FirebaseAuth.getInstance();
                    Toast.makeText(RegistrationActivity.this,"This is working",Toast.LENGTH_SHORT).show();
                    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Toast.makeText(RegistrationActivity.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
                            createUser();
                        }
                    });
                }
            }
        });
    }

    private void createUser()
    {
        String currentUser=FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        databaseReference= FirebaseDatabase.getInstance().getReference("user").child(name);

        User user=new User(name,phone,password,email,blood);

        databaseReference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                    finish();
                }
            }
        });
    }
}