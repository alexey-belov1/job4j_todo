create table if not exists users (
    id serial primary key not null,
    name varchar(50) not null,
    email varchar(50) unique not null,
    password varchar(50) not null
);

create table if not exists item (
   id serial primary key not null,
   description text not null ,
   created timestamp not null,
   done boolean,
   user_id integer references users(id)
);