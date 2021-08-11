/**
 * This class holds the job details scraped from the website
 * Note: This class is not complete yet. Changes will be made.
 */
public class Job {
    // fields
    private String jobTitle = "";
    private String compName = "";
    private String location = "";
    private String description = "";
    private int salary;

    // Constructors
    public Job(String jobTitle, String compName, String location, String description, int salary) {
        setJobTitle(jobTitle);
        setCompName(compName);
        setLocation(location);
        setDescription(description);
        setSalary(salary);
    }

    // methods
    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        if (salary > -1) {
            this.salary = salary;
        }
        else {
            salary = 0;
        }
    }


}
