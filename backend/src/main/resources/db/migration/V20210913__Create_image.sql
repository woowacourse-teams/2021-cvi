create table image
(
    image_id         bigint auto_increment not null,
    created_at       timestamp default CURRENT_TIMESTAMP,
    last_modified_at timestamp default CURRENT_TIMESTAMP,
    url              varchar(255),
    post_id          bigint,
    primary key (image_id)
);

alter table image
    add constraint fk_image_post foreign key (post_id) references post (post_id) on delete cascade;