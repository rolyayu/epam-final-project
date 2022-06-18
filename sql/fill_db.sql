INSERT INTO lodgers (name) 
VALUES 
('Better'),
('Call'),
('Saul'),
('Boris'),
('John');


INSERT INTO request (scale,time_to_do,type_of_work,lodger_id,in_process)
VALUES
('SMALL',30,'REGULAR',1,true),
('MEDIUM',65,'REMOTE',3,false ),
('LARGE',115,'TRAVELING',5,false),
('SMALL',15,'HOME',2,false),
('MEDIUM',50,'PART_TIME',4,true);

INSERT INTO workers (is_busy)
VALUES
(true),
(false),
(false),
(true),
(true),
(true),
(true),
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