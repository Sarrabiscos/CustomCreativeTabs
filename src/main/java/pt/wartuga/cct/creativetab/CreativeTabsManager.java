package pt.wartuga.cct.creativetab;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import pt.wartuga.cct.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CreativeTabsManager {

	private static Map<String, Item[]> items = new HashMap<String, Item[]>();
	private static List<CreativeTabs> tabs = new ArrayList<CreativeTabs>();
	public static Configuration configuration;

	public static void init(File configFile) {
		if (configuration != null) return;
		configuration = new Configuration(configFile);
		loadConfiguration();
	}

	public static void loadConfiguration() {
		items.clear();
		for (String s : configuration.getCategoryNames()) {
			if (s.equalsIgnoreCase(Configuration.CATEGORY_GENERAL)) continue;

			String label = configuration.getString("label", s, s, "Name of the Category");
			Item icon = (Item) Item.itemRegistry.getObject(configuration.getString("icon", s,
					"minecraft:apple", "Name of the item for the icon. Ex: minecraft:apple"));

			CreativeTabs tab = null;
			for (CreativeTabs t : tabs)
				if (t.getTranslatedTabLabel().equalsIgnoreCase(label)) {
					tab = t;
					break;
				}
			if (tab == null) {
				addTab(label, icon);
			}

			List<Item> items = new ArrayList<Item>();
			String[] defaultItems = { "minecraft:apple" };
			String[] itemString = configuration.getStringList("items", s, defaultItems,
					"The name items that this tab will have");
			for (String i : itemString)
				items.add((Item) Item.itemRegistry.getObject(i));

			setItems(label, items.toArray(new Item[items.size()]));
		}
		if (configuration.hasChanged()) configuration.save();
	}

	@SideOnly(Side.CLIENT)
	public static void addTab(final String label, final Item icon) {
		CreativeTabs tab = new CreativeTabs(Reference.MOD_ID + ":" + label) {
			@Override
			@SideOnly(Side.CLIENT)
			public Item getTabIconItem() {
				return icon;
			}

			@Override
			@SideOnly(Side.CLIENT)
			public String getTranslatedTabLabel() {
				return this.getTabLabel().substring(4);
			}

			@SuppressWarnings("rawtypes")
			@Override
			@SideOnly(Side.CLIENT)
			public void displayAllReleventItems(List finalItems) {
				if (!items.containsKey(label)) return;
				for (Item item : items.get(label))
					item.getSubItems(item, this, finalItems);
			}

		};
		tabs.add(tab);
	}

	@SideOnly(Side.CLIENT)
	public static void setItems(String label, Item[] is) {
		if (items.containsKey(label)) items.remove(label);
		items.put(label, is);
	}

}
