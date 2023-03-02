package database;

import database.maker.MakerReagentRowMapper;
import database.note.NoteRowMapper;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class PgDatabaseConnection {
    private final DataSource dataSource;
    private final Jdbi jdbi;

    public PgDatabaseConnection(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbi = Jdbi.create(dataSource);
        registerRowMappers();
        // TODO: configure jdbi elsewhere
    }

    private void registerRowMappers() {
        jdbi.registerRowMapper(new NoteRowMapper())
                .registerRowMapper(new MakerReagentRowMapper());
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public Handle getHandle() {
        return jdbi.open();
    }
}
