package com.example.app.Controllers.TechnicalOfficer;

import com.example.app.Models.*;
import com.example.app.Services.*;
import com.example.app.Util.FormUtil;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TechnicalOfficerController implements Initializable {

    @FXML private AnchorPane profileView;
    @FXML private AnchorPane attendanceView;
    @FXML private AnchorPane medicalView;
    @FXML private AnchorPane noticeView;
    @FXML private AnchorPane timetableView;
    @FXML private AnchorPane dashboardView;

    // Dashboard components
    @FXML private Label attendanceCountLabel;
    @FXML private Label medicalCountLabel;
    @FXML private Label noticeCountLabel;
    @FXML private Label statusLabel;
    @FXML private TableView<Timetable> todaySessionsTableView;
    @FXML private TableColumn<Timetable, String> sessionTimeColumn;
    @FXML private TableColumn<Timetable, String> sessionCourseColumn;
    @FXML private TableColumn<Timetable, String> sessionTypeColumn;
    @FXML private TableColumn<Timetable, String> sessionVenueColumn;
    @FXML private TableColumn<Timetable, String> sessionLecturerColumn;

    // Profile components
    @FXML private Label usernameLabel;
    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private ComboBox<String> departmentComboBox;
    @FXML private ImageView profileImageView;
    @FXML private ImageView profilePictureView;

    // Notice components
    @FXML private TableView<Notice> noticeTableView;
    @FXML private TableColumn<Notice, Integer> noticeIdColumn;
    @FXML private TableColumn<Notice, String> noticeTitleColumn;
    @FXML private TableColumn<Notice, Date> noticeDateColumn;
    @FXML private TableColumn<Notice, String> noticeAuthorColumn;
    @FXML private TextArea noticeContentArea;

    // Timetable components
    @FXML private ComboBox<String> departmentTimetableComboBox;
    @FXML private ComboBox<Integer> semesterComboBox;
    @FXML private TableView<Map<String, Object>> timetableTableView;
    @FXML private TableColumn<Map<String, Object>, String> timeColumn;
    @FXML private TableColumn<Map<String, Object>, String> mondayColumn;
    @FXML private TableColumn<Map<String, Object>, String> tuesdayColumn;
    @FXML private TableColumn<Map<String, Object>, String> wednesdayColumn;
    @FXML private TableColumn<Map<String, Object>, String> thursdayColumn;
    @FXML private TableColumn<Map<String, Object>, String> fridayColumn;

    // Attendance components
    @FXML private ComboBox<Course> courseComboBox;
    @FXML private ComboBox<String> sessionTypeComboBox;
    @FXML private DatePicker attendanceDatePicker;
    @FXML private TableView<Map<String, Object>> attendanceTableView;
    @FXML private TableColumn<Map<String, Object>, Integer> idColumn;
    @FXML private TableColumn<Map<String, Object>, String> nameColumn;
    @FXML private TableColumn<Map<String, Object>, Boolean> presentColumn;
    @FXML private TableColumn<Map<String, Object>, Boolean> medicalColumn;
    @FXML private TableColumn<Map<String, Object>, String> notesColumn;
    @FXML private TableColumn<Map<String, Object>, Double> attendanceRateColumn;
    
    // Attendance Statistics components
    @FXML private TitledPane statisticsPane;
    @FXML private Label overallAttendanceLabel;
    @FXML private Label presentStudentsLabel;
    @FXML private Label absentStudentsLabel;
    @FXML private Label medicalLeaveLabel;
    @FXML private Label totalSessionsLabel;
    @FXML private Label todaySessionsLabel;
    @FXML private Label todayRateLabel;
    @FXML private Label perfectAttendanceLabel;
    @FXML private Label criticalAttendanceLabel;
    @FXML private Label totalEnrolledLabel;

    // Medical components
    @FXML private TextField searchStudentField;
    @FXML private TableView<Medical> medicalTableView;
    @FXML private TableColumn<Medical, Integer> medicalIdColumn;
    @FXML private TableColumn<Medical, Integer> studentIdColumn;
    @FXML private TableColumn<Medical, String> studentNameColumn;
    @FXML private TableColumn<Medical, LocalDate> startDateColumn;
    @FXML private TableColumn<Medical, LocalDate> endDateColumn;
    @FXML private TableColumn<Medical, String> reasonColumn;
    @FXML private TableColumn<Medical, String> statusColumn;
    @FXML private TextField medicalStudentIdField;
    @FXML private DatePicker medicalStartDatePicker;
    @FXML private DatePicker medicalEndDatePicker;
    @FXML private TextArea medicalReasonField;
    @FXML private ComboBox<String> medicalStatusComboBox;

    // Services
    private final UserService userService = new UserService();
    private final CourseService courseService = new CourseService();
    private final AttendanceService attendanceService = new AttendanceService();
    private final MedicalService medicalService = new MedicalService();
    private final NoticeService noticeService = new NoticeService();
    private final TimetableService timetableService = new TimetableService();

    private User currentUser;
    private String currentDepartment;
    private AttendanceStatistics currentStatistics;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentUser = userService.getCurrentUser();
        currentDepartment = currentUser.getDepartment();

        initializeUIComponents();
        loadDashboardData();

        statusLabel.setText("Logged in as: " + currentUser.getName());
        showDashboardView();
    }

    private void initializeUIComponents() {
        // Set up profile
        usernameLabel.setText(currentUser.getUsername());
        nameField.setText(currentUser.getName());
        emailField.setText(currentUser.getEmail());
        phoneField.setText(currentUser.getPhone());

        // Load profile picture
        loadProfilePicture();

        // Set up department dropdown
        List<String> departments = courseService.getAllDepartments();
        departmentComboBox.setItems(FXCollections.observableArrayList(departments));
        departmentComboBox.setValue(currentDepartment);
        departmentTimetableComboBox.setItems(FXCollections.observableArrayList(departments));
        departmentTimetableComboBox.setValue(currentDepartment);

        // Set up session type dropdown
        sessionTypeComboBox.setItems(FXCollections.observableArrayList("Theory", "Practical"));

        // Set up medical status dropdown
        medicalStatusComboBox.setItems(FXCollections.observableArrayList("Pending", "Approved", "Rejected"));

        // Set up semester dropdown
        semesterComboBox.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8));

        // Set up notice table
        noticeIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        noticeTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        noticeDateColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getPostDate() != null) {
                java.util.Date date = java.sql.Timestamp.valueOf(cellData.getValue().getPostDate());
                return new javafx.beans.property.SimpleObjectProperty<>(date);
            }
            return null;
        });
        noticeAuthorColumn.setCellValueFactory(cellData -> {
            User author = userService.getUserById(cellData.getValue().getPostedBy());
            String authorName = author != null ? author.getName() : "Unknown";
            return new javafx.beans.property.SimpleStringProperty(authorName);
        });

        // Set up notice selection listener
        noticeTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                noticeContentArea.setText(newSelection.getContent());
            } else {
                noticeContentArea.clear();
            }
        });

        // Set up medical table
        medicalIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        studentNameColumn.setCellValueFactory(cellData -> {
            User student = userService.getUserById(cellData.getValue().getStudentId());
            String studentName = student != null ? student.getName() : "Unknown";
            return new javafx.beans.property.SimpleStringProperty(studentName);
        });
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        reasonColumn.setCellValueFactory(new PropertyValueFactory<>("reason"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        // Set up medical table selection listener to populate form fields when a record is selected
        medicalTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Populate form fields with the selected medical record
                medicalStudentIdField.setText(String.valueOf(newSelection.getStudentId()));
                medicalStartDatePicker.setValue(newSelection.getStartDate());
                medicalEndDatePicker.setValue(newSelection.getEndDate());
                medicalReasonField.setText(newSelection.getReason());
                medicalStatusComboBox.setValue(newSelection.getStatus());
            }
        });

        // Set up today's sessions table
        sessionTimeColumn.setCellValueFactory(cellData -> {
            String timeRange = cellData.getValue().getStartTime().toString() + " - " +
                    cellData.getValue().getEndTime().toString();
            return new javafx.beans.property.SimpleStringProperty(timeRange);
        });
        sessionCourseColumn.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        sessionTypeColumn.setCellValueFactory(new PropertyValueFactory<>("sessionType"));
        sessionVenueColumn.setCellValueFactory(new PropertyValueFactory<>("venue"));
        sessionLecturerColumn.setCellValueFactory(cellData -> {
            Integer lecturerId = cellData.getValue().getLecturerId();
            if (lecturerId != null) {
                User lecturer = userService.getUserById(lecturerId);
                return new javafx.beans.property.SimpleStringProperty(
                        lecturer != null ? lecturer.getName() : "Unknown"
                );
            }
            return new javafx.beans.property.SimpleStringProperty("Not assigned");
        });

        // Set up attendance table columns
        idColumn.setCellValueFactory(data -> {
            Integer id = (Integer) data.getValue().get("id");
            return new javafx.beans.property.SimpleObjectProperty<>(id);
        });
        
        nameColumn.setCellValueFactory(data -> {
            String name = (String) data.getValue().get("name");
            return new javafx.beans.property.SimpleStringProperty(name);
        });
        
        presentColumn.setCellValueFactory(data -> {
            Boolean present = (Boolean) data.getValue().get("present");
            return new javafx.beans.property.SimpleBooleanProperty(present != null && present);
        });
        
        presentColumn.setCellFactory(column -> new CheckBoxTableCell<>());
        
        medicalColumn.setCellValueFactory(data -> {
            Boolean medical = (Boolean) data.getValue().get("medical");
            return new javafx.beans.property.SimpleBooleanProperty(medical != null && medical);
        });
        
        medicalColumn.setCellFactory(column -> new CheckBoxTableCell<>());
        
        notesColumn.setCellValueFactory(data -> {
            String notes = (String) data.getValue().get("notes");
            return new javafx.beans.property.SimpleStringProperty(notes != null ? notes : "");
        });
        
        // Set up attendance rate column
        attendanceRateColumn.setCellValueFactory(data -> {
            Double rate = (Double) data.getValue().get("attendanceRate");
            return new javafx.beans.property.SimpleObjectProperty<>(rate != null ? rate : 0.0);
        });
        
        attendanceRateColumn.setCellFactory(column -> new TableCell<Map<String, Object>, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(String.format("%.1f%%", item));
                    
                    // Color code based on attendance rate
                    if (item < 60.0) {
                        setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                    } else if (item < 80.0) {
                        setStyle("-fx-text-fill: orange;");
                    } else if (item >= 95.0) {
                        setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });
        
        // Make notes column editable
        notesColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        notesColumn.setOnEditCommit(event -> {
            Map<String, Object> rowData = event.getRowValue();
            rowData.put("notes", event.getNewValue());
        });
        
        // Make attendance table editable
        attendanceTableView.setEditable(true);
        
        // Add row click handler to show student details
        attendanceTableView.setRowFactory(tv -> {
            TableRow<Map<String, Object>> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 1) {
                    Map<String, Object> studentData = row.getItem();
                    handleStudentRowClick(studentData);
                }
            });
            return row;
        });

        // Set up course dropdown
        List<Course> courses = courseService.getCoursesByDepartment(currentDepartment);
        courseComboBox.setItems(FXCollections.observableArrayList(courses));
        courseComboBox.setCellFactory(param -> new ListCell<Course>() {
            @Override
            protected void updateItem(Course course, boolean empty) {
                super.updateItem(course, empty);
                if (empty || course == null) {
                    setText(null);
                } else {
                    setText(course.getCode() + " - " + course.getName());
                }
            }
        });
        courseComboBox.setButtonCell(courseComboBox.getCellFactory().call(null));
        
        // Add listener for course selection to update statistics
        courseComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && sessionTypeComboBox.getValue() != null) {
                updateAttendanceStatistics(newVal.getCode(), sessionTypeComboBox.getValue());
            }
        });
        
        // Add listener for session type to update statistics
        sessionTypeComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            Course selectedCourse = courseComboBox.getValue();
            if (newVal != null && selectedCourse != null) {
                updateAttendanceStatistics(selectedCourse.getCode(), newVal);
            }
        });

        // Set default date for attendance
        attendanceDatePicker.setValue(LocalDate.now());
        
        // Initialize statistics pane
        resetStatisticsDisplay();
        
        // Add debug info when loading students
        System.out.println("Attendance UI components initialized");
    }
    
    /**
     * Handle click on a student row in the attendance table
     * Shows detailed statistics for the selected student
     */
    private void handleStudentRowClick(Map<String, Object> studentData) {
        if (studentData == null) return;
        
        Course selectedCourse = courseComboBox.getValue();
        String sessionType = sessionTypeComboBox.getValue();
        
        if (selectedCourse == null) return;
        
        int studentId = (int) studentData.get("id");
        String studentName = (String) studentData.get("name");
        
        // Get detailed attendance history for this student
        Map<String, Object> history = attendanceService.getStudentAttendanceHistory(
                studentId, selectedCourse.getCode(), sessionType);
                
        if (history.containsKey("name")) {
            showStudentHistoryDialog(history, selectedCourse.getCode(), selectedCourse.getName());
        }
    }

    private void loadDashboardData() {
        // Load today's sessions
        List<Timetable> todaySessions = timetableService.getTodaySessions(currentDepartment);
        todaySessionsTableView.setItems(FXCollections.observableArrayList(todaySessions));

        // Load notices
        List<Notice> notices = noticeService.getNoticesForTechnicalOfficers(currentDepartment);
        noticeTableView.setItems(FXCollections.observableArrayList(notices));
        noticeCountLabel.setText(notices.size() + " New Notices");

        // Load medical record count
        List<Medical> pendingMedicals = medicalService.getPendingMedicals();
        medicalCountLabel.setText(pendingMedicals.size() + " Pending Approvals");

        // Load attendance count
        int todayAttendanceCount = attendanceService.getTodayAttendanceCount(currentDepartment);
        attendanceCountLabel.setText(todayAttendanceCount + " Records Today");
        
        // Update the dashboard with timetable information
        updateDashboardWithTimetable();
    }

    // Navigation handlers
    @FXML private void handleDashboardAction() {
        hideAllViews();
        dashboardView.setVisible(true);
        loadDashboardData(); // Refresh dashboard data
    }

    @FXML private void handleProfileAction() {
        hideAllViews();
        profileView.setVisible(true);
    }

    @FXML private void handleAttendanceAction() {
        hideAllViews();
        attendanceView.setVisible(true);
        resetStatisticsDisplay();
    }

    @FXML private void handleMedicalAction() {
        hideAllViews();
        medicalView.setVisible(true);
        loadMedicalRecords();
    }

    @FXML private void handleNoticeAction() {
        hideAllViews();
        noticeView.setVisible(true);
    }

    @FXML private void handleTimetableAction() {
        hideAllViews();
        timetableView.setVisible(true);
    }

    @FXML private void handleLogoutAction() {
        userService.logout();
        Stage stage = (Stage) dashboardView.getScene().getWindow();
        FormUtil.switchScene(stage, "Login.fxml", null);
    }

    // Profile handlers
    @FXML private void handleUpdateProfile() {
        currentUser.setName(nameField.getText());
        currentUser.setEmail(emailField.getText());
        currentUser.setPhone(phoneField.getText());
        currentUser.setDepartment(departmentComboBox.getValue());

        boolean success = userService.updateUser(currentUser);
        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Profile updated successfully");
            currentDepartment = currentUser.getDepartment();
            loadDashboardData();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to update profile");
        }
    }

    @FXML private void handleChangePassword() {
        // Show dialog to change password
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Change Password");
        dialog.setHeaderText("Enter new password");
        dialog.setContentText("New password:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && !result.get().isEmpty()) {
            boolean success = userService.changePassword(currentUser.getId(), result.get());
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Password changed successfully");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to change password");
            }
        }
    }

    @FXML private void handleChangePicture() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Profile Picture");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(profileView.getScene().getWindow());

        if (selectedFile != null) {
            try {
                // Convert the selected image to bytes
                byte[] imageData = convertImageToBytes(selectedFile);
                
                if (imageData != null && imageData.length > 0) {
                    // Update the user's profile picture in the database
                    boolean success = userService.updateProfilePicture(currentUser.getId(), imageData);
                    
                    if (success) {
                        // Update current user's profile picture
                        currentUser.setProfilePicture(imageData);
                        
                        // Refresh the profile pictures in the UI
                        loadProfilePicture();
                        
                        showAlert(Alert.AlertType.INFORMATION, "Success", "Profile picture updated successfully");
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Error", "Failed to update profile picture");
                    }
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Could not process the selected image");
                }
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Error reading image: " + e.getMessage());
            }
        }
    }
    
    private byte[] convertImageToBytes(File imageFile) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(imageFile);
        
        if (bufferedImage == null) {
            throw new IOException("Could not read image file");
        }
        
        // Create a square image to ensure proper circular display
        int size = Math.min(bufferedImage.getWidth(), bufferedImage.getHeight());
        
        // Calculate crop position to take center of image
        int x = (bufferedImage.getWidth() - size) / 2;
        int y = (bufferedImage.getHeight() - size) / 2;
        
        // Crop to square
        BufferedImage squareImage = bufferedImage.getSubimage(x, y, size, size);
        
        // Resize if too large
        int maxSize = 400; // Maximum width/height in pixels
        if (size > maxSize) {
            BufferedImage resizedImage = new BufferedImage(maxSize, maxSize, BufferedImage.TYPE_INT_ARGB);
            java.awt.Graphics2D g = resizedImage.createGraphics();
            g.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION, 
                              java.awt.RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g.drawImage(squareImage, 0, 0, maxSize, maxSize, null);
            g.dispose();
            squareImage = resizedImage;
        }
        
        // Convert to PNG byte array
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(squareImage, "png", outputStream);
        return outputStream.toByteArray();
    }

    private void loadProfilePicture() {
        Image profileImage = null;

        // Try to get the user's profile image
        if (currentUser != null && currentUser.getProfileImage() != null) {
            profileImage = currentUser.getProfileImage();
        }
        
        // If no profile image, use the default profile picture from resources
        if (profileImage == null) {
            try {
                profileImage = new Image(getClass().getResourceAsStream("/Images/default_profile.png"));
                if (profileImage.isError()) {
                    throw new Exception("Error loading default profile image");
                }
            } catch (Exception e) {
                System.err.println("Could not load default profile image: " + e.getMessage());
                // Last resort - create a simple default image
                profileImage = new Image(new ByteArrayInputStream(new byte[0]));
            }
        }
        
        // Set the image in both ImageView components
        profileImageView.setImage(profileImage);
        profilePictureView.setImage(profileImage);
        
        // Apply circular clip to both image views
        setCircularClip(profileImageView, 50);
        setCircularClip(profilePictureView, 75);
    }
    
    /**
     * Apply a circular clip to an ImageView
     * @param imageView The ImageView to clip
     * @param radius The radius of the circle
     */
    private void setCircularClip(ImageView imageView, double radius) {
        javafx.scene.shape.Circle clip = new javafx.scene.shape.Circle(radius, radius, radius);
        imageView.setClip(clip);
    }

    // Attendance handlers
    @FXML private void handleLoadStudents() {
        Course selectedCourse = courseComboBox.getValue();
        String sessionType = sessionTypeComboBox.getValue();
        LocalDate date = attendanceDatePicker.getValue();

        if (selectedCourse == null || sessionType == null || date == null) {
            showAlert(Alert.AlertType.WARNING, "Missing Data", "Please select course, session type and date");
            return;
        }

        List<Map<String, Object>> attendanceData = attendanceService.getAttendanceData(
                selectedCourse.getCode(), sessionType, date);
        attendanceTableView.setItems(FXCollections.observableArrayList(attendanceData));
        
        // Update statistics
        updateAttendanceStatistics(selectedCourse.getCode(), sessionType);
        
        // Expand the statistics pane to show data
        statisticsPane.setExpanded(true);
    }

    @FXML private void handleSaveAttendance() {
        Course selectedCourse = courseComboBox.getValue();
        String sessionType = sessionTypeComboBox.getValue();
        LocalDate date = attendanceDatePicker.getValue();

        if (selectedCourse == null || sessionType == null || date == null) {
            showAlert(Alert.AlertType.WARNING, "Missing Data", "Please select course, session type and date");
            return;
        }

        List<Map<String, Object>> attendanceData = new ArrayList<>(attendanceTableView.getItems());
        boolean success = attendanceService.saveAttendance(
                selectedCourse.getCode(), sessionType, date, attendanceData, currentUser.getId());

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Attendance saved successfully");
            loadDashboardData();
            
            // Update statistics after saving
            updateAttendanceStatistics(selectedCourse.getCode(), sessionType);
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to save attendance");
        }
    }

    @FXML private void handleGenerateAttendanceReport() {
        Course selectedCourse = courseComboBox.getValue();

        if (selectedCourse == null) {
            showAlert(Alert.AlertType.WARNING, "Missing Data", "Please select a course");
            return;
        }

        boolean success = attendanceService.generateReport(selectedCourse.getCode(), currentDepartment);
        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Report generated successfully");
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to generate report");
        }
    }
    
    @FXML private void handleViewDetailedReport() {
        Course selectedCourse = courseComboBox.getValue();
        String sessionType = sessionTypeComboBox.getValue();
        
        if (selectedCourse == null) {
            showAlert(Alert.AlertType.WARNING, "Missing Data", "Please select a course");
            return;
        }
        
        // Create detailed report window with course statistics
        createDetailedReportWindow(selectedCourse.getCode(), selectedCourse.getName(), sessionType);
    }
    
    private void createDetailedReportWindow(String courseCode, String courseName, String sessionType) {
        // In a real application, this would create a detailed report window
        // For this example, we'll just show the statistics in an alert dialog
        AttendanceStatistics stats = attendanceService.getCourseAttendanceStatistics(courseCode, sessionType);
        
        StringBuilder report = new StringBuilder();
        report.append("Detailed Attendance Report\n\n");
        report.append("Course: ").append(courseCode).append(" - ").append(courseName).append("\n");
        report.append("Session Type: ").append(sessionType != null ? sessionType : "All").append("\n\n");
        report.append("Total Sessions: ").append(stats.getTotalSessions()).append("\n");
        report.append("Total Students Enrolled: ").append(stats.getEnrolledStudents()).append("\n\n");
        
        report.append("Overall Attendance Rate: ").append(stats.getFormattedOverallAttendanceRate()).append("\n");
        report.append("Total Present: ").append(stats.getPresentCount()).append("\n");
        report.append("Total Absent: ").append(stats.getAbsentCount()).append("\n");
        report.append("Total Medical Leave: ").append(stats.getMedicalLeaveCount()).append("\n\n");
        
        report.append("Students with Perfect Attendance: ").append(stats.getPerfectAttendanceCount()).append("\n");
        report.append("Students with Critical Attendance (<80%): ").append(stats.getCriticalAttendanceCount());
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Detailed Attendance Report");
        alert.setHeaderText("Attendance Report for " + courseCode);
        
        TextArea textArea = new TextArea(report.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefWidth(500);
        textArea.setPrefHeight(300);
        
        alert.getDialogPane().setContent(textArea);
        alert.setResizable(true);
        alert.showAndWait();
    }
    
    @FXML private void handleViewStudentHistory() {
        Course selectedCourse = courseComboBox.getValue();
        String sessionType = sessionTypeComboBox.getValue();
        
        if (selectedCourse == null) {
            showAlert(Alert.AlertType.WARNING, "Missing Data", "Please select a course");
            return;
        }
        
        // Show a dialog to select a student
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("View Student Attendance History");
        dialog.setHeaderText("Enter Student ID");
        dialog.setContentText("Student ID:");
        
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && !result.get().isEmpty()) {
            try {
                int studentId = Integer.parseInt(result.get());
                Map<String, Object> history = attendanceService.getStudentAttendanceHistory(
                        studentId, selectedCourse.getCode(), sessionType);
                
                if (history.containsKey("name")) {
                    showStudentHistoryDialog(history, selectedCourse.getCode(), selectedCourse.getName());
                } else {
                    showAlert(Alert.AlertType.WARNING, "Not Found", "Student records not found");
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a valid student ID");
            }
        }
    }
    
    /**
     * Shows a dialog with detailed attendance history for a student
     * @param history Attendance history data
     * @param courseCode The course code
     * @param courseName The course name
     */
    private void showStudentHistoryDialog(Map<String, Object> history, String courseCode, String courseName) {
        if (history == null || history.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "No Data", "No attendance history available for this student");
            return;
        }
        
        // Get student info
        String studentName = (String) history.get("name");
        String regNumber = (String) history.get("registrationNumber");
        Double attendanceRate = (Double) history.get("attendanceRate");
        Integer totalAttended = (Integer) history.get("totalAttended");
        Integer totalAbsent = (Integer) history.get("totalAbsent");
        Integer totalMedical = (Integer) history.get("totalMedical");
        Integer totalSessions = (Integer) history.get("totalSessions");
        
        // Create dialog
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Student Attendance History");
        dialog.setHeaderText(studentName + " (" + regNumber + ")");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        
        // Create a horizontal layout using SplitPane
        SplitPane splitPane = new SplitPane();
        splitPane.setDividerPositions(0.35);
        
        // Left side - Statistics and Chart
        VBox leftPane = new VBox(10);
        leftPane.setPadding(new javafx.geometry.Insets(10));
        leftPane.setMinWidth(250);
        
        // Add course info
        Label courseLabel = new Label("Course: " + courseCode + " - " + courseName);
        courseLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        leftPane.getChildren().add(courseLabel);
        
        // Statistics section
        TitledPane statisticsPane = new TitledPane();
        statisticsPane.setText("Attendance Statistics");
        statisticsPane.setCollapsible(false);
        
        GridPane statsGrid = new GridPane();
        statsGrid.setHgap(20);
        statsGrid.setVgap(10);
        statsGrid.setPadding(new javafx.geometry.Insets(10));
        
        // Add attendance rate with color coding
        Label attendanceRateLabel = new Label("Attendance Rate:");
        Label attendanceRateValue = new Label(String.format("%.1f%%", attendanceRate));
        if (attendanceRate < 60.0) {
            attendanceRateValue.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        } else if (attendanceRate < 80.0) {
            attendanceRateValue.setStyle("-fx-text-fill: orange; -fx-font-weight: bold;");
        } else {
            attendanceRateValue.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
        }
        
        // Add all statistics
        statsGrid.add(attendanceRateLabel, 0, 0);
        statsGrid.add(attendanceRateValue, 1, 0);
        statsGrid.add(new Label("Total Sessions:"), 0, 1);
        statsGrid.add(new Label(String.valueOf(totalSessions)), 1, 1);
        statsGrid.add(new Label("Sessions Attended:"), 0, 2);
        statsGrid.add(new Label(String.valueOf(totalAttended)), 1, 2);
        statsGrid.add(new Label("Absences:"), 0, 3);
        statsGrid.add(new Label(String.valueOf(totalAbsent)), 1, 3);
        statsGrid.add(new Label("Medical Leave:"), 0, 4);
        statsGrid.add(new Label(String.valueOf(totalMedical)), 1, 4);
        
        statisticsPane.setContent(statsGrid);
        leftPane.getChildren().add(statisticsPane);
        
        // Add attendance trend chart if there are records
        @SuppressWarnings("unchecked") 
        List<Attendance> attendanceRecords = (List<Attendance>) history.get("attendanceRecords");
        if (attendanceRecords != null && !attendanceRecords.isEmpty() && attendanceRecords.size() > 1) {
            TitledPane chartPane = new TitledPane();
            chartPane.setText("Attendance Trend");
            chartPane.setCollapsible(false);
            
            // Create attendance trend chart
            LineChart<String, Number> lineChart = createAttendanceTrendChart(attendanceRecords);
            chartPane.setContent(lineChart);
            leftPane.getChildren().add(chartPane);
        }
        
        // Right side - Attendance Records
        VBox rightPane = new VBox(10);
        rightPane.setPadding(new javafx.geometry.Insets(10));
        rightPane.setMinWidth(300);
        
        // Attendance records section
        TitledPane historyPane = new TitledPane();
        historyPane.setText("Attendance Records");
        historyPane.setCollapsible(false);
        
        TableView<Attendance> recordsTable = new TableView<>();
        recordsTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        
        // Date column
        TableColumn<Attendance, LocalDate> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateCol.setCellFactory(column -> new TableCell<Attendance, LocalDate>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString());
                }
            }
        });
        
        // Session type column
        TableColumn<Attendance, String> sessionCol = new TableColumn<>("Session Type");
        sessionCol.setCellValueFactory(new PropertyValueFactory<>("sessionType"));
        
        // Status column
        TableColumn<Attendance, Boolean> statusCol = new TableColumn<>("Status");
        statusCol.setCellFactory(column -> new TableCell<Attendance, Boolean>() {
            @Override
            protected void updateItem(Boolean isPresent, boolean empty) {
                super.updateItem(isPresent, empty);
                if (empty) {
                    setText(null);
                    setStyle("");
                } else {
                    Attendance attendance = getTableView().getItems().get(getIndex());
                    if (attendance.isPresent()) {
                        setText("Present");
                        setStyle("-fx-text-fill: green;");
                    } else if (attendance.hasMedical()) {
                        setText("Medical");
                        setStyle("-fx-text-fill: blue;");
                    } else {
                        setText("Absent");
                        setStyle("-fx-text-fill: red;");
                    }
                }
            }
        });
        statusCol.setCellValueFactory(data -> new javafx.beans.property.SimpleBooleanProperty(data.getValue().isPresent()));
        
        // Notes column
        TableColumn<Attendance, String> notesCol = new TableColumn<>("Notes");
        notesCol.setCellValueFactory(new PropertyValueFactory<>("notes"));
        
        // Using explicit generic types to avoid varargs warning
        recordsTable.getColumns().setAll(dateCol, sessionCol, statusCol, notesCol);
        
        // Set the data
        recordsTable.setItems(FXCollections.observableArrayList(attendanceRecords));
        
        historyPane.setContent(recordsTable);
        rightPane.getChildren().add(historyPane);
        
        // Add both sides to the split pane
        splitPane.getItems().addAll(leftPane, rightPane);
        
        // Set preferred sizes
        dialog.getDialogPane().setPrefWidth(800);
        dialog.getDialogPane().setPrefHeight(500);
        
        dialog.getDialogPane().setContent(splitPane);
        dialog.setResizable(true);
        dialog.showAndWait();
    }
    
    /**
     * Creates a line chart showing the student's attendance trend over time
     * @param attendanceRecords List of attendance records
     * @return LineChart showing attendance trend
     */
    private LineChart<String, Number> createAttendanceTrendChart(List<Attendance> attendanceRecords) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis(0, 1, 0.5);
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        
        xAxis.setLabel("Date");
        yAxis.setLabel("Attendance");
        lineChart.setTitle("Attendance Over Time");
        lineChart.setLegendVisible(false);
        
        // Create a series for attendance data
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        
        // Sort records by date
        attendanceRecords.sort(Comparator.comparing(Attendance::getDate));
        
        // Add data points for each record
        for (Attendance record : attendanceRecords) {
            String dateStr = record.getDate().toString();
            double value = record.isPresent() || record.hasMedical() ? 1.0 : 0.0;
            series.getData().add(new XYChart.Data<>(dateStr, value));
        }
        
        lineChart.getData().add(series);
        return lineChart;
    }
    
    @FXML private void handleUpdateStatistics() {
        Course selectedCourse = courseComboBox.getValue();
        String sessionType = sessionTypeComboBox.getValue();
        
        if (selectedCourse == null || sessionType == null) {
            showAlert(Alert.AlertType.WARNING, "Missing Data", "Please select course and session type");
            return;
        }
        
        updateAttendanceStatistics(selectedCourse.getCode(), sessionType);
    }
    
    /**
     * Updates the statistics display with data for the selected course and session type
     */
    private void updateAttendanceStatistics(String courseCode, String sessionType) {
        // Get comprehensive statistics
        AttendanceStatistics stats = attendanceService.getCourseAttendanceStatistics(courseCode, sessionType);
        currentStatistics = stats;
        
        // Update UI elements
        overallAttendanceLabel.setText(stats.getFormattedOverallAttendanceRate());
        presentStudentsLabel.setText(String.valueOf(stats.getPresentCount()));
        absentStudentsLabel.setText(String.valueOf(stats.getAbsentCount()));
        medicalLeaveLabel.setText(String.valueOf(stats.getMedicalLeaveCount()));
        
        totalSessionsLabel.setText(String.valueOf(stats.getTotalSessions()));
        todaySessionsLabel.setText(String.valueOf(stats.getTodaySessions()));
        todayRateLabel.setText(stats.getFormattedTodayAttendanceRate());
        
        perfectAttendanceLabel.setText(String.valueOf(stats.getPerfectAttendanceCount()));
        criticalAttendanceLabel.setText(String.valueOf(stats.getCriticalAttendanceCount()));
        totalEnrolledLabel.setText(String.valueOf(stats.getEnrolledStudents()));
        
        // Apply color coding for overall attendance rate
        if (stats.getOverallAttendanceRate() < 60.0) {
            overallAttendanceLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        } else if (stats.getOverallAttendanceRate() < 80.0) {
            overallAttendanceLabel.setStyle("-fx-text-fill: orange; -fx-font-weight: bold;");
        } else {
            overallAttendanceLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
        }
        
        // Highlight critical attendance count if it's high
        double criticalRate = stats.getEnrolledStudents() > 0 ? 
                (double) stats.getCriticalAttendanceCount() / stats.getEnrolledStudents() * 100.0 : 0.0;
        if (criticalRate > 20.0) {
            criticalAttendanceLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        } else {
            criticalAttendanceLabel.setStyle("");
        }
    }
    
    /**
     * Reset statistics display to default values
     */
    private void resetStatisticsDisplay() {
        overallAttendanceLabel.setText("0%");
        presentStudentsLabel.setText("0");
        absentStudentsLabel.setText("0");
        medicalLeaveLabel.setText("0");
        
        totalSessionsLabel.setText("0");
        todaySessionsLabel.setText("0");
        todayRateLabel.setText("0%");
        
        perfectAttendanceLabel.setText("0");
        criticalAttendanceLabel.setText("0");
        totalEnrolledLabel.setText("0");
        
        overallAttendanceLabel.setStyle("");
        criticalAttendanceLabel.setStyle("");
        
        currentStatistics = null;
    }

    // Medical handlers
    @FXML private void handleSearchStudent() {
        String studentId = searchStudentField.getText();
        if (studentId.isEmpty()) {
            loadMedicalRecords();
        } else {
            try {
                int id = Integer.parseInt(studentId);
                List<Medical> medicals = medicalService.getMedicalsByStudentId(id);
                medicalTableView.setItems(FXCollections.observableArrayList(medicals));
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a valid student ID");
            }
        }
    }

    private void loadMedicalRecords() {
        List<Medical> medicals = medicalService.getAllMedicals();
        medicalTableView.setItems(FXCollections.observableArrayList(medicals));
    }

    @FXML private void handleAddMedical() {
        try {
            int studentId = Integer.parseInt(medicalStudentIdField.getText());
            LocalDate startDate = medicalStartDatePicker.getValue();
            LocalDate endDate = medicalEndDatePicker.getValue();
            String reason = medicalReasonField.getText();
            String status = medicalStatusComboBox.getValue();

            if (startDate == null || endDate == null || reason.isEmpty() || status == null) {
                showAlert(Alert.AlertType.WARNING, "Missing Data", "Please fill all fields");
                return;
            }

            Medical medical = new Medical(0, studentId, startDate, endDate, reason, status);
            boolean success = medicalService.addMedical(medical);

            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Medical record added successfully");
                handleClearMedical();
                loadMedicalRecords();
                loadDashboardData();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add medical record");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a valid student ID");
        }
    }

    @FXML private void handleUpdateMedical() {
        Medical selectedMedical = medicalTableView.getSelectionModel().getSelectedItem();
        if (selectedMedical == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a medical record to update");
            return;
        }

        String status = medicalStatusComboBox.getValue();
        if (status == null) {
            showAlert(Alert.AlertType.WARNING, "Missing Data", "Please select a status");
            return;
        }

        selectedMedical.setStatus(status);
        selectedMedical.setApprovedBy(currentUser.getId());

        boolean success = medicalService.updateMedical(selectedMedical);
        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Medical record updated successfully");
            loadMedicalRecords();
            loadDashboardData();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to update medical record");
        }
    }

    @FXML private void handleClearMedical() {
        medicalStudentIdField.clear();
        medicalStartDatePicker.setValue(null);
        medicalEndDatePicker.setValue(null);
        medicalReasonField.clear();
        medicalStatusComboBox.setValue(null);
    }
    
    @FXML private void handleRemoveMedical() {
        Medical selectedMedical = medicalTableView.getSelectionModel().getSelectedItem();
        if (selectedMedical == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a medical record to remove");
            return;
        }

        // Show confirmation dialog
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Removal");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to remove this medical record? This action cannot be undone.");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean success = medicalService.removeMedical(selectedMedical.getId());
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Medical record removed successfully");
                loadMedicalRecords();
                loadDashboardData();
                handleClearMedical(); // Clear form fields
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to remove medical record");
            }
        }
    }

    // Timetable handlers
    @FXML private void handleViewTimetable() {
        String department = departmentTimetableComboBox.getValue();
        Integer semester = semesterComboBox.getValue();

        if (department == null || semester == null) {
            showAlert(Alert.AlertType.WARNING, "Missing Data", "Please select a department and semester");
            return;
        }

        List<Timetable> timetableList = timetableService.getTimetableByDepartmentAndSemester(department, semester);
        populateTimetableView(timetableList);
    }

    private void populateTimetableView(List<Timetable> timetableList) {
        // Clear existing data
        timetableTableView.getItems().clear();
        
        // Prepare data structure for the timetable
        Map<String, Map<String, Timetable>> timetableData = new HashMap<>();
        
        // Define default time slots if needed
        List<String> defaultTimeSlots = Arrays.asList(
            "08:00-09:00", "09:00-10:00", "10:00-11:00", "11:00-12:00", 
            "13:00-14:00", "14:00-15:00", "15:00-16:00", "16:00-17:00"
        );
        
        // Process timetable entries
        for (Timetable entry : timetableList) {
            String entryTime = entry.getStartTime() + "-" + entry.getEndTime();
            if (!timetableData.containsKey(entryTime)) {
                timetableData.put(entryTime, new HashMap<>());
            }
            timetableData.get(entryTime).put(entry.getDayOfWeek(), entry);
        }
        
        // Sort time slots
        List<String> timeSlots = new ArrayList<>(timetableData.keySet());
        if (timeSlots.isEmpty()) {
            // Use default time slots if no data
            timeSlots = new ArrayList<>(defaultTimeSlots);
        } else {
            Collections.sort(timeSlots, (t1, t2) -> {
                LocalTime time1 = LocalTime.parse(t1.split("-")[0]);
                LocalTime time2 = LocalTime.parse(t2.split("-")[0]);
                return time1.compareTo(time2);
            });
        }
        
        // Configure column widths for better display
        timeColumn.setPrefWidth(100);
        mondayColumn.setPrefWidth(150);
        tuesdayColumn.setPrefWidth(150);
        wednesdayColumn.setPrefWidth(150);
        thursdayColumn.setPrefWidth(150);
        fridayColumn.setPrefWidth(150);
        
        // Configure the cell value factories
        timeColumn.setCellValueFactory(data -> {
            String time = (String) data.getValue().get("time");
            return new javafx.beans.property.SimpleStringProperty(time);
        });
        
        // Create a custom cell factory that will display timetable entries with formatting
        Callback<TableColumn<Map<String, Object>, String>, TableCell<Map<String, Object>, String>> cellFactory = 
            column -> new TableCell<Map<String, Object>, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    
                    // Clear any existing styling
                    getStyleClass().removeAll("theory-session", "practical-session", "timetable-empty-cell", "timetable-cell");
                    
                    if (empty || item == null || item.isEmpty()) {
                        setText(null);
                        setGraphic(null);
                        getStyleClass().add("timetable-empty-cell");
                    } else {
                        setText(item);
                        setWrapText(true);
                        getStyleClass().add("timetable-cell");
                        
                        // Apply styling based on session type if the content contains "Theory" or "Practical"
                        if (item.contains("Theory")) {
                            getStyleClass().add("theory-session");
                        } else if (item.contains("Practical")) {
                            getStyleClass().add("practical-session");
                        }
                        
                        // Set tooltip for better readability
                        setTooltip(new Tooltip(item));
                    }
                }
            };
        
        // Set cell factories for all day columns
        mondayColumn.setCellFactory(cellFactory);
        tuesdayColumn.setCellFactory(cellFactory);
        wednesdayColumn.setCellFactory(cellFactory);
        thursdayColumn.setCellFactory(cellFactory);
        fridayColumn.setCellFactory(cellFactory);
        
        // Configure value factories for day columns
        mondayColumn.setCellValueFactory(data -> {
            Timetable entry = (Timetable) data.getValue().get("monday");
            if (entry == null) return new javafx.beans.property.SimpleStringProperty("");
            
            return new javafx.beans.property.SimpleStringProperty(formatTimetableEntry(entry));
        });
        
        tuesdayColumn.setCellValueFactory(data -> {
            Timetable entry = (Timetable) data.getValue().get("tuesday");
            if (entry == null) return new javafx.beans.property.SimpleStringProperty("");
            
            return new javafx.beans.property.SimpleStringProperty(formatTimetableEntry(entry));
        });
        
        wednesdayColumn.setCellValueFactory(data -> {
            Timetable entry = (Timetable) data.getValue().get("wednesday");
            if (entry == null) return new javafx.beans.property.SimpleStringProperty("");
            
            return new javafx.beans.property.SimpleStringProperty(formatTimetableEntry(entry));
        });
        
        thursdayColumn.setCellValueFactory(data -> {
            Timetable entry = (Timetable) data.getValue().get("thursday");
            if (entry == null) return new javafx.beans.property.SimpleStringProperty("");
            
            return new javafx.beans.property.SimpleStringProperty(formatTimetableEntry(entry));
        });
        
        fridayColumn.setCellValueFactory(data -> {
            Timetable entry = (Timetable) data.getValue().get("friday");
            if (entry == null) return new javafx.beans.property.SimpleStringProperty("");
            
            return new javafx.beans.property.SimpleStringProperty(formatTimetableEntry(entry));
        });
        
        // Setup row height
        timetableTableView.setFixedCellSize(80);
        
        // Now add the data to the table
        for (String timeSlot : timeSlots) {
            Map<String, Timetable> dayData = timetableData.getOrDefault(timeSlot, new HashMap<>());
            
            Map<String, Object> row = new HashMap<>();
            row.put("time", timeSlot);
            row.put("monday", dayData.get("Monday"));
            row.put("tuesday", dayData.get("Tuesday"));
            row.put("wednesday", dayData.get("Wednesday"));
            row.put("thursday", dayData.get("Thursday"));
            row.put("friday", dayData.get("Friday"));
            
            timetableTableView.getItems().add(row);
        }
        
        // Set double-click handler for editing timetable entries
        timetableTableView.setRowFactory(tv -> {
            TableRow<Map<String, Object>> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    // Double-click detected, show dialog to edit or add entry
                    handleEditTimetableEntry(row.getItem());
                }
            });
            return row;
        });
    }

    /**
     * Format a timetable entry for display in the table
     */
    private String formatTimetableEntry(Timetable entry) {
        if (entry == null) return "";
        
        StringBuilder sb = new StringBuilder();
        sb.append(entry.getCourseCode()).append("\n");
        sb.append(entry.getSessionType()).append("\n");
        sb.append(entry.getVenue());
        
        // Add lecturer info if available
        if (entry.getLecturerId() != null) {
            User lecturer = userService.getUserById(entry.getLecturerId());
            if (lecturer != null) {
                sb.append("\n").append(lecturer.getName());
            }
        }
        
        return sb.toString();
    }

    /**
     * Handle editing or adding a timetable entry
     */
    private void handleEditTimetableEntry(Map<String, Object> rowData) {
        // This would be implemented in a real application to allow editing timetable entries
        // For now, we'll just show an info dialog
        showAlert(Alert.AlertType.INFORMATION, "Edit Timetable", 
                  "This feature would allow editing timetable entries.\n" +
                  "Time slot: " + rowData.get("time"));
    }

    @FXML private void handleExportTimetable() {
        if (timetableTableView.getItems().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "No Data", "No timetable data to export");
            return;
        }

        String department = departmentTimetableComboBox.getValue();
        Integer semester = semesterComboBox.getValue();
        
        if (department == null || semester == null) {
            showAlert(Alert.AlertType.WARNING, "Missing Data", "Please select a department and semester");
            return;
        }

        // In a real application, this would export to PDF
        // For demonstration purposes, we'll just show a message
        showAlert(Alert.AlertType.INFORMATION, "Export", 
                  "Timetable for " + department + " - Semester " + semester + " exported to PDF.\n" +
                  "File: " + department + "_Semester_" + semester + "_Timetable.pdf");
    }

    /**
     * Update the dashboard with a timetable card
     */
    private void updateDashboardWithTimetable() {
        // Add a timetable card to the dashboard
        VBox timetableCard = new VBox(10);
        timetableCard.getStyleClass().add("timetable-dashboard-card");
        timetableCard.setMaxWidth(Double.MAX_VALUE);
        
        Label titleLabel = new Label("Today's Timetable");
        titleLabel.getStyleClass().add("timetable-card-title");
        
        List<Timetable> todaySessions = timetableService.getTodaySessions(currentDepartment);
        Label countLabel = new Label(todaySessions.size() + " Sessions Today");
        countLabel.getStyleClass().add("timetable-card-count");
        
        Button viewButton = new Button("View Timetable");
        viewButton.getStyleClass().add("action-button");
        viewButton.setOnAction(e -> handleTimetableAction());
        
        timetableCard.getChildren().addAll(titleLabel, countLabel, viewButton);
        
        // Find the HBox in the dashboard and add the timetable card
        for (javafx.scene.Node node : dashboardView.getChildrenUnmodifiable()) {
            if (node instanceof VBox) {
                VBox dashboardVBox = (VBox) node;
                for (javafx.scene.Node innerNode : dashboardVBox.getChildren()) {
                    if (innerNode instanceof HBox) {
                        HBox dashboardHBox = (HBox) innerNode;
                        
                        // Check if there are already 3 cards
                        if (dashboardHBox.getChildren().size() >= 3) {
                            // Replace the third card with our timetable card
                            dashboardHBox.getChildren().set(2, timetableCard);
                        } else {
                            // Add as a new card
                            dashboardHBox.getChildren().add(timetableCard);
                        }
                        break;
                    }
                }
                break;
            }
        }
    }

    @FXML private void handlePrintTimetable() {
        if (timetableTableView.getItems().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "No Data", "No timetable data to print");
            return;
        }

        // Print logic would go here
        showAlert(Alert.AlertType.INFORMATION, "Print", "Timetable sent to printer");
    }

    // System Administration handlers
    @FXML private void handleEnsureEnrollments() {
        // Show confirmation dialog first
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Ensure Course Enrollments");
        confirmAlert.setHeaderText("Add Sample Students to Courses");
        confirmAlert.setContentText("This will add department-specific sample students to any courses that have " +
                                   "insufficient enrollments. Do you want to continue?");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Use the existing method from AttendanceService to add students to courses with insufficient enrollments
            int minStudents = 10; // Minimum number of students per course
            int studentsAdded = attendanceService.ensureSufficientEnrollments(minStudents);
            
            if (studentsAdded > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Students Added", 
                          "Successfully added " + studentsAdded + " student enrollments to courses.");
                
                // Refresh attendance data if a course is selected
                Course selectedCourse = courseComboBox.getValue();
                String sessionType = sessionTypeComboBox.getValue();
                if (selectedCourse != null && sessionType != null) {
                    // Reload attendance data for the selected course
                    handleLoadStudents();
                }
            } else {
                showAlert(Alert.AlertType.INFORMATION, "No Changes", 
                          "All courses already have sufficient student enrollments.");
            }
        }
    }

    // Helper methods
    private void hideAllViews() {
        profileView.setVisible(false);
        attendanceView.setVisible(false);
        medicalView.setVisible(false);
        noticeView.setVisible(false);
        timetableView.setVisible(false);
        dashboardView.setVisible(false);
    }

    private void showDashboardView() {
        hideAllViews();
        dashboardView.setVisible(true);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}