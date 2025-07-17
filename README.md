# Enhanced College ERP System – Assignment 2

This project is an enhancement of a previously developed **College ERP System**, expanding its functionality and robustness using advanced Java concepts like **Generic Programming**, **Object Class**, and **Exception Handling**.

---

## 🚀 Features Added in Assignment 2

### 1. 📊 Feedback System (Generic Programming)
- Implemented using **generic classes** and **wildcards**.
- Allows students to submit feedback for completed courses.
- Professors can view feedback in two formats:
  - **Rating (Integer)**
  - **Textual comments (String)**

### 2. 🎓 Teaching Assistant (TA) Functionality (Object Class)
- Introduced a new `TA` class that extends student capabilities.
- TA can:
  - View the grades of students in their assigned course.
  - Assign or update marks for enrolled students.
- Utilized **Object class methods** (e.g., `equals`, `toString`) to manage identity and behavior.

### 3. ⚠️ Robust Exception Handling
Handled multiple custom exceptions using **try-catch blocks** to improve system reliability:
- `CourseFullException`: Prevents enrollment once capacity is reached.
- `InvalidLoginException`: Ensures secure login via credential validation.
- `DeadlinePassedException`: Prevents course dropping after a set deadline.

---

## 📌 Assumptions
- Students cannot drop a course after a deadline.
- TA and Professors can only manage courses they are assigned to.
- Feedback is allowed **only after course completion**.
- TA functionalities are additional and **distinct from Students**.

---

## 🧠 Object-Oriented Programming Concepts Used

| Concept           | Application                                                                 |
|------------------|------------------------------------------------------------------------------|
| **Abstraction**   | Interfaces like `IF_User` and `IF_CourseManagement`                         |
| **Inheritance**   | `Student`, `Professor`, `Administrator`, and `TA` inherit from `User`       |
| **Polymorphism**  | Method overriding for `login`, `signup`, and other behaviors                |
| **Encapsulation** | Private attributes with public getters and setters                          |
| **Relationships** | Uses **Association**, **Aggregation**, **Composition**, and **Dependency** |

---

## 🛠️ Technologies Used
- Language: **Java**
- OOP Features: Interfaces, Abstract Classes, Inheritance, Exception Handling
- IDE: IntelliJ / Eclipse
- Design Tool: [Lucidchart UML Diagram](https://lucid.app/lucidchart/9e3acbf7-8c22-4ef0-bfa8-e34db00a1223/edit?invitationId=inv_320684c8-3e63-4a67-9479-1433714ae8a2&page=0_0#)

---

## 🖥️ How to Run the Program

1. **Download or clone** the repository.
2. Open the project folder in your preferred IDE.
3. Navigate to the `Main.java` file.
4. **Run** the program.
