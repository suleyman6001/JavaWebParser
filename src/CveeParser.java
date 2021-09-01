import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import java.sql.Date;
import java.util.*;

public class CveeParser extends WebParser {
    private final static String SITE_NAME = Job.SITE_NAMES.cvee.name();

    public CveeParser() {
        url = "https://www.cv.ee/en/search?limit=20&offset=0&categories%5B0%5D" +
                "=INFORMATION_TECHNOLOGY&isHourlySalary=false&isRemoteWork=false";
        connector = new DatabaseConnector();
    }

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
        Document jobDocument;
        int id;
        int num = 1;
        String jobTitle;
        String compName;
        String description;
        Date publishDate;
        List<Job> jobList = new ArrayList<>();
        List<String> urlList = getJobUrls();


        for (String curUrl:urlList ) {
            try {
                jobDocument = Jsoup.connect(curUrl).timeout(200000).get();

                // scraping the necessary data
                jobTitle = jobDocument.select("div.jsx-1778450779.vacancy-content-mobile-header__position").
                        text();
                compName = jobDocument.select("span.jsx-1778450779.vacancy-content-header__employer").text();
                curUrl = curUrl.replace(jobTitle, "").replace(compName, "");
                id = Integer.parseInt(curUrl.replaceAll("[^0-9]", ""));
                description = null;
                publishDate = null;

                // Creating a job object with necessary info and adding it to the job list
                Job job = new Job(SITE_NAME, id, jobTitle, compName, description, publishDate);
                System.out.println(num);
                System.out.println(job);
                System.out.println();
                num++;
                connector.insertJob(job);
                jobList.add(job);
            }
            catch (Exception e) {
                //System.out.println("Error occurred while parsing the job in cv.ee/en");
                //e.printStackTrace();
            }
        }
        return jobList;
    }
}