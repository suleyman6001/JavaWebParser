import java.sql.*;
import java.sql.Date;

public class DatabaseConnector {
    private final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "showmustgoon";
    private Connection connection;
    private PreparedStatement statement;

    public DatabaseConnector() {
        try {
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        }
        catch (Exception e) {
            System.out.println("Connection could not be established");
        }
    }

    /**
     * This method inserts a given job to the database
     * @param job - job to be inserted
     * @return true if insertion is successful, false otherwise
     */
    public boolean insertJob(Job job) {
        String insertQuery = "INSERT INTO job_table VALUES(DEFAULT, ?, ?, ?, ?, ?, ?);";
        boolean isSuccessful = false;

        if (!hasDuplicate(job)) {
            try {
                statement = connection.prepareStatement(insertQuery);
                int jobId = job.getId();
                String jobTitle = job.getJobTitle();
                String companyName = job.getCompanyName();
                String description = job.getDescription();
                Date publishDate = job.getPublishDate();

                if (statement != null) {
                    statement.setInt(1, jobId );
                    statement.setInt(2, job.getSiteId());
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
        }
        return isSuccessful;
    }

    /**
     * This method checks whether the job is present in the database or not
     * @param job The job to be inserted
     * @return true if the job is present in the database and false otherwise
     */
    private boolean hasDuplicate(Job job) {
        PreparedStatement stmt = null;
        ResultSet queryResults = null;
        try {
            String selectQuery = "SELECT * FROM job_table WHERE job_id = ? AND site_id = ?;";
            stmt = connection.prepareStatement(selectQuery);
            stmt.setInt(1, job.getSiteId());
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
        }
        finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            }
            catch (Exception e) {
                System.out.println("Exception occurred in hasDuplicate() method");
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * This method closes database resources
     */
    public void closeResources() {
        // closing the statement
        if (statement != null) {
            try {
                statement.close();
                System.out.println("Successfully closed statement");
            }
            catch (SQLException e) {}
            finally {
                try {
                    if (!statement.isClosed()) {
                        statement.close();
                    }
                }
                catch (Exception e) {}
            }
        }

        // closing the connection
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Successfully closed connection");
            }
            catch (SQLException e) {}
            finally {
                try {
                    if (!connection.isClosed()) {
                        connection.close();
                    }
                }
                catch (Exception e) {}
            }
        }
    }
}