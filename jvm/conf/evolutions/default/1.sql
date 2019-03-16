-- !Ups

CREATE TABLE "User" (
    "idx" bigint(20) NOT NULL AUTO_INCREMENT,
    "id" varchar(255) NOT NULL,
    "password" varchar(255) NOT NULL,
    PRIMARY KEY ("idx")
);

-- !Downs

DROP TABLE "User";
