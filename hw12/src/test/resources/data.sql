insert into authors(full_name)
values ('Author_1'), ('Author_2'), ('Author_3');

insert into genres(name)
values ('Genre_1'), ('Genre_2'), ('Genre_3'),
       ('Genre_4'), ('Genre_5'), ('Genre_6');

insert into books(title, author_id)
values ('BookTitle_1', 1), ('BookTitle_2', 2), ('BookTitle_3', 3);

insert into books_genres(book_id, genre_id)
values (1, 1),   (1, 2),
       (2, 3),   (2, 4),
       (3, 5),   (3, 6);

insert into comments(text, book_id)
values ('Very interesting book', 1), ('Could have been better', 2), ('Not recommended', 3), ('Very interesting book', 2);

insert into roles(role)
values ('admin'), ('user');

insert into users(username, password, role_id)
values ('administrator', '$2a$12$vyQbXf09gUFseffgZoux/uK0RNRI43MYtGxCFw/UMT9BH0WUVIOu6', 1),
('user', '$2a$10$28wGUwLfZck025R1H2fRWe/HIXsMdKnGNl7.3Ut4WsrhqJDmfOkv6', 2);

insert into users_roles(user_id, role_id)
values (1, 1);