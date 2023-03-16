CREATE TABLE users_tb (
      id int primary key auto_increment,
      username varchar(255) not null unique,
      pwd varchar(255) not null,
      email varchar(100) not null,
      provider varchar(100) not null default 'me',
      created_at timestamp not null
);

commit;