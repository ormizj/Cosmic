package database.shop;

import database.DaoException;
import database.PgDatabaseConnection;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.JdbiException;

import java.util.List;

public class ShopDao {
    private final PgDatabaseConnection connection;

    public ShopDao(PgDatabaseConnection connection) {
        this.connection = connection;
    }

    public List<ShopItem> getShopItems(int shopId) {
        try (Handle handle = connection.getHandle()) {
            return handle.createQuery("""
                            SELECT *
                            FROM shop_item
                            WHERE shop = ?
                            ORDER BY position DESC;""")
                    .bind(0, shopId)
                    .mapTo(ShopItem.class)
                    .list();
        } catch (JdbiException e) {
            throw new DaoException("Failed to get shop items for shop %d".formatted(shopId), e);
        }
    }
}
