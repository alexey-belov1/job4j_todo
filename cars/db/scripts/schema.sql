drop table post;
drop table photo;
drop table colour;
drop table model_body;
drop table body;
drop table model;
drop table mark;
drop table users;

create table users (
   id serial primary key,
   name varchar(50) not null,
   email varchar(50) not null,
   password varchar(50) not null
);

create table mark (
    id serial primary key,
    name varchar(50) not null
);

create table model (
    id serial primary key,
    name varchar(50) not null,
    mark_id int references mark(id) not null
);

create table body (
    id serial primary key,
    name varchar(50) not null
);

create table model_body (
    id serial primary key,
    model_id int references model(id) not null,
    body_id int references body(id) not null
);

create table colour (
    id serial primary key,
    name varchar(50) not null
);

create table photo (
   id SERIAL primary key,
   name varchar(50) not null
);

create table post (
   id serial primary key,
   created timestamp not null,
   user_id int references users(id) not null,
   mark_id int references mark(id) not null,
   model_id int references model(id) not null,
   body_id int references body(id) not null,
   colour_id int references colour(id) not null,
   mileage int not null,
   photo_id int references photo(id),
   status boolean not null
);

insert into mark(name) values
    ('Toyota'), ('Ford'), ('Kia');
insert into model(name, mark_id) values
    ('Corolla', 1), ('Auris', 1), ('Camry', 1),
    ('Focus', 2), ('Mondeo', 2), ('Fusion', 2),
    ('Rio', 3), ('Sorento', 3), ('Optima', 3);
insert into body(name) values
    ('Седан'), ('Хэтчбек'), ('Универсал'), ('Внедорожник');
insert into model_body(model_id, body_id) values
    (1, 1),
    (2, 2),
    (3, 1),
    (4, 1), (4, 2), (4, 3),
    (5, 1),
    (6, 2),
    (7, 1), (7, 2),
    (8, 4),
    (9, 1);
insert into colour(name) values
    ('Белый'), ('Черный'), ('Красный'), ('Серебристый'), ('Синий');