CREATE OR REPLACE FUNCTION check_co_pi_limit()
RETURNS TRIGGER AS $$
DECLARE co_pi_count INT;
BEGIN
    SELECT COUNT(*) INTO co_pi_count
    FROM works_on_co_pis
    WHERE pnum = NEW.pnum;

    IF co_pi_count >= 4 THEN
        RAISE EXCEPTION 'Project % already has 4 co-PIs.', NEW.pnum;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER faculty_restrict
BEFORE INSERT OR UPDATE ON works_on_co_pis
FOR EACH ROW
EXECUTE FUNCTION check_co_pi_limit();



CREATE OR REPLACE FUNCTION check_student_project_limit()
RETURNS TRIGGER AS $$
DECLARE project_count INT;
BEGIN
    SELECT COUNT(*) INTO project_count
    FROM works_on_grad_students
    WHERE student_ssn = NEW.student_ssn;

    IF project_count >= 2 THEN
        RAISE EXCEPTION 'Student % already works on 2 projects.', NEW.student_ssn;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER student_restrict
BEFORE INSERT OR UPDATE ON works_on_grad_students
FOR EACH ROW
EXECUTE FUNCTION check_student_project_limit();
