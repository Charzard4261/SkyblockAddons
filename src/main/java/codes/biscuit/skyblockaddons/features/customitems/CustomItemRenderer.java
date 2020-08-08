package codes.biscuit.skyblockaddons.features.customitems;

import codes.biscuit.skyblockaddons.utils.ItemUtils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomItemRenderer extends ItemRenderer {

    public CustomItemRenderer(Minecraft mcIn) {
        super(mcIn);
    }

    /**
     * Renders a Held Item for entities, e.g. blocks on head, held items, see RenderItem for more
     * @param entityIn
     * @param heldStack
     * @param transform
     */
    @Override
    public void renderItem(EntityLivingBase entityIn, ItemStack heldStack, ItemCameraTransforms.TransformType transform) {
        super.renderItem(entityIn, heldStack, transform);
    }
}