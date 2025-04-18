package com.example.hubjob;
public class JobSeekerProfileModel {
    private String firstName;
    private String lastName;
    private String city;
    private String suburb;
    private String postalCode;
    private String highestEducation;
    private String gender;
    private String race;
    private String skills;

    // Constructors
    public JobSeekerProfileModel() {}

    public JobSeekerProfileModel(String firstName, String lastName, String city, String suburb,
                                 String postalCode, String highestEducation, String gender,
                                 String race, String skills) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.suburb = suburb;
        this.postalCode = postalCode;
        this.highestEducation = highestEducation;
        this.gender = gender;
        this.race = race;
        this.skills = skills;
    }

    // Getters and setters
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getHighestEducation() {
        return highestEducation;
    }

    public void setHighestEducation(String highestEducation) {
        this.highestEducation = highestEducation;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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
}

