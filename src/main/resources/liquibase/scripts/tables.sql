-- liquibase formatted sql

-- changeset komaru:1
CREATE TABLE users (
user_id serial PRIMARY KEY,
user_tg_id bigint,
user_contact_details text,
is_voloteer bool
);

create table shelter (
shelter_id serial primary key,
shelter_info text,
address text,
shelter_schedule text,
shelter_security_contact text
);


create table adopter (
adopter_id serial primary key,
user_id bigint references users(user_id),
start_date date,
adopter_status text
);

create table daily_report (
report_id serial primary key,
adopter_id bigint references adopter(adopter_id),
report_details text,
report_date date,
is_approved bool
);

create table animal (
animal_id serial primary key,
animal_name text,
animal_age decimal(3,1),
animal_decription text,
animal_status text --в приюте/забронирован/испытательный срок/ взяли
)