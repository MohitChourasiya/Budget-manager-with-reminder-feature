package com.example.budgetmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.budgetmanagement.databinding.ActivityNewTransectionBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.Type;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class NewTransection extends AppCompatActivity {

    ActivityNewTransectionBinding binding;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    FirebaseUser user;
    String type="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityNewTransectionBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        firestore =FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        user= auth.getCurrentUser();


        binding.expenceAmountCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                type="Expense";
                binding.incomeAmountCheck.setChecked(false);
                binding.expenceAmountCheck.setChecked(true);

            }
        });
        binding.incomeAmountCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                type="Income";
                binding.incomeAmountCheck.setChecked(true);
                binding.expenceAmountCheck.setChecked(false);
            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount =binding.userAmount.getText().toString().trim();
                String note =binding.editTxtNote.getText().toString().trim();


                if (amount.length()<=0){ return;}
                if (type.length()<=0){
                    Toast.makeText(NewTransection.this,"Select Transection Type",Toast.LENGTH_SHORT).show();
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String currentDate=dateFormat.format(new Date());


                String id= UUID.randomUUID().toString();
                Map<String,Object> transection =new HashMap<>();
                transection.put("id",id);
                transection.put("amount",amount);
                transection.put("note",note);
                transection.put("type",type);
                transection.put("date",currentDate);

                firestore.collection("Expenses").document(auth.getUid()).collection("Notes").document(id)
                        .set(transection).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                                Toast.makeText(NewTransection.this,"Added",Toast.LENGTH_SHORT).show();

                                binding.editTxtNote.setText("");
                                binding.userAmount.setText("");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(NewTransection.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                            }
                        });
            }
        });
    }
}