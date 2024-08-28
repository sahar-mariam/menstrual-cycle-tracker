import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class MenstrualTrackerGUI {
    private DatabaseHelper dbHelper;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM");

    public MenstrualTrackerGUI() {
        dbHelper = new DatabaseHelper();
        dbHelper.createTable(); // Ensure the table is created

        JFrame frame = new JFrame("Menstrual Cycle Tracker");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("User Name:");
        userLabel.setBounds(10, 20, 100, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(150, 20, 200, 25);
        panel.add(userText);

        JLabel startDateLabel = new JLabel("Cycle Start Date (dd MM):");
        startDateLabel.setBounds(10, 60, 200, 25);
        panel.add(startDateLabel);

        JTextField startDateText = new JTextField(20);
        startDateText.setBounds(220, 60, 130, 25);
        startDateText.setText("01 08"); // Example input
        panel.add(startDateText);

        JLabel cycleLengthLabel = new JLabel("Cycle Length (days):");
        cycleLengthLabel.setBounds(10, 100, 150, 25);
        panel.add(cycleLengthLabel);

        JTextField cycleLengthText = new JTextField(20);
        cycleLengthText.setBounds(220, 100, 130, 25);
        cycleLengthText.setText("28"); // Example input
        panel.add(cycleLengthText);

        JLabel periodLengthLabel = new JLabel("Period Length (days):");
        periodLengthLabel.setBounds(15, 140, 160, 20);
        panel.add(periodLengthLabel);

        JTextField periodLengthText = new JTextField(20);
        periodLengthText.setBounds(220, 140, 130, 25);
        periodLengthText.setText("5"); // Example input
        panel.add(periodLengthText);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(150, 180, 100, 25);
        panel.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = userText.getText();
                String startDate = startDateText.getText();
                int cycleLength;
                int periodLength;

                try {
                    // Parse input start date
                    LocalDate startDateParsed = LocalDate.parse(startDate + " 2024", DateTimeFormatter.ofPattern("dd MM yyyy"));

                    // Parse cycle and period lengths
                    cycleLength = Integer.parseInt(cycleLengthText.getText());
                    periodLength = Integer.parseInt(periodLengthText.getText());

                    // Validate cycle length and period length
                    if (cycleLength <= 0 || periodLength <= 0 || periodLength > cycleLength) {
                        throw new NumberFormatException("Invalid cycle or period length");
                    }

                    // Insert data into the database
                    dbHelper.insertUserCycleData(userName, startDateParsed, cycleLength, periodLength);

                    // Predict next period start date
                    LocalDate nextPeriodStart = dbHelper.predictNextPeriodStart(startDateParsed, cycleLength);
                    JOptionPane.showMessageDialog(panel, "Data Saved!\nNext Period Start: " + nextPeriodStart.format(formatter));

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panel, "Please enter valid numbers for cycle length and period length.");
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(panel, "Please enter the start date in 'dd MM' format. For example, '22 08'.");
                }
            }
        });
    }

    public static void main(String[] args) {
        new MenstrualTrackerGUI();
    }
}
