package database.maker;

import net.jcip.annotations.ThreadSafe;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class MakerInfoProvider {
    private final MakerDao makerDao;
    private final Map<Integer, MakerReagent> reagentCache = new ConcurrentHashMap<>();
    private final Map<Integer, MakerRecipe> recipeCache = new ConcurrentHashMap<>();

    public MakerInfoProvider(MakerDao makerDao) {
        this.makerDao = makerDao;
    }

    public Optional<MakerReagent> getMakerReagent(int itemId) {
        final MakerReagent cachedReagent = reagentCache.get(itemId);
        if (cachedReagent != null) {
            return Optional.of(cachedReagent);
        }

        final Optional<MakerReagent> reagentFromDb = makerDao.getReagent(itemId);
        if (reagentFromDb.isEmpty()) {
            return Optional.empty();
        }
        reagentCache.put(itemId, reagentFromDb.get());
        return reagentFromDb;
    }

    public Optional<MakerRecipe> getMakerRecipe(int itemId) {
        final MakerRecipe cachedRecipe = recipeCache.get(itemId);
        if (cachedRecipe != null) {
            return Optional.of(cachedRecipe);
        }

        final Optional<MakerRecipe> recipeFromDb = makerDao.getRecipe(itemId);
        if (recipeFromDb.isEmpty()) {
            return Optional.empty();
        }
        recipeCache.put(itemId, recipeFromDb.get());
        return recipeFromDb;
    }

    public Optional<Integer> getStimulant(int itemId) {
        return getMakerRecipe(itemId).map(MakerRecipe::catalyst);
    }
}
