package jeresources.config;

import jeresources.reference.Reference;
import jeresources.util.TranslationHelper;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ConfigHandler {
    public static Configuration config;
    private static File configDir;
    private static final String worldGenFileName = "world-gen.json";
    private static final String scanBlacklistName = "scan-blacklist.txt";

    public static void init(File configDir) {
        if (config == null) {
            configDir = new File(configDir, Reference.ID);
            configDir.mkdir();
            ConfigHandler.configDir = configDir;
            config = new Configuration(new File(configDir, Reference.ID + ".cfg"));
            loadConfig();
        }
    }

    public static File getConfigDir() {
        return configDir;
    }

    public static File getWorldGenFile() {
        return new File(configDir, worldGenFileName);
    }

    public static File getScanBlacklistFile() {
        return new File(configDir, scanBlacklistName);
    }

    @SubscribeEvent
    public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equalsIgnoreCase(Reference.ID)) {
            loadConfig();
        }
    }

    private static void loadConfig() {
        Property prop;

        prop = config.get(Configuration.CATEGORY_GENERAL, "itemsPerColumn", 4);
        prop.setComment(TranslationHelper.translateToLocal("jer.config.itemsPerColumn.description"));
        prop.setMinValue(1).setMaxValue(4);
        prop.setLanguageKey("jer.config.itemsPerColumn.title");
        Settings.ITEMS_PER_COLUMN = prop.getInt();

        prop = config.get(Configuration.CATEGORY_GENERAL, "itemsPerRow", 4);
        prop.setComment(TranslationHelper.translateToLocal("jer.config.itemsPerRow.description"));
        prop.setMinValue(1).setMaxValue(4);
        prop.setLanguageKey("jer.config.itemsPerRow.title");
        Settings.ITEMS_PER_ROW = prop.getInt();

        prop = config.get(Configuration.CATEGORY_GENERAL, "diyData", true);
        prop.setComment(TranslationHelper.translateToLocal("jer.config.diyData.description"));
        prop.setLanguageKey("jer.config.diyData.title");
        prop.requiresMcRestart();
        Settings.useDIYdata = prop.getBoolean();

        prop = config.get(Configuration.CATEGORY_GENERAL, "showDevData", false);
        prop.setComment(TranslationHelper.translateToLocal("jer.config.showDevData.description"));
        prop.setLanguageKey("jer.config.showDevData.title");
        prop.requiresMcRestart();
        Settings.showDevData = prop.getBoolean();

        prop = config.get(Configuration.CATEGORY_GENERAL, "enchantsBlacklist", new String[]{"flimflam", "soulBound"});
        prop.setComment(TranslationHelper.translateToLocal("jer.config.enchantsBlacklist.description"));
        prop.setLanguageKey("jer.config.enchantsBlacklist.title");
        prop.requiresMcRestart();
        Settings.excludedEnchants = prop.getStringList();

        prop = config.get(Configuration.CATEGORY_GENERAL, "hiddenTabs", new String[]{});
        prop.setComment(TranslationHelper.translateToLocal("jer.config.hiddenTabs.description"));
        prop.setLanguageKey("jer.config.hiddenTabs.title");
        prop.requiresMcRestart();
        Settings.hiddenCategories = prop.getStringList();

        prop = config.get(Configuration.CATEGORY_GENERAL, "dimensionsBlacklist", new int[]{-11});
        prop.setComment(TranslationHelper.translateToLocal("jer.config.dimensionsBlacklist.description"));
        prop.setLanguageKey("jer.config.dimensionsBlacklist.title");
        prop.requiresMcRestart();
        Settings.excludedDimensions = IntStream.of(prop.getIntList()).boxed().collect(Collectors.toList());

        if (config.hasChanged())
            config.save();
        Settings.reload();
    }

    @SuppressWarnings("unchecked")
    public static List<IConfigElement> getConfigElements() {
        List<IConfigElement> list = new ArrayList<>();
        list.addAll(new ConfigElement(config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements());
        return list;
    }
}
