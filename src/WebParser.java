import java.util.List;

public abstract class WebParser {
    protected String url;
    public abstract List<Job> getJobs() throws Exception;
}
