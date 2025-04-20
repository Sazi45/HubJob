package com.example.hubjob;

public class JobApplicationModel {

    private String userEmail;
    private String firstName;
    private String lastName;
    private String gender;
    private String highestEducation;
    private String race;
    private String skills;
    private String city;
    private String postalCode;
    private String jobTitle;
    private String jobDescription;
    private String jobLocation;
    private String jobSalary;
    private String jobExperience;
    private String jobCategory;
    private String jobPostedBy;
    private long timestamp;

    // Default constructor for Firestore
    public JobApplicationModel() {}

    // Constructor with all fields
    public JobApplicationModel(String userEmail, String firstName, String lastName, String gender,
                               String highestEducation, String race, String skills, String city, String postalCode,
                               String jobTitle, String jobDescription, String jobLocation, String jobSalary,
                               String jobExperience, String jobCategory, String jobPostedBy, long timestamp) {
        this.userEmail = userEmail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.highestEducation = highestEducation;
        this.race = race;
        this.skills = skills;
        this.city = city;
        this.postalCode = postalCode;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.jobLocation = jobLocation;
        this.jobSalary = jobSalary;
        this.jobExperience = jobExperience;
        this.jobCategory = jobCategory;
        this.jobPostedBy = jobPostedBy;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHighestEducation() {
        return highestEducation;
    }

    public void setHighestEducation(String highestEducation) {
        this.highestEducation = highestEducation;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(String jobLocation) {
        this.jobLocation = jobLocation;
    }

    public String getJobSalary() {
        return jobSalary;
    }

    public void setJobSalary(String jobSalary) {
        this.jobSalary = jobSalary;
    }

    public String getJobExperience() {
        return jobExperience;
    }

    public void setJobExperience(String jobExperience) {
        this.jobExperience = jobExperience;
    }

    public String getJobCategory() {
        return jobCategory;
    }

    public void setJobCategory(String jobCategory) {
        this.jobCategory = jobCategory;
    }

    public String getJobPostedBy() {
        return jobPostedBy;
    }

    public void setJobPostedBy(String jobPostedBy) {
        this.jobPostedBy = jobPostedBy;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
