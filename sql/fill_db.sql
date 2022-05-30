INSERT INTO lodgers (name) 
VALUES 
('Better'),
('Call'),
('Saul'),
('Boris'),
('John');


INSERT INTO request (scale,time_to_do,type_of_work,lodger_id)
VALUES
('SMALL',30,'REGULAR',1),
('MEDIUM',65,'REMOTE',3),
('LARGE',115,'TRAVELING',5),
('SMALL',15,'HOME',2),
('MEDIUM',50,'PART_TIME',4);

INSERT INTO workers (is_busy)
VALUES
(false),
(true),
(true),
(true),
(false),
(false),
(false),
(false),
(false),
(false);

INSERT INTO brigade (workers_id)
VALUES 
('{1,4}'),
('{5,6,7}');

INSERT INTO work_plan (brigade_id, request_id)
VALUES
(1,1),
(2,5);