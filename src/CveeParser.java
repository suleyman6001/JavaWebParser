import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import java.util.*;

import java.util.List;

public class CveeParser extends WebParser {

    public CveeParser() {
        url = "https://www.cv.ee/en/search?limit=20&offset=0&categories%5B0%5D" +
                "=INFORMATION_TECHNOLOGY&isHourlySalary=false&isRemoteWork=false";
    }

    @Override
    /**
     * This method returns all the jobs scraped from cv.ee/en
     */
    public List<Job> getJobs() throws Exception {
        ArrayList<Job> jobList = new ArrayList<Job>();
        String jobTitle;
        String compName;
        String city;
        String jobCountHeader;
        int jobCount;
        int num = 1;

        try {
            url = url.replace("limit=20", "limit=10000");
            final Document doc = Jsoup.connect(url).timeout(60000).get();

            if (doc == null ) {
                return null;
            }

            Elements jobs = doc.select("ul.jsx-3347674222.jsx-4142501799.vacancies-list")
                                .select("li.jsx-3347674222.jsx-4142501799.vacancies-list__item.false");

            // Scraping the number of listed jobs from the website
            jobCountHeader = doc.select("span.jsx-3347674222.jsx-4142501799").text()
                    .replaceAll("[^0-9]","");
            jobCount = Integer.parseInt(jobCountHeader);
            System.out.println("\n\nCV.ee/en IT Jobs List\nTotal IT job count: " + jobCount);

            // Foreach loop to iterate through every IT job listed to scrape necessary information
            for (Element job : jobs ) { //TODO Improve exception handling
                System.out.println("\n-----------------------------------------------------");

                // In this part, necessary information is parsed from a list item
                jobTitle = job.select("span.jsx-1471379408.vacancy-item__title").text();
                compName = job.select("div.jsx-1471379408.vacancy-item__info-main").
                        select("a").text();
                city = job.select("span.jsx-1471379408.vacancy-item__locations").text();

                // Printing out the job details
                System.out.println(num);
                Job curJob = new Job(5, jobTitle, compName, "desc", "publish");
                System.out.println(curJob);
                /*System.out.println("Job Title: " + jobTitle);
                System.out.println("Company Name: " + compName);
                System.out.println("City: " + city);*/
                System.out.println("-----------------------------------------------------");
                num++;
            }
        }

        catch (Exception e) {
            System.out.println("Error occurred while connecting to cv.ee/en");
            e.printStackTrace();
        }
        return jobList;
    }
}

