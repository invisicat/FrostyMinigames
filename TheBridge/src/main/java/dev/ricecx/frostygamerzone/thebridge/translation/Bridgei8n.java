package dev.ricecx.frostygamerzone.thebridge.translation;

public enum Bridgei8n {
    SOULBOUND_DROP_CONFIRM("soulbound_drop_confirm"),
    SOULBOUND_CANNOT_DROP("soulbound_cannot_drop"),
    ;

    private final String key;

    Bridgei8n(String key) {
        this.key = key;
    }

    public String getTranslation() {
        // TODO: Implement i8n
        return "No translation provided for " + key;
    }
}
