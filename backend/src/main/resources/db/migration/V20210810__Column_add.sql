drop table if exists vaccination_statistic;
drop table if exists public_data;

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
    add last_modified_at timestamp default CURRENT_TIMESTAMP;
alter table comment
    modify column content text;
alter table likes
    add last_modified_at timestamp default CURRENT_TIMESTAMP;
alter table user
    add last_modified_at timestamp default CURRENT_TIMESTAMP;
alter table post
    add last_modified_at timestamp default CURRENT_TIMESTAMP;
alter table post
    modify column content text;

alter table vaccination_statistic
    add constraint fk_vaccination_statistic_public_data foreign key (vaccination_statistic_id) references public_data (public_data_id) on delete cascade on update cascade;
