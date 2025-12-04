create table accounts(
    account_id serial primary key,
    document_number varchar(11) not null unique
);