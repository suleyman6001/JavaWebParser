public class Job {
    enum SITE_NAMES {
        jobsearch, cvee
    }
    private String siteName;
    private int id;
    private String jobTitle;
    private String companyName;
    private String description;
    private java.util.Date publishDate;

    public Job(String siteName, int id, String jobTitle, String companyName,
               String description, java.util.Date publishDate) {
        this.siteName = siteName;
        this.id = id;
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.description = description;
        this.publishDate = publishDate;
    }

    public String getSiteName() {
        return siteName;
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

    public java.util.Date getPublishDate() {
        return publishDate;
    }

    @Override
    public String toString() {
        return ("Site Name: " + siteName + "\nJob ID: " + id + "\nJob Title: " + jobTitle +
                "\nCompany Name: " + companyName + "\nDescription: " + description +
                "\nPublish Date: " + publishDate);
    }
}