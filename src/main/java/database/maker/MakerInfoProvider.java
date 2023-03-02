package database.maker;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class MakerInfoProvider {
    private final MakerDao makerDao;
    private final Map<Integer, MakerReagent> reagents = new ConcurrentHashMap<>();

    public MakerInfoProvider(MakerDao makerDao) {
        this.makerDao = makerDao;
    }

    public Optional<MakerReagent> getMakerReagent(int itemId) {
        final MakerReagent cachedReagent = reagents.get(itemId);
        if (cachedReagent != null) {
            return Optional.of(cachedReagent);
        }

        final Optional<MakerReagent> reagentFromDb = makerDao.getReagent(itemId);
        if (reagentFromDb.isEmpty()) {
            return Optional.empty();
        }
        reagents.put(itemId, reagentFromDb.get());
        return reagentFromDb;
    }
}
