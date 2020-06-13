package baguchan.mcmod.tofucraft;

import baguchan.mcmod.tofucraft.client.ClientRegistrar;
import baguchan.mcmod.tofucraft.entity.MorijioEntity;
import baguchan.mcmod.tofucraft.init.*;
import baguchan.mcmod.tofucraft.world.gen.DefaultTofuBiomeFeature;
import net.minecraft.block.TallGrassBlock;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("tofucraft")
public class TofuCraftCore {
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "tofucraft";

    public TofuCraftCore() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

        TofuPaintings.PAINTINGS.register(modEventBus);

        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientRegistrar::setup));

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(TofuCraftCore.MODID, name);
    }

    private void setup(final FMLCommonSetupEvent event) {

        TofuVillagers.init();
        TofuEntitys.spawnEntity();
        DefaultTofuBiomeFeature.addNetherSoybean();
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
    }

    private void processIMC(final InterModProcessEvent event) {

    }

    @SubscribeEvent
    public void onGrassBroken(BlockEvent.BreakEvent event) {
        if (!event.getWorld().isRemote() && !event.getPlayer().isCreative()) {

            if (event.getWorld().getBlockState(event.getPos()).getBlock() instanceof TallGrassBlock) {
                if (Math.random() <= 0.025) {
                    event.getWorld().addEntity(new ItemEntity((World) event.getWorld(), event.getPos().getX(),
                            event.getPos().getY(), event.getPos().getZ(), new ItemStack(TofuItems.SEEDS_SOYBEAN)));
                } else if (Math.random() <= 0.025) {
                    event.getWorld().addEntity(new ItemEntity((World) event.getWorld(), event.getPos().getX(),
                            event.getPos().getY(), event.getPos().getZ(), new ItemStack(TofuItems.SEEDS_RICE)));
                }

            }
        }
    }

    @SubscribeEvent
    public void onEntityHurt(LivingHurtEvent event) {
        LivingEntity livingEntity = event.getEntityLiving();

        if (event.getSource().isProjectile()) {
            if (livingEntity.getActivePotionEffect(TofuEffectRegistry.TOFU_RESISTANCE) != null) {
                event.setAmount(event.getAmount() * 0.75F);
            }
        }

        if (livingEntity.getActivePotionEffect(TofuEffectRegistry.UNSTABLE_RESISTANCE) != null) {
            event.setAmount(event.getAmount() * 1.25F);
        }
    }


    @SubscribeEvent
    public void onCheckSpawnEvent(LivingSpawnEvent.CheckSpawn event) {
        if (event.getEntityLiving().getType().getClassification() == EntityClassification.MONSTER || event.getEntityLiving() instanceof IMob) {
            if (event.getSpawnReason() != SpawnReason.STRUCTURE && event.getSpawnReason() != SpawnReason.EVENT && event.getSpawnReason() != SpawnReason.MOB_SUMMONED && event.getSpawnReason() != SpawnReason.SPAWN_EGG && event.getSpawnReason() != SpawnReason.SPAWNER) {
                List<MorijioEntity> list = event.getWorld().getEntitiesWithinAABB(MorijioEntity.class, event.getEntityLiving().getBoundingBox().expand(48.0D, 20.0D, 48.0D));

                if (list.size() > 0) {
                    event.setResult(Event.Result.DENY);
                }
            }
        }
    }
}
