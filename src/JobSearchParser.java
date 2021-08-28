import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import java.sql.Date;
import java.util.*;

public class JobSearchParser extends WebParser {

    final static int SITE_ID = 2;

    public JobSearchParser() {
        url = "https://www.jobsearch.az/";
        connector = new DatabaseConnector();
    }


    @Override
    public List<String> getJobUrls() {
        String jobUrl;
        ArrayList<String> urlList = new ArrayList<>();

        System.out.println();
        try {
            final Document doc = Jsoup.connect(url).timeout(200000).get();
            Elements elems = doc.select("a.hotv_text");

            // Foreach loop to iterate through every IT job listed to scrape the urls
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
            System.out.println("Error occurred while connecting to jobsearch.az");
            e.printStackTrace();
        }
        return urlList;
    }

    @Override
    public List<Job> addParsedJobsToDatabase() throws Exception {
        Document jobDocument;
        int id;
        int num = 1;
        String jobTitle;
        String compName;
        String description;
        Date publishDate;
        List<Job> jobList = new ArrayList<>();
        List<String> urlList = getJobUrls();

        try {
            Elements jobDetails;
            for (String curUrl:urlList ) {
                jobDocument = Jsoup.connect(curUrl).timeout(200000).get();

                jobDetails = jobDocument.select("td.text");

                // scraping the necessary data
                id = Integer.parseInt(curUrl.replaceAll("[^0-9]",""));
                jobTitle = jobDetails.get(0).text().replace("JOB TITLE: ", "");
                compName = jobDetails.get(1).text().replace("EMPLOYER: ", "");
                description = jobDetails.get(6).text().replace("EMPLOYER: ", "");
                publishDate = Date.valueOf(jobDetails.get(3).text().replace("PUBLISHED: ", ""));

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
            System.out.println("Error occurred while connecting to jobsearch.az");
            e.printStackTrace();
        }
        return jobList;
    }

    @Override
    public void closeDatabaseConnection() {
        connector.closeResources();
    }
}