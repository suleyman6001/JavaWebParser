import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

public class WebParserRunner {
    public static void main(String[] args) {
        CveeParser cvParser = new CveeParser();
        JobSearchParser jobSearchParser = new JobSearchParser();

        try {
            cvParser.printJobs();
            jobSearchParser.printJobs();
        }
        catch (Exception e) {
            System.out.println("Error");
        }
    }
}