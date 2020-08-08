package codes.biscuit.skyblockaddons.features.customitems;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CustomRenderItem extends RenderItem {

    public CustomRenderItem(TextureManager textureManager, ModelManager modelManager) {
        super(textureManager, modelManager);
    }

    /**
     * Renders a Held Item for entities, e.g. blocks on head, held items, TODO test for more
     * @param stack
     * @param entityToRenderFor
     * @param cameraTransformType
     */
    @Override
    public void renderItemModelForEntity(ItemStack stack, EntityLivingBase entityToRenderFor, ItemCameraTransforms.TransformType cameraTransformType) {
        //TODO if using custom items
        CustomItemsManager.CustomItem cItem = SkyblockAddons.getInstance().getCmi().getCustomItem(stack);
        if (cItem != null) {
            //renderItemModelForEntity(stack, entityToRenderFor, cameraTransformType, cItem);
            System.out.println("renderItemModelForEntity cItem exists! " + cItem.skyblockID);
        } else super.renderItemModelForEntity(stack, entityToRenderFor, cameraTransformType);
    }

    private void renderItemModelForEntity(ItemStack stack, EntityLivingBase entityToRenderFor, ItemCameraTransforms.TransformType cameraTransformType, CustomItemsManager.CustomItem cItem) {
        if (stack != null && entityToRenderFor != null) {
            IBakedModel ibakedmodel = this.itemModelMesher.getItemModel(stack);
            if (entityToRenderFor instanceof EntityPlayer) {
                EntityPlayer entityplayer = (EntityPlayer) entityToRenderFor;
                Item item = stack.getItem();
                ModelResourceLocation modelresourcelocation = null;
                if (item == Items.fishing_rod && entityplayer.fishEntity != null) {
                    modelresourcelocation = new ModelResourceLocation("fishing_rod_cast", "inventory");
                } else if (item == Items.bow && entityplayer.getItemInUse() != null) {
                    int i = stack.getMaxItemUseDuration() - entityplayer.getItemInUseCount();
                    if (i >= 18) {
                        modelresourcelocation = new ModelResourceLocation("bow_pulling_2", "inventory");
                    } else if (i > 13) {
                        modelresourcelocation = new ModelResourceLocation("bow_pulling_1", "inventory");
                    } else if (i > 0) {
                        modelresourcelocation = new ModelResourceLocation("bow_pulling_0", "inventory");
                    }
                } else {
                    modelresourcelocation = item.getModel(stack, entityplayer, entityplayer.getItemInUseCount());
                }

                if (modelresourcelocation != null) {
                    ibakedmodel = this.itemModelMesher.getModelManager().getModel(modelresourcelocation);
                }
            }

            this.renderItemModelTransform(stack, ibakedmodel, cameraTransformType);
        }
    }

    @Override
    public void renderItem(ItemStack stack, ItemCameraTransforms.TransformType cameraTransformType) {
        //TODO if using custom items
        CustomItemsManager.CustomItem cItem = SkyblockAddons.getInstance().getCmi().getCustomItem(stack);
        System.out.println(stack.getDisplayName());
        if (cItem != null) {
            System.out.println("renderItem cItem exists! " + cItem.skyblockID);
        } else
        super.renderItem(stack, cameraTransformType);
        /*if (stack != null) {
            IBakedModel ibakedmodel = this.itemModelMesher.getItemModel(stack);
            this.renderItemModelTransform(stack, ibakedmodel, cameraTransformType);
        }*/

    }

/*
    @Override
    public void renderItem(ItemStack stack, IBakedModel model) {
        if (stack != null) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            if (model.isBuiltInRenderer()) {
                GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                GlStateManager.translate(-0.5F, -0.5F, -0.5F);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.enableRescaleNormal();
                TileEntityItemStackRenderer.instance.renderByItem(stack);
            } else {
                GlStateManager.translate(-0.5F, -0.5F, -0.5F);
                this.renderModel(model, stack);
                if (stack.hasEffect()) {
                    this.renderEffect(model);
                }
            }

            GlStateManager.popMatrix();
        }

    }

    private void renderModel(IBakedModel model, int color, ItemStack stack) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.ITEM);
        EnumFacing[] var6 = EnumFacing.values();
        int var7 = var6.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            EnumFacing enumfacing = var6[var8];
            this.renderQuads(worldrenderer, model.getFaceQuads(enumfacing), color, stack);
        }

        this.renderQuads(worldrenderer, model.getGeneralQuads(), color, stack);
        tessellator.draw();
    }*/

}
