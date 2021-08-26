public class WebParserRunner {
    public static void main(String[] args) {
        CveeParser cvParser = new CveeParser();
        JobSearchParser jobSearchParser = new JobSearchParser();

        try {
            cvParser.addParsedJobsToDatabase();
            cvParser.closeDatabaseConnection();

            jobSearchParser.addParsedJobsToDatabase();
            jobSearchParser.closeDatabaseConnection();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}