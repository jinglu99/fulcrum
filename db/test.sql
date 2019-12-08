## fulcrum_info
create table if not exists fulcrum_info (
    id int primary key auto_increment,
    name varchar(50) not null ,
    val varchar(50) not null
);

insert into fulcrum_info (name, val) value ('version', '1.0');