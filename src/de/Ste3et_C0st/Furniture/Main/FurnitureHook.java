package de.Ste3et_C0st.Furniture.Main;

import de.Ste3et_C0st.Furniture.Main.Event.redstoneEvent;
import de.Ste3et_C0st.Furniture.Objects.RPG.*;
import de.Ste3et_C0st.Furniture.Objects.School.TrashCan;
import de.Ste3et_C0st.Furniture.Objects.christmas.AdventCalender;
import de.Ste3et_C0st.Furniture.Objects.christmas.FireworkLauncher;
import de.Ste3et_C0st.Furniture.Objects.electric.billboard;
import de.Ste3et_C0st.Furniture.Objects.electric.camera;
import de.Ste3et_C0st.Furniture.Objects.electric.streetlamp;
import de.Ste3et_C0st.Furniture.Objects.garden.*;
import de.Ste3et_C0st.Furniture.Objects.indoor.largeTable;
import de.Ste3et_C0st.Furniture.Objects.light.WaxCandle;
import de.Ste3et_C0st.Furniture.Objects.outdoor.*;
import de.Ste3et_C0st.Furniture.Objects.trap.BearTrap;
import de.Ste3et_C0st.FurnitureLib.Crafting.Project;
import de.Ste3et_C0st.FurnitureLib.main.FurnitureLib;
import de.Ste3et_C0st.FurnitureLib.main.FurniturePlugin;
import de.Ste3et_C0st.FurnitureLib.main.ObjectID;
import de.Ste3et_C0st.FurnitureLib.main.Type.CenterType;
import de.Ste3et_C0st.FurnitureLib.main.Type.PlaceableSide;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import java.lang.reflect.Field;
import java.util.Objects;

public class FurnitureHook extends FurniturePlugin {

    private static Boolean newVersion = null;
    private final boolean editModels = FurnitureLib.getInstance().getConfig().getBoolean("config.editDiceFurnitureModels", false);

    public FurnitureHook(Plugin pluginInstance) {
        super(pluginInstance);
        Bukkit.getPluginManager().registerEvents(new redstoneEvent(), pluginInstance);
    }

    public static boolean isNewVersion() {
        if (Objects.isNull(newVersion)) {
            try {
                Class<?> descriptionClass = PluginDescriptionFile.class;
                Field field = descriptionClass.getDeclaredField("apiVersion");
                boolean bool = Objects.nonNull(field);
                newVersion = bool;
                return bool;
            } catch (Exception e) {
                newVersion = false;
                return false;
            }
        } else {
            return newVersion;
        }
    }

    @Override
    public void registerProjects() {
        try {
            String modelFolder = FurnitureHook.isNewVersion() ? "Models113/" : "Models109/";
            String ending = FurnitureHook.isNewVersion() ? ".dModel" : ".yml";

            new Project("Catapult", getPlugin(), getResource(modelFolder + "Catapult" + ending), Catapult.class).setSize(3, 2, 3, CenterType.RIGHT);
            new Project("HumanSkeleton", getPlugin(), getResource(modelFolder + "HumanSkeleton" + ending)).setSize(3, 1, 2, CenterType.RIGHT);
            new Project("CandyCane", getPlugin(), getResource(modelFolder + "CandyCane" + ending)).setSize(3, 4, 1, CenterType.RIGHT);
            new Project("SnowGolem", getPlugin(), getResource(modelFolder + "SnowGolem" + ending)).setSize(1, 2, 1, CenterType.RIGHT);
            new Project("TV", getPlugin(), getResource(modelFolder + "TV" + ending)).setSize(1, 2, 3, CenterType.CENTER);
            new Project("Chair", getPlugin(), getResource(modelFolder + "Chair" + ending)).setSize(1, 1, 1, CenterType.RIGHT);
            new Project("CampChair", getPlugin(), getResource(modelFolder + "CampChair" + ending)).setSize(1, 1, 1, CenterType.RIGHT);
            new Project("CactusPlant", getPlugin(), getResource(modelFolder + "CactusPlant" + ending)).setSize(1, 1, 1, CenterType.RIGHT);
            new Project("SleepingBag", getPlugin(), getResource(modelFolder + "SleepingBag" + ending)).setSize(1, 1, 2, CenterType.RIGHT);
            new Project("ChristmasTree", getPlugin(), getResource(modelFolder + "ChristmasTree" + ending)).setSize(1, 1, 2, CenterType.RIGHT);
            new Project("Table", getPlugin(), getResource(modelFolder + "Table" + ending)).setSize(1, 1, 1, CenterType.RIGHT);
            new Project("SchoolChair", getPlugin(), getResource(modelFolder + "SchoolChair" + ending)).setSize(1, 1, 1, CenterType.RIGHT);
            new Project("SchoolTable", getPlugin(), getResource(modelFolder + "SchoolTable" + ending)).setSize(1, 1, 1, CenterType.RIGHT);
            new Project("BlackBoard", getPlugin(), getResource(modelFolder + "BlackBoard" + ending)).setPlaceableSide(PlaceableSide.SIDE).setSize(1, 2, 3, CenterType.RIGHT);
            new Project("Barrels", getPlugin(), getResource(modelFolder + "Barrels" + ending), barrels.class).setSize(1, 1, 1, CenterType.RIGHT);
            new Project("WaxCandle", getPlugin(), getResource(modelFolder + "WaxCandle" + ending), WaxCandle.class).setSize(1, 1, 1, CenterType.RIGHT);
            new Project("Lantern", getPlugin(), getResource(modelFolder + "Lantern" + ending), WaxCandle.class).setSize(1, 1, 1, CenterType.RIGHT);
            new Project("MailBox", getPlugin(), getResource(modelFolder + "MailBox" + ending)).setSize(1, 2, 1, CenterType.RIGHT);
            new Project("Fence", getPlugin(), getResource(modelFolder + "Fence" + ending), fence.class).setSize(1, 1, 1, CenterType.RIGHT);
            new Project("Trunk", getPlugin(), getResource(modelFolder + "Trunk" + ending), Trunk.class).setSize(1, 1, 4, CenterType.RIGHT);
            new Project("Sunshade", getPlugin(), getResource(modelFolder + "Sunshade" + ending), sunshade.class).setSize(1, 3, 1, CenterType.RIGHT);
            new Project("Hammock", getPlugin(), getResource(modelFolder + "Hammock" + ending), hammock.class).setSize(1, 2, 7, CenterType.RIGHT);
            new Project("Crossbow", getPlugin(), getResource(modelFolder + "Crossbow" + ending), Crossbow.class).setSize(1, 1, 1, CenterType.RIGHT);
            new Project("Tent1", getPlugin(), getResource(modelFolder + "Tent1" + ending), tent_1.class).setSize(4, 3, 5, CenterType.RIGHT);
            new Project("GraveStone", getPlugin(), getResource(modelFolder + "GraveStone" + ending), graveStone.class).setSize(1, 2, 3, CenterType.CENTER);
            new Project("Camera", getPlugin(), getResource(modelFolder + "Camera" + ending), camera.class).setSize(1, 1, 1, CenterType.RIGHT);
            new Project("Sofa", getPlugin(), getResource(modelFolder + "Sofa" + ending)).setSize(1, 1, 3, CenterType.RIGHT);
            new Project("Log", getPlugin(), getResource(modelFolder + "Log" + ending), log.class).setSize(1, 1, 1, CenterType.CENTER);
            new Project("LargeTable", getPlugin(), getResource(modelFolder + "LargeTable" + ending), PlaceableSide.TOP, largeTable.class).setSize(2, 1, 2, CenterType.RIGHT);
            new Project("Campfire1", getPlugin(), getResource(modelFolder + "Campfire1" + ending), PlaceableSide.TOP, campfire_1.class).setSize(1, 1, 1, CenterType.RIGHT);
            new Project("Campfire2", getPlugin(), getResource(modelFolder + "Campfire2" + ending), PlaceableSide.TOP, campfire_2.class).setSize(2, 1, 2, CenterType.RIGHT);
            new Project("Tent2", getPlugin(), getResource(modelFolder + "Tent2" + ending), PlaceableSide.TOP, tent_2.class).setSize(6, 3, 5, CenterType.RIGHT);
            new Project("Tent3", getPlugin(), getResource(modelFolder + "Tent3" + ending), PlaceableSide.TOP, tent_3.class).setSize(3, 2, 3, CenterType.CENTER);
            new Project("Streetlamp", getPlugin(), getResource(modelFolder + "Streetlamp" + ending), PlaceableSide.TOP, streetlamp.class).setSize(2, 4, 1, CenterType.FRONT);
            new Project("Billboard", getPlugin(), getResource(modelFolder + "Billboard" + ending), PlaceableSide.TOP, billboard.class).setSize(1, 3, 3, CenterType.RIGHT);
            new Project("WeaponStand", getPlugin(), getResource(modelFolder + "WeaponStand" + ending), PlaceableSide.TOP, weaponStand.class).setSize(1, 1, 1, CenterType.RIGHT);
            new Project("Guillotine", getPlugin(), getResource(modelFolder + "Guillotine" + ending), PlaceableSide.TOP, Guillotine.class).setSize(1, 5, 2, CenterType.RIGHT);
            new Project("FlowerPot", getPlugin(), getResource(modelFolder + "FlowerPot" + ending), PlaceableSide.BOTTOM, TFlowerPot.class).setSize(1, 1, 1, CenterType.RIGHT);
            new Project("BearTrap", getPlugin(), getResource(modelFolder + "BearTrap" + ending), PlaceableSide.TOP, BearTrap.class).setSize(1, 1, 1, CenterType.RIGHT);
            new Project("TrashCan", getPlugin(), getResource(modelFolder + "TrashCan" + ending), PlaceableSide.TOP, TrashCan.class).setSize(1, 1, 1, CenterType.RIGHT);
            new Project("Flag", getPlugin(), getResource(modelFolder + "Flag" + ending), PlaceableSide.TOP, flag.class).setSize(1, 3, 1, CenterType.RIGHT);
            new Project("AdventCalender", getPlugin(), getResource(modelFolder + "AdventCalender" + ending), PlaceableSide.TOP, AdventCalender.class).setSize(1, 1, 1, CenterType.RIGHT);
            new Project("FireworkLauncher", getPlugin(), getResource(modelFolder + "FireworkLauncher" + ending), PlaceableSide.TOP, FireworkLauncher.class).setSize(1, 1, 1, CenterType.CENTER);

            FurnitureLib.getInstance().getFurnitureManager().getProjects().stream().filter(pro -> pro.getPlugin().equals(getPlugin())).forEach(pro -> pro.setEditorProject(editModels));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void applyPluginFunctions() {
        FurnitureLib.getInstance().getFurnitureManager().getProjects().stream()
                .filter(pro -> pro.getPlugin().getName().equals(getPlugin().getDescription().getName()))
                .forEach(Project::applyFunction);
    }

    @Override
    public void onFurnitureLateSpawn(ObjectID obj) {

    }

}
