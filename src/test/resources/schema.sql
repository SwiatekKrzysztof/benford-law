create table if not exists uploaded_document
(
    id             bigint generated by default as identity
        constraint uploaded_document_pkey
            primary key,
    uuid            UUID UNIQUE ,
    content        text[],
    time_added     timestamp,
    title varchar
);

create table graph
(
    id                  bigint generated by default as identity
        constraint graph_pkey
            primary key,
    document_uuid         uuid,
    matches_benford_law boolean,
    ones_count          bigint,
    twos_count          bigint,
    threes_count        bigint,
    fours_count         bigint,
    fives_count         bigint,
    sixes_count         bigint,
    sevens_count        bigint,
    eights_count        bigint,
    nines_count         bigint
);


