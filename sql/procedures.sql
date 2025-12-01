CREATE OR REPLACE FUNCTION female_faculty()
RETURNS NUMERIC AS $$
DECLARE
    total_faculty INT;
    female_count INT;
BEGIN
    SELECT COUNT(*) INTO total_faculty FROM professor;
    SELECT COUNT(*) INTO female_count FROM professor WHERE gender = 'F';

    IF total_faculty = 0 THEN
        RETURN 0;
    END IF;

    RETURN (female_count::NUMERIC / total_faculty::NUMERIC) * 100;
END;
$$ LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION total_people(p_no INT)
RETURNS INT AS $$
DECLARE total_count INT;
BEGIN
    SELECT COUNT(DISTINCT ssn) INTO total_count
    FROM (
        SELECT pi_ssn AS ssn FROM project WHERE pnum = p_no
        UNION ALL
        SELECT professor_ssn AS ssn FROM works_on_co_pis WHERE pnum = p_no
        UNION ALL
        SELECT student_ssn AS ssn FROM works_on_grad_students WHERE pnum = p_no
    ) AS all_people;

    RETURN total_count;
END;
$$ LANGUAGE plpgsql;
