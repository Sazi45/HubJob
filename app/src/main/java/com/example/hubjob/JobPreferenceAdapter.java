package com.example.hubjob;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class JobPreferenceAdapter extends RecyclerView.Adapter<JobPreferenceAdapter.ViewHolder> {

    private List<JobPreferenceModel> jobList;
    private OnJobClickListener listener;

    // ✅ Interface for click listener
    public interface OnJobClickListener {
        void onJobClick(JobPreferenceModel job);
    }

    // ✅ Updated constructor to include listener
    public JobPreferenceAdapter(List<JobPreferenceModel> jobList, OnJobClickListener listener) {
        this.jobList = jobList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public JobPreferenceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobPreferenceAdapter.ViewHolder holder, int position) {
        JobPreferenceModel job = jobList.get(position);
        holder.title.setText(job.getJobTitle());
        holder.location.setText(job.getLocation());
        holder.salary.setText(job.getSalary());

        // ✅ Trigger click event through listener
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onJobClick(job);
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, location, salary;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.jobTitleText);
            location = itemView.findViewById(R.id.locationText);
            salary = itemView.findViewById(R.id.salaryText);
        }
    }
}
