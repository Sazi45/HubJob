package com.example.hubjob;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class JobPostAdapter extends RecyclerView.Adapter<JobPostAdapter.JobPostViewHolder> {

    private List<JobPostModel> jobList;

    public JobPostAdapter(List<JobPostModel> jobList) {
        this.jobList = jobList;
    }

    @NonNull
    @Override
    public JobPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_job_post, parent, false);
        return new JobPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobPostViewHolder holder, int position) {
        JobPostModel job = jobList.get(position);
        holder.titleText.setText(job.getJobTitle());
        holder.locationText.setText(job.getLocation());
        holder.salaryText.setText("Salary: " + job.getSalary());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), JobDetailsActivity.class);
            intent.putExtra("jobTitle", job.getJobTitle());
            intent.putExtra("description", job.getDescription());
            intent.putExtra("location", job.getLocation());
            intent.putExtra("salary", job.getSalary());
            intent.putExtra("experience", job.getExperience());
            intent.putExtra("skills", job.getSkills());
            intent.putExtra("category", job.getCategory());
            intent.putExtra("postedBy", job.getPostedBy());
            intent.putExtra("timestamp", job.getTimestamp());
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public static class JobPostViewHolder extends RecyclerView.ViewHolder {
        TextView titleText, locationText, salaryText;

        public JobPostViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.jobTitleText);
            locationText = itemView.findViewById(R.id.jobLocationText);
            salaryText = itemView.findViewById(R.id.jobSalaryText);
        }
    }
}
