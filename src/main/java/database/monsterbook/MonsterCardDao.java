package database.monsterbook;

import database.DaoException;
import database.PgDatabaseConnection;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.JdbiException;

import java.util.List;

public class MonsterCardDao {
    private final PgDatabaseConnection connection;

    public MonsterCardDao(PgDatabaseConnection connection) {
        this.connection = connection;
    }

    public List<MonsterCard> load(int chrId) {
        try (Handle handle = connection.getHandle()) {
            return handle.createQuery("""
                            SELECT *
                            FROM monster_card
                            WHERE chr_id = ?;""")
                    .bind(0, chrId)
                    .mapTo(MonsterCard.class)
                    .list();
        } catch (JdbiException e) {
            throw new DaoException("Failed to find monster cards for chr id %d".formatted(chrId), e);
        }
    }
}
