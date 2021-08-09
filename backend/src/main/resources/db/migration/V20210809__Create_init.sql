drop table if exists comment CASCADE;
drop table if exists likes CASCADE;
drop table if exists post CASCADE;
drop table if exists user CASCADE;
drop table if exists vaccination_statistic;
drop table if exists public_data;

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

create table public_data
(
    public_data_type  varchar(31) not null,
    public_data_id    bigint auto_increment not null,
    created_at        timestamp default CURRENT_TIMESTAMP,
    last_modified_at  timestamp default CURRENT_TIMESTAMP,
    region_population varchar(255),
    primary key (public_data_id)
);

create table vaccination_statistic
(
    accumulated_first_cnt    bigint,
    accumulated_second_cnt   bigint,
    base_date                date,
    first_cnt                bigint,
    second_cnt               bigint,
    total_first_cnt          bigint,
    total_first_rate         decimal(19, 1),
    total_second_cnt         bigint,
    total_second_rate        decimal(19, 1),
    vaccination_statistic_id bigint not null,
    primary key (vaccination_statistic_id)
);

alter table comment
    add constraint fk_comment_post foreign key (post_id) references post (post_id) on delete cascade;
alter table comment
    add constraint fk_comment_user foreign key (user_id) references user (user_id) on delete cascade;
alter table likes
    add constraint fk_likes_post foreign key (post_id) references post (post_id) on delete cascade;
alter table likes
    add constraint fk_likes_user foreign key (user_id) references user (user_id) on delete cascade;
alter table post
    add constraint fk_post_user foreign key (user_id) references user (user_id) on delete cascade;
alter table vaccination_statistic
    add constraint fk_vaccination_statistic_public_data foreign key (vaccination_statistic_id) references public_data (public_data_id);
