CREATE TABLE user (
      id int primary key auto_increment,
      username varchar(255) not null unique,
      password varchar(255) not null,
      email char(10) not null,
      provider varchar(100) not null,
      created_at timestamp not null
);