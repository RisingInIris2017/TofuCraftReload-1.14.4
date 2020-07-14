package baguchan.mcmod.tofucraft.world.biome.layer;


import baguchan.mcmod.tofucraft.init.TofuBiomes;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.LazyAreaLayerContext;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;
import net.minecraft.world.gen.layer.*;
import net.minecraft.world.gen.layer.traits.IAreaTransformer1;

import java.util.function.LongFunction;

public class TofuLayerUtil {
    private java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry>[] biomes = new java.util.ArrayList[net.minecraftforge.common.BiomeManager.BiomeType.values().length];

    public static <T extends IArea, C extends IExtendedNoiseRandom<T>> IAreaFactory<T> repeat(long seed, IAreaTransformer1 parent, IAreaFactory<T> p_202829_3_, int count, LongFunction<C> contextFactory) {
        IAreaFactory<T> iareafactory = p_202829_3_;

        for (int i = 0; i < count; ++i) {
            iareafactory = parent.apply(contextFactory.apply(seed + (long) i), iareafactory);
        }

        return iareafactory;
    }

    public static <T extends IArea, C extends IExtendedNoiseRandom<T>> IAreaFactory<T> buildTofuProcedure(LongFunction<C> contextFactory) {
        IAreaFactory<T> iareafactory = IslandLayer.INSTANCE.apply(contextFactory.apply(1L));
        iareafactory = ZoomLayer.FUZZY.apply(contextFactory.apply(2000L), iareafactory);
        iareafactory = AddIslandLayer.INSTANCE.apply(contextFactory.apply(1L), iareafactory);
        iareafactory = ZoomLayer.NORMAL.apply(contextFactory.apply(2001L), iareafactory);
        iareafactory = AddIslandLayer.INSTANCE.apply(contextFactory.apply(2L), iareafactory);
        iareafactory = AddIslandLayer.INSTANCE.apply(contextFactory.apply(50L), iareafactory);
        iareafactory = AddIslandLayer.INSTANCE.apply(contextFactory.apply(70L), iareafactory);
        iareafactory = AddIslandLayer.INSTANCE.apply(contextFactory.apply(3L), iareafactory);
        iareafactory = EdgeLayer.CoolWarm.INSTANCE.apply(contextFactory.apply(2L), iareafactory);
        iareafactory = EdgeLayer.HeatIce.INSTANCE.apply(contextFactory.apply(2L), iareafactory);
        iareafactory = EdgeLayer.Special.INSTANCE.apply(contextFactory.apply(3L), iareafactory);
        iareafactory = ZoomLayer.NORMAL.apply(contextFactory.apply(2002L), iareafactory);
        iareafactory = ZoomLayer.NORMAL.apply(contextFactory.apply(2003L), iareafactory);
        iareafactory = repeat(1000L, ZoomLayer.NORMAL, iareafactory, 0, contextFactory);
        int i = 5;
        int j = i;

        IAreaFactory<T> lvt_7_1_ = repeat(1000L, ZoomLayer.NORMAL, iareafactory, 0, contextFactory);
        lvt_7_1_ = StartRiverLayer.INSTANCE.apply((IExtendedNoiseRandom) contextFactory.apply(100L), lvt_7_1_);
        IAreaFactory<T> lvt_8_1_ = getBiomeLayer(iareafactory, contextFactory);
        IAreaFactory<T> lvt_9_1_ = repeat(1000L, ZoomLayer.NORMAL, lvt_7_1_, 2, contextFactory);
        lvt_8_1_ = HillsLayer.INSTANCE.apply((IExtendedNoiseRandom) contextFactory.apply(1000L), lvt_8_1_, lvt_9_1_);
        lvt_7_1_ = repeat(1000L, ZoomLayer.NORMAL, lvt_7_1_, 2, contextFactory);
        lvt_7_1_ = repeat(1000L, ZoomLayer.NORMAL, lvt_7_1_, j, contextFactory);
        lvt_7_1_ = TofuRiverLayer.INSTANCE.apply((IExtendedNoiseRandom) contextFactory.apply(1L), lvt_7_1_);
        lvt_7_1_ = SmoothLayer.INSTANCE.apply((IExtendedNoiseRandom) contextFactory.apply(1000L), lvt_7_1_);

        for (int k = 0; k < i; ++k) {
            lvt_8_1_ = ZoomLayer.NORMAL.apply((IExtendedNoiseRandom) contextFactory.apply((long) (1000 + k)), lvt_8_1_);
            if (k == 0) {
                lvt_8_1_ = AddIslandLayer.INSTANCE.apply((IExtendedNoiseRandom) contextFactory.apply(3L), lvt_8_1_);
            }

            if (k == 1 || i == 1) {
                lvt_8_1_ = ShoreLayer.INSTANCE.apply((IExtendedNoiseRandom) contextFactory.apply(1000L), lvt_8_1_);
            }
        }

        lvt_8_1_ = SmoothLayer.INSTANCE.apply((IExtendedNoiseRandom) contextFactory.apply(1000L), lvt_8_1_);
        lvt_8_1_ = TofuMixRiverLayer.INSTANCE.apply((IExtendedNoiseRandom) contextFactory.apply(100L), lvt_8_1_, lvt_7_1_);

        return lvt_8_1_;
    }

    private static <T extends IArea, C extends IExtendedNoiseRandom<T>> IAreaFactory<T> getBiomeLayer(IAreaFactory<T> parentLayer, LongFunction<C> contextFactory) {

        parentLayer = (new TofuBiomeLayer()).apply(contextFactory.apply(200L), parentLayer);
        parentLayer = LayerUtil.repeat(1000L, ZoomLayer.NORMAL, parentLayer, 2, contextFactory);
        parentLayer = EdgeBiomeLayer.INSTANCE.apply(contextFactory.apply(1000L), parentLayer);
        return parentLayer;
    }

    public static Layer buildTofuProcedure(long seed) {
        int i = 25;
        IAreaFactory<LazyArea> iareafactory = buildTofuProcedure((p_227473_2_) -> {
            return new LazyAreaLayerContext(25, seed, p_227473_2_);
        });
        return new Layer(iareafactory);
    }

    protected static boolean isRiver(int biomeIn) {
        return biomeIn == Registry.BIOME.getId(TofuBiomes.TOFU_RIVER);
    }
}