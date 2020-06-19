create table if not exists item (
   id serial primary key not null,
   description text not null ,
   created timestamp not null,
   done boolean
);