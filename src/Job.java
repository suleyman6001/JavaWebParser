import java.sql.Date;

public class Job {
    private int siteId;
    private int id;
    private String jobTitle;
    private String companyName;
    private String description;
    private Date publishDate;

    public Job(int siteId, int id, String jobTitle, String companyName,
               String description, Date publishDate) {
        this.siteId = siteId;
        this.id = id;
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.description = description;
        this.publishDate = publishDate;
    }

    public int getSiteId() {
        return siteId;
    }

    public int getId() {
        return id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getDescription() {
        return description;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    @Override
    public String toString() {
        String output = "Job ID: " + id + "\nJob Title: " + jobTitle + "\nCompany Name: " + companyName;
        output += "\nDescription: " ;
        if (description != null) {
            output += description;
        }
        output += "\nPublish Date: ";
        if (publishDate != null) {
            output += publishDate.toString();
        }
        return output;
    }
}