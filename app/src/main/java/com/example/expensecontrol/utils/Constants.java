package com.example.expensecontrol.utils;

import com.example.expensecontrol.R;
import com.example.expensecontrol.adapters.CategoryAdapter;
import com.example.expensecontrol.models.Category;

import java.util.ArrayList;

public class Constants {
    public static  String INCOME="INCOME";
    public static  String EXPENSE="EXPENSE";

    public static ArrayList<Category> categories;
    public static int SELECTED_TAB=0;
    public static int DAILY=0;
    public static int MONTHLY=1;
    public static int CALENDER=2;
    public static int SUMMARY=3;
    public static int NOTE=4;

    public static void setCategories(){
        categories=new ArrayList<>();
        categories.add(new Category("Salary", R.drawable.ic_salary,R.color.salary));
        categories.add(new Category("Business",R.drawable.ic_business,R.color.business));
        categories.add(new Category("Investment",R.drawable.ic_investment,R.color.investment));
        categories.add(new Category("Loan",R.drawable.ic_loan,R.color.loan));
        categories.add(new Category("Rent",R.drawable.ic_rent,R.color.rent));
        categories.add(new Category("Other",R.drawable.ic_other,R.color.other));

        }

        public static  Category getCategoryDetails(String categoryName) {
            for (Category cat :
                    categories) {
                if (cat.getCategoryName().equals(categoryName)) {
                return cat;
                }
    
    }
        return null;

}
    public static int getAccountColor(String accountName){

        switch (accountName){
            case  "Bank":
                return R.color.bank_color;
            case  "Cash":
                return  R.color.cash_color;
            case  "Card":
                return R.color.card_color;
            default:
                return R.color.default_color;

        }

    }

}
