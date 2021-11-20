package dev.ricecx.frostygamerzone.minigameapi.modules.gui.core.pagination;

public enum FrostPaginationButtonType {
    PREV_BUTTON(3),
    CURRENT_BUTTON(4),
    NEXT_BUTTON(5),
    UNASSIGNED(0);

    private final int slot;

    FrostPaginationButtonType(int slot) {
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }

    public static FrostPaginationButtonType forSlot(int slot) {
        for (FrostPaginationButtonType buttonType : FrostPaginationButtonType.values()) {
            if (buttonType.slot == slot) return buttonType;
        }

        return FrostPaginationButtonType.UNASSIGNED;
    }

}
