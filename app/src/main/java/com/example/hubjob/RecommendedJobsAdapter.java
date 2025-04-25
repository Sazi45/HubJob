package com.example.hubjob;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecommendedJobsAdapter extends RecyclerView.Adapter<RecommendedJobsAdapter.JobViewHolder> {

    private final List<JobPostModel> jobList;

    public RecommendedJobsAdapter(List<JobPostModel> jobList) {
        this.jobList = jobList;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recommended_job, parent, false);
        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        JobPostModel job = jobList.get(position);
        holder.title.setText(job.getJobTitle());
        holder.description.setText(job.getDescription());
        holder.location.setText("Location: " + job.getLocation());
        holder.skills.setText("Skills: " + job.getSkills());
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public static class JobViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, location, skills;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.jobTitleText);
            description = itemView.findViewById(R.id.jobDescriptionText);
            location = itemView.findViewById(R.id.jobLocationText);
            skills = itemView.findViewById(R.id.jobSkillsText);
        }
    }
}
