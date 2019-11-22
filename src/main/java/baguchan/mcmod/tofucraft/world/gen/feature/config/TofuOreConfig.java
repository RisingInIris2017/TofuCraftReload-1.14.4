package baguchan.mcmod.tofucraft.world.gen.feature.config;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class TofuOreConfig implements IFeatureConfig {
    public final BlockState state;
    public final int size;

    public TofuOreConfig(BlockState state, int size) {
        this.state = state;
        this.size = size;
    }

    @Override
    public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
        return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(
                ops.createString("size"), ops.createInt(this.size),
                ops.createString("state"), BlockState.serialize(ops, this.state).getValue()
        )));
    }

    public static TofuOreConfig deserialize(Dynamic<?> dynamic) {
        int size = dynamic.get("size").asInt(0);

        BlockState state = dynamic.get("state").map(BlockState::deserialize).orElse(Blocks.AIR.getDefaultState());
        return new TofuOreConfig(state, size);
    }
}