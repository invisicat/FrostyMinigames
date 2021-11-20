package dev.ricecx.frostygamerzone.minigameapi.map;

public interface MapMeta {
    int getVersion();
    void setVersion(int version);

    long getLastModified();
    void setLastModified(long lastModified);

    String getName();
    void setName(String name);

    String getWorldTemplateName();
    void setWorldTemplateName(String template);

}
