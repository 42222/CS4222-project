INSERT INTO department VALUES
(10, 'Computer Science', 'ET'),
(20, 'Mechanical Engineering', 'ET'),
(30, 'Physics', 'BIOSCI');

INSERT INTO professor VALUES
('111111111', 'Dr. Lim', 52, 'F', 'Full', 'AI & Robotics', 10),
('222222222', 'Dr. Pamula ', 45, 'M', 'Associate', 'Thermal Dynamics', 20),
('333333333', 'Dr. Guzman', 38, 'F', 'Assistant', 'Quantum Computing', 10),
('444444444', 'Dr. Krum', 60, 'M', 'Full', 'Astrophysics', 30);

UPDATE department SET chairssn = '111111111' WHERE dnum = 10;
UPDATE department SET chairssn = '222222222' WHERE dnum = 20;
UPDATE department SET chairssn = '444444444' WHERE dnum = 30;

INSERT INTO student VALUES
('555555555', 'athena ruiz', 28, 'F', 'Ph.D.', 10, NULL),
('666666666', 'iqra irfan', 24, 'M', 'M.S.', 10, '555555555'),
('777777777', 'rosa salazar', 30, 'F', 'Ph.D.', 20, NULL),
('888888888', 'harry potter', 25, 'M', 'M.S.', 20, '777777777');

INSERT INTO project VALUES
(100, 'NSF', '2024-01-01', '2025-12-31', 500000, '111111111'),
(200, 'DOD', '2023-06-15', '2025-06-15', 850000, '222222222'),
(300, 'Internal Grant', '2024-09-01', '2026-08-31', 150000, '333333333');

INSERT INTO works_on_co_pis VALUES
(100, '333333333'),
(100, '444444444'),
(200, '111111111');

INSERT INTO works_on_grad_students VALUES
(100, '555555555'),
(100, '666666666'),
(200, '777777777'),
(200, '888888888'),
(300, '555555555');
