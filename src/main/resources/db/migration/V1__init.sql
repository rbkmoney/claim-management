create schema if not exists cm;
create sequence cm.hibernate_sequence start 1 increment 1;

create table cm.claim_model
(
    id                  int8                        not null,
    party_id            character varying           not null,
    claim_status        character varying           not null,
    claim_status_reason character varying,
    created_at          timestamp without time zone not null,
    revision            int4                        not null,
    updated_at          timestamp without time zone,
    primary key (id)
);

create table cm.metadata_model
(
    id       int8              not null,
    key      character varying not null,
    value    bytea             not null,
    claim_id int8              not null,
    primary key (id)
);

alter table if exists cm.metadata_model
    add constraint claim_model_fkey foreign key (claim_id) references cm.claim_model;

create table cm.modification_model
(
    id           int8                        not null,
    created_at   timestamp without time zone not null,
    claim_id     int8                        not null,
    user_info_id int8                        not null,
    primary key (id)
);

alter table if exists cm.modification_model
    add constraint claim_model_fkey foreign key (claim_id) references cm.claim_model;

create table cm.user_info_model
(
    id       int8              not null,
    email    character varying not null,
    type     character varying not null,
    user_id  character varying not null,
    username character varying not null,
    primary key (id)
);

alter table if exists cm.modification_model
    add constraint user_info_model_fkey foreign key (user_info_id) references cm.user_info_model;

create table cm.shop_modification_model
(
    id      int8              not null,
    shop_id character varying not null,
    primary key (id)
);

alter table if exists cm.shop_modification_model
    add constraint modification_model_fkey foreign key (id) references cm.modification_model;

create table cm.contract_modification_model
(
    id          int8              not null,
    contract_id character varying not null,
    primary key (id)
);

alter table if exists cm.contract_modification_model
    add constraint modification_model_fkey foreign key (id) references cm.modification_model;

create table cm.contractor_modification_model
(
    id            int8              not null,
    contractor_id character varying not null,
    primary key (id)
);

alter table if exists cm.contractor_modification_model
    add constraint modification_model_fkey foreign key (id) references cm.modification_model;

create table cm.file_modification_model
(
    id                     int8              not null,
    file_id                character varying not null,
    file_modification_type character varying not null,
    primary key (id)
);

alter table if exists cm.file_modification_model
    add constraint modification_model_fkey foreign key (id) references cm.modification_model;

create table cm.document_modification_model
(
    id                         int8              not null,
    document_id                character varying not null,
    document_modification_type character varying not null,
    primary key (id)
);

alter table if exists cm.document_modification_model
    add constraint modification_model_fkey foreign key (id) references cm.modification_model;

create table cm.comment_modification_model
(
    id                        int8              not null,
    comment_id                character varying not null,
    comment_modification_type character varying not null,
    primary key (id)
);

alter table if exists cm.comment_modification_model
    add constraint modification_model_fkey foreign key (id) references cm.modification_model;

create table cm.status_modification_model
(
    id                       int8              not null,
    claim_status             character varying not null,
    claim_status_reason      character varying,
    status_modification_type character varying not null,
    primary key (id)
);

alter table if exists cm.status_modification_model
    add constraint modification_model_fkey foreign key (id) references cm.modification_model;

create table cm.shop_creation_modification_model
(
    id             int8 not null,
    shop_params_id int8 not null,
    primary key (id)
);

alter table if exists cm.shop_creation_modification_model
    add constraint shop_modification_model_fkey foreign key (id) references cm.shop_modification_model;

create table cm.shop_details_modification_model
(
    id              int8 not null,
    shop_details_id int8 not null,
    primary key (id)
);

alter table if exists cm.shop_details_modification_model
    add constraint shop_modification_model_fkey foreign key (id) references cm.shop_modification_model;

create table cm.shop_category_modification_model
(
    id               int8 not null,
    shop_category_id int4 not null,
    primary key (id)
);

alter table if exists cm.shop_category_modification_model
    add constraint shop_modification_model_fkey foreign key (id) references cm.shop_modification_model;

create table cm.shop_account_creation_modification_model
(
    id                     int8 not null,
    shop_account_params_id int8 not null,
    primary key (id)
);

alter table if exists cm.shop_account_creation_modification_model
    add constraint shop_modification_model_fkey foreign key (id) references cm.shop_modification_model;

create table cm.shop_contract_modification_model
(
    id             int8              not null,
    contract_id    character varying not null,
    payout_tool_id character varying not null,
    primary key (id)
);

alter table if exists cm.shop_contract_modification_model
    add constraint shop_modification_model_fkey foreign key (id) references cm.shop_modification_model;

create table cm.shop_payout_tool_modification_model
(
    id             int8              not null,
    payout_tool_id character varying not null,
    primary key (id)
);

alter table if exists cm.shop_payout_tool_modification_model
    add constraint shop_modification_model_fkey foreign key (id) references cm.shop_modification_model;

create table cm.shop_payout_schedule_modification_model
(
    id                   int8 not null,
    business_schedule_id int4,
    primary key (id)
);

alter table if exists cm.shop_payout_schedule_modification_model
    add constraint shop_modification_model_fkey foreign key (id) references cm.shop_modification_model;

create table cm.shop_location_modification_model
(
    id               int8 not null,
    shop_location_id int8 not null,
    primary key (id)
);

alter table if exists cm.shop_location_modification_model
    add constraint shop_modification_model_fkey foreign key (id) references cm.shop_modification_model;

create table cm.contract_creation_modification_model
(
    id                 int8 not null,
    contract_params_id int8 not null,
    primary key (id)
);

alter table if exists cm.contract_creation_modification_model
    add constraint contract_modification_model_fkey foreign key (id) references cm.contract_modification_model;

create table cm.contract_adjustment_modification_model
(
    id                     int8              not null,
    contract_adjustment_id character varying not null,
    primary key (id)
);

alter table if exists cm.contract_adjustment_modification_model
    add constraint contract_modification_model_fkey foreign key (id) references cm.contract_modification_model;

create table cm.contract_adjustment_creation_modification_model
(
    id                            int8 not null,
    contract_adjustment_params_id int8 not null,
    primary key (id)
);

alter table if exists cm.contract_adjustment_creation_modification_model
    add constraint contract_adjustment_modification_model_fkey foreign key (id) references cm.contract_adjustment_modification_model;

create table cm.contract_legal_agreement_binding_modification_model
(
    id                 int8 not null,
    legal_agreement_id int8 not null,
    primary key (id)
);

alter table if exists cm.contract_legal_agreement_binding_modification_model
    add constraint contract_modification_model_fkey foreign key (id) references cm.contract_modification_model;

create table cm.contract_contractor_change_modification_model
(
    id            int8              not null,
    contractor_id character varying not null,
    primary key (id)
);

alter table if exists cm.contract_contractor_change_modification_model
    add constraint contract_modification_model_fkey foreign key (id) references cm.contract_modification_model;

create table cm.contract_payout_tool_modification_model
(
    id             int8              not null,
    payout_tool_id character varying not null,
    primary key (id)
);

alter table if exists cm.contract_payout_tool_modification_model
    add constraint contract_modification_model_fkey foreign key (id) references cm.contract_modification_model;

create table cm.contract_payout_tool_creation_modification_model
(
    id                    int8 not null,
    payout_tool_params_id int8 not null,
    primary key (id)
);

alter table if exists cm.contract_payout_tool_creation_modification_model
    add constraint contract_payout_tool_modification_model_fkey foreign key (id) references cm.contract_payout_tool_modification_model;

create table cm.contract_payout_tool_change_modification_model
(
    id                  int8 not null,
    payout_tool_info_id int8 not null,
    primary key (id)
);

alter table if exists cm.contract_payout_tool_change_modification_model
    add constraint contract_payout_tool_modification_model_fkey foreign key (id) references cm.contract_payout_tool_modification_model;

create table cm.contract_termination_modification_model
(
    id     int8 not null,
    reason character varying,
    primary key (id)
);

alter table if exists cm.contract_termination_modification_model
    add constraint contract_modification_model_fkey foreign key (id) references cm.contract_modification_model;

create table cm.contractor_registered_user_creation_modification_model
(
    id    int8              not null,
    email character varying not null,
    primary key (id)
);

alter table if exists cm.contractor_registered_user_creation_modification_model
    add constraint contractor_modification_model_fkey foreign key (id) references cm.contractor_modification_model;

create table cm.contractor_identification_level_modification_model
(
    id                              int8              not null,
    contractor_identification_level character varying not null,
    primary key (id)
);

alter table if exists cm.contractor_identification_level_modification_model
    add constraint contractor_modification_model_fkey foreign key (id) references cm.contractor_modification_model;

create table cm.contractor_legal_entity_creation_modification_model
(
    id              int8 not null,
    legal_entity_id int8 not null,
    primary key (id)
);

alter table if exists cm.contractor_legal_entity_creation_modification_model
    add constraint contractor_modification_model_fkey foreign key (id) references cm.contractor_modification_model;

create table cm.contractor_private_entity_creation_modification_model
(
    id                int8 not null,
    private_entity_id int8 not null,
    primary key (id)
);

alter table if exists cm.contractor_private_entity_creation_modification_model
    add constraint contractor_modification_model_fkey foreign key (id) references cm.contractor_modification_model;

create table cm.private_entity_model
(
    id              int8        not null,
    dtype           varchar(31) not null,
    first_name      character varying,
    middle_name     character varying,
    second_name     character varying,
    contact_info_id int8,
    primary key (id)
);

alter table if exists cm.contractor_private_entity_creation_modification_model
    add constraint private_entity_model_fkey foreign key (private_entity_id) references cm.private_entity_model;

create table cm.contract_report_preferences_modification_model
(
    id                                    int8 not null,
    service_acceptance_act_preferences_id int8,
    primary key (id)
);

alter table if exists cm.contract_report_preferences_modification_model
    add constraint contract_modification_model_fkey foreign key (id) references cm.contract_modification_model;

create table cm.shop_params_model
(
    id               int8              not null,
    category_id      int4              not null,
    contract_id      character varying not null,
    payout_tool_id   character varying not null,
    shop_details_id  int8              not null,
    shop_location_id int8              not null,
    primary key (id)
);

alter table if exists cm.shop_creation_modification_model
    add constraint shop_params_model_fkey foreign key (shop_params_id) references cm.shop_params_model;

create table cm.shop_details_model
(
    id          int8              not null,
    name        character varying not null,
    description character varying,
    primary key (id)
);

alter table if exists cm.shop_details_modification_model
    add constraint shop_details_model_fkey foreign key (shop_details_id) references cm.shop_details_model;

alter table if exists cm.shop_params_model
    add constraint shop_details_model_fkey foreign key (shop_details_id) references cm.shop_details_model;

create table cm.shop_account_params_model
(
    id                     int8              not null,
    currency_symbolic_code character varying not null,
    primary key (id)
);

alter table if exists cm.shop_account_creation_modification_model
    add constraint shop_account_params_model_fkey foreign key (shop_account_params_id) references cm.shop_account_params_model;

create table cm.contract_params_model
(
    id                     int8 not null,
    contract_template_id   int4,
    contractor_id          character varying,
    payment_institution_id int4,
    primary key (id)
);

alter table if exists cm.contract_creation_modification_model
    add constraint contract_params_model_fkey foreign key (contract_params_id) references cm.contract_params_model;

create table cm.contract_adjustment_params_model
(
    id                   int8 not null,
    contract_template_id int4 not null,
    primary key (id)
);

alter table if exists cm.contract_adjustment_creation_modification_model
    add constraint contract_adjustment_params_model_fkey foreign key (contract_adjustment_params_id) references cm.contract_adjustment_params_model;

create table cm.payout_tool_info_model
(
    id                            int8        not null,
    dtype                         varchar(31) not null,
    wallet_id                     character varying,
    international_bank_account_id int8,
    russian_bank_account_id       int8,
    primary key (id)
);

alter table if exists cm.contract_payout_tool_change_modification_model
    add constraint payout_tool_info_model_fkey foreign key (payout_tool_info_id) references cm.payout_tool_info_model;

create table cm.payout_tool_params_model
(
    id                     int8              not null,
    currency_symbolic_code character varying not null,
    payout_tool_info_id    int8              not null,
    primary key (id)
);

alter table if exists cm.contract_payout_tool_creation_modification_model
    add constraint payout_tool_params_model_fkey foreign key (payout_tool_params_id) references cm.payout_tool_params_model;

create table cm.legal_entity_model
(
    id                       int8        not null,
    dtype                    varchar(31) not null,
    actual_address           character varying,
    legal_name               character varying,
    registered_address       character varying,
    registered_number        character varying,
    trading_name             character varying,
    inn                      character varying,
    post_address             character varying,
    registered_name          character varying,
    representative_document  character varying,
    representative_full_name character varying,
    representative_position  character varying,
    russian_bank_account_id  int8,
    primary key (id)
);

alter table if exists cm.contractor_legal_entity_creation_modification_model
    add constraint legal_entity_model_fkey foreign key (legal_entity_id) references cm.legal_entity_model;

create table cm.russian_bank_account_model
(
    id                int8              not null,
    account           character varying not null,
    bank_bik          character varying not null,
    bank_name         character varying not null,
    bank_post_account character varying not null,
    primary key (id)
);

alter table if exists cm.legal_entity_model
    add constraint russian_bank_account_model_fkey foreign key (russian_bank_account_id) references cm.russian_bank_account_model;

alter table if exists cm.payout_tool_info_model
    add constraint russian_bank_account_model_fkey foreign key (russian_bank_account_id) references cm.russian_bank_account_model;

create table cm.international_bank_account_model
(
    id                            int8 not null,
    account_holder                character varying,
    iban                          character varying,
    number                        character varying,
    correspondent_account_id      int8,
    international_bank_details_id int8,
    primary key (id)
);

alter table if exists cm.international_bank_account_model
    add constraint correspondent_bank_account_model_fkey foreign key (correspondent_account_id) references cm.international_bank_account_model;

alter table if exists cm.payout_tool_info_model
    add constraint international_bank_account_model_fkey foreign key (international_bank_account_id) references cm.international_bank_account_model;

create table cm.international_bank_details_model
(
    id           int8 not null,
    aba_rtn      character varying,
    address      character varying,
    bic          character varying,
    country_code int4,
    name         character varying,
    primary key (id)
);

alter table if exists cm.international_bank_account_model
    add constraint international_bank_details_model_fkey foreign key (international_bank_details_id) references cm.international_bank_details_model;

create table cm.legal_agreement_model
(
    id                 int8                        not null,
    legal_agreement_id character varying           not null,
    signed_at          timestamp without time zone not null,
    valid_until        timestamp without time zone,
    primary key (id)
);

alter table if exists cm.contract_legal_agreement_binding_modification_model
    add constraint legal_agreement_model_fkey foreign key (legal_agreement_id) references cm.legal_agreement_model;

create table cm.contact_info_model
(
    id           int8 not null,
    email        character varying,
    phone_number character varying,
    primary key (id)
);

alter table if exists cm.private_entity_model
    add constraint contact_info_model_fkey foreign key (contact_info_id) references cm.contact_info_model;

create table cm.representative_document_model
(
    id                 int8        not null,
    dtype              varchar(31) not null,
    legal_agreement_id int8,
    primary key (id)
);

alter table if exists cm.representative_document_model
    add constraint legal_agreement_model_fkey foreign key (legal_agreement_id) references cm.legal_agreement_model;

create table cm.representative_model
(
    id          int8              not null,
    full_name   character varying not null,
    position    character varying not null,
    document_id int8              not null,
    primary key (id)
);

alter table if exists cm.representative_model
    add constraint representative_document_model_fkey foreign key (document_id) references cm.representative_document_model;

create table cm.service_acceptance_act_preferences_model
(
    id           int8 not null,
    scheduler_id int4 not null,
    signer_id    int8 not null,
    primary key (id)
);

alter table if exists cm.contract_report_preferences_modification_model
    add constraint service_acceptance_act_preferences_model_fkey foreign key (service_acceptance_act_preferences_id) references cm.service_acceptance_act_preferences_model;

alter table if exists cm.service_acceptance_act_preferences_model
    add constraint representative_model_fkey foreign key (signer_id) references cm.representative_model;

create table cm.shop_location_model
(
    id    int8        not null,
    dtype varchar(31) not null,
    url   character varying,
    primary key (id)
);

alter table if exists cm.shop_location_modification_model
    add constraint shop_location_model_fkey foreign key (shop_location_id) references cm.shop_location_model;

alter table if exists cm.shop_params_model
    add constraint shop_location_model_fkey foreign key (shop_location_id) references cm.shop_location_model

