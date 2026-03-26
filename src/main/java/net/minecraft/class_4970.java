package net.minecraft;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

// BlockBehavior (AbstractBlock)
public interface class_4970 {
    default BlockBehaviour asBlockBehaviour() {
        return (BlockBehaviour) this;
    }

    default Item method_8389() {
        return asBlockBehaviour().asItem();
    }

    default Block method_26160() {
        return (Block) asBlockBehaviour();
    }

    default float method_36555() {
        return asBlockBehaviour().defaultDestroyTime();
    }

    public static class class_2251 extends BlockBehaviour.Properties {
        public class_2251() {
            super();
        }
    }
}
