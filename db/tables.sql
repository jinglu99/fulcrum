## fulcrum_info
drop table if exists fulcrum_info;
create table fulcrum_info (
    id int primary key auto_increment comment 'id',
    name varchar(50) not null comment 'info name',
    val varchar(50) not null comment 'value'
);

##insert version info
insert into fulcrum_info (name, val) values ('version', '1.0');

##fulcrum_resource
drop table if exists fulcrum_resource;
create table fulcrum_resource (
    id int primary key auto_increment comment 'id',
    resource_id varchar(100) not null comment 'resource id',
    dynamic_sql TEXT not null comment 'dynamic sql'
);



