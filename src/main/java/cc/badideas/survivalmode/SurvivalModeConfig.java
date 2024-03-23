package cc.badideas.survivalmode;

import com.github.repletsin5.lunar.api.modmenu.ConfigScreenFactory;
import com.github.repletsin5.lunar.api.modmenu.ModConfigButtonAPI;
import finalforeach.cosmicreach.gamestates.OptionsMenu;

public class SurvivalModeConfig implements ModConfigButtonAPI {
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return OptionsMenu::new;
    }
}
