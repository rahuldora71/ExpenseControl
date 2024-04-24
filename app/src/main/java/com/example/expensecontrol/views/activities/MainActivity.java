package com.example.expensecontrol.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.example.expensecontrol.adapters.TransactionsAdapter;
import com.example.expensecontrol.databinding.ActivityMainBinding;
import com.example.expensecontrol.models.Transaction;
import com.example.expensecontrol.utils.Constants;
import com.example.expensecontrol.utils.Helpers;
import com.example.expensecontrol.viewmodels.MainViewModel;
import com.example.expensecontrol.views.fragments.AddTransactionFragment;
import com.example.expensecontrol.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Calendar calendar;
    /*
    * 0=Daily
    * 1=Monthly
    * 2=Calender
    * 3=Summary
    * 4=Notes
    * */



    public  MainViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel=new ViewModelProvider(this).get(MainViewModel.class);



        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Transactions");

        Constants.setCategories();

        calendar=Calendar.getInstance();
        updateDate();
        binding.nextDateBtn.setOnClickListener(c->{
            if (Constants.SELECTED_TAB==Constants.MONTHLY){
                calendar.add(Calendar.MONTH,1);

            }else if (Constants.SELECTED_TAB==Constants.DAILY){

            calendar.add(Calendar.DATE,1);
            }
            updateDate();
        });
        binding.PreviousDateBtn.setOnClickListener(c->{
            if (Constants.SELECTED_TAB==Constants.MONTHLY){
                calendar.add(Calendar.MONTH,-1);

            }else if (Constants.SELECTED_TAB==Constants.DAILY){

                calendar.add(Calendar.DATE,-1);
            }            updateDate();
        });


        binding.floatingActionButton2.setOnClickListener(c->{
            new AddTransactionFragment().show(getSupportFragmentManager(),null);
        });

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                Toast.makeText(MainActivity.this,tab.getText().toString(),Toast.LENGTH_SHORT).show();

                if (tab.getText().equals("Monthly")){
                    Constants.SELECTED_TAB=1;
                    updateDate();

                }else if (tab.getText().equals("Daily")){
                    Constants.SELECTED_TAB=0;
                    updateDate();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });





        binding.transactionList.setLayoutManager(new LinearLayoutManager(this));


        viewModel.transactions.observe(this, new Observer<RealmResults<Transaction>>() {
            @Override
            public void onChanged(RealmResults<Transaction> transactions) {
                TransactionsAdapter transactionsAdapter = new TransactionsAdapter(MainActivity.this, transactions);
                binding.transactionList.setAdapter(transactionsAdapter);
                if(transactions.size()>0) {
                    binding.emptySpace.setVisibility(View.GONE);

                }else {
                    binding.emptySpace.setVisibility(View.VISIBLE);
                }
            }
        });
        viewModel.totalIncome.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {

                binding.incomeLbl.setText(String.valueOf(aDouble));
            }
        });
        viewModel.totalExpense.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.expenceLbl.setText(String.valueOf(aDouble));
            }
        });

        viewModel.totalAmount.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.totalLbl.setText(String.valueOf(aDouble));
            }
        });




        viewModel.getTransactions(calendar);





    }

    public void getTransactions(){
        viewModel.getTransactions(calendar);
    }




    public void updateDate(){
        if (Constants.SELECTED_TAB==Constants.DAILY){
        binding.currentDate.setText(Helpers.formatDate(calendar.getTime()));

        }else if (Constants.SELECTED_TAB==Constants.MONTHLY){
            binding.currentDate.setText(Helpers.formatDateByMonth(calendar.getTime()));

        }
        viewModel.getTransactions(calendar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}