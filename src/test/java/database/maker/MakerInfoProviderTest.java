package database.maker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import testutil.AnyValues;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MakerInfoProviderTest {

    @Mock
    private MakerDao makerDao;

    private MakerInfoProvider makerInfoProvider;

    @BeforeEach
    void reset() {
        MockitoAnnotations.openMocks(this);
        this.makerInfoProvider = new MakerInfoProvider(makerDao);
    }

    @Test
    void requireNonNullDao() {
        assertThrows(IllegalArgumentException.class, () -> new MakerInfoProvider(null));
    }

    @Test
    void getReagentFromDb() {
        int itemId = 5783;
        MakerReagent reagent = createReagent(itemId);
        when(makerDao.getReagent(anyInt())).thenReturn(Optional.of(reagent));

        Optional<MakerReagent> retrievedReagent = makerInfoProvider.getMakerReagent(itemId);

        assertTrue(retrievedReagent.isPresent());
        assertEquals(reagent, retrievedReagent.get());
    }

    @Test
    void getCachedReagent() {
        int itemId = 90123444;
        MakerReagent reagent = createReagent(itemId);
        when(makerDao.getReagent(anyInt())).thenReturn(Optional.of(reagent));

        Optional<MakerReagent> firstReagent = makerInfoProvider.getMakerReagent(itemId);
        Optional<MakerReagent> secondReagent = makerInfoProvider.getMakerReagent(itemId);

        assertTrue(firstReagent.isPresent());
        assertEquals(reagent, firstReagent.get());
        assertTrue(secondReagent.isPresent());
        assertEquals(reagent, secondReagent.get());
        verify(makerDao, times(1)).getReagent(itemId);
    }

    private MakerReagent createReagent(int itemId) {
        return new MakerReagent(itemId, "incPAD", 3);
    }

    @Test
    void getRecipeFromDb() {
        int itemId = 43893;
        MakerRecipe recipe = createRecipe(itemId);
        when(makerDao.getRecipe(itemId)).thenReturn(Optional.of(recipe));

        Optional<MakerRecipe> retrievedRecipe = makerInfoProvider.getMakerRecipe(itemId);

        assertTrue(retrievedRecipe.isPresent());
        assertEquals(recipe, retrievedRecipe.get());
    }

    @Test
    void getRecipeFromDb_notFound() {
        when(makerDao.getRecipe(anyInt())).thenReturn(Optional.empty());

        Optional<MakerRecipe> recipe = makerInfoProvider.getMakerRecipe(AnyValues.integer());

        assertTrue(recipe.isEmpty());
    }

    @Test
    void getCachedRecipe() {
        int itemId = 10848;
        MakerRecipe recipe = createRecipe(itemId);
        when(makerDao.getRecipe(anyInt())).thenReturn(Optional.of(recipe));

        Optional<MakerRecipe> firstRecipe = makerInfoProvider.getMakerRecipe(itemId);
        Optional<MakerRecipe> secondRecipe = makerInfoProvider.getMakerRecipe(itemId);

        assertTrue(firstRecipe.isPresent());
        assertEquals(recipe, firstRecipe.get());
        assertTrue(secondRecipe.isPresent());
        assertEquals(recipe, secondRecipe.get());
        verify(makerDao, times(1)).getRecipe(itemId);
    }

    private MakerRecipe createRecipe(int itemId) {
        return new MakerRecipe(itemId, (short) 0, (short) 45, (short) 1, 100_000, null, null, null, (short) 1, (short) 1);
    }

    @Test
    void getStimulant() {
        int catalyst = 4031200;
        MakerRecipe recipeWithCatalyst = new MakerRecipe(0, (short) 0, (short) 0, (short) 0, 0, null, null, catalyst, (short) 0, (short) 0);
        when(makerDao.getRecipe(anyInt())).thenReturn(Optional.of(recipeWithCatalyst));

        Optional<Integer> stimulant = makerInfoProvider.getStimulant(AnyValues.integer());

        assertTrue(stimulant.isPresent());
        assertEquals(catalyst, stimulant.get());
    }

    @Test
    void getStimulant_noRecipe() {
        when(makerDao.getRecipe(anyInt())).thenReturn(Optional.empty());

        Optional<Integer> stimulant = makerInfoProvider.getStimulant(AnyValues.integer());

        assertTrue(stimulant.isEmpty());
    }
}
