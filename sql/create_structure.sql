
CREATE TABLE lodgers (
    id serial primary key,
    name text not null
);

CREATE TABLE request(
    id serial primary key,
    scale text not null CHECK(scale in ('small','medium','large')),
    time_to_do int not null,
    type_of_work text not null CHECK (type_of_work in ('traveling', 'remote', 'home','part-time', 'regular')),
    lodger_id int not null REFERENCES lodgers(id) ON DELETE RESTRICT
);


CREATE TABLE workers (
    id serial primary key,
    is_busy boolean not null default false
);

CREATE TABLE brigade (
    id serial primary key,
    workers_id INT ARRAY not null
);

CREATE TABLE work_plan (
    id serial primary key,
    brigade_id INT NOT NULL REFERENCES brigade(id) ON DELETE CASCADE,
    request_id INT NOT NULL REFERENCES request(id) ON DELETE CASCADE
);