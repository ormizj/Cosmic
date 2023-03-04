package database.maker;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import net.jcip.annotations.ThreadSafe;

import java.util.Optional;

@ThreadSafe
public class MakerInfoProvider {
    private final MakerDao makerDao;
    private final Cache<Integer, Optional<MakerReagent>> reagentCache = Caffeine.newBuilder().build();
    private final Cache<Integer, Optional<MakerRecipe>> recipeCache = Caffeine.newBuilder().build();

    public MakerInfoProvider(MakerDao makerDao) {
        if (makerDao == null) {
            throw new IllegalArgumentException("MakerDao must not be null");
        }
        this.makerDao = makerDao;
    }

    public Optional<MakerReagent> getMakerReagent(int itemId) {
        return reagentCache.get(itemId, makerDao::getReagent);
    }

    public Optional<MakerRecipe> getMakerRecipe(int itemId) {
        return recipeCache.get(itemId, makerDao::getRecipe);
    }

    public Optional<Integer> getStimulant(int itemId) {
        return getMakerRecipe(itemId).map(MakerRecipe::catalyst);
    }
}
