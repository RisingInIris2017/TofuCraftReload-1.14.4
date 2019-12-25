package baguchan.mcmod.tofucraft.init;

import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class TofuSurfaceBuilder {
    public static final SurfaceBuilderConfig TOFUDEFAULT_CONFIG = new SurfaceBuilderConfig(TofuBlocks.TOFUTERRAIN.getDefaultState(), TofuBlocks.TOFUTERRAIN.getDefaultState(), TofuBlocks.TOFUTERRAIN.getDefaultState());
    public static final SurfaceBuilderConfig TOFUZUNDA_CONFIG = new SurfaceBuilderConfig(TofuBlocks.ZUNDATOFUTERRAIN.getDefaultState(), TofuBlocks.TOFUTERRAIN.getDefaultState(), TofuBlocks.TOFUTERRAIN.getDefaultState());
}
