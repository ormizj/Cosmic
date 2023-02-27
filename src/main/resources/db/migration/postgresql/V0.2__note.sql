CREATE TABLE note(
    id serial NOT NULL,
    receiver text NOT NULL,
    sender text NOT NULL,
    message text NOT NULL,
    timestamp bigint NOT NULL,
    fame smallint NOT NULL,
    deleted smallint NOT NULL,
    PRIMARY KEY (id)
);
CREATE INDEX note_received_idx ON note(receiver);
GRANT SELECT, INSERT, UPDATE ON note TO ${server-username};
GRANT USAGE ON note_id_seq TO ${server-username};
