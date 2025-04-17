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
| Admin             | admin      | admin123   |
| Lecturer          | kamal      | pass123    |
| Technical Officer | amara      | pass123    |
| Student           | ICT19001   | pass123    |

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

---

## 🛠 Help & Troubleshooting

Make sure:
- ✅ MySQL Server is running and accessible 
- ✅ Create database iff not exists (faculty_db) 
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
- Dhanush Madusanka
- Vihanga Sajith

## 🙏 Acknowledgments
- ❤️ Open-source contributors  
- 👨‍👩‍👦‍👦 Group members who contributed to this project  
- 🏫 Faculty of Technology, University of Ruhuna, for guidance & specifications