import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class Department {
    private String code;
    private String name;

    public Department(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}

class Student {
    private String roll;
    private String deptCode;
    private String name;
    private String address;
    private String phone;

    public Student(String roll, String deptCode, String name, String address, String phone) {
        this.roll = roll;
        this.deptCode = deptCode;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    // Getters and Setters
    public String getRoll() {
        return roll;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

public class StudentManagementSystem extends JFrame {
    // Sample data
    private Department[] departments = {
            new Department("CS", "Computer Science"),
            new Department("IT", "Information Technology"),
            new Department("EE", "Electrical Engineering"),
            new Department("ME", "Mechanical Engineering"),
            new Department("CE", "Civil Engineering")
    };

    private ArrayList<Student> students = new ArrayList<>();

    // Components
    private JPanel mainPanel;
    private JPanel actionPanel;
    private JPanel displayPanel;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JButton addButton, searchButton, editButton, deleteButton, displayButton;
    private JButton prevButton, nextButton;
    private int currentPage = 0;
    private final int RECORDS_PER_PAGE = 5;

    public StudentManagementSystem() {
        // Initialize with sample data
        initializeSampleData();

        setTitle("Student Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel(new BorderLayout());
        
        // Action Panel
        actionPanel = new JPanel();
        addButton = new JButton("Add Student");
        searchButton = new JButton("Search Student");
        editButton = new JButton("Edit Student");
        deleteButton = new JButton("Delete Student");
        displayButton = new JButton("Display All");
        
        actionPanel.add(addButton);
        actionPanel.add(searchButton);
        actionPanel.add(editButton);
        actionPanel.add(deleteButton);
        actionPanel.add(displayButton);
        
        // Display Panel
        displayPanel = new JPanel(new BorderLayout());
        String[] columnNames = {"Roll", "Department", "Name", "Address", "Phone"};
        tableModel = new DefaultTableModel(columnNames, 0);
        studentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        
        JPanel pagePanel = new JPanel();
        prevButton = new JButton("Previous");
        nextButton = new JButton("Next");
        pagePanel.add(prevButton);
        pagePanel.add(nextButton);
        
        displayPanel.add(scrollPane, BorderLayout.CENTER);
        displayPanel.add(pagePanel, BorderLayout.SOUTH);
        
        mainPanel.add(actionPanel, BorderLayout.NORTH);
        mainPanel.add(displayPanel, BorderLayout.CENTER);
        
        add(mainPanel);
        
        // Add action listeners
        addActionListeners();
        
        // Initially display all students
        updateStudentTable();
    }
    
    private void initializeSampleData() {
        // Sample students
    students.add(new Student("101", "CS", "Raj Sharma", "123 Gandhi Road, Mumbai", "555-1234"));
    students.add(new Student("102", "IT", "Priya Patel", "456 Nehru Avenue, Delhi", "555-5678"));
    students.add(new Student("103", "EE", "Amit Kumar", "789 Tagore Street, Kolkata", "555-9012"));
    students.add(new Student("104", "ME", "Anjali Singh", "012 Bose Lane, Chennai", "555-3456"));
    students.add(new Student("105", "CE", "Vikram Malhotra", "345 Ambedkar Road, Bangalore", "555-7890"));
    students.add(new Student("106", "CS", "Sunita Desai", "678 Shastri Nagar, Hyderabad", "555-2345"));
    students.add(new Student("107", "IT", "Rajesh Gupta", "901 Patel Colony, Ahmedabad", "555-6789"));
    students.add(new Student("108", "EE", "Meena Reddy", "234 Gandhi Nagar, Pune", "555-0123"));
    students.add(new Student("109", "ME", "Arjun Mehta", "567 Nehru Road, Jaipur", "555-4567"));
    students.add(new Student("110", "CE", "Kiran Iyer", "890 Subhash Marg, Lucknow", "555-8901"));
    students.add(new Student("111", "CS", "Neha Joshi", "123 Tilak Road, Chandigarh", "555-2345"));
    students.add(new Student("112", "IT", "Rahul Verma", "456 Birla Colony, Bhopal", "555-6789"));
    }
    
    private void addActionListeners() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddStudentDialog();
            }
        });
        
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSearchStudentDialog();
            }
        });
        
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showEditStudentDialog();
            }
        });
        
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDeleteStudentDialog();
            }
        });
        
        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPage = 0;
                updateStudentTable();
            }
        });
        
        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPage > 0) {
                    currentPage--;
                    updateStudentTable();
                }
            }
        });
        
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((currentPage + 1) * RECORDS_PER_PAGE < students.size()) {
                    currentPage++;
                    updateStudentTable();
                }
            }
        });
    }
    
    private void updateStudentTable() {
        // Clear table
        tableModel.setRowCount(0);
        
        // Calculate start and end index for the current page
        int startIndex = currentPage * RECORDS_PER_PAGE;
        int endIndex = Math.min(startIndex + RECORDS_PER_PAGE, students.size());
        
        // Add rows for the current page
        for (int i = startIndex; i < endIndex; i++) {
            Student student = students.get(i);
            String deptName = getDepartmentName(student.getDeptCode());
            Object[] rowData = {student.getRoll(), deptName, student.getName(), 
                             student.getAddress(), student.getPhone()};
            tableModel.addRow(rowData);
        }
        
        // Enable/disable navigation buttons
        prevButton.setEnabled(currentPage > 0);
        nextButton.setEnabled((currentPage + 1) * RECORDS_PER_PAGE < students.size());
    }
    
    private String getDepartmentName(String deptCode) {
        for (Department dept : departments) {
            if (dept.getCode().equals(deptCode)) {
                return dept.getName();
            }
        }
        return "";
    }
    
    private Department getDepartmentByCode(String code) {
        for (Department dept : departments) {
            if (dept.getCode().equals(code)) {
                return dept;
            }
        }
        return null;
    }
    
    private Student findStudentByRoll(String roll) {
        for (Student student : students) {
            if (student.getRoll().equals(roll)) {
                return student;
            }
        }
        return null;
    }
    
    private void showAddStudentDialog() {
        JPanel panel = new JPanel(new GridLayout(5, 2));
        
        JLabel rollLabel = new JLabel("Roll Number:");
        JTextField rollField = new JTextField();
        
        JLabel deptLabel = new JLabel("Department:");
        JComboBox<Department> deptComboBox = new JComboBox<>(departments);
        
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        
        JLabel addressLabel = new JLabel("Address:");
        JTextField addressField = new JTextField();
        
        JLabel phoneLabel = new JLabel("Phone:");
        JTextField phoneField = new JTextField();
        
        panel.add(rollLabel);
        panel.add(rollField);
        panel.add(deptLabel);
        panel.add(deptComboBox);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(addressLabel);
        panel.add(addressField);
        panel.add(phoneLabel);
        panel.add(phoneField);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Add Student", 
                                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            String roll = rollField.getText().trim();
            
            // Validate roll number (must be unique)
            if (roll.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Roll number cannot be empty!", 
                                       "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (findStudentByRoll(roll) != null) {
                JOptionPane.showMessageDialog(this, "A student with this roll number already exists!", 
                                       "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Department selectedDept = (Department) deptComboBox.getSelectedItem();
            String name = nameField.getText().trim();
            String address = addressField.getText().trim();
            String phone = phoneField.getText().trim();
            
            // Validate other fields
            if (name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled out!", 
                                       "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Create and add new student
            Student newStudent = new Student(roll, selectedDept.getCode(), name, address, phone);
            students.add(newStudent);
            
            // Update the display
            updateStudentTable();
            JOptionPane.showMessageDialog(this, "Student added successfully!", 
                                   "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void showSearchStudentDialog() {
        String roll = JOptionPane.showInputDialog(this, "Enter Roll Number:");
        
        if (roll != null && !roll.trim().isEmpty()) {
            Student student = findStudentByRoll(roll.trim());
            
            if (student != null) {
                // Display student details
                Department dept = getDepartmentByCode(student.getDeptCode());
                String deptName = dept != null ? dept.getName() : "";
                
                JPanel panel = new JPanel(new GridLayout(5, 2));
                panel.add(new JLabel("Roll Number:"));
                panel.add(new JLabel(student.getRoll()));
                panel.add(new JLabel("Department:"));
                panel.add(new JLabel(deptName));
                panel.add(new JLabel("Name:"));
                panel.add(new JLabel(student.getName()));
                panel.add(new JLabel("Address:"));
                panel.add(new JLabel(student.getAddress()));
                panel.add(new JLabel("Phone:"));
                panel.add(new JLabel(student.getPhone()));
                
                JOptionPane.showMessageDialog(this, panel, "Student Details", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No student found with roll number: " + roll, 
                                       "Not Found", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    
    private void showEditStudentDialog() {
        String roll = JOptionPane.showInputDialog(this, "Enter Roll Number to Edit:");
        
        if (roll != null && !roll.trim().isEmpty()) {
            Student student = findStudentByRoll(roll.trim());
            
            if (student != null) {
                // Create edit dialog
                JPanel panel = new JPanel(new GridLayout(5, 2));
                
                JLabel rollLabel = new JLabel("Roll Number:");
                JLabel rollValueLabel = new JLabel(student.getRoll());
                
                JLabel deptLabel = new JLabel("Department:");
                JComboBox<Department> deptComboBox = new JComboBox<>(departments);
                for (int i = 0; i < departments.length; i++) {
                    if (departments[i].getCode().equals(student.getDeptCode())) {
                        deptComboBox.setSelectedIndex(i);
                        break;
                    }
                }
                
                JLabel nameLabel = new JLabel("Name:");
                JTextField nameField = new JTextField(student.getName());
                
                JLabel addressLabel = new JLabel("Address:");
                JTextField addressField = new JTextField(student.getAddress());
                
                JLabel phoneLabel = new JLabel("Phone:");
                JTextField phoneField = new JTextField(student.getPhone());
                
                panel.add(rollLabel);
                panel.add(rollValueLabel);
                panel.add(deptLabel);
                panel.add(deptComboBox);
                panel.add(nameLabel);
                panel.add(nameField);
                panel.add(addressLabel);
                panel.add(addressField);
                panel.add(phoneLabel);
                panel.add(phoneField);
                
                int result = JOptionPane.showConfirmDialog(this, panel, "Edit Student", 
                                                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                
                if (result == JOptionPane.OK_OPTION) {
                    // Update student details
                    Department selectedDept = (Department) deptComboBox.getSelectedItem();
                    String name = nameField.getText().trim();
                    String address = addressField.getText().trim();
                    String phone = phoneField.getText().trim();
                    
                    // Validate fields
                    if (name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "All fields must be filled out!", 
                                               "Validation Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    student.setDeptCode(selectedDept.getCode());
                    student.setName(name);
                    student.setAddress(address);
                    student.setPhone(phone);
                    
                    // Update the display
                    updateStudentTable();
                    JOptionPane.showMessageDialog(this, "Student updated successfully!", 
                                           "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "No student found with roll number: " + roll, 
                                       "Not Found", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    
    private void showDeleteStudentDialog() {
        String roll = JOptionPane.showInputDialog(this, "Enter Roll Number to Delete:");
        
        if (roll != null && !roll.trim().isEmpty()) {
            Student student = findStudentByRoll(roll.trim());
            
            if (student != null) {
                int confirmResult = JOptionPane.showConfirmDialog(this, 
                                            "Are you sure you want to delete the student with roll number: " + roll + "?", 
                                            "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                
                if (confirmResult == JOptionPane.YES_OPTION) {
                    students.remove(student);
                    currentPage = 0; // Reset to first page
                    updateStudentTable();
                    JOptionPane.showMessageDialog(this, "Student deleted successfully!", 
                                           "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "No student found with roll number: " + roll, 
                                       "Not Found", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StudentManagementSystem().setVisible(true);
            }
        });
    }
}
