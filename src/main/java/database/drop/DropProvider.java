package database.drop;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import server.life.MonsterDropEntry;

import java.util.List;

public class DropProvider {
    private final DropDao dropDao;
    private final Cache<Integer, List<MonsterDrop>> monsterDropCache = Caffeine.newBuilder().build();

    public DropProvider(DropDao dropDao) {
        if (dropDao == null) {
            throw new IllegalArgumentException("DropDao must not be null");
        }
        this.dropDao = dropDao;
    }

    public List<MonsterDropEntry> getMonsterDropEntries(int monsterId) {
        return monsterDropCache.get(monsterId, dropDao::getMonsterDrops).stream()
                .map(this::mapToDropEntry)
                .toList();
    }

    // TODO: Temporary. MonsterDropEntry should be removed.
    private MonsterDropEntry mapToDropEntry(MonsterDrop monsterDrop) {
        short questId = monsterDrop.questId() == null ? 0 : monsterDrop.questId().shortValue();
        return new MonsterDropEntry(monsterDrop.itemId(), monsterDrop.chance(), monsterDrop.minQuantity(),
                monsterDrop.maxQuantity(), questId);
    }
}
