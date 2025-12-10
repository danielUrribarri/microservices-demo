package org.example.filmservice.entity;

public enum Rating {
    G,
    PG,
    PG_13,
    R,
    NC_17;

    public static Rating fromDatabase(String dbValue) {
        if (dbValue == null) return null;

        return switch (dbValue) {
            case "PG-13" -> PG_13;
            case "NC-17" -> NC_17;
            default -> Rating.valueOf(dbValue);
        };
    }

    public String toDatabase() {
        return switch (this) {
            case PG_13 -> "PG-13";
            case NC_17 -> "NC-17";
            default -> name();
        };
    }
}
