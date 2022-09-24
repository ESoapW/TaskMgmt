DROP TABLE IF EXISTS task;

CREATE TABLE task (
     id Integer NOT NULL,
     name varchar(100) NOT NULL,
     date varchar(100) NOT NULL,
     primary key (id)
);

insert into task (id, name, date) values (1, 'Onboarding - first day', '2022-05-17T09:00+0800');
insert into task (id, name, date) values (2, 'Offboarding - last day', '2022-09-16T18:00+0800');
