package baguchan.mcmod.tofucraft.init;

import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;

public class TofuMaterial {
    public static final Material TOFU = (new Material.Builder(MaterialColor.WOOL)).build();
    public static final Material TOFUORE = (new TofuMaterial.Builder(MaterialColor.WOOL)).build();

    public static class Builder {
        private PushReaction pushReaction = PushReaction.NORMAL;
        private boolean blocksMovement = true;
        private boolean canBurn;
        private boolean isLiquid;
        private boolean isReplaceable;
        private boolean isSolid = true;
        private final MaterialColor color;
        private boolean isOpaque = true;

        public Builder(MaterialColor color) {
            this.color = color;
        }

        public TofuMaterial.Builder liquid() {
            this.isLiquid = true;
            return this;
        }

        public TofuMaterial.Builder notSolid() {
            this.isSolid = false;
            return this;
        }

        public TofuMaterial.Builder doesNotBlockMovement() {
            this.blocksMovement = false;
            return this;
        }

        private TofuMaterial.Builder notOpaque() {
            this.isOpaque = false;
            return this;
        }

        protected TofuMaterial.Builder flammable() {
            this.canBurn = true;
            return this;
        }

        public TofuMaterial.Builder replaceable() {
            this.isReplaceable = true;
            return this;
        }

        protected TofuMaterial.Builder pushDestroys() {
            this.pushReaction = PushReaction.DESTROY;
            return this;
        }

        protected TofuMaterial.Builder pushBlocks() {
            this.pushReaction = PushReaction.BLOCK;
            return this;
        }

        public Material build() {
            return new Material(this.color, this.isLiquid, this.isSolid, this.blocksMovement, this.isOpaque, this.canBurn, this.isReplaceable, this.pushReaction);
        }
    }
}
