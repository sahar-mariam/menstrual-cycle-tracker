import java.time.LocalDate;
//import java.time.Period;
//import java.time.format.DateTimeFormatter;
import java.sql.*;

public class DatabaseHelper {
    private static final String URL = "jdbc:h2:./menstrual_tracker";

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS user_cycle_data (" +
                     "id INT AUTO_INCREMENT PRIMARY KEY, " +
                     "user_name VARCHAR(255), " +
                     "cycle_start_date DATE, " +
                     "cycle_length INT, " +
                     "period_length INT)";
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertUserCycleData(String userName, LocalDate startDate, int cycleLength, int periodLength) {
        String sql = "INSERT INTO user_cycle_data(user_name, cycle_start_date, cycle_length, period_length) VALUES (?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userName);
            pstmt.setDate(2, Date.valueOf(startDate));
            pstmt.setInt(3, cycleLength);
            pstmt.setInt(4, periodLength);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public LocalDate predictNextPeriodStart(LocalDate startDate, int cycleLength) {
        // Calculate the next period start date based on the cycle length
        return startDate.plusDays(cycleLength);
    }
}
