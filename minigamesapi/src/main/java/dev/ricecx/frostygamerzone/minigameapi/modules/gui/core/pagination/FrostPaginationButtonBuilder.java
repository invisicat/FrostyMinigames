package dev.ricecx.frostygamerzone.minigameapi.modules.gui.core.pagination;

import dev.ricecx.frostygamerzone.minigameapi.modules.gui.core.FrostMenu;
import dev.ricecx.frostygamerzone.minigameapi.modules.gui.core.buttons.FrostButton;

public interface FrostPaginationButtonBuilder {

    FrostButton buildPaginationButton(FrostPaginationButtonType type, FrostMenu inventory);

}
