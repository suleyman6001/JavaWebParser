import java.sql.*;
import java.sql.Date;

public class DatabaseConnector {
    private final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "showmustgoon";

    public DatabaseConnector() {}

    /**
     * This method inserts a given job to the database
     * @param job - job to be inserted
     * @return true if insertion is successful, false otherwise
     */
    public boolean insertJob(Job job) {
        String insertQuery = "INSERT INTO job VALUES(DEFAULT, ?, ?, ?, ?, ?, ?);";
        boolean isSuccessful = false;
        Date publishDate = null;
        Connection connection = null;
        PreparedStatement statement = null;

        if (!hasDuplicate(job)) {
            try {
                connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                statement = connection.prepareStatement(insertQuery);

                int jobId = job.getId();
                String jobTitle = job.getJobTitle();
                String companyName = job.getCompanyName();
                String description = job.getDescription();
                java.util.Date date = job.getPublishDate();

                if (date != null) {
                    publishDate = new Date(date.getTime());
                }

                if (statement != null) {
                    statement.setInt(1, jobId );
                    statement.setString(2, job.getSiteName());
                    statement.setString(3, jobTitle);
                    statement.setString(4, companyName);
                    statement.setString(5, description);
                    statement.setDate(6, publishDate);
                    statement.executeUpdate();
                    isSuccessful = true;
                }
            }
            catch (SQLException e) {
                System.out.println("Exception occurred while insertJob() method");
                e.printStackTrace();
                isSuccessful = false;
            }
            finally {
                // closing the statement
                try {
                    if (statement != null) {
                        statement.close();
                    }
                }
                catch (Exception e) {
                    System.out.println("Could not close the statement");
                    e.printStackTrace();
                }

                // closing the connection
                try {
                    if (connection != null) {
                        connection.close();
                    }
                }
                catch (Exception e) {
                    System.out.println("Could not close the connection");
                    e.printStackTrace();
                }
            }
        }
        return isSuccessful;
    }

    /**
     * This method checks whether the job is present in the database or not
     * @param job The job to be inserted
     * @return true if the job is present in the database and false otherwise
     */
    private boolean hasDuplicate(Job job) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet queryResults;

        try {
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        }
        catch (Exception e) {
            System.out.println("Connection could not be opened");
            e.printStackTrace();
        }
        try {
            String selectQuery = "SELECT * FROM job WHERE site_name = ? AND job_id = ?;";
            stmt = connection.prepareStatement(selectQuery);
            stmt.setString(1, job.getSiteName());
            stmt.setInt(2, job.getId());
            queryResults = stmt.executeQuery();

            // if ResultSet has element(s), this job is a duplicate
            if (queryResults.next()) {
                System.out.println("No duplicates allowed\n");
                return true;
            }
        }

        catch (Exception e) {
            System.out.println("Exception occurred");
            e.printStackTrace();
        }

        finally {
            // closing the statement
            try {
                if (stmt != null) {
                    stmt.close();
                }
            }
            catch (Exception e) {
                System.out.println("Could not close the statement");
                e.printStackTrace();
            }

            // closing the connection
            try {
                if (connection != null) {
                    connection.close();
                }
            }
            catch (Exception e) {
                System.out.println("Could not close the connection");
                e.printStackTrace();
            }
        }
        return false;
    }
}