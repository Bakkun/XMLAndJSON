package model;

// Опционально, возможно, смогу связать
public enum MoneyMovementType {
    RECEIPT ("доход"),
    WITHDRAWAL ("расход");

    private final String name;

    MoneyMovementType(String str) {
        this.name = str;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
