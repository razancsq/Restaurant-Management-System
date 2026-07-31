
package aldiwan;

public class SessionPerson {
// Utility class to store global user data
    private static String currentPersonName;
    private static int currentPersonID;
    private static int tableRowsCount;

    // setter method
    public static void setCurrentPersonName(String username) {
        currentPersonName = username;
    }
    // getter method
    public static String getCurrentPersonName() {
        return currentPersonName;
    }

    // setter method
    public static void setCurrentPersonID(int userID) {
        currentPersonID = userID;
    }
    // getter method
    public static int getCurrentPersonID() {
        return currentPersonID;
    }
    // setter method
    public static void setTableRowsCount(int count) {
        tableRowsCount = count;
    }
    // getter method
    public static int getTableRowsCount() {
        return tableRowsCount;
    }
    
}// SessionPerson {}
