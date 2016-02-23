package net.blay09.mods.excompressum;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.blay09.mods.excompressum.block.BlockBait;
import net.blay09.mods.excompressum.block.BlockCompressedDust;
import net.blay09.mods.excompressum.block.BlockHeavySieve;
import net.blay09.mods.excompressum.block.BlockWoodenCrucible;
import net.blay09.mods.excompressum.handler.CompressedHammerHandler;
import net.blay09.mods.excompressum.item.*;
import net.blay09.mods.excompressum.registry.ChickenStickRegistry;
import net.blay09.mods.excompressum.registry.CompressedHammerRegistry;
import net.blay09.mods.excompressum.registry.HeavySieveRegistry;
import net.blay09.mods.excompressum.registry.WoodenCrucibleRegistry;
import net.blay09.mods.excompressum.tile.TileEntityBait;
import net.blay09.mods.excompressum.tile.TileEntityWoodenCrucible;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = ExCompressum.MOD_ID, name = "ExCompressum", dependencies = "required-after:exnihilo")
public class ExCompressum {

    public static final Logger logger = LogManager.getLogger();
    public static final String MOD_ID = "excompressum";

    @Mod.Instance
    public static ExCompressum instance;

    @SidedProxy(serverSide = "net.blay09.mods.excompressum.CommonProxy", clientSide = "net.blay09.mods.excompressum.client.ClientProxy")
    public static CommonProxy proxy;

    private Configuration config;
    public static float compressedCrookDurabilityMultiplier;
    public static float compressedCrookSpeedMultiplier;
    public static float chickenStickSoundChance;
    public static boolean allowHeavySieveAutomation;
    public static int woodenCrucibleSpeed;

    public static ItemChickenStick chickenStick;
    public static ItemCompressedHammer compressedHammerWood;
    public static ItemCompressedHammer compressedHammerStone;
    public static ItemCompressedHammer compressedHammerIron;
    public static ItemCompressedHammer compressedHammerGold;
    public static ItemCompressedHammer compressedHammerDiamond;
    public static ItemCompressedCrook compressedCrook;
    public static ItemHeavySilkMesh heavySilkMesh;

    public static BlockCompressedDust compressedDust;
    public static BlockHeavySieve heavySieve;
    public static BlockWoodenCrucible woodenCrucible;
    public static BlockBait bait;

    @Mod.EventHandler
    @SuppressWarnings("unused")
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new CompressedHammerHandler());

        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        chickenStickSoundChance = config.getFloat("Chicken Stick Sound Chance", "general", 0.15f, 0f, 1f, "The chance for the chicken stick to make sounds when breaking blocks. Set to 0 to disable.");
        compressedCrookDurabilityMultiplier = config.getFloat("Compressed Crook Durability Multiplier", "general", 2f, 0.1f, 10f, "The multiplier applied to the Compressed Crook's durability (based on the normal wooden crook)");
        compressedCrookSpeedMultiplier = config.getFloat("Compressed Crook Speed Multiplier", "general", 4f, 0.1f, 10f, "The multiplier applied to the Compressed Crook's speed (based on the normal wooden crook)");
        allowHeavySieveAutomation = config.getBoolean("Allow Heavy Sieve Automation", "general", false, "Set this to true if you want to allow automation of the heavy sieve through fake players (i.e. Autonomous Activator)");
        woodenCrucibleSpeed = config.getInt("Wooden Crucible Speed", "general", 1, 1, 10, "The speed at which the wooden crucible extracts water. 0.1 is equivalent to a torch below a crucible, 0.3 is equivalent to fire below a crucible.");

        compressedDust = new BlockCompressedDust();
        GameRegistry.registerBlock(compressedDust, "compressed_dust"); // god damn it Blay. can't rename because already released
        heavySieve = new BlockHeavySieve();
        GameRegistry.registerBlock(heavySieve, ItemBlockHeavySieve.class, "heavySieve");
        woodenCrucible = new BlockWoodenCrucible();
        GameRegistry.registerBlock(woodenCrucible, ItemBlockWoodenCrucible.class, "woodenCrucible");
        bait = new BlockBait();
        GameRegistry.registerBlock(bait, ItemBlockBait.class, "bait");

        chickenStick = new ItemChickenStick();
        GameRegistry.registerItem(chickenStick, "chickenStick");
        compressedHammerWood = new ItemCompressedHammer(Item.ToolMaterial.WOOD, "wood");
        GameRegistry.registerItem(compressedHammerWood, "compressedHammerWood");
        compressedHammerStone = new ItemCompressedHammer(Item.ToolMaterial.STONE, "stone");
        GameRegistry.registerItem(compressedHammerStone, "compressedHammerStone");
        compressedHammerIron = new ItemCompressedHammer(Item.ToolMaterial.IRON, "iron");
        GameRegistry.registerItem(compressedHammerIron, "compressedHammerIron");
        compressedHammerGold = new ItemCompressedHammer(Item.ToolMaterial.GOLD, "gold");
        GameRegistry.registerItem(compressedHammerGold, "compressedHammerGold");
        compressedHammerDiamond = new ItemCompressedHammer(Item.ToolMaterial.EMERALD, "diamond");
        GameRegistry.registerItem(compressedHammerDiamond, "compressedHammerDiamond");
        compressedCrook = new ItemCompressedCrook();
        GameRegistry.registerItem(compressedCrook, "compressedCrook");
        heavySilkMesh = new ItemHeavySilkMesh();
        GameRegistry.registerItem(heavySilkMesh, "heavySilkMesh");

        GameRegistry.registerTileEntity(TileEntityWoodenCrucible.class, "woodenCrucible");
        GameRegistry.registerTileEntity(TileEntityBait.class, "bait");

        proxy.preInit(event);
    }

    @Mod.EventHandler
    @SuppressWarnings("unused")
    public void postInit(FMLPostInitializationEvent event) {
        boolean easyMode = config.getBoolean("Easy Mode", "general", false, "Set this to true to enable easy-mode, which disables the compressed hammers and makes compressed smashables work for normal hammers instead.");
        CompressedHammerRegistry.load(config, easyMode);
        ChickenStickRegistry.load(config);
        HeavySieveRegistry.load(config);
        WoodenCrucibleRegistry.load(config);

        if (!easyMode) {
            ItemCompressedHammer.registerRecipes(config);
        }
        ItemCompressedCrook.registerRecipes(config);
        BlockHeavySieve.registerRecipes(config);
        BlockWoodenCrucible.registerRecipes(config);
        BlockCompressedDust.registerRecipes(config);
        BlockBait.registerRecipes(config);

        config.save();
    }

}