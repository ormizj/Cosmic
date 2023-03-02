package database.maker;

import database.DaoException;
import database.PgDatabaseConnection;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.JdbiException;

import java.util.Optional;

public class MakerDao {
    private final PgDatabaseConnection connection;

    public MakerDao(PgDatabaseConnection connection) {
        this.connection = connection;
    }

    public Optional<MakerReagent> getReagent(int itemId) {
        try (Handle handle = connection.getHandle()) {
            return handle.createQuery("""
                            SELECT *
                            FROM maker_reagent
                            WHERE item_id = ?;""")
                    .bind(0, itemId)
                    .mapTo(MakerReagent.class)
                    .findOne();
        } catch (JdbiException e) {
            throw new DaoException("Failed to get maker reagent with item id: %d".formatted(itemId), e);
        }
    }
}
