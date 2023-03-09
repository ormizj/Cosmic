package database.drop;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import server.life.MonsterDropEntry;
import server.life.MonsterGlobalDropEntry;

import java.util.List;

public class DropProvider {
    private final DropDao dropDao;
    private final Cache<Integer, List<MonsterDrop>> monsterDropCache = Caffeine.newBuilder().build();
    private volatile List<GlobalMonsterDrop> globalMonsterDrops = null;

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

    public List<MonsterGlobalDropEntry> getGlobalDropEntries() {
        if (this.globalMonsterDrops == null) {
            loadGlobalDrops();
        }

        return globalMonsterDrops.stream()
                .map(this::mapToDropEntry)
                .toList();
    }

    private void loadGlobalDrops() {
        this.globalMonsterDrops = dropDao.getGlobalMonsterDrops();
    }

    // TODO: Temporary. MonsterDropEntry should be removed.
    private MonsterGlobalDropEntry mapToDropEntry(GlobalMonsterDrop globalDrop) {
        short questId = globalDrop.questId() == null ? 0 : globalDrop.questId().shortValue();
        return new MonsterGlobalDropEntry(globalDrop.itemId(), globalDrop.chance(), globalDrop.continent(),
                globalDrop.minQuantity(), globalDrop.maxQuantity(), questId);
    }
}
