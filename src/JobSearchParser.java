import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import java.util.*;

public class JobSearchParser extends WebParser {

    public JobSearchParser() {
        url = "https://www.jobsearch.az/";
    }

    @Override
    /**
     * This method returns all the jobs scraped from jobsearch.az
     */
    public List<Job> getJobs() throws Exception {
        int id;
        String jobTitle = "";
        String compName = "";
        String description = "";
        String publishDate = "";
        String jobUrl = "";
        ArrayList<String> urlList = new ArrayList<String>();
        ArrayList<Job> jobList = new ArrayList<Job>();
        int num = 1;

        System.out.println();
        try {
            final Document doc = Jsoup.connect(url).timeout(60000).get();
            Document jobDocument;

            if (doc == null ) {
                return null;
            }
            Elements elems = doc.select("a.hotv_text");

            // Foreach loop to iterate through every IT job listed to scrape the urls
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
            Elements jobDetails;
            for (int i = 0; i < urlList.size(); i++ ) {
                jobUrl = urlList.get(i);
                jobDocument = Jsoup.connect(jobUrl).timeout(60000).get();

                if (jobDocument == null) {
                    return null;
                }
                jobDetails = jobDocument.select("td.text");

                // scraping the necessary data
                id = Integer.parseInt(jobUrl.replaceAll("[^0-9]",""));
                jobTitle = jobDetails.get(0).text().replace("JOB TITLE: ", "");  // 0
                compName = jobDetails.get(1).text().replace("EMPLOYER: ", "");   // 1
                description = jobDetails.get(6).text().replace("EMPLOYER: ", "");  // 6
                publishDate = jobDetails.get(3).text().replace("PUBLISHED: ", "");   // 2

                // Creating a job object with necessary info and adding it to the job list
                Job job = new Job(id, jobTitle, compName, description, publishDate);
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
        return jobList;
    }
}