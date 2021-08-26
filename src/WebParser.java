import java.util.List;

public abstract class WebParser {
    protected String url;
    protected DatabaseConnector connector;

    public abstract List<String> getJobUrls();
    public abstract List<Job> addParsedJobsToDatabase() throws Exception;
    public abstract void closeDatabaseConnection();
}
