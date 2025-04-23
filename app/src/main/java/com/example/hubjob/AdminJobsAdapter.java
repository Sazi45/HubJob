package com.example.hubjob;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdminJobsAdapter extends RecyclerView.Adapter<AdminJobsAdapter.JobViewHolder> {
    private List<JobPostModel> jobList;

    public AdminJobsAdapter(List<JobPostModel> jobList) {
        this.jobList = jobList;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_job_post_item, parent, false);
        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        JobPostModel job = jobList.get(position);
        holder.title.setText(job.getJobTitle());
        holder.company.setText(job.getCategory());
        holder.location.setText(job.getLocation());
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    static class JobViewHolder extends RecyclerView.ViewHolder {
        TextView title, company, location;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_job_title);
            company = itemView.findViewById(R.id.tv_job_company);
            location = itemView.findViewById(R.id.tv_job_location);
        }
    }
}
