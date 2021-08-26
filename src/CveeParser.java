import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class CveeParser extends WebParser {
    final static int SITE_ID = 1;

    public CveeParser() {
        url = "https://www.cv.ee/en/search?limit=20&offset=0&categories%5B0%5D" +
                "=INFORMATION_TECHNOLOGY&isHourlySalary=false&isRemoteWork=false";
        connector = new DatabaseConnector();
    }

    /*public List<Job> getJobs() throws Exception {
        int id;
        String jobTitle;
        String compName;
        String description;
        Date publishDate;
        String jobUrl;
        ArrayList<String> urlList = new ArrayList<>();
        ArrayList<Job> jobList = new ArrayList<>();
        int num = 1;

        System.out.println();
        try {
            url = url.replace("limit=20", "limit=10000");
            final Document doc = Jsoup.connect(url).timeout(200000).get();
            Document jobDocument;

            Elements elems = doc.select("a.jsx-1471379408.vacancy-item__logo.hide-mobile");

            // Foreach loop to iterate through every IT job listed to scrape the job urls
            for (Element elem : elems ) {
                if (elem != null) {
                    jobUrl = elem.attr("abs:href");

                    if (jobUrl.equals("") ) {
                        continue;
                    }
                    urlList.add(jobUrl);
                }
            }

            // Now we scrape individual job details
            for (String curUrl:urlList ) {
                jobDocument = Jsoup.connect(curUrl).timeout(200000).get();

                // scraping the necessary data
                jobTitle = jobDocument.select("div.jsx-1778450779.vacancy-content-mobile-header__position").
                                        text();
                compName = jobDocument.select("span.jsx-1778450779.vacancy-content-header__employer").text();
                curUrl = curUrl.replace(jobTitle, "").replace(compName, "");
                id = Integer.parseInt(curUrl.replaceAll("[^0-9]",""));
                description = "";
                publishDate = null;

                // Creating a job object with necessary info and adding it to the job list
                Job job = new Job(SITE_ID, id, jobTitle, compName, description, publishDate);
                System.out.println(num);
                System.out.println(job);
                System.out.println();
                num++;
                jobList.add(job);
            }
        }
        catch (Exception e) {
            System.out.println("Error occurred while connecting to jobsearch.az");
            e.printStackTrace();
        }
        addToDatabase(jobList);
        return jobList;
    }*/

    /*private void addToDatabase(List<Job> jobs) {
        final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
        final String USERNAME = "postgres";
        final String PASSWORD = "showmustgoon";
        int jobId;
        String jobTitle;
        String companyName;
        String description;
        Date publishDate;
        Connection connection;
        String sqlQuery;
        PreparedStatement stmt = null;

        // Connecting to the database
        try {
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            if (connection != null)  {
                System.out.println("Successfully connected to the database");
            }
        }
        catch (SQLException e) {
            System.out.println("Could not connect to the database. Please try again.");
            return;
        }

        // inserting jobs to the database
        try {
            sqlQuery = "INSERT INTO Cvee_Job_Table VALUES(DEFAULT, ?, ?, ?, ?, ?, ?)";
            if (connection != null) {
                stmt = connection.prepareStatement(sqlQuery);
            }

            for (Job job : jobs) {
                // inserting into cv.ee table
                try {
                    jobId = job.getId();
                    jobTitle = job.getJobTitle();
                    companyName = job.getCompanyName();
                    description = job.getDescription();
                    publishDate = job.getPublishDate();

                    if (stmt != null) {
                        stmt.setInt(1, SITE_ID);
                        stmt.setInt(2, jobId);
                        stmt.setString(3, jobTitle);
                        stmt.setString(4, companyName);
                        stmt.setString(5, description);
                        stmt.setDate(6, publishDate);
                        stmt.executeUpdate();
                    }
                    System.out.println("Insertion was successful");
                }
                catch (Exception e) {
                    System.out.println("Duplicate insertion not allowed");
                }
            }
        }
        catch (SQLException e) {
            System.out.println("Query could not be executed");
            e.printStackTrace();
        }

        // close the database connection
        try {
            if (stmt != null) {
                stmt.close();
                System.out.println("\nStatement closed successfully");
            }

            if (connection != null ) {
                connection.close();
                System.out.println("\nConnection closed successfully");
            }
        }
        catch (SQLException e) {
            System.out.println("\nConnection or Statement does not exist. It could not be closed");
        }
    }*/

    @Override
    public List<String> getJobUrls() {
        String jobUrl;
        ArrayList<String> urlList = new ArrayList<>();

        System.out.println();
        try {
            url = url.replace("limit=20", "limit=10000");
            final Document doc = Jsoup.connect(url).timeout(200000).get();

            Elements elems = doc.select("a.jsx-1471379408.vacancy-item__logo.hide-mobile");

            // Foreach loop to iterate through every IT job listed to scrape the job urls
            for (Element elem : elems) {
                if (elem != null) {
                    jobUrl = elem.attr("abs:href");

                    if (jobUrl.equals("")) {
                        continue;
                    }
                    urlList.add(jobUrl);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return urlList;
    }

    @Override
    public List<Job> addParsedJobsToDatabase() throws Exception {
        Document jobDocument = null;
        int id;
        int num = 1;
        String jobTitle;
        String compName;
        String description;
        Date publishDate;
        List<Job> jobList = new ArrayList<>();
        List<String> urlList = getJobUrls();

        try {
            for (String curUrl:urlList ) {
                jobDocument = Jsoup.connect(curUrl).timeout(200000).get();

                // scraping the necessary data
                jobTitle = jobDocument.select("div.jsx-1778450779.vacancy-content-mobile-header__position").
                                        text();
                compName = jobDocument.select("span.jsx-1778450779.vacancy-content-header__employer").text();
                curUrl = curUrl.replace(jobTitle, "").replace(compName, "");
                id = Integer.parseInt(curUrl.replaceAll("[^0-9]",""));
                description = null;
                publishDate = null;

                // Creating a job object with necessary info and adding it to the job list
                Job job = new Job(SITE_ID, id, jobTitle, compName, description, publishDate);
                System.out.println(num);
                System.out.println(job);
                System.out.println();
                num++;
                connector.insertJob(job);
                jobList.add(job);
            }
        }
        catch (Exception e) {
            System.out.println("Error occurred while connecting to cv.ee/en");
            e.printStackTrace();
        }
        return jobList;
    }

    @Override
    public void closeDatabaseConnection() {
        connector.closeResources();
    }
}