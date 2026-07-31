
package aldiwan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class Dash2 extends javax.swing.JFrame {
    /**
     * Creates new form Dash2
     * Pass the Full name and Role from the database to the Class
     * @param fullname
     * @param role
     */
    public Dash2(String fullname, String role) {

        Font font16Bold = new Font("Dialog", Font.BOLD, 16);
        Color fontColor = new Color(89, 44, 73);

        // Create the JFrame
        JFrame frame = new JFrame("ALDIWAN Restaurant Dashboard");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center to frame
        frame.setExtendedState(Dash2.MAXIMIZED_BOTH); // Maximize the window
        // Set the default size of the window
        frame.setSize(900, 700); // the window restore state Width & Height

        frame.setLayout(new BorderLayout());

        // Top Panel (Title)
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.decode("#400601"));
        topPanel.setPreferredSize(new Dimension(frame.getWidth(), 50));
        JLabel titleLabel = new JLabel("", SwingConstants.CENTER);
        titleLabel.setText("Welcome: " + fullname);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        // Left Navigation Panel
        JPanel leftPanel = new JPanel();
        // GridLayout(Rows, Columns, H Space, V space)
        //  leftPanel.setLayout(new GridLayout(4, 1, 0, 10));
        leftPanel.setLayout(new GridLayout(12, 1, 0, 20));
        leftPanel.setBackground(Color.decode("#F2DCEB"));
        leftPanel.setPreferredSize(new Dimension(200, frame.getHeight()));

        JLabel gapLabel = new JLabel("", SwingConstants.CENTER);
        gapLabel.setOpaque(false);
        
        // Define the top and bottom borders
        Border topBottomBorder = BorderFactory.createMatteBorder(
                1, // Top border thickness (pixels)
                0, // Left border thickness (pixels)
                1, // Bottom border thickness (pixels)
                0, // Right border thickness (pixels)
                Color.decode("#400601")); // Border color
                
        JLabel lblDashboardTitle = new JLabel("Dashboard", SwingConstants.CENTER);
        lblDashboardTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblDashboardTitle.setForeground(Color.decode("#400601"));
        lblDashboardTitle.setOpaque(true);
        lblDashboardTitle.setBackground(Color.decode("#F2D7B6"));
        lblDashboardTitle.setBorder(topBottomBorder);

        JLabel lbladdUser = new JLabel("Add User", SwingConstants.CENTER);
        lbladdUser.setFont(new Font("Arial", Font.BOLD, 18));
        lbladdUser.setForeground(Color.decode("#400601"));
        lbladdUser.setOpaque(true);
        lbladdUser.setBackground(Color.decode("#F2CAB3"));
        lbladdUser.setBorder(topBottomBorder);

        JLabel lblUupdateUser = new JLabel("Update/ Delete User", SwingConstants.CENTER);
        lblUupdateUser.setFont(new Font("Arial", Font.BOLD, 18));
        lblUupdateUser.setForeground(Color.decode("#400601"));
        lblUupdateUser.setOpaque(true);
        lblUupdateUser.setBackground(Color.decode("#D9C5D2"));
        lblUupdateUser.setBorder(topBottomBorder);

        JLabel exitLabel = new JLabel("Exit", SwingConstants.CENTER);
        exitLabel.setFont(new Font("Arial", Font.BOLD, 18));
        exitLabel.setForeground(Color.decode("#400601"));
        exitLabel.setOpaque(true);
        exitLabel.setBackground(Color.decode("#F294AD"));
        exitLabel.setBorder(topBottomBorder);

        leftPanel.add(gapLabel);
        leftPanel.add(lblDashboardTitle);
        leftPanel.add(lbladdUser);
        leftPanel.add(lblUupdateUser);
        leftPanel.add(exitLabel);
        frame.add(leftPanel, BorderLayout.WEST);

        // Content Area (JTabbedPane) define properties
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Custom UI to set the selected or NOT selected tabs background color 
            tabbedPane.setUI(new BasicTabbedPaneUI() {
                @Override
                protected void paintTabBackground(
                        Graphics g, 
                        int tabPlacement, 
                        int tabIndex, 
                        int x, int y, 
                        int w, int h, 
                        boolean isSelected) {
                    if (isSelected) {
                        g.setColor(Color.decode("#400601")); // Set background color for selected tab
                        tabbedPane.setForeground(Color.decode("#F2DCEB"));
                    } else {
                        g.setColor(Color.decode("#F2CAB3")); // Set background color for unselected tabs
                        tabbedPane.setForeground(Color.decode("#400601"));
                    }
                    g.fillRect(x, y, w, h);
                }
            });

        // First Tab contents: Numeric Values and Charts
        JPanel firstTab = new JPanel(new BorderLayout());
        firstTab.setBackground(Color.decode("#F25430"));
        
        // calculate and retreave the statistics from the database tabels
        // to fill the 4 boxes on top
        int userCount = 0;
        int mgrCount = 0;
        int orderCount = 0;
        int cashCount = 0;
        int creditCount = 0;
        int takeawayCount = 0;
        int menuItemsCount = 0;
        
        try{ 
            Connection conn = DBConnection.connection;
            // this sql statement counts the number of rows in the table
            // the alias in the resultSet will be used to get the values
            String sql_userCount = "SELECT COUNT(*) AS userCount FROM person WHERE role = 'user'";
            String sql_mgrCount = "SELECT COUNT(*) AS mgrCount FROM person WHERE role = 'manager'";
            String sql_order_Count = "SELECT COUNT(*) AS orderCount FROM orders";
            String sql_cash_Count = "SELECT COUNT(*) AS cashCount FROM orders WHERE type = 'Cash'";
            String sql_credit_Count = "SELECT COUNT(*) AS creditCount FROM orders WHERE type = 'Credit'";
            String sql_takeaway_Count = "SELECT COUNT(*) AS takeawayCount FROM orders WHERE type = 'Takeaway'";
            String sql_menuItemsCount = "SELECT COUNT(*) AS menuItemsCount FROM menu_items";
            
            PreparedStatement preparedStatement = conn.prepareStatement (sql_userCount);
            ResultSet userCountResultSet = preparedStatement.executeQuery();
            if (userCountResultSet.next()){
                userCount = userCountResultSet.getInt("userCount");
                // this also works, since the result is noe column, 
                // and no need to use the Alias "AS userCount"
                // userCount = userCountResultSet.getInt(1);
            }
            
            PreparedStatement mgrCountPS = conn.prepareStatement (sql_mgrCount);
            ResultSet mgrCountResultSet = mgrCountPS.executeQuery();
            if (mgrCountResultSet.next()){
                mgrCount = mgrCountResultSet.getInt("mgrCount");
            }
            
            PreparedStatement orderCountPS = conn.prepareStatement (sql_order_Count);
            ResultSet orderCountResultSet = orderCountPS.executeQuery();
            if (orderCountResultSet.next()){
                orderCount = orderCountResultSet.getInt("orderCount");
            }
            
            PreparedStatement cashCountPS = conn.prepareStatement (sql_cash_Count);
            ResultSet cashCountResultSet = cashCountPS.executeQuery();
            if (cashCountResultSet.next()){
                cashCount = cashCountResultSet.getInt("cashCount");
            }
            
            PreparedStatement creditCountPS = conn.prepareStatement (sql_credit_Count);
            ResultSet creditCountResultSet = creditCountPS.executeQuery();
            if (creditCountResultSet.next()){
                creditCount = creditCountResultSet.getInt("creditCount"); 
            }
            
            PreparedStatement takeaway_orderCountPS = conn.prepareStatement (sql_takeaway_Count);
            ResultSet takeawayCountResultSet = takeaway_orderCountPS.executeQuery();
            if (takeawayCountResultSet.next()){
                takeawayCount = takeawayCountResultSet.getInt("takeawayCount"); 
            }
            
            PreparedStatement menuItemsCountPS = conn.prepareStatement (sql_menuItemsCount);
            ResultSet menuItemsCountResultSet = menuItemsCountPS.executeQuery();
            if (menuItemsCountResultSet.next()){
                menuItemsCount = menuItemsCountResultSet.getInt("menuItemsCount");
            }
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null,
                    """
                    Failed Connect
                    \nReason:\n Dash2 Class \n""" + e.getMessage(),
                    "Connection Failed",
                    JOptionPane.ERROR_MESSAGE);
        } // try catch block

        // use these values to Set padding using an EmptyBorder
        int top = 10;    // Top padding
        int left = 0;   // Left padding
        int bottom = 10; // Bottom padding
        int right = 0;  // Right padding
        
        // Numeric Value Boxes on top
        //                          GridLayout(Rows, Columns, H Space, V space)
        JPanel numericBoxesPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        numericBoxesPanel.setBackground(Color.decode("#F2DCEB"));
        JLabel box1Label = new JLabel("", SwingConstants.CENTER);
        
        box1Label.setOpaque(true);
        box1Label.setForeground(Color.decode("#400601"));
        box1Label.setBackground(Color.decode("#F294AD"));
        box1Label.setText("Employees: " + userCount);
        box1Label.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
        
        JLabel box2Label = new JLabel("Managers: ", SwingConstants.CENTER);
        box2Label.setOpaque(true);
        box2Label.setForeground(Color.decode("#400601"));
        box2Label.setBackground(Color.decode("#F294AD"));
        box2Label.setText("Managers: " + mgrCount);
        box2Label.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
        
        JLabel box3Label = new JLabel("Orders: ", SwingConstants.CENTER);
        box3Label.setOpaque(true);
        box3Label.setForeground(Color.decode("#400601"));
        box3Label.setBackground(Color.decode("#F294AD"));
        box3Label.setText("Orders: " + orderCount);
        box3Label.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
        
        JLabel box4Label = new JLabel("Menu Items:", SwingConstants.CENTER);
        box4Label.setOpaque(true);
        box4Label.setForeground(Color.decode("#400601"));
        box4Label.setBackground(Color.decode("#F294AD"));
        box4Label.setText("Menu Items: " + menuItemsCount);
        box4Label.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
        
        box1Label.setFont(new Font("Arial", Font.BOLD, 20));
        box2Label.setFont(new Font("Arial", Font.BOLD, 20));
        box3Label.setFont(new Font("Arial", Font.BOLD, 20));
        box4Label.setFont(new Font("Arial", Font.BOLD, 20));
        
        box1Label.setHorizontalAlignment(JTextField.CENTER);
        box2Label.setHorizontalAlignment(JTextField.CENTER);
        box3Label.setHorizontalAlignment(JTextField.CENTER);
        box4Label.setHorizontalAlignment(JTextField.CENTER);

        numericBoxesPanel.add(box1Label);
        numericBoxesPanel.add(box2Label);
        numericBoxesPanel.add(box3Label);
        numericBoxesPanel.add(box4Label);
        firstTab.add(numericBoxesPanel, BorderLayout.NORTH);

        // Charts Panel
        JPanel chartsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        chartsPanel.setBackground(Color.decode("#F2D7B6"));

        // Column Chart
        DefaultCategoryDataset columnDataset = new DefaultCategoryDataset();
        columnDataset.addValue(mgrCount, "Managers", "Managers");
        columnDataset.addValue(userCount, "Employees", "Employees");
        JFreeChart columnChart = ChartFactory.createBarChart("Number of Managers to Employees", "Category", "Number", columnDataset);
        ChartPanel columnChartPanel = new ChartPanel(columnChart);
        chartsPanel.add(columnChartPanel);

        // Bar Chart
        DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
        barDataset.addValue(cashCount, "Cash", "Cash Payed");
        barDataset.addValue(creditCount, "Credit", "Credit Payed");
        barDataset.addValue(takeawayCount, "Takeaway", "Pay On Delivery");
        JFreeChart barChart = ChartFactory.createBarChart("Number of Pay Types", "", "", barDataset);
        ChartPanel barChartPanel = new ChartPanel(barChart);
        chartsPanel.add(barChartPanel);

        // Pie Chart
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        pieDataset.setValue("cash Orders: " + cashCount, cashCount);
        pieDataset.setValue("Credit Orders: " + creditCount, creditCount);
        pieDataset.setValue("Takeaway Orders: " + takeawayCount, takeawayCount);
        JFreeChart pieChart = ChartFactory.createPieChart("Orders Categorized as Cash, Credit card and Takeaway", pieDataset);
        ChartPanel pieChartPanel = new ChartPanel(pieChart);
        chartsPanel.add(pieChartPanel);

        // Line Chart
//        DefaultCategoryDataset lineDataset = new DefaultCategoryDataset();
//        lineDataset.addValue(10, "Series", "Jan");
//        lineDataset.addValue(20, "Series", "Feb");
//        lineDataset.addValue(30, "Series", "Mar");
//        JFreeChart lineChart = ChartFactory.createLineChart("Line Chart", "", "", lineDataset);
//        ChartPanel lineChartPanel = new ChartPanel(lineChart);
//        chartsPanel.add(lineChartPanel);

        firstTab.add(chartsPanel, BorderLayout.CENTER);
        tabbedPane.addTab("Dashboard", firstTab);
///////////////////////////////////////////////////////////////////////////////////
        
        // Second Tab -- Add New User to the database table person
        // addUser Tab: Textboxes and Save Button
        
        JPanel addUserTab = new JPanel(new BorderLayout());
        addUserTab.setBackground(Color.decode("#F2DCEB"));
        JPanel inputFormPane = new JPanel(new BorderLayout());
        inputFormPane.setBackground(Color.decode("#F2CAB3"));
        inputFormPane.setPreferredSize(new Dimension(800,600));// 60% of screen size
        
        
        JLabel lblTitle = new JLabel("Add New User");
        // JLabel.setBounds( X, Y + i * gapBetweenRows, labelWidth, componentHeight);
        lblTitle.setBounds(250, 40, 180, 40);
        lblTitle.setBorder(topBottomBorder);
        lblTitle.setFont(new Font("Dialog", Font.BOLD, 25));
        lblTitle.setForeground(fontColor);
        inputFormPane.add(lblTitle, BorderLayout.CENTER);
        
        JLabel lblUsername = new JLabel("Username: ");
        // JLabel.setBounds( X, Y + i * gapBetweenRows, labelWidth, componentHeight);
        lblUsername.setBounds(100, 100, 100, 40);
        lblUsername.setFont(font16Bold);
        lblUsername.setForeground(fontColor);
        inputFormPane.add(lblUsername);
        
        JTextField txtUsername = new JTextField();
        // JTextField.setBounds( X, Y + i * gapBetweenRows, textWidth, componentHeight);
        txtUsername.setBounds(220, 100, 250, 40);
        txtUsername.setFont(font16Bold);
        txtUsername.setForeground(fontColor);
        inputFormPane.add(txtUsername);
        
        JLabel lblPassword = new JLabel("Password: ");
        lblPassword.setBounds(100, 150, 100, 40);
        lblPassword.setFont(font16Bold);
        lblPassword.setForeground(fontColor);
        inputFormPane.add(lblPassword);
        
        JTextField txtPassword = new JTextField();
        txtPassword.setBounds(220, 150, 250, 40);
        txtPassword.setFont(font16Bold);
        txtPassword.setForeground(fontColor);
        inputFormPane.add(txtPassword);
        
        JLabel lblRole = new JLabel("Role: ");
        lblRole.setBounds(100, 200, 100, 40);
        lblRole.setFont(font16Bold);
        lblRole.setForeground(fontColor);
        inputFormPane.add(lblRole);
        
        JComboBox<String> comboRole = new JComboBox<>();
        comboRole.addItem("Manager");
        comboRole.addItem("User");
        comboRole.setSelectedIndex(1); // Selects "User" as default Value
        comboRole.setBounds(220, 200, 250, 40);
        comboRole.setFont(font16Bold);
        comboRole.setForeground(fontColor);
        inputFormPane.add(comboRole);
        
        JLabel lblFullname = new JLabel("Fullname: ");
        lblFullname.setBounds(100, 250, 100, 40);
        lblFullname.setFont(font16Bold);
        lblFullname.setForeground(fontColor);
        inputFormPane.add(lblFullname);
        
        JTextField txtFullname = new JTextField();
        txtFullname.setBounds(220, 250, 250, 40);
        txtFullname.setFont(font16Bold);
        txtFullname.setForeground(fontColor);
        inputFormPane.add(txtFullname);
        
        JButton btnSave = new JButton("Save");
        btnSave.setBounds(250, 300, 100, 30);
        btnSave.setFont(font16Bold);
        btnSave.setForeground(fontColor);
        inputFormPane.add(btnSave);
        
        // the last object takes up the rest of the space 
        // I created this Bummy lbl to keep the interface intact.
        JLabel lblDummy = new JLabel("");
        inputFormPane.add(lblDummy);
        
        addUserTab.add(inputFormPane, BorderLayout.CENTER);
        tabbedPane.addTab("Add User", addUserTab);
        
        // ============ Save Button ActionListener ===========
        btnSave.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                // Read all the values 
                String username = txtUsername.getText();
                String selectedRole = (String) comboRole.getSelectedItem();
                String password = txtPassword.getText();
                String fullname = txtFullname.getText();
                if(username.equals("") || 
                    password.equals("") ||
                    fullname.equals("")){
                    JOptionPane.showMessageDialog(addUserTab,
                    """
                    Empty Field.\n
                    Reason:\n At least one of the feilds is EMPTY\n """,
                    "Please, fill All the fields.",
                    JOptionPane.ERROR_MESSAGE);
                }else{
                    try{
                        // DBConnection.connection ==> declared in the DBConnection CLASS
                        Connection conn = (Connection) DBConnection.connection;
                        String sqlInsert = "INSERT INTO person (name, role, password, fullname)"
                                + " VALUES (?,?,?,?)";
                        PreparedStatement preparedStatement = conn.prepareStatement (sqlInsert);
                        preparedStatement.setString(1, username); // for customer column too
                        preparedStatement.setString(2, selectedRole);
                        preparedStatement.setString(3, password);
                        preparedStatement.setString(4, fullname);
                        // Execute the query
                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(addUserTab,
                                """
                                User Updated\n 
                                Fullname: """+fullname,
                                "Saving Done",
                                JOptionPane.INFORMATION_MESSAGE);
                            // Clear the ttext fields to be ready for the next input
                            txtUsername.setText("");
                            comboRole.setSelectedIndex(1);
                            txtPassword.setText("");
                            txtFullname.setText("");
                        } else {
                            JOptionPane.showMessageDialog(addUserTab,
                                "Failed to save User Information",
                                "Save Failed",
                                JOptionPane.ERROR_MESSAGE);
                        }// if (rowsAffected)
                        }catch(SQLException ex) {
                        JOptionPane.showMessageDialog(null,
                                """
                                Failed to Connect\n
                                Reason:\nUpdate/ Delete Form\n""" + ex.getMessage(),
                                "Connection Failed",
                                JOptionPane.ERROR_MESSAGE);
                    } // try catch block
                }// if(username.equals("")|| ......
            } // btnSave action Performed
        });// btnSave add action listener
        // ----------- Save Button ENDs Here -----------
        
///////////////////////////////////////////////////////////////////////////

        // Third Tab: Textboxes and Save Button
        // Update Delete Tab: Labels, Textboxes and Save Button
        JPanel updateDeleteTab = new JPanel(new BorderLayout());
        updateDeleteTab.setBackground(Color.decode("#F2DCEB"));
        JPanel updateDeletePanel = new JPanel(new BorderLayout());
        updateDeletePanel.setBackground(Color.decode("#D9C5D2"));
        
        JLabel lblTitleUD = new JLabel("Update OR Delete User From the DATABASE");
        // JLabel.setBounds( X, Y + i * gapBetweenRows, labelWidth, componentHeight);
        lblTitleUD.setBounds(100, 40, 550, 40);
        lblTitleUD.setBorder(topBottomBorder);
        lblTitleUD.setFont(new Font("Dialog", Font.BOLD, 25));
        lblTitleUD.setForeground(fontColor);
        updateDeletePanel.add(lblTitleUD, BorderLayout.CENTER);

        // Use the "DefaultComboBoxModel" to be able to refresh the ComboBox
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
        comboBoxModel.removeAllElements(); // Clear at every Entry 
        JComboBox<String> comboPersonName = new JComboBox<>(comboBoxModel);
        try{
            // set "Select Person Name" as the first item
            comboPersonName.insertItemAt("Select Person Name", 0);
            comboPersonName.setSelectedIndex(0); // Set as default
            
            // DBConnection.connection ==> declared in the DBConnection CLASS
            Connection conn = (Connection) DBConnection.connection;
            String sqlSelect = "SELECT fullname FROM person ORDER BY fullname";
            PreparedStatement preparedStatement = conn.prepareStatement (sqlSelect);
            ResultSet resultSet = preparedStatement.executeQuery();
            // fill the comboBox with Fullnames from the database
            while(resultSet.next()){
                // "fullname" is the query result from the database table column
                String personName = resultSet.getString("fullname");
                comboPersonName.addItem(personName);
            }// while
            }catch(SQLException ex) {
                JOptionPane.showMessageDialog(addUserTab,
                    """
                    Update/ Delete Form...Fill Combobox\n
                    Failed to Connect\n
                    Reason:\n""" + ex.getMessage(),
                    "Connection Failed",
                    JOptionPane.ERROR_MESSAGE);
            } // try catch block
        //========================================================================
        comboPersonName.setBounds(220, 100, 250, 40);
        comboPersonName.setFont(font16Bold);
        comboPersonName.setForeground(fontColor);
        updateDeletePanel.add(comboPersonName);
        
        JLabel lblUsernameUD = new JLabel("Username: ");
        // JLabel.setBounds( X, Y + i * gapBetweenRows, labelWidth, componentHeight);
        lblUsernameUD.setBounds(100, 150, 100, 40);
        lblUsernameUD.setFont(font16Bold);
        lblUsernameUD.setForeground(fontColor);
        updateDeletePanel.add(lblUsernameUD);
        
        JTextField txtUsernameUD = new JTextField();
        // JTextField.setBounds( X, Y + i * gapBetweenRows, textWidth, componentHeight);
        txtUsernameUD.setBounds(220, 150, 250, 40);
        txtUsernameUD.setFont(font16Bold);
        txtUsernameUD.setForeground(fontColor);
        updateDeletePanel.add(txtUsernameUD);
        
        JLabel lblPasswordUD = new JLabel("Password: ");
        lblPasswordUD.setBounds(100, 200, 100, 40);
        lblPasswordUD.setFont(font16Bold);
        lblPasswordUD.setForeground(fontColor);
        updateDeletePanel.add(lblPasswordUD);
        
        JTextField txtPasswordUD = new JTextField();
        txtPasswordUD.setBounds(220, 200, 250, 40);
        txtPasswordUD.setFont(font16Bold);
        txtPasswordUD.setForeground(fontColor);
        updateDeletePanel.add(txtPasswordUD);
        
        
        JLabel lblRoleUD = new JLabel("Role: ");
        lblRoleUD.setBounds(100, 250, 100, 40);
        lblRoleUD.setFont(font16Bold);
        lblRoleUD.setForeground(fontColor);
        updateDeletePanel.add(lblRoleUD);
        
        JTextField txtRoleUD = new JTextField();
        txtRoleUD.setBounds(220, 250, 250, 40);
        txtRoleUD.setFont(font16Bold);
        txtRoleUD.setForeground(fontColor);
        updateDeletePanel.add(txtRoleUD);
        
        JLabel lblFullnameUD = new JLabel("Fullname: ");
        lblFullnameUD.setBounds(100, 300, 100, 40);
        lblFullnameUD.setFont(font16Bold);
        lblFullnameUD.setForeground(fontColor);
        updateDeletePanel.add(lblFullnameUD);
        
        JTextField txtFullnameUD = new JTextField();
        txtFullnameUD.setBounds(220, 300, 250, 40);
        txtFullnameUD.setFont(font16Bold);
        txtFullnameUD.setForeground(fontColor);
        updateDeletePanel.add(txtFullnameUD);
        
        JButton btnUpdateUD = new JButton("Update");
        btnUpdateUD.setBounds(220, 350, 100, 30);
        btnUpdateUD.setFont(font16Bold);
        btnUpdateUD.setForeground(fontColor);
        updateDeletePanel.add(btnUpdateUD);
        
        JButton btnDeleteUD = new JButton("Delete");
        btnDeleteUD.setBounds(370, 350, 100, 30);
        btnDeleteUD.setFont(font16Bold);
        btnDeleteUD.setForeground(fontColor);
        btnDeleteUD.setBackground(Color.decode("#F294AD"));
        updateDeletePanel.add(btnDeleteUD);
        
        // the last object takes up the rest of the space 
        // I created this Bummy lbl to keep the interface intact.
        JLabel lblDummyUD = new JLabel("");
        updateDeletePanel.add(lblDummyUD);
        
        // ----------- comboPersonName add Action Listener -----------
        comboPersonName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected item from the combo box
                String selectedPerson = (String) comboPersonName.getSelectedItem();

                // Check if the selected item is not the placeholder
                if ( ! "Select Person Name".equals(selectedPerson)) {
                    // assign the text fields with the selected Person name data
                    try{
                        // DBConnection.connection ==> declared in the DBConnection CLASS
                        Connection conn = (Connection) DBConnection.connection;
                        String sqlSelect = "SELECT * FROM person WHERE fullname = ?";
                        PreparedStatement preparedStatement = conn.prepareStatement (sqlSelect);
                        preparedStatement.setString(1, selectedPerson);
                        ResultSet resultSet = preparedStatement.executeQuery();
                        if(resultSet.next()){
                            txtUsernameUD.setText(resultSet.getString("name"));
                            txtPasswordUD.setText(resultSet.getString("password"));
                            txtRoleUD.setText(resultSet.getString("role"));
                            txtFullnameUD.setText(resultSet.getString("fullname"));
                        } else {
                            // Clear the text field if the placeholder is selected
                            txtUsernameUD.setText("");
                            txtPasswordUD.setText("");
                            txtRoleUD.setText("");
                            txtFullnameUD.setText("");
                        }// if(resultSet.next())
                    }catch(SQLException ex) {
                            JOptionPane.showMessageDialog(addUserTab,
                                """
                                Update/ Delete Form... Populate text fields\n
                                Failed to Connect\n
                                Reason:\n""" + ex.getMessage(),
                                "Connection Failed",
                                JOptionPane.ERROR_MESSAGE);
                        } // try catch block
                }// if ( ! "Select Person Name".equals(selectedItem)) 
            }// combobPersonName actionPerformed(ActionEvent e) 
        }); // comboPersonName addActionListener(new ActionListener()
        // ----------- comboPersonName ActionListener ENDs Here -----------

        // ============ Update Button add Action Listener ===========
        btnUpdateUD.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                // Read all the values 
                String username = txtUsernameUD.getText().trim();
                String role = txtRoleUD.getText().trim();
                String password = txtPasswordUD.getText().trim();
                String fullname = txtFullnameUD.getText().trim();
                if(username.equals("") || 
                    role.equals("") ||
                    password.equals("") ||
                    fullname.equals("")){
                    JOptionPane.showMessageDialog(addUserTab,
                    """
                    Empty Field On UPDATE.\n
                    Reason:\n At least one of the feilds is EMPTY\n """,
                    "Please, fill All the fields.",
                    JOptionPane.ERROR_MESSAGE);
                }else{
                    try{
                        // the "selectedPerson" in the combobox is the string that has not changed yet.
                        String selectedPerson = (String) comboPersonName.getSelectedItem();
                        int personID = getPersonIdByName(selectedPerson);
                        // DBConnection.connection ==> declared in the DBConnection CLASS
                        Connection conn = (Connection) DBConnection.connection;
                        String sqlUpdate = "UPDATE person SET name = ?, role = ?, password = ?, fullname = ?"
                                + " WHERE person_id = ?";
                        PreparedStatement preparedStatement = conn.prepareStatement (sqlUpdate);
                        preparedStatement.setString(1, username);
                        preparedStatement.setString(2, role);
                        preparedStatement.setString(3, password);
                        preparedStatement.setString(4, fullname);
                        preparedStatement.setInt(5, personID);
                        // Execute the query
                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(addUserTab,
                                """
                                Person Updated\n
                                Fullname: """ + fullname,
                                "Update Done",
                                JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(addUserTab,
                                "Failed to update User data",
                                "Update Failed",
                                JOptionPane.ERROR_MESSAGE);
                        }// if (rowsAffected)
                        }catch(SQLException ex) {
                        JOptionPane.showMessageDialog(addUserTab,
                                """
                                Update Form\n
                                Failed to Connect\n
                                Reason:\n""" + ex.getMessage(),
                                "Connection Failed",
                                JOptionPane.ERROR_MESSAGE);
                    } // try catch block
                }// if(username.equals("")|| ......
            } // btnUpdateUD action Performed
        });// btnUpdateUD add action listener
        // ============ Update Button add Action Listener ENDs here ===========

        // ============ DELETE Button add Action Listener ===========
        btnDeleteUD.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(comboPersonName.getSelectedIndex() == 0 ) {
                    JOptionPane.showMessageDialog(addUserTab,
                    """
                    No Person Selected.\n
                    Reason:\n Please Secet a person name\n """,
                    "",
                    JOptionPane.ERROR_MESSAGE);
                }else{
                    try{
                        // the "selectedPerson" in the combobox is the string that has not changed yet.
                        String selectedPersonToDelete = (String) comboPersonName.getSelectedItem();
                        int personID = getPersonIdByName(selectedPersonToDelete);
                        // DBConnection.connection ==> declared in the DBConnection CLASS
                        Connection conn = (Connection) DBConnection.connection;
                        String sqlDelete = "DELETE FROM person WHERE person_id = ?";
                        PreparedStatement preparedStatement = conn.prepareStatement (sqlDelete);
                        preparedStatement.setInt(1, personID);
                        // Execute the query
                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(addUserTab,
                                """
                                Person Deleted\n""",
                                "DELETE Done",
                                JOptionPane.INFORMATION_MESSAGE);
                            comboPersonName.setSelectedIndex(0); // Set as default
                            txtUsernameUD.setText("");
                            txtPasswordUD.setText("");
                            txtRoleUD.setText("");
                            txtFullnameUD.setText("");
                        } else {
                            JOptionPane.showMessageDialog(addUserTab,
                                "Failed to Delete User",
                                "Delete Failed",
                                JOptionPane.ERROR_MESSAGE);
                        }// if (rowsAffected)
                        }catch(SQLException ex) {
                        JOptionPane.showMessageDialog(addUserTab,
                                """
                                Delete Form\n
                                Failed to Connect\n
                                Reason:\n""" + ex.getMessage(),
                                "Connection Failed",
                                JOptionPane.ERROR_MESSAGE);
                    } // try catch block
                }// if(comboPersonName.getSelectedIndex() ......
            } // btnDeleteUD action Performed
        });// btnDeleteUD add action listener
        // ============ DELETE Button add Action Listener ENDs here ===========
        

        updateDeleteTab.add(updateDeletePanel, BorderLayout.CENTER);
        tabbedPane.addTab("Update/ Delete User", updateDeleteTab);
        // Add JTabbedPane to Frame
        frame.add(tabbedPane, BorderLayout.CENTER);

        // Add Action Listeners to Navigation Labels
        lblDashboardTitle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabbedPane.setSelectedIndex(0);
            }
        });
        lbladdUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabbedPane.setSelectedIndex(1);
            }
        });
        lblUupdateUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabbedPane.setSelectedIndex(2);
            }
        });

        exitLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                frame.setVisible(false); // hide the current frame
                dispose(); // close the current frame
                new LoginFrame().setVisible(true); // show the new frame
            }
        });

        // Make the Frame Visible
        frame.setVisible(true);
    }// Dash2(String fullname, String role) Constructor

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 223, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 123, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Dash2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dash2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dash2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dash2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new Dash2().setVisible(true);
            }
        });
    }// main()

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables


// Method to retrieve the person_id of a person by name 
    private static int getPersonIdByName(String name) {
        try {
            // DBConnection.connection ==> declared in the DBConnection CLASS
            Connection conn = (Connection) DBConnection.connection;
            String sqlSelect = "SELECT person_id FROM person WHERE fullname = ?";
            PreparedStatement preparedStatement = conn.prepareStatement (sqlSelect);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("person_id");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                """
                getPersonIdByName Method\n
                Coulldn't retrieve person_id\n
                Reason:\n""" + ex.getMessage(),
                "Retrieve Failed",
                JOptionPane.ERROR_MESSAGE);
        }
        return -1; // Return -1 if no ID is found
    }
} // class Dash2{}
