package com.example.expensecontrol.views.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.expensecontrol.R;
import com.example.expensecontrol.adapters.AccountsAdapter;
import com.example.expensecontrol.adapters.CategoryAdapter;
import com.example.expensecontrol.databinding.FragmentAddTransactionBinding;
import com.example.expensecontrol.databinding.ListDialogBinding;
import com.example.expensecontrol.models.Account;
import com.example.expensecontrol.models.Category;
import com.example.expensecontrol.models.Transaction;
import com.example.expensecontrol.utils.Constants;
import com.example.expensecontrol.utils.Helpers;
import com.example.expensecontrol.views.activities.MainActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.Calendar;

public class AddTransactionFragment extends BottomSheetDialogFragment {


    public AddTransactionFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentAddTransactionBinding binding;
    Transaction transaction;

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentAddTransactionBinding.inflate(inflater);



        transaction=new Transaction();


        binding.incomeBtn.setOnClickListener(v -> {
       binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.income_selector));
       binding.expenceBtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
       binding.expenceBtn.setTextColor(getContext().getColor(R.color.textcolor));
       binding.incomeBtn.setTextColor(getContext().getColor(R.color.green));

       transaction.setType(Constants.INCOME);
        });
        binding.expenceBtn.setOnClickListener(v -> {
            binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
            binding.expenceBtn.setBackground(getContext().getDrawable(R.drawable.expense_selector));
            binding.expenceBtn.setTextColor(getContext().getColor(R.color.red));
            binding.incomeBtn.setTextColor(getContext().getColor(R.color.textcolor));
            transaction.setType(Constants.EXPENSE);
        });
        binding.date.setOnClickListener(c->{
            DatePickerDialog datePickerDialog=new DatePickerDialog(getContext());
            datePickerDialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {
                Calendar calendar=Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH, view.getDayOfMonth());
                calendar.set(Calendar.MONTH, view.getMonth());
                calendar.set(Calendar.YEAR, view.getYear());

//                SimpleDateFormat dateFormat= new SimpleDateFormat("dd MMMM, yyyy");
                String dateToShow= Helpers.formatDate(calendar.getTime());
                binding.date.setText(dateToShow);

                transaction.setDate(calendar.getTime());
                transaction.setId(calendar.getTime().getTime());
            });
            datePickerDialog.show();
        });
        binding.category.setOnClickListener(c->{
            ListDialogBinding dialogBinding= ListDialogBinding.inflate(inflater);
            AlertDialog categoryDialog =new AlertDialog.Builder(getContext()).create();
            categoryDialog.setView(dialogBinding.getRoot());


             CategoryAdapter categoryAdapter =new CategoryAdapter(getContext(), Constants.categories, new CategoryAdapter.CategoryClockListner() {
                @Override
                public void onCategoryClicked(Category category) {
                    binding.category.setText(category.getCategoryName());
                    transaction.setCategory(category.getCategoryName());
                    categoryDialog.dismiss();
                }
            });
            dialogBinding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
            dialogBinding.recyclerView.setAdapter(categoryAdapter);
            categoryDialog.show();

        });

        binding.accountLabl.setOnClickListener(c->{
            ListDialogBinding dialogBinding= ListDialogBinding.inflate(inflater);
            AlertDialog accountDialog =new AlertDialog.Builder(getContext()).create();
            accountDialog.setView(dialogBinding.getRoot());
            ArrayList<Account> accounts=new ArrayList<>();
            accounts.add(new Account(0,"Cash"));
            accounts.add(new Account(0,"Bank"));
            accounts.add(new Account(0,"Paytm"));
            accounts.add(new Account(0,"EasyPay"));
            accounts.add(new Account(0,"Other"));

            AccountsAdapter adapter =new AccountsAdapter(getContext(), accounts, new AccountsAdapter.AccountsClickListener() {
                @Override
                public void onAccountSelected(Account account) {
                    binding.accountLabl.setText(account.getAccountName());
                    transaction.setAccount(account.getAccountName());
                    accountDialog.dismiss();
                }
            });
            dialogBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            dialogBinding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
            dialogBinding.recyclerView.setAdapter(adapter);
            accountDialog.show();


        });


        binding.saveTransectionBtn.setOnClickListener(c->{
            double amount =Double.parseDouble(binding.amount.getText().toString());
            String note= binding.note.getText().toString();
            if (transaction.getType().equals(Constants.EXPENSE)){
                transaction.setAmount(amount*-1);
            }else {
                transaction.setAmount(amount);

            }
                transaction.setNote(note);

            ((MainActivity)getActivity()).viewModel.addTransaction(transaction);
            ((MainActivity)getActivity()).getTransactions();

            dismiss();
        });
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}