package codes.biscuit.skyblockaddons.asm.hooks;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.tweaker.SkyblockAddonsTransformer;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.core.Location;
import codes.biscuit.skyblockaddons.utils.nifty.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityEnderChestRenderer;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TileEntityEnderChestRendererHook {

    private static final ResourceLocation BLANK_ENDERCHEST = new ResourceLocation("skyblockaddons", "enderchest.png");

    private static Method bindTexture = null;

    public static void bindTexture(TileEntityEnderChestRenderer tileEntityEnderChestRenderer, ResourceLocation enderChestTexture) {
        SkyblockAddons main = SkyblockAddons.getInstance();

        if (main.getUtils().isOnSkyblock() && Minecraft.getMinecraft().currentScreen == null && main.getConfigValues().isEnabled(Feature.MAKE_ENDERCHESTS_GREEN_IN_END) &&
                (main.getUtils().getLocation() == Location.THE_END || main.getUtils().getLocation() == Location.DRAGONS_NEST)) {
            bindRightTexture(tileEntityEnderChestRenderer, BLANK_ENDERCHEST);
        } else {
            bindRightTexture(tileEntityEnderChestRenderer, enderChestTexture);
        }
    }

    private static void bindRightTexture(TileEntityEnderChestRenderer tileEntityEnderChestRenderer, ResourceLocation resourceLocation) {
        if (SkyblockAddonsTransformer.isLabymodClient()) { // There are no access transformers in labymod.
            try {
                if (bindTexture == null) {
                    bindTexture = tileEntityEnderChestRenderer.getClass().getSuperclass().getDeclaredMethod("a", ResourceLocation.class);
                    bindTexture.setAccessible(true);
                }
                if (bindTexture != null) {
                    bindTexture.invoke(tileEntityEnderChestRenderer, resourceLocation);
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            tileEntityEnderChestRenderer.bindTexture(resourceLocation);
        }
    }

    public static void setEnderchestColor() {
        SkyblockAddons main = SkyblockAddons.getInstance();
        if (main.getUtils().isOnSkyblock() && Minecraft.getMinecraft().currentScreen == null && main.getConfigValues().isEnabled(Feature.MAKE_ENDERCHESTS_GREEN_IN_END) &&
                main.getUtils().getLocation() == Location.DRAGONS_NEST) {
            Color color = main.getConfigValues().getColor(Feature.MAKE_ENDERCHESTS_GREEN_IN_END);
            if (color.getRGB() == ChatFormatting.GREEN.getRGB()) {
                GlStateManager.color(0, 1, 0); // classic lime green
            } else {
                GlStateManager.color((float)color.getRed()/255, (float)color.getGreen()/255, (float)color.getBlue()/255);
            }
        }
    }
}
