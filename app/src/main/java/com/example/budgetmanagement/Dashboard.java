package com.example.budgetmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.budgetmanagement.databinding.ActivityDashboardBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    ActivityDashboardBinding binding;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth auth;
    int visible=0;
    int sumEx=0;
    int sumIn=0;
    ArrayList<TransactionModel>  transactionModelArrayList;
    TransactionAdapter transactionAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDashboardBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        firebaseFirestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        transactionModelArrayList=new ArrayList<>();

        binding.recyclerHistory.setLayoutManager(new LinearLayoutManager(this));

        binding.recyclerHistory.setHasFixedSize(true);


        binding.addTransectionCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (visible==1){
                    binding.refreshBtn.setVisibility(View.VISIBLE);
                    binding.addMoneyBtn.setVisibility(View.VISIBLE);
                    binding.reminderBtn.setVisibility(View.VISIBLE);

                    visible=0;
                }else{
                    visible=1;
                    binding.refreshBtn.setVisibility(View.GONE);
                    binding.addMoneyBtn.setVisibility(View.GONE);
                    binding.reminderBtn.setVisibility(View.GONE);
                }


            }

        });
        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (auth.getCurrentUser()==null){
                    startActivity(new Intent(Dashboard.this,Login.class));
                    finish();
                }
            }
        });

        binding.refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,Dashboard.class));
            }
        });

        binding.addMoneyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in  = new Intent(Dashboard.this,NewTransection.class);
                startActivity(in);
            }
        });

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog =new Dialog(Dashboard.this);
                dialog.getWindow();
                dialog.getWindow().setGravity(Gravity.CENTER);
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.setContentView(R.layout.popupl_ogout);
                dialog.setCancelable(false);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.register);
                dialog.show();

                CardView applybtn = dialog.findViewById(R.id.noLogoutButton);
                applybtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                CardView yesApply = dialog.findViewById(R.id.yesLogoutButton);
                yesApply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        auth.signOut();
                        startActivity(new Intent(Dashboard.this,Login.class));
                        finish();
                    }
                });
            }
        });

        loadData();


    }



    private void loadData() {

        firebaseFirestore.collection("Expenses").document(auth.getUid()).collection("Notes")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        for (DocumentSnapshot ds: task.getResult()) {
                            TransactionModel model =new TransactionModel(
                                    ds.getString("id"),
                                    ds.getString("note"),
                                    ds.getString("amount"),
                                    ds.getString("type"),
                                    ds.getString("date"));
                            int amt =Integer.parseInt(ds.getString("amount"));
                            if (ds.getString("type").equals("Expense")){
                                sumEx=sumEx+amt;
                            }else {
                                sumIn=sumIn+amt;
                            }
                            transactionModelArrayList.add(model);
                        }
                        binding.incomeTotal.setText(String.valueOf(sumIn));
                        binding.expenditureTotal.setText(String.valueOf(sumEx));
                        binding.balanceTotal.setText(String.valueOf(sumIn-sumEx));
                        transactionAdapter=new TransactionAdapter(Dashboard.this,transactionModelArrayList);

                        binding.recyclerHistory.setAdapter(transactionAdapter);
                    }
                });

        binding.reminderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Dashboard.this,MainActivity.class));

            }
        });




    }


}