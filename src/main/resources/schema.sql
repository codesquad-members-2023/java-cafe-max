
create table article(
    id bigint not null auto_increment,
    writer varchar(200) not null,
    title varchar(200) not null,
    contents text not null,
    date datetime,
    primary key(id)
);

create table cafe_user(
    id varchar(200) not null,
    password varchar(200) not null,
    name varchar(200) not null,
    email varchar(200) not null,
    primary key (id)
)