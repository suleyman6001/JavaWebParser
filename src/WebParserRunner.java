public class WebParserRunner {
    public static void main(String[] args) {
        CveeParser cvParser = new CveeParser();
        JobSearchParser jobSearchParser = new JobSearchParser();

        try {
            cvParser.getJobs();
            jobSearchParser.getJobs();
        }
        catch (Exception e) {
            System.out.println("Error");
        }
    }
}