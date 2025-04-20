package com.example.hubjob;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MyApplicationAdapter extends RecyclerView.Adapter<MyApplicationAdapter.ViewHolder> {

    private List<JobApplicationModel> applicationList;

    public MyApplicationAdapter(List<JobApplicationModel> applicationList) {
        this.applicationList = applicationList;
    }

    @NonNull
    @Override
    public MyApplicationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_application, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyApplicationAdapter.ViewHolder holder, int position) {
        JobApplicationModel application = applicationList.get(position);
        holder.title.setText(application.getJobTitle());
        holder.company.setText("Posted by: " + application.getJobPostedBy());
        holder.location.setText("Location: " + application.getJobLocation());
        holder.salary.setText("Salary: " + application.getJobSalary());
    }

    @Override
    public int getItemCount() {
        return applicationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, company, location, salary;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.appJobTitle);
            company = itemView.findViewById(R.id.appJobCompany);
            location = itemView.findViewById(R.id.appJobLocation);
            salary = itemView.findViewById(R.id.appJobSalary);
        }
    }
}
