-- Drop existing tables in reverse dependency order
DROP TABLE IF EXISTS Attendance;
DROP TABLE IF EXISTS Medical;
DROP TABLE IF EXISTS Mark;
DROP TABLE IF EXISTS Enrollment;
DROP TABLE IF EXISTS Timetable;
DROP TABLE IF EXISTS Notice;
DROP TABLE IF EXISTS Course;
DROP TABLE IF EXISTS User;

-- Create User table
CREATE TABLE User (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      username VARCHAR(50) UNIQUE NOT NULL,
                      password VARCHAR(100) NOT NULL,
                      name VARCHAR(100) NOT NULL,
                      email VARCHAR(100) UNIQUE NOT NULL,
                      phone VARCHAR(20),
                      department VARCHAR(50),
                      role ENUM('Admin', 'Lecturer', 'Technical Officer', 'Student') NOT NULL,
                      registration_number VARCHAR(20) UNIQUE,
                      profile_picture LONGBLOB,
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Course table
CREATE TABLE Course (
                        code VARCHAR(10) PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        credits DECIMAL(3,1) NOT NULL,
                        theory_hours INT DEFAULT 0,
                        practical_hours INT DEFAULT 0,
                        semester INT NOT NULL,
                        department VARCHAR(50) NOT NULL,
                        description TEXT
);

-- Create Enrollment table
CREATE TABLE Enrollment (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            student_id INT NOT NULL,
                            course_code VARCHAR(10) NOT NULL,
                            academic_year VARCHAR(9) NOT NULL,
                            FOREIGN KEY (student_id) REFERENCES User(id) ON DELETE CASCADE,
                            FOREIGN KEY (course_code) REFERENCES Course(code) ON DELETE CASCADE,
                            UNIQUE KEY (student_id, course_code, academic_year)
);

-- Create Mark table
CREATE TABLE Mark (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      student_id INT NOT NULL,
                      course_code VARCHAR(10) NOT NULL,
                      assessment_type ENUM('Quiz', 'Mid Term', 'Assessment', 'Final Theory', 'Final Practical') NOT NULL,
                      assessment_number INT NOT NULL,
                      marks DECIMAL(5,2) NOT NULL,
                      out_of DECIMAL(5,2) NOT NULL DEFAULT 100.00,
                      recorded_by INT NOT NULL,
                      recorded_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      FOREIGN KEY (student_id) REFERENCES User(id) ON DELETE CASCADE,
                      FOREIGN KEY (course_code) REFERENCES Course(code) ON DELETE CASCADE,
                      FOREIGN KEY (recorded_by) REFERENCES User(id) ON DELETE CASCADE,
                      UNIQUE KEY (student_id, course_code, assessment_type, assessment_number)
);

-- Create Attendance table
CREATE TABLE Attendance (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            student_id INT NOT NULL,
                            course_code VARCHAR(10) NOT NULL,
                            date DATE NOT NULL,
                            session_type ENUM('Theory', 'Practical') NOT NULL,
                            is_present BOOLEAN NOT NULL DEFAULT FALSE,
                            has_medical BOOLEAN NOT NULL DEFAULT FALSE,
                            notes TEXT,
                            recorded_by INT NOT NULL,
                            FOREIGN KEY (student_id) REFERENCES User(id) ON DELETE CASCADE,
                            FOREIGN KEY (course_code) REFERENCES Course(code) ON DELETE CASCADE,
                            FOREIGN KEY (recorded_by) REFERENCES User(id) ON DELETE CASCADE,
                            UNIQUE KEY (student_id, course_code, date, session_type)
);

-- Create Medical table
CREATE TABLE Medical (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         student_id INT NOT NULL,
                         start_date DATE NOT NULL,
                         end_date DATE NOT NULL,
                         reason TEXT NOT NULL,
                         status ENUM('Pending', 'Approved', 'Rejected') NOT NULL DEFAULT 'Pending',
                         submitted_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         approved_by INT,
                         FOREIGN KEY (student_id) REFERENCES User(id) ON DELETE CASCADE,
                         FOREIGN KEY (approved_by) REFERENCES User(id) ON DELETE SET NULL
);

-- Create Notice table
CREATE TABLE Notice (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        title VARCHAR(200) NOT NULL,
                        content TEXT NOT NULL,
                        posted_by INT NOT NULL,
                        post_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        target_roles VARCHAR(100),
                        target_departments VARCHAR(100),
                        FOREIGN KEY (posted_by) REFERENCES User(id) ON DELETE CASCADE
);

-- Create Timetable table
CREATE TABLE Timetable (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           course_code VARCHAR(10) NOT NULL,
                           day_of_week ENUM('Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday') NOT NULL,
                           start_time TIME NOT NULL,
                           end_time TIME NOT NULL,
                           venue VARCHAR(50) NOT NULL,
                           session_type ENUM('Theory', 'Practical') NOT NULL,
                           lecturer_id INT,
                           FOREIGN KEY (course_code) REFERENCES Course(code) ON DELETE CASCADE,
                           FOREIGN KEY (lecturer_id) REFERENCES User(id) ON DELETE SET NULL
);

-- Insert sample data

-- Admin (1)
INSERT INTO User (username, password, name, email, phone, role)
VALUES ('admin', 'admin123', 'System Administrator', 'admin@ruhuna.ac.lk', '0770000000', 'Admin');

-- Lecturers (5)
INSERT INTO User (username, password, name, email, phone, department, role) VALUES
                                                                                ('kamal', 'pass123', 'Dr. Kamal Perera', 'kamal@ruhuna.ac.lk', '0771234561', 'ICT', 'Lecturer'),
                                                                                ('nimal', 'pass123', 'Prof. Nimal Silva', 'nimal@ruhuna.ac.lk', '0771234562', 'ICT', 'Lecturer'),
                                                                                ('saman', 'pass123', 'Dr. Saman Fernando', 'saman@ruhuna.ac.lk', '0771234563', 'ICT', 'Lecturer'),
                                                                                ('sunil', 'pass123', 'Mr. Sunil Gunaratne', 'sunil@ruhuna.ac.lk', '0771234564', 'ET', 'Lecturer'),
                                                                                ('priya', 'pass123', 'Ms. Priya Dissanayake', 'priya@ruhuna.ac.lk', '0771234565', 'BST', 'Lecturer');

-- Technical Officers (4)
INSERT INTO User (username, password, name, email, phone, department, role) VALUES
                                                                                ('lakmal', 'pass123', 'Lakmal Jayasuriya', 'lakmal@ruhuna.ac.lk', '0772345671', 'ICT', 'Technical Officer'),
                                                                                ('amara', 'pass123', 'Amara Perera', 'amara@ruhuna.ac.lk', '0772345672', 'ICT', 'Technical Officer'),
                                                                                ('kasun', 'pass123', 'Kasun Bandara', 'kasun@ruhuna.ac.lk', '0772345673', 'ET', 'Technical Officer'),
                                                                                ('chamara', 'pass123', 'Chamara Rathnayake', 'chamara@ruhuna.ac.lk', '0772345674', 'BST', 'Technical Officer');

-- Students (20)
INSERT INTO User (username, password, name, email, phone, department, role, registration_number) VALUES
                                                                                                     ('ICT19001', 'pass123', 'Dinesh Rajapaksa', 'dinesh@student.ruhuna.ac.lk', '0773456781', 'ICT', 'Student', 'ICT/19/001'),
                                                                                                     ('ICT19002', 'pass123', 'Samanthi Fernando', 'samanthi@student.ruhuna.ac.lk', '0773456782', 'ICT', 'Student', 'ICT/19/002'),
                                                                                                     ('ICT19003', 'pass123', 'Ruwan Kumara', 'ruwan@student.ruhuna.ac.lk', '0773456783', 'ICT', 'Student', 'ICT/19/003'),
                                                                                                     ('ICT19004', 'pass123', 'Thilini Perera', 'thilini@student.ruhuna.ac.lk', '0773456784', 'ICT', 'Student', 'ICT/19/004'),
                                                                                                     ('ICT19005', 'pass123', 'Hasitha Silva', 'hasitha@student.ruhuna.ac.lk', '0773456785', 'ICT', 'Student', 'ICT/19/005'),
                                                                                                     ('ICT19006', 'pass123', 'Mahesh Kumara', 'mahesh@student.ruhuna.ac.lk', '0773456786', 'ICT', 'Student', 'ICT/19/006'),
                                                                                                     ('ICT19007', 'pass123', 'Dilini Rathnayake', 'dilini@student.ruhuna.ac.lk', '0773456787', 'ICT', 'Student', 'ICT/19/007'),
                                                                                                     ('ICT19008', 'pass123', 'Chathura Perera', 'chathura@student.ruhuna.ac.lk', '0773456788', 'ICT', 'Student', 'ICT/19/008'),
                                                                                                     ('ICT19009', 'pass123', 'Lakmini Bandara', 'lakmini@student.ruhuna.ac.lk', '0773456789', 'ICT', 'Student', 'ICT/19/009'),
                                                                                                     ('ICT19010', 'pass123', 'Ranil Dissanayake', 'ranil@student.ruhuna.ac.lk', '0773456790', 'ICT', 'Student', 'ICT/19/010'),
                                                                                                     ('ICT19011', 'pass123', 'Malini Jayawardena', 'malini@student.ruhuna.ac.lk', '0773456791', 'ICT', 'Student', 'ICT/19/011'),
                                                                                                     ('ICT19012', 'pass123', 'Aruna Senanayake', 'aruna@student.ruhuna.ac.lk', '0773456792', 'ICT', 'Student', 'ICT/19/012'),
                                                                                                     ('ICT19013', 'pass123', 'Shantha Fernando', 'shantha@student.ruhuna.ac.lk', '0773456793', 'ICT', 'Student', 'ICT/19/013'),
                                                                                                     ('ICT19014', 'pass123', 'Nimali Perera', 'nimali@student.ruhuna.ac.lk', '0773456794', 'ICT', 'Student', 'ICT/19/014'),
                                                                                                     ('ICT19015', 'pass123', 'Darshana Silva', 'darshana@student.ruhuna.ac.lk', '0773456795', 'ICT', 'Student', 'ICT/19/015'),
                                                                                                     ('ET19001', 'pass123', 'Asanka Perera', 'asanka@student.ruhuna.ac.lk', '0773456796', 'ET', 'Student', 'ET/19/001'),
                                                                                                     ('ET19002', 'pass123', 'Tharaka Silva', 'tharaka@student.ruhuna.ac.lk', '0773456797', 'ET', 'Student', 'ET/19/002'),
                                                                                                     ('ET19003', 'pass123', 'Sanduni Fernando', 'sanduni@student.ruhuna.ac.lk', '0773456798', 'ET', 'Student', 'ET/19/003'),
                                                                                                     ('BST19001', 'pass123', 'Isuru Perera', 'isuru@student.ruhuna.ac.lk', '0773456799', 'BST', 'Student', 'BST/19/001'),
                                                                                                     ('BST19002', 'pass123', 'Nadeeka Silva', 'nadeeka@student.ruhuna.ac.lk', '0773456800', 'BST', 'Student', 'BST/19/002');

-- Courses based on provided data
INSERT INTO Course (code, name, credits, theory_hours, practical_hours, semester, department) VALUES
                                                                                                  ('ICT2113', 'Object Oriented Programming', 3.0, 2, 1, 3, 'ICT'),
                                                                                                  ('ICT2122', 'Database Management Systems', 2.0, 2, 0, 3, 'ICT'),
                                                                                                  ('ICT2133', 'Web Programming', 3.0, 2, 1, 3, 'ICT'),
                                                                                                  ('ICT2142', 'Network Programming', 2.0, 0, 2, 3, 'ICT'),
                                                                                                  ('ICT2152', 'Software Engineering', 2.0, 2, 0, 3, 'ICT'),
                                                                                                  ('IIC2212', 'Fundamentals of Multimedia', 3.0, 2, 1, 4, 'ICT'),
                                                                                                  ('IIC2192', 'Operating Systems', 3.0, 2, 1, 4, 'ICT'),
                                                                                                  ('IIC2203', 'Visual Application Programming', 3.0, 1, 2, 4, 'ICT'),
                                                                                                  ('IIC2172', 'Data Structures and Algorithms', 3.0, 2, 1, 4, 'ICT'),
                                                                                                  ('IIC2183', 'Networking Essentials', 3.0, 1, 1, 4, 'ICT'),
                                                                                                  ('ISC2162', 'Mathematics for ICT', 2.0, 1, 1, 4, 'ICT');

-- Enrollments - Enroll ICT students in all ICT courses for 2023/2024
INSERT INTO Enrollment (student_id, course_code, academic_year)
SELECT u.id, c.code, '2023/2024'
FROM User u, Course c
WHERE u.role = 'Student' AND u.department = 'ICT' AND c.department = 'ICT';

-- Clear existing timetable data if needed
DELETE FROM Timetable WHERE course_code LIKE 'IIC%' OR course_code LIKE 'ISC%';

-- Timetable entries
INSERT INTO Timetable (course_code, day_of_week, start_time, end_time, venue, session_type, lecturer_id) VALUES
                                                                                                             ('ICT2113', 'Monday', '09:00:00', '11:00:00', 'Lecture Hall A', 'Theory', 2),
                                                                                                             ('ICT2113', 'Wednesday', '14:00:00', '16:00:00', 'Lab 1', 'Practical', 2),
                                                                                                             ('ICT2122', 'Tuesday', '09:00:00', '11:00:00', 'Lecture Hall B', 'Theory', 3),
                                                                                                             ('ICT2133', 'Wednesday', '09:00:00', '11:00:00', 'Lecture Hall A', 'Theory', 4),
                                                                                                             ('ICT2133', 'Friday', '14:00:00', '16:00:00', 'Lab 2', 'Practical', 4),
                                                                                                             ('ICT2142', 'Thursday', '14:00:00', '16:00:00', 'Lab 3', 'Practical', 5),
                                                                                                             ('ICT2152', 'Friday', '09:00:00', '11:00:00', 'Lecture Hall B', 'Theory', 6),
                                                                                                             ('IIC2212', 'Monday', '08:00:00', '09:00:00', 'FF 07 (152)', 'Theory', 2),
                                                                                                             ('IIC2212', 'Monday', '09:00:00', '10:00:00', 'FF 07 (152)', 'Theory', 2),
                                                                                                             ('IIC2203', 'Monday', '13:00:00', '14:00:00', 'ICT Lab 1 (152)', 'Practical', 4),
                                                                                                             ('IIC2203', 'Monday', '14:00:00', '15:00:00', 'ICT Lab 1 (152)', 'Practical', 4),
                                                                                                             ('IIC2183', 'Monday', '15:00:00', '16:00:00', 'FF 05 Hall (152)', 'Theory', 3),
                                                                                                             ('IIC2183', 'Monday', '16:00:00', '17:00:00', 'FF 05 Hall (152)', 'Practical', 3),
                                                                                                             ('IIC2192', 'Tuesday', '09:00:00', '10:00:00', 'FF 05 (152)', 'Theory', 5),
                                                                                                             ('IIC2192', 'Tuesday', '10:00:00', '11:00:00', 'FF 05 (152)', 'Theory', 5),
                                                                                                             ('ISC2162', 'Tuesday', '13:00:00', '14:00:00', 'FF 05 (155)', 'Theory', 2),
                                                                                                             ('ISC2162', 'Tuesday', '14:00:00', '15:00:00', 'FF 05 (155)', 'Theory', 2),
                                                                                                             ('IIC2192', 'Tuesday', '15:00:00', '16:00:00', 'ICT Lab 1 (152)', 'Practical', 5),
                                                                                                             ('IIC2192', 'Tuesday', '16:00:00', '17:00:00', 'ICT Lab 1 (152)', 'Practical', 5),
                                                                                                             ('IIC2203', 'Wednesday', '10:00:00', '11:00:00', 'SF 01 (152)', 'Theory', 4),
                                                                                                             ('IIC2203', 'Wednesday', '11:00:00', '12:00:00', 'SF 01 (152)', 'Theory', 4),
                                                                                                             ('IIC2203', 'Wednesday', '13:00:00', '14:00:00', 'ICT Lab 1 (152)', 'Practical', 4),
                                                                                                             ('IIC2203', 'Wednesday', '14:00:00', '15:00:00', 'ICT Lab 1 (152)', 'Practical', 4),
                                                                                                             ('IIC2172', 'Thursday', '09:00:00', '10:00:00', 'FF 05 Hall (152)', 'Theory', 3),
                                                                                                             ('IIC2172', 'Thursday', '10:00:00', '11:00:00', 'FF 05 Hall (152)', 'Theory', 3),
                                                                                                             ('ISC2162', 'Thursday', '13:00:00', '14:00:00', 'FF 06 (152)', 'Theory', 2),
                                                                                                             ('ISC2162', 'Thursday', '14:00:00', '15:00:00', 'FF 06 (152)', 'Theory', 2),
                                                                                                             ('IIC2212', 'Friday', '09:00:00', '10:00:00', 'SF 02 (152)', 'Theory', 2),
                                                                                                             ('IIC2212', 'Friday', '10:00:00', '11:00:00', 'SF 02 (152)', 'Theory', 2);

-- Notices
INSERT INTO Notice (title, content, posted_by, target_roles, target_departments) VALUES
                                                                                     ('Exam Schedule for Semester 1', 'The examination for semester 1 will start from May 15th, 2024. Please check the timetable.', 1, 'Student,Lecturer,Technical Officer', 'ICT,ET,BST'),
                                                                                     ('Faculty Meeting', 'All academic staff are requested to attend the faculty meeting on April 25th, 2024 at 2:00 PM.', 1, 'Lecturer,Technical Officer', 'ICT,ET,BST'),
                                                                                     ('Lab Maintenance', 'ICT Labs will be closed for maintenance from April 10th to April 12th, 2024.', 7, 'Student,Lecturer', 'ICT'),
                                                                                     ('Project Submission Deadline', 'The deadline for the mini project submission has been extended to April 27th, 2024.', 3, 'Student', 'ICT');

-- Generate attendance data for ICT2113
-- We'll create 15 sessions worth of data with variations

-- Define session dates (15 sessions)
SET @session_dates = '2024-02-05,2024-02-12,2024-02-19,2024-02-26,2024-03-04,2024-03-11,2024-03-18,2024-03-25,2024-04-01,2024-04-08,2024-04-15,2024-04-22,2024-04-29,2024-05-06,2024-05-13';

-- Attendance for ICT2113 Theory sessions
-- Students 1-5: >80% attendance (13+ out of 15)
-- Students 6-7: Exactly 80% attendance (12 out of 15)
-- Students 8-10: <80% attendance without medicals (8-11 out of 15)
-- Students 11-12: >80% with medicals (13+ out of 15, some with medicals)
-- Students 13-15: <80% with medicals (8-11 out of 15, some with medicals)

-- Insert attendance for student 1 (>80% - 14/15)
INSERT INTO Attendance (student_id, course_code, date, session_type, is_present, has_medical, recorded_by, notes)
VALUES
    (11, 'ICT2113', '2024-02-05', 'Theory', TRUE, FALSE, 7, NULL),
    (11, 'ICT2113', '2024-02-12', 'Theory', TRUE, FALSE, 7, NULL),
    (11, 'ICT2113', '2024-02-19', 'Theory', TRUE, FALSE, 7, NULL),
    (11, 'ICT2113', '2024-02-26', 'Theory', TRUE, FALSE, 7, NULL),
    (11, 'ICT2113', '2024-03-04', 'Theory', TRUE, FALSE, 7, NULL),
    (11, 'ICT2113', '2024-03-11', 'Theory', TRUE, FALSE, 7, NULL),
    (11, 'ICT2113', '2024-03-18', 'Theory', FALSE, FALSE, 7, 'Absent without notice'),
    (11, 'ICT2113', '2024-03-25', 'Theory', TRUE, FALSE, 7, NULL),
    (11, 'ICT2113', '2024-04-01', 'Theory', TRUE, FALSE, 7, NULL),
    (11, 'ICT2113', '2024-04-08', 'Theory', TRUE, FALSE, 7, NULL),
    (11, 'ICT2113', '2024-04-15', 'Theory', TRUE, FALSE, 7, NULL),
    (11, 'ICT2113', '2024-04-22', 'Theory', TRUE, FALSE, 7, NULL),
    (11, 'ICT2113', '2024-04-29', 'Theory', TRUE, FALSE, 7, NULL),
    (11, 'ICT2113', '2024-05-06', 'Theory', TRUE, FALSE, 7, NULL),
    (11, 'ICT2113', '2024-05-13', 'Theory', TRUE, FALSE, 7, NULL);

-- Insert attendance for student 6 (exactly 80% - 12/15)
INSERT INTO Attendance (student_id, course_code, date, session_type, is_present, has_medical, recorded_by, notes)
VALUES
    (16, 'ICT2113', '2024-02-05', 'Theory', TRUE, FALSE, 7, NULL),
    (16, 'ICT2113', '2024-02-12', 'Theory', TRUE, FALSE, 7, NULL),
    (16, 'ICT2113', '2024-02-19', 'Theory', TRUE, FALSE, 7, NULL),
    (16, 'ICT2113', '2024-02-26', 'Theory', FALSE, FALSE, 7, 'Absent without notice'),
    (16, 'ICT2113', '2024-03-04', 'Theory', TRUE, FALSE, 7, NULL),
    (16, 'ICT2113', '2024-03-11', 'Theory', TRUE, FALSE, 7, NULL),
    (16, 'ICT2113', '2024-03-18', 'Theory', FALSE, FALSE, 7, 'Absent without notice'),
    (16, 'ICT2113', '2024-03-25', 'Theory', TRUE, FALSE, 7, NULL),
    (16, 'ICT2113', '2024-04-01', 'Theory', TRUE, FALSE, 7, NULL),
    (16, 'ICT2113', '2024-04-08', 'Theory', TRUE, FALSE, 7, NULL),
    (16, 'ICT2113', '2024-04-15', 'Theory', TRUE, FALSE, 7, NULL),
    (16, 'ICT2113', '2024-04-22', 'Theory', FALSE, FALSE, 7, 'Absent without notice'),
    (16, 'ICT2113', '2024-04-29', 'Theory', TRUE, FALSE, 7, NULL),
    (16, 'ICT2113', '2024-05-06', 'Theory', TRUE, FALSE, 7, NULL),
    (16, 'ICT2113', '2024-05-13', 'Theory', TRUE, FALSE, 7, NULL);

-- Insert attendance for student 8 (<80% without medicals - 10/15)
INSERT INTO Attendance (student_id, course_code, date, session_type, is_present, has_medical, recorded_by, notes)
VALUES
    (18, 'ICT2113', '2024-02-05', 'Theory', TRUE, FALSE, 7, NULL),
    (18, 'ICT2113', '2024-02-12', 'Theory', FALSE, FALSE, 7, 'Absent without notice'),
    (18, 'ICT2113', '2024-02-19', 'Theory', TRUE, FALSE, 7, NULL),
    (18, 'ICT2113', '2024-02-26', 'Theory', FALSE, FALSE, 7, 'Absent without notice'),
    (18, 'ICT2113', '2024-03-04', 'Theory', TRUE, FALSE, 7, NULL),
    (18, 'ICT2113', '2024-03-11', 'Theory', TRUE, FALSE, 7, NULL),
    (18, 'ICT2113', '2024-03-18', 'Theory', FALSE, FALSE, 7, 'Absent without notice'),
    (18, 'ICT2113', '2024-03-25', 'Theory', TRUE, FALSE, 7, NULL),
    (18, 'ICT2113', '2024-04-01', 'Theory', TRUE, FALSE, 7, NULL),
    (18, 'ICT2113', '2024-04-08', 'Theory', TRUE, FALSE, 7, NULL),
    (18, 'ICT2113', '2024-04-15', 'Theory', FALSE, FALSE, 7, 'Absent without notice'),
    (18, 'ICT2113', '2024-04-22', 'Theory', TRUE, FALSE, 7, NULL),
    (18, 'ICT2113', '2024-04-29', 'Theory', TRUE, FALSE, 7, NULL),
    (18, 'ICT2113', '2024-05-06', 'Theory', FALSE, FALSE, 7, 'Absent without notice'),
    (18, 'ICT2113', '2024-05-13', 'Theory', TRUE, FALSE, 7, NULL);

-- Insert attendance for student 11 (>80% with medicals - 12/15 present + 2 medical)
INSERT INTO Attendance (student_id, course_code, date, session_type, is_present, has_medical, recorded_by, notes)
VALUES
    (21, 'ICT2113', '2024-02-05', 'Theory', TRUE, FALSE, 7, NULL),
    (21, 'ICT2113', '2024-02-12', 'Theory', TRUE, FALSE, 7, NULL),
    (21, 'ICT2113', '2024-02-19', 'Theory', TRUE, FALSE, 7, NULL),
    (21, 'ICT2113', '2024-02-26', 'Theory', FALSE, TRUE, 7, 'Medical submitted'),
    (21, 'ICT2113', '2024-03-04', 'Theory', FALSE, TRUE, 7, 'Medical submitted'),
    (21, 'ICT2113', '2024-03-11', 'Theory', TRUE, FALSE, 7, NULL),
    (21, 'ICT2113', '2024-03-18', 'Theory', TRUE, FALSE, 7, NULL),
    (21, 'ICT2113', '2024-03-25', 'Theory', TRUE, FALSE, 7, NULL),
    (21, 'ICT2113', '2024-04-01', 'Theory', TRUE, FALSE, 7, NULL),
    (21, 'ICT2113', '2024-04-08', 'Theory', TRUE, FALSE, 7, NULL),
    (21, 'ICT2113', '2024-04-15', 'Theory', TRUE, FALSE, 7, NULL),
    (21, 'ICT2113', '2024-04-22', 'Theory', FALSE, FALSE, 7, 'Absent without notice'),
    (21, 'ICT2113', '2024-04-29', 'Theory', TRUE, FALSE, 7, NULL),
    (21, 'ICT2113', '2024-05-06', 'Theory', TRUE, FALSE, 7, NULL),
    (21, 'ICT2113', '2024-05-13', 'Theory', TRUE, FALSE, 7, NULL);

-- Insert attendance for student 13 (<80% with medicals - 8/15 present + 3 medical = 11/15 effective)
INSERT INTO Attendance (student_id, course_code, date, session_type, is_present, has_medical, recorded_by, notes)
VALUES
    (23, 'ICT2113', '2024-02-05', 'Theory', TRUE, FALSE, 7, NULL),
    (23, 'ICT2113', '2024-02-12', 'Theory', FALSE, TRUE, 7, 'Medical submitted'),
    (23, 'ICT2113', '2024-02-19', 'Theory', TRUE, FALSE, 7, NULL),
    (23, 'ICT2113', '2024-02-26', 'Theory', FALSE, TRUE, 7, 'Medical submitted'),
    (23, 'ICT2113', '2024-03-04', 'Theory', TRUE, FALSE, 7, NULL),
    (23, 'ICT2113', '2024-03-11', 'Theory', FALSE, FALSE, 7, 'Absent without notice'),
    (23, 'ICT2113', '2024-03-18', 'Theory', FALSE, FALSE, 7, 'Absent without notice'),
    (23, 'ICT2113', '2024-03-25', 'Theory', TRUE, FALSE, 7, NULL),
    (23, 'ICT2113', '2024-04-01', 'Theory', TRUE, FALSE, 7, NULL),
    (23, 'ICT2113', '2024-04-08', 'Theory', FALSE, TRUE, 7, 'Medical submitted'),
    (23, 'ICT2113', '2024-04-15', 'Theory', FALSE, FALSE, 7, 'Absent without notice'),
    (23, 'ICT2113', '2024-04-22', 'Theory', TRUE, FALSE, 7, NULL),
    (23, 'ICT2113', '2024-04-29', 'Theory', TRUE, FALSE, 7, NULL),
    (23, 'ICT2113', '2024-05-06', 'Theory', FALSE, FALSE, 7, 'Absent without notice'),
    (23, 'ICT2113', '2024-05-13', 'Theory', TRUE, FALSE, 7, NULL);

-- Medical records
INSERT INTO Medical (student_id, start_date, end_date, reason, status, approved_by)
VALUES
    (21, '2024-02-25', '2024-03-05', 'Fever and respiratory infection', 'Approved', 7),
    (23, '2024-02-10', '2024-02-14', 'Dengue fever', 'Approved', 7),
    (23, '2024-02-25', '2024-02-28', 'Food poisoning', 'Approved', 7),
    (23, '2024-04-07', '2024-04-09', 'Viral fever', 'Approved', 7);

-- Insert marks for ICT2113
-- Quizzes (3 quizzes, best 2 count for 10%)
INSERT INTO Mark (student_id, course_code, assessment_type, assessment_number, marks, recorded_by)
VALUES
-- Student 1 Quizzes
(11, 'ICT2113', 'Quiz', 1, 85.00, 2),
(11, 'ICT2113', 'Quiz', 2, 78.00, 2),
(11, 'ICT2113', 'Quiz', 3, 92.00, 2),
-- Student 1 Mid Term (20%)
(11, 'ICT2113', 'Mid Term', 1, 75.00, 2),
-- Student 1 Final Theory (40%)
(11, 'ICT2113', 'Final Theory', 1, 82.00, 2),
-- Student 1 Final Practical (30%)
(11, 'ICT2113', 'Final Practical', 1, 88.00, 2);

-- Add data for other courses similarly

-- Insert marks for ICT2122 (For student 1)
-- Quizzes (4 quizzes, best 3 count for 10%)
INSERT INTO Mark (student_id, course_code, assessment_type, assessment_number, marks, recorded_by)
VALUES
    (11, 'ICT2122', 'Quiz', 1, 72.00, 3),
    (11, 'ICT2122', 'Quiz', 2, 85.00, 3),
    (11, 'ICT2122', 'Quiz', 3, 68.00, 3),
    (11, 'ICT2122', 'Quiz', 4, 90.00, 3),
-- Assessment (10%)
    (11, 'ICT2122', 'Assessment', 1, 76.00, 3),
-- Mid Term (20%)
    (11, 'ICT2122', 'Mid Term', 1, 82.00, 3),
-- Final Theory (60%)
    (11, 'ICT2122', 'Final Theory', 1, 78.00, 3);