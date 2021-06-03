create table cm.external_info_modification_model
(
    id                   int8                  not null,
    document_id          character varying     not null,
    roistat_id           character varying     not null,
    primary key (id)
);

alter table if exists cm.external_info_modification_model
    add constraint modification_model_fkey foreign key (id) references cm.modification_model;
