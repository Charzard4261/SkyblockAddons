package codes.biscuit.skyblockaddons.utils;

import codes.biscuit.skyblockaddons.utils.skyblockdata.PetInfo;
import codes.biscuit.skyblockaddons.utils.skyblockdata.Rune;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import codes.biscuit.skyblockaddons.core.ItemRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility methods for Skyblock Items
 */
public class ItemUtils {

    // Group 0 -> Recombobulator 3000 & Group 1 -> Color Codes
    private static final Pattern RARITY_PATTERN = Pattern.compile("(§[0-9a-f]§l§ka§r )?([§0-9a-fk-or]+)(?<rarity>[A-Z]+)");

    /**
     * Returns the rarity of a given Skyblock item
     *
     * @param item the Skyblock item to check
     * @return the rarity of the item if a valid rarity is found, {@code INVALID} if no rarity is found, {@code null} if item is {@code null}
     */
    public static ItemRarity getRarity(ItemStack item) {
        if (item == null || !item.hasTagCompound())  {
            return null;
        }

        NBTTagCompound display = item.getSubCompound("display", false);

        if (display == null || !display.hasKey("Lore")) {
            return null;
        }

        NBTTagList lore = display.getTagList("Lore", Constants.NBT.TAG_STRING);

        // Determine the item's rarity
        for (int i = 0; i < lore.tagCount(); i++) {
            String currentLine = lore.getStringTagAt(i);

            Matcher rarityMatcher = RARITY_PATTERN.matcher(currentLine);
            if (rarityMatcher.find()) {
                String rarity = rarityMatcher.group("rarity");

                for (ItemRarity itemRarity : EnumSet.allOf(ItemRarity.class)) {
                    if (rarity.startsWith(itemRarity.getTag())) {
                        return itemRarity;
                    }
                }
            }
        }

        // If the item doesn't have a valid rarity, return null
        return null;
    }

    /**
     * Returns the Skyblock Item ID of a given Skyblock item
     *
     * @param item the Skyblock item to check
     * @return the Skyblock Item ID of this item or {@code null} if this isn't a valid Skyblock item
     */
    public static String getSkyBlockItemID(final ItemStack item) {
        if (item == null) {
            throw new NullPointerException("Item cannot be null.");
        } else if (!item.hasTagCompound()) {
            return null;
        }

        NBTTagCompound skyBlockData = item.getSubCompound("ExtraAttributes", false);

        if (skyBlockData != null) {
            String itemId = skyBlockData.getString("id");

            if (!itemId.equals("")) {
                return itemId;
            }
        }

        return null;
    }

    public static NBTTagCompound getSkyblockData(final ItemStack item) {
        if (item == null) {
            throw new NullPointerException("Item cannot be null.");
        } else if (!item.hasTagCompound()) {
            return null;
        }

        NBTTagCompound skyBlockData = item.getSubCompound("ExtraAttributes", false);

        return skyBlockData;
    }

    /**
     * Returns the Skyblock Item ID of a given Skyblock Extra Attributes NBT Compound
     *
     * @param extraAttributes the NBT to check
     * @return the Skyblock Item ID of this item or {@code null} if this isn't a valid Skyblock NBT
     */
    public static String getSkyBlockItemID(NBTTagCompound extraAttributes) {

        if (extraAttributes != null) {
            String itemId = extraAttributes.getString("id");

            if (!itemId.equals("")) {
                return itemId;
            }
        }

        return null;
    }

    /**
     * Returns the Skyblock Reforge of a given Skyblock Extra Attributes NBT Compound
     *
     * @param extraAttributes the NBT to check
     * @return the Reforge (in lowercase) of this item or {@code null} if this isn't a valid Skyblock NBT or reforge
     */
    public static String getReforge(NBTTagCompound extraAttributes) {

        if (extraAttributes != null) {
            if (!extraAttributes.hasKey("modifier")) return null;
            return extraAttributes.getString("modifier");
        }

        return null;
    }

    /**
     * Returns a {@link Rune} from the ExtraAttributes Skyblock data
     * This can ge retrieved from a rune itself or an infused item
     *
     * @param extraAttributes the Skyblock Data to check
     * @return A {@link Rune} or {@code null} if it doesn't have it
     */
    public static Rune getRuneData(NBTTagCompound extraAttributes) {
        if (extraAttributes != null) {
            String itemId = extraAttributes.getString("id");

            if (!extraAttributes.hasKey("runes"))
                return null;

            return new Rune(extraAttributes.getCompoundTag("runes"));
            //return new Pair<String, Integer>(runeName, runeData.getInteger(runeName));
        }

        return null;
    }

    /**
     * Returns a {@link PetInfo} from the ExtraAttributes Skyblock data
     *
     * @param extraAttributes the Skyblock Data to check
     * @return A {@link PetInfo} or {@code null} if it isn't a pet
     */
    public static PetInfo getPetInfo(NBTTagCompound extraAttributes) {
        if (extraAttributes != null) {
            String itemId = extraAttributes.getString("id");

            if (!itemId.equals("PET")) {
                return null;
            }

            return new PetInfo(extraAttributes.getString("petInfo"));
        }

        return null;
    }

    /**
     * Returns a {@link ItemStack[]} of Items from the ExtraAttributes Skyblock data
     *
     * @param extraAttributes the Skyblock Data to check
     * @return A {@link ItemStack[]} or {@code null} if it isn't a Personal Compactor
     */
    public static ItemStack[] getPersonalCompactorContents(NBTTagCompound extraAttributes) {
        if (extraAttributes != null) {
            String itemId = extraAttributes.getString("id");

            if (!itemId.startsWith("PERSONAL_COMPACTOR")) {
                return null;
            }

            ItemStack[] items;
            if (itemId.endsWith("4000"))
                items = new ItemStack[1];
            else if (itemId.endsWith("5000"))
                items = new ItemStack[3];
            else if (itemId.endsWith("6000"))
                items = new ItemStack[7];
            else
                items = new ItemStack[0];

            for (int i = 0; i < 7; i++) {
                if (!extraAttributes.hasKey("personal_compact_" + i))
                    continue;

                String itemname = extraAttributes.getString("personal_compact_" + i);
                itemname = itemname.replaceFirst("ENCHANTED_", "");
                itemname = itemname.replaceFirst("RAW_", "");
                itemname = itemname.toLowerCase();
                if (itemname.contains("log")) {
                    ItemStack is;
                    switch (itemname) {
                        case "oak_log":
                            is = new ItemStack(Blocks.log);
                            break;
                        case "birch_log":
                            is = new ItemStack(Blocks.log, 1, 2);
                            break;
                        case "spruce_log":
                            is = new ItemStack(Blocks.log, 1, 1);
                            break;
                        case "jungle_log":
                            is = new ItemStack(Blocks.log, 1, 3);
                            break;
                        case "acacia_log":
                            is = new ItemStack(Blocks.log2);
                            break;
                        case "dark_oak_log":
                            is = new ItemStack(Blocks.log2, 1, 1);
                            break;
                        default:
                            continue;
                    }

                    is.addEnchantment(Enchantment.protection, 1);
                    items[i] = is;
                    continue;
                }
                Item it = null;
                if (Item.itemRegistry.getObject(new ResourceLocation(itemname)) != null) {
                    it = (Item.itemRegistry.getObject(new ResourceLocation(itemname)));
                } else if (Block.blockRegistry.getObject(new ResourceLocation(itemname)) != null) {
                    it = (Item.getItemFromBlock(Block.blockRegistry.getObject(new ResourceLocation(itemname))));
                }
                if (it != null) {
                    ItemStack is = new ItemStack(it);
                    is.addEnchantment(Enchantment.protection, 1);
                    items[i] = is;
                }
            }

            return items;
        }

        return null;
    }

    /**
     * Returns a boolean relating to if the rarity of a given Skyblock Extra Attributes NBT Compound has been upgraded
     *
     * @param extraAttributes the NBT to check
     * @return {@code true} if its rarity has been upgraded or {@code false} if it hasn't or this isn't a valid Skyblock NBT
     */
    public static boolean rarityUpgraded(NBTTagCompound extraAttributes) {
        if (extraAttributes != null) {
            String itemId = extraAttributes.getString("id");

            if (!extraAttributes.hasKey("rarity_upgrades")) {
                return false;
            }

            return extraAttributes.getInteger("rarity_upgrades") == 1;
        }

        return false;
    }

    /**
     * Returns the winning bid of a Midas Sword from a given Skyblock Extra Attributes NBT Compound
     * @param extraAttributes the NBT to check
     * @return the winning bid or {@code -1} if it isn't a Midas Sword
     */
    public static int getMidasBid(NBTTagCompound extraAttributes) {
        if (extraAttributes != null) {
            String itemId = extraAttributes.getString("id");

            if (!itemId.equals("MIDAS_SWORD")) {
                return -1;
            }

            return extraAttributes.getInteger("winning_bid");
        }

        return -1;
    }

    /**
     * Returns the number of anvil uses from a given Skyblock Extra Attributes NBT Compound
     * WARNING: Cannot differentiate between an item that can or cannot have anvil uses
     * @param extraAttributes the NBT to check
     * @return the number of anvil uses or {@code -1} if it isn't a valid Skyblock NBT
     */
    public static int getAnvilUses(NBTTagCompound extraAttributes) {
        if (extraAttributes != null) {

            if (!extraAttributes.hasKey("anvil_uses")) {
                return 0;
            }

            return extraAttributes.getInteger("anvil_uses");
        }

        return -1;
    }

    /**
     * Returns the number of Hot Potato Books from a given Skyblock Extra Attributes NBT Compound
     * MAX 15 because of Fuming Potato Books
     * @param extraAttributes the NBT to check
     * @return the number of Hot Potato Books or {@code -1} if it isn't a valid Skyblock NBT
     */
    public static int getHotPotatoBookCount(NBTTagCompound extraAttributes) {
        if (extraAttributes != null) {

            if (!extraAttributes.hasKey("hot_potato_count")) {
                return 0;
            }

            return extraAttributes.getInteger("hot_potato_count");
        }

        return -1;
    }

}