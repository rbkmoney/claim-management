create table cm.shop_cash_register_modification_model
(
    id          int8              not null,
    register_id character varying not null,
    primary key (id)
);

alter table if exists cm.shop_cash_register_modification_model
    add constraint shop_modification_model_fkey foreign key (id) references cm.shop_modification_model;

create table cm.shop_cash_register_creation_modification_model
(
    id                      int8 not null,
    cash_register_params_id int8 not null,
    primary key (id)
);

create table cm.cash_register_params_model
(
    id              int8  not null,
    provider_id     int4  not null,
    provider_params bytea not null,
    primary key (id)
);

alter table if exists cm.shop_cash_register_creation_modification_model
    add constraint shop_cash_register_modification_model_fkey foreign key (id) references cm.shop_cash_register_modification_model;

alter table if exists cm.shop_cash_register_creation_modification_model
    add constraint cash_register_params_model_fkey foreign key (cash_register_params_id) references cm.cash_register_params_model;
