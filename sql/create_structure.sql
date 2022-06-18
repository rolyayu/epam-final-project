
create TABLE lodgers (
    id serial primary key,
    name text not null
);

create TABLE request(
    id serial primary key,
    scale text not null CHECK(scale in ('SMALL','MEDIUM','LARGE')),
    time_to_do int not null,
    type_of_work text not null CHECK (type_of_work in ('TRAVELING', 'REMOTE', 'HOME','PART_TIME', 'REGULAR')),
    lodger_id int not null REFERENCES lodgers(id) ON delete RESTRICT,
    in_process boolean not null default false
);


create TABLE workers (
    id serial primary key,
    is_busy boolean not null default false
);

create TABLE brigade (
    id serial primary key,
    workers_id INT ARRAY not null,
    is_busy boolean not null default true
);

create TABLE work_plan (
    id serial primary key,
    brigade_id INT NOT NULL REFERENCES brigade(id) ON delete CASCADE,
    request_id INT NOT NULL REFERENCES request(id) ON delete CASCADE,
    is_done boolean NOT NULL default false,
    UNIQUE (brigade_id),
    UNIQUE (request_id)
);