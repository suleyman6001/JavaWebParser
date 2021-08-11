import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

public class JobSearchParser extends WebParser {

    public JobSearchParser() {
        url = "https://www.jobsearch.az/";
    }

    @Override
    public void printJobs() throws Exception {
        String jobTitle;
        String compName;
        int num = 1;

        System.out.println();
        try {
            System.out.println("\n                  JOBSEARCH.AZ JOBS LIST");
            final Document doc = Jsoup.connect(url).timeout(60000).get();
            Elements jobs = doc.select("table.hotvac").select("tr");

            // Foreach loop to iterate through every IT job listed to scrape necessary information
            for (Element job : jobs ) {
                // In this part, necessary information is parsed from a list item
                jobTitle = job.select("a.hotv_text").text().replace("&nbsp;", " ");
                compName = job.select("td.hotv_text").text().replace("&nbsp;", " ")
                        .replace(jobTitle, "").replace(" new ", "");

                if (jobTitle.equals("") ) {
                    continue;
                }

                // Printing out the job details
                System.out.println("\n-----------------------------------------------------");
                System.out.println(num);
                System.out.println("Job Title: " + jobTitle);
                System.out.println("Company Name: " + compName);
                System.out.println("-----------------------------------------------------");
                num++;
            }
        }

        catch (Exception e) {
            System.out.println("Error occurred while connecting to jobsearch.az");
            e.printStackTrace();
        }
    }
}
