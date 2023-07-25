package database.monsterbook;

public record MonsterCard(int cardId, byte level) {

    public MonsterCard {
        if (cardId / 10_000 != 238) {
            throw new IllegalArgumentException("Invalid monster card id: %d".formatted(cardId));
        }
        if (level < 0 || level > 5) {
            throw new IllegalArgumentException("Invalid monster card level: %d".formatted(level));
        }
    }

    public boolean isSpecial() {
        return cardId / 1000 == 2388;
    }
}
