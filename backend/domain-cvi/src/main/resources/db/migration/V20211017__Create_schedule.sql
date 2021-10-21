create table schedule
(
    schedule_id      bigint auto_increment not null,
    created_at       timestamp default CURRENT_TIMESTAMP,
    last_modified_at timestamp default CURRENT_TIMESTAMP,
    is_running       varchar(255) not null,
    name             varchar(255) unique,
    version          int,
    primary key (schedule_id)
)
