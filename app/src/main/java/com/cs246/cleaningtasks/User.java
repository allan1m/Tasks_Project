package com.cs246.cleaningtasks;

public class User {
    private String name;
    private Long id;
    private String occupation;
    private String company;

    public User() { String employeeName = name; Long employeeNum = id; String employeeOccupation = occupation; String companyName = company; }

    //Getter and Setter for name
    public String getName() { return name; }
    public void setName(String employeeName) {
        this.name = employeeName;
    }

    //Getter and Setter for employee ID
    public Long getId() { return id; }
    public void setId(Long employeeNum) {
        this.id = employeeNum;
    }

    //Getter and Setter for occupation
    public String getOccupation() { return occupation; }
    public void setOccupation(String employeeOccupation) { this.occupation = employeeOccupation; }

    //Getter and Setter for company name
    public String getCompany() { return company; }
    public void setCompany(String companyName) { this.company = companyName; }
}
