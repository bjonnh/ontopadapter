CREATE TABLE project_user (
    id         SERIAL PRIMARY KEY
);

CREATE TABLE project (
    id         SERIAL PRIMARY KEY,
    parent_id  integer            NULL REFERENCES project (id) ,
    owner      integer            NULL REFERENCES project_user (id)
);

INSERT INTO project_user VALUES (0), (1);
INSERT INTO project VALUES (0, NULL, 0 ), (1, 0, 0), (2, NULL, 1);