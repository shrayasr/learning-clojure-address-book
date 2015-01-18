-- name: all-contacts
select id,name,phone,email from contacts;

-- name: insert-contact<!
insert into contacts (name, phone, email) 
  values (:name, :phone, :email);

-- name: drop-contacts-table!
drop table contacts;

-- name: create-contacts-table-if-not-exists!
create table if not exists contacts (
  id serial primary key,
  name varchar(20) not null,
  phone varchar(14) not null,
  email varchar(25) not null);

