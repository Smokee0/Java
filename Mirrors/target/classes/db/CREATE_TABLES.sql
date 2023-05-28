-- drop table mirror;

CREATE TABLE mirror
(
    id SERIAL NOT NULL PRIMARY KEY,
    isSphere BOOLEAN,
    isConcave BOOLEAN,
    R INTEGER,
    posX INTEGER,
    posY INTEGER,
    model_id INTEGER
);

CREATE TABLE model
(
    id SERIAL NOT NULL PRIMARY KEY,
    startX INTEGER,
    startY INTEGER,
    directionX INTEGER,
    directionY INTEGER
);