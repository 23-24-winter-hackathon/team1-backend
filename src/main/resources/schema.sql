drop table if exists food;
drop table if exists nutrition;

create table nutrition
(
    calorie      double null,
    carbon       double null,
    fat          double null,
    protein      double null,
    nutrition_id bigint auto_increment
        primary key
);

create table food
(
    api_index    int                                         null,
    view         int                                         null,
    food_id      bigint auto_increment
        primary key,
    nutrition_id bigint                                      null,
    food_name    varchar(255)                                null,
    img_src      varchar(255)                                null,
    food_type    enum ('반찬', '후식', '일품', '밥', '기타', '국_찌개')  null,
    ingredients  text                                    null,
    way_to_cook  enum ('끓이기', '기타', '굽기', '튀기기', '찌기', '볶기') null,
    constraint UK_e104jxunuv2s4d4y2uedei5vi
        unique (nutrition_id),
    constraint FKhlhq1p9yf9meoljfm7cg74o21
        foreign key (nutrition_id) references nutrition (nutrition_id)
)