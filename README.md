# SQL Scripts — University Database Project

This folder contains all SQL scripts required to build, populate, and test the
University Database for CS4222.

---

## 1. `create_tables.sql`
Creates all base tables in the database:

- `department`
- `professor`
- `student`
- `project`
- `works_on_co_pis`
- `works_on_grad_students`

Includes:
- primary keys  
- foreign keys  
- NOT NULL constraints  
- reference rules (ON DELETE RESTRICT)

---

## 2. `sample_data.sql`
Inserts sample data into all tables:

- Departments  
- Professors  
- Students (with advisor assignment)  
- Projects (with PI assignment)  
- Co-PI assignments  
- Graduate student project assignments  

This data matches the data used in our Java tests and screenshots.

---

## 3. `triggers.sql`
Contains trigger functions and triggers for:

### **3a. faculty_restrict**
Ensures each project has **at most 4 co-PIs**.

### **3b. student_restrict**
Ensures each graduate student works on **no more than 2 projects**.

Both triggers were fully tested (screenshots included in `/docs` and Canvas submission).

---

## 4. `procedures.sql`
Contains stored functions:

### **4a. `female_faculty()`**
Returns the **percentage of female faculty** in the university.

### **4b. `total_people(pno)`**
Returns **total number of people working on a project**, counting:
- PI
- Co-PIs
- Graduate students (RAs)

Both stored procedures were tested and their results included in the project documentation.

---

## Usage

Run the files in this order on the CSULA PostgreSQL remote server:

```sql
\i create_tables.sql
\i sample_data.sql
\i triggers.sql
\i procedures.sql
