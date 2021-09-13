create table user
(
    user_id          bigint auto_increment not null,
    created_at       timestamp        default CURRENT_TIMESTAMP,
    last_modified_at timestamp        default CURRENT_TIMESTAMP,
    nickname         varchar(255),
    age_range        varchar(255),
    shot_verified    boolean not null default false,
    social_provider  varchar(255),
    social_id        varchar(255),
    profile_url      varchar(255),
    primary key (user_id),
    unique (nickname)
);

create table post
(
    post_id          bigint auto_increment not null,
    created_at       timestamp        default CURRENT_TIMESTAMP,
    last_modified_at timestamp        default CURRENT_TIMESTAMP,
    user_id          bigint,
    content          text,
    view_count       integer not null default 0,
    vaccination_type varchar(255),
    primary key (post_id)
);

create table comment
(
    comment_id       bigint auto_increment not null,
    created_at       timestamp default CURRENT_TIMESTAMP,
    last_modified_at timestamp default CURRENT_TIMESTAMP,
    post_id          bigint,
    user_id          bigint,
    content          text,
    primary key (comment_id)
);

create table likes
(
    likes_id         bigint auto_increment not null,
    created_at       timestamp default CURRENT_TIMESTAMP,
    last_modified_at timestamp default CURRENT_TIMESTAMP,
    post_id          bigint,
    user_id          bigint,
    primary key (likes_id)
);

alter table post
    add constraint fk_post_user foreign key (user_id) references user (user_id) on delete cascade;
alter table comment
    add constraint fk_comment_user foreign key (user_id) references user (user_id) on delete cascade;
alter table comment
    add constraint fk_comment_post foreign key (post_id) references post (post_id) on delete cascade;
alter table likes
    add constraint fk_likes_user foreign key (user_id) references user (user_id) on delete cascade;
alter table likes
    add constraint fk_likes_post foreign key (post_id) references post (post_id) on delete cascade;
