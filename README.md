# 🎓 Faculty Management System

A comprehensive Java + MySQL-based platform built for the **Faculty of Technology** to manage:

- 👥 User Profiles (Admin, Lecturer, Student, Technical Officer)  
- 📚 Course Details  
- 📝 Undergraduate Marks & Attendance  
- 📢 Notices & Timetables  
- 🏥 Medical Records  

---

## 🚀 Getting Started

### 🛠️ Dependencies
- ☕ Java Development Kit (JDK 21)  
- 🐬 MySQL Database Server (5.7 or later)  
- 🔌 MySQL Connector for Java (9.2.0)
- 📦 Maven for dependency management
- 🎭 JavaFX 21
- 💻 OS Support: Windows / macOS  

### 📦 Installation Steps
1. 📥 Clone the repository or download the ZIP file.
2. 🛠 Set up the MySQL database:
   - Create a database named `faculty_db`
   - Import the schema from `src/main/resources/faculty_db.sql`
   - Default credentials are username: `root`, password: `2003` (configurable)
3. ⚙ Configure database connection settings:
   - Edit `src/main/resources/config/config.properties` if needed
   - Default URL: `jdbc:mysql://localhost:3306/faculty_db`

### ▶️ Running the Application
1. 🐬 Start the MySQL database server.
2. 💻 Run the application using one of these methods:
   - **Using Maven**:
     ```bash
     cd app
     mvn clean javafx:run
     ```
   - **Using an IDE**:
     - Open the project in IntelliJ IDEA or another IDE
     - Run `com.example.app.App` as a Java application

### 🔐 Default Login Credentials
| Role              | Username   | Password   |
|-------------------|------------|------------|
| Admin             | admin      | admin      |
| Lecturer          | lecturer   | lecturer   |
| Technical Officer | technical  | technical  |
| Student           | student    | student    |

---

## 📋 Project Details

### 📚 Course Information
This project was developed for **UNIVERSITY OF RUHUNA** - Bachelor of Information and Communication Technology Degree (Level II - Semester I).

- **Course Unit**: ICT2132 – Object Oriented Programming Practicum
- **Project Type**: Mini-Project
- **Submission Date**: April 27th, 2025

### 💻 Technical Implementation

The project demonstrates the following Object-Oriented Programming concepts:
- ✅ Classes and Objects
- ✅ Inheritance
- ✅ Abstraction
- ✅ Polymorphism
- ✅ Encapsulation
- ✅ Error and Exception Handling
- ✅ Database Integration
- ✅ Graphical User Interface (JavaFX)

### 📊 Data Structure
The system includes sample data for:
- 1 admin
- 5+ lecturers
- 4+ technical officers
- 20+ undergraduate students
- 5 course modules with attendance records
- Various assessment types (quizzes, mid-terms, finals)

---

## 🌟 Features

### 👑 Admin
- ➕ Create & manage user profiles  
- 📘 Create & manage courses  
- 📆 Manage notices and timetables  

### 👨‍🏫 Lecturer
- ✍ Update profile (except username and password)
- 📝 Modify and add course materials
- 📊 Upload and manage exam marks
- 🔍 View student details, eligibility status, marks, grades, GPA, attendance, and medical records
- 📢 Access notices  

### 🛠 Technical Officer
- ⚙️ Update profile (except username and password)
- 🕒 Maintain student attendance records
- 🏥 Manage student medical records
- 📢 View notices
- 🗓 Access department-specific timetables

### 🎓 Undergraduate Student
- 👤 Update contact details and profile picture
- 📊 View personal attendance records
- 🏥 Access medical records
- 📚 View course details
- 📈 Check grades and GPA
- 📅 Access personal timetables
- 📢 View notices

---

## 📈 Attendance & Assessment System

### 📋 Attendance Tracking
- Records both theory and practical sessions (15 sessions each)
- Medical record submission and verification
- Various attendance scenarios supported:
  - >80% attendance
  - Exactly 80% attendance
  - <80% attendance without medical records
  - >80% attendance with medical records
  - <80% attendance with medical records
- Session duration: 2 hours per credit for both theory and practical

### 📝 Assessment Structure
| Subject Code | Quizzes | Assessments | Mid-term | Final Theory | Final Practical |
|-------------|---------|-------------|----------|--------------|----------------|
| ICT2113 (2T+1P) | 10% (best 2/3) | - | 20% | 40% | 30% |
| ICT2122 (2T) | 10% (best 3/4) | 10% (1 assessment) | 20% | 60% | - |
| ICT2133 (2T+1P) | 10% (best 2/3) | 20% (2/2 assessments) | - | 30% | 40% |
| ICT2142 (2P) | - | 20% (1 assessment) | 20% | - | 60% |
| ICT2152 (2T) | 10% (best 2/3) | 20% (2/2 assessments) | - | 70% | - |

- All marks are calculated out of 100
- To obtain eligibility, CA component must be ≥50%

---

## 🛠 Help & Troubleshooting

Make sure:
- ✅ MySQL Server is running and accessible  
- 🔄 Java version is compatible with the project (JDK 21 recommended)  
- 🔑 DB credentials are correctly configured in `config.properties`
- 📦 All Maven dependencies are properly resolved

Common issues:
- If database connection fails, verify MySQL service is running
- For JavaFX errors, ensure the JavaFX runtime is properly configured
- For configuration issues, check the path to `config.properties`

---

## 🙋‍♂️ Team Members
- Dhyan Randika
- Minoka Induwara
- Dilhara Samaranayaka
- Malindu Shasan

## 🙏 Acknowledgments
- ❤️ Open-source contributors  
- 👨‍👩‍👦‍👦 Group members who contributed to this project  
- 🏫 Faculty of Technology, University of Ruhuna, for guidance & specifications
