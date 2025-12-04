create table operation_types (
    operation_type_id serial primary key,
    description varchar(30) not null
);

insert into operation_types(operation_type_id, description) values
(1, 'PURCHASE'),
(2, 'INSTALLMENT PURCHASE'),
(3, 'WITHDRAWAL'),
(4, 'PAYMENT');