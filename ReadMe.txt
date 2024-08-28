Mentrual Cycle Tracker:
The menstrual cycle tracker is a Java-based desktop application that allows users to input their menstrual cycle details (start date, cycle length, and period length) and predicts the next period start date. It uses a simple GUI (Graphical User Interface) created with Swing and stores user data in a database using H2, a lightweight Java database engine.

All '.java' and '.jar' files must be in the same directory. navigate to this directory before running the project.

To compile and run with H2 JAR:
->  javac -cp ".:h2-2.3.232.jar" *.java
->  java -cp ".:h2-2.3.232.jar" Main

DatabaseHelper.java :
- connectio to a local H2 database using the jdbc:h2 URL is established.
- a table called user_cycle_data is created if it doesn't already exist. The table stores user information, including the start date of their cycle, the cycle length, and the period length.
- The 'insertUserCycleData' method adds a new record to the user_cycle_data table with the user’s name, cycle start date, cycle length, and period length.
- The 'predictNextPeriodStart' method calculates and returns the next period start date by adding the cycle length to the current cycle start date.

MenstrualTrackerGUI.java: 
- creates a graphical user interface (GUI) for a menstrual cycle tracker using Java's Swing library to build the interface, where users can input their name, the start date of their menstrual cycle, the cycle length, and the period length.
- When the GUI starts, it initializes a connection to a database and creates a table to store the user’s menstrual cycle data if it doesn’t already exist.
- When the user clicks the "Submit" button, input is validated : checks that the cycle and period lengths are valid numbers and that the start date is in the correct format.
data is saved : stores the user’s details in the database.
next period is predicted : calculates the start date of the next menstrual period based on the cycle length and shows it in a message box.

Main.java:
When java code is run, an instance of the MTGUI class is created (constructor within that class is called), displays GUI.

