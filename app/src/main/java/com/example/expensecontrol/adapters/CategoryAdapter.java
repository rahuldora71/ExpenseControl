package com.example.expensecontrol.adapters;


import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensecontrol.R;
import com.example.expensecontrol.databinding.SampleCategoryItemBinding;
import com.example.expensecontrol.models.Category;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    Context context;
    ArrayList<Category> categories;

    public interface CategoryClockListner{
        void onCategoryClicked(Category category);

    }
    CategoryClockListner categoryClockListner;

    public  CategoryAdapter(Context context, ArrayList<Category> categories ,CategoryClockListner categoryClockListner){
    this.context=context;
    this.categories=categories;
this.categoryClockListner=categoryClockListner;
    }
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.sample_category_item,parent,false));

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category=categories.get(position);
        holder.binding.categoryText.setText(category.getCategoryName());
        holder.binding.categoryIcon.setImageResource(category.getCategoryImage());
        holder.binding.categoryIcon.setBackgroundTintList(context.getColorStateList(category.getCategoryColor()));
        holder.itemView.setOnClickListener(c->{
            categoryClockListner.onCategoryClicked(category);
        });



    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        SampleCategoryItemBinding binding;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=SampleCategoryItemBinding.bind(itemView);
        }
    }
}
