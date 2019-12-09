package baguchan.mcmod.tofucraft.tileentity;

import baguchan.mcmod.tofucraft.init.TofuBlocks;
import baguchan.mcmod.tofucraft.init.TofuTileEntitys;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class TofuChestTileEntity extends ChestTileEntity {
    private Block block = Blocks.AIR;

    protected TofuChestTileEntity(TileEntityType<?> entityType) {
        super(entityType);
    }

    public TofuChestTileEntity() {
        this(TofuTileEntitys.TOFUCHEST);
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(
                this.pos.add(-1, 0, -1),
                this.pos.add(2, 2, 2)
        );
    }

    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.tofucraft.tofuchest");
    }

    public void setChestModel(Block block) {
        this.block = block;
    }

    public Block getChestModel() {
        return this.block != Blocks.AIR ? this.block : TofuBlocks.TOFUCHEST;
    }
}