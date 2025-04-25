package com.example.hubjob;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ApplicationsAdapter extends RecyclerView.Adapter<ApplicationsAdapter.ApplicationViewHolder> {

    private final List<JobApplicationModel> applicationList;
    private final Context context;

    public ApplicationsAdapter(Context context, List<JobApplicationModel> applicationList) {
        this.context = context;
        this.applicationList = applicationList;
    }

    @NonNull
    @Override
    public ApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.application_item, parent, false);
        return new ApplicationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApplicationViewHolder holder, int position) {
        JobApplicationModel app = applicationList.get(position);

        holder.nameText.setText(app.getFirstName() + " " + app.getLastName());
        holder.jobTitleText.setText(app.getJobTitle());

        holder.approveButton.setOnClickListener(v -> {
            String interviewDate = getNextWeekdayAfterDays(3); // Calculate date excluding weekends

            new Thread(() -> {
                new GmailSender().sendMail(
                        app.getUserEmail(),
                        "Application Approved - Interview Scheduled",
                        "Dear " + app.getFirstName() + ",\n\n" +
                                "Congratulations! Your application for the position of " + app.getJobTitle() + " at Job Hub has been approved.\n\n" +
                                "We are pleased to invite you for an interview scheduled on **" + interviewDate + "**.\n" +
                                "Please visit our office at 31 Ritson Road, Berea, and use Gate 6 for Entrance.\n" +
                                "Further details will be provided closer to the date.\n\n" +
                                "Best regards,\nJob Hub Recruitment Team"
                );
            }).start();

            Toast.makeText(context, "Approval email sent", Toast.LENGTH_SHORT).show();
        });

        holder.declineButton.setOnClickListener(v -> {
            new Thread(() -> {
                new GmailSender().sendMail(
                        app.getUserEmail(),
                        "Application Declined",
                        "Dear " + app.getFirstName() + ",\n\nWe regret to inform you that your application for " +
                                app.getJobTitle() + " was not successful.\n\nThank you,\nHubJob Team"
                );
            }).start();
            Toast.makeText(context, "Decline email sent", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return applicationList.size();
    }

    public static class ApplicationViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, jobTitleText;
        Button approveButton, declineButton;

        public ApplicationViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.tv_applicant_name);
            jobTitleText = itemView.findViewById(R.id.tv_job_title);
            approveButton = itemView.findViewById(R.id.btn_approve);
            declineButton = itemView.findViewById(R.id.btn_decline);
        }
    }

    // Helper method to get next weekday after adding a specific number of days
    private String getNextWeekdayAfterDays(int daysAhead) {
        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("EEEE, dd MMMM yyyy");
        java.util.Calendar calendar = java.util.Calendar.getInstance();

        // Start from today, and skip weekends if needed
        int addedDays = 0;
        while (addedDays < daysAhead) {
            calendar.add(java.util.Calendar.DAY_OF_YEAR, 1);
            int dayOfWeek = calendar.get(java.util.Calendar.DAY_OF_WEEK);

            // If it's Saturday or Sunday, skip this day and continue adding days
            if (dayOfWeek != java.util.Calendar.SATURDAY && dayOfWeek != java.util.Calendar.SUNDAY) {
                addedDays++;
            }
        }

        // If the calculated date is Saturday or Sunday, move to the next Monday
        int dayOfWeek = calendar.get(java.util.Calendar.DAY_OF_WEEK);
        if (dayOfWeek == java.util.Calendar.SATURDAY) {
            calendar.add(java.util.Calendar.DAY_OF_YEAR, 2); // Skip to Monday
        } else if (dayOfWeek == java.util.Calendar.SUNDAY) {
            calendar.add(java.util.Calendar.DAY_OF_YEAR, 1); // Skip to Monday
        }

        return dateFormat.format(calendar.getTime());
    }
}
