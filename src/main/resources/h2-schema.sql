drop table if exists menu cascade;
drop table if exists banner cascade;

create table menu (
    menu_id         bigint not null auto_increment,
    menu_title      varchar(40) not null,
    menu_link       varchar(2083) not null default '#',
    parent_menu_id  bigint not null default 0,
    sort_order      int not null default 0,
    create_at       datetime default now(),
    update_at       datetime default now(),
    PRIMARY KEY (menu_id)
);

create table banner (
    banner_id           bigint not null auto_increment,
    banner_link         varchar(2083) not null default '#',
    banner_image_url    varchar(2083) not null default '#',
    menu_id             bigint not null,
    create_at           datetime default now(),
    PRIMARY KEY (banner_id)
);

create index idx_banner_menu_id on banner(menu_id);
