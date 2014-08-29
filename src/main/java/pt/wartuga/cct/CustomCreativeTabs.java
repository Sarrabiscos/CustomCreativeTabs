package pt.wartuga.cct;

import pt.wartuga.cct.creativetab.CreativeTabsManager;
import pt.wartuga.cct.reference.Reference;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class CustomCreativeTabs {

	@Mod.Instance(Reference.MOD_ID)
	public static CustomCreativeTabs instance;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		CreativeTabsManager.init(event.getSuggestedConfigurationFile());
	}

}
