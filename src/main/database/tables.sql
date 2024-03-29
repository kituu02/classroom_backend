create database classroom;
\c classroom;

create table sdetails (
    sid serial primary key,
    name text,
    email text,
    branch text,
    section text,
    rollno text,
    batch text,
    ph_no text
);

create table slogin (
    sid serial,
    email text,
    password text
);

create table tdetails (
    tid serial primary key,
    tname text,
    email text,
    branch text,
    phno text
);

create table tlogin (
    tid serial,
    email text,
    password text  
);

create table classrooms (
    cid serial primary key,
    branch text,
    section text,
    subject text,
    tid numeric,
    batch text
);

create table material_links(
    cid numeric,
    mat_name text,
    mat_link text
);

create table forms_message_table (
    mid serial primary key,
    message text,
    cid numeric,
    sid numeric,
    reactions numeric
);

create table individual_message_table (
    mid serial,
    tid numeric,
    cid numeric,
    sid numeric,
    sender numeric,
    message text
);