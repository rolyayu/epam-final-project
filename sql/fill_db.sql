INSERT INTO lodgers (name) 
VALUES 
('Better'),
('Call'),
('Saul'),
('Boris'),
('John');


INSERT INTO request (scale,time_to_do,type_of_work,lodger_id)
VALUES
('small',30,'regular',1),
('medium',65,'remote',3),
('large',115,'traveling',5),
('small',15,'home',2),
('medium',50,'part-time',4);

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