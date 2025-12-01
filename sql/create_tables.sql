DROP TABLE IF EXISTS works_on_grad_students CASCADE;
DROP TABLE IF EXISTS works_on_co_pis CASCADE;
DROP TABLE IF EXISTS student CASCADE;
DROP TABLE IF EXISTS professor CASCADE;
DROP TABLE IF EXISTS project CASCADE;
DROP TABLE IF EXISTS department CASCADE;

CREATE TABLE department (
    dnum INT PRIMARY KEY,
    dname VARCHAR(100) NOT NULL,
    mainoffice VARCHAR(100),
    chairssn CHAR(9)
);

CREATE TABLE professor (
    ssn CHAR(9) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT,
    gender CHAR(1),
    rank VARCHAR(100),
    researchspecialty VARCHAR(100),
    dnum INT NOT NULL,
    FOREIGN KEY(dnum) REFERENCES department(dnum)
);

ALTER TABLE department
ADD CONSTRAINT fk_chair FOREIGN KEY (chairssn) REFERENCES professor(ssn);

CREATE TABLE student (
    ssn CHAR(9) PRIMARY KEY,
    name VARCHAR(100),
    age INT,
    gender CHAR(1),
    degreeprogram VARCHAR(10),
    majordeptnum INT,
    advisorssn CHAR(9),
    FOREIGN KEY(majordeptnum) REFERENCES department(dnum),
    FOREIGN KEY(advisorssn) REFERENCES student(ssn)
);

CREATE TABLE project (
    pnum INT PRIMARY KEY,
    sponser VARCHAR(100),
    startdate DATE,
    enddate DATE,
    budget NUMERIC,
    pi_ssn CHAR(9),
    FOREIGN KEY(pi_ssn) REFERENCES professor(ssn)
);

CREATE TABLE works_on_co_pis (
    pnum INT,
    professor_ssn CHAR(9),
    PRIMARY KEY(pnum, professor_ssn),
    FOREIGN KEY(pnum) REFERENCES project(pnum),
    FOREIGN KEY(professor_ssn) REFERENCES professor(ssn)
);

CREATE TABLE works_on_grad_students (
    pnum INT,
    student_ssn CHAR(9),
    PRIMARY KEY(pnum, student_ssn),
    FOREIGN KEY(pnum) REFERENCES project(pnum),
    FOREIGN KEY(student_ssn) REFERENCES student(ssn)
);
