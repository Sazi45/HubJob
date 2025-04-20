package com.example.hubjob;

public class JobPreferenceModel {
    private String userEmail;
    private String jobTitle;
    private String description;
    private String location;
    private String salary;
    private String experience;
    private String skills;
    private String category;
    private String postedBy;
    private long savedAt;

    public JobPreferenceModel() {
        // Firestore needs empty constructor
    }

    public JobPreferenceModel(String userEmail, String jobTitle, String description, String location,
                              String salary, String experience, String skills, String category,
                              String postedBy, long savedAt) {
        this.userEmail = userEmail;
        this.jobTitle = jobTitle;
        this.description = description;
        this.location = location;
        this.salary = salary;
        this.experience = experience;
        this.skills = skills;
        this.category = category;
        this.postedBy = postedBy;
        this.savedAt = savedAt;
    }

    // Getters and Setters
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getSalary() { return salary; }
    public void setSalary(String salary) { this.salary = salary; }

    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }

    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getPostedBy() { return postedBy; }
    public void setPostedBy(String postedBy) { this.postedBy = postedBy; }

    public long getSavedAt() { return savedAt; }
    public void setSavedAt(long savedAt) { this.savedAt = savedAt; }
}
