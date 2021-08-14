/**
 * This class holds the job details scraped from the website
 * Note: This class is not complete yet. Changes will be made.
 */
public class Job {
    // fields
    private int id;
    private String jobTitle = "";
    private String compName = "";
    private String description = "";
    private String publishDate = "";

    // Constructors
    public Job (int id, String jobTitle, String compName, String description, String publishDate) {
        setId(id);
        setJobTitle(jobTitle);
        setCompName(compName);
        setDescription(description);
        setPublishDate(publishDate);
    }

    // methods
    public int getId() {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public String toString() {
        String output = "Job ID: " + id + "\nJob Title: " + jobTitle + "\nCompany Name: " + compName;
        output += "\nDescription: " + description + "\nPublish Date: " + publishDate;
        return output;
    }
}