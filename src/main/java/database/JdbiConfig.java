package database;

import database.drop.GlobalMonsterDropRowMapper;
import database.drop.MonsterDropRowMapper;
import database.maker.MakerIngredientRowMapper;
import database.maker.MakerReagentRowMapper;
import database.maker.MakerRecipeRowMapper;
import database.note.NoteRowMapper;
import database.shop.ShopItemRowMapper;
import org.jdbi.v3.core.Jdbi;

import javax.sql.DataSource;

public final class JdbiConfig {
    private JdbiConfig() {}

    public static Jdbi createConfigured(DataSource dataSource) {
        return Jdbi.create(dataSource)
                .registerRowMapper(new NoteRowMapper())
                .registerRowMapper(new MakerReagentRowMapper())
                .registerRowMapper(new MakerRecipeRowMapper())
                .registerRowMapper(new MakerIngredientRowMapper())
                .registerRowMapper(new MonsterDropRowMapper())
                .registerRowMapper(new GlobalMonsterDropRowMapper())
                .registerRowMapper(new ShopItemRowMapper());
    }
}
