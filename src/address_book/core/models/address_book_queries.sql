-- name: create-contacts-table-if-not-exists!
create table if not exists contacts (
  id serial primary key,
  name varchar(20) not null,
  phone varchar(14) not null,
  email varchar(25) not null);

-- name: insert-contact<!
insert into contacts (name, phone, email) 
  values (:name, :phone, :email);

-- name: all-contacts
select id,name,phone,email from contacts;

-- name: update-contact<!
update contacts set name = :name, phone = :phone, email = :email
  where id = :id;

-- name: delete-contact<!
delete from contacts where id = :id;

-- name: drop-contacts-table!
drop table contacts;
