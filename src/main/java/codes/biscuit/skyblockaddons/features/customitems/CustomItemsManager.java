package codes.biscuit.skyblockaddons.features.customitems;

import codes.biscuit.skyblockaddons.utils.ItemUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

//TODO resourcepack reload in RenderItem
public class CustomItemsManager {

    public ArrayList<CustomItem> items;

    public CustomItemsManager() {
        items = new ArrayList<>();
        items.add(new CustomItem("RAIDER_AXE"));
    }

    public CustomItem getCustomItem(ItemStack stack) {
        for (CustomItem it : items)
            if (it.isItem(stack))
                return it;
        return null;
    }

    public class CustomItem {
        Item item = null;
        String displayName = "", skyblockID = ""; // Requirements
        int anvilUses = 0, midasBid = -1, hpb = 0;

        public CustomItem(String skyblockID) // TODO take file
        {
            this.skyblockID = skyblockID;
            /*
            if (file.hasKey(itemType))
             item = Items.fromString(file.getKey(itemType));
            etc
             */
        }

        public boolean isItem(ItemStack stack) {
            if (item != null && stack.getItem() != item)
                return false;

            //TODO get extraAtts
            String skyID = ItemUtils.getSkyBlockItemID(stack); //TODO change to getId from extraAtts
            if (!skyblockID.isEmpty() && !skyblockID.equals(skyID))
                return false;

            //TODO other checks, maybe isonSkyblock check before all the extra atts stuff ALSO MAKE SURE LOGIC IS RIGHT LOL

            return true;
        }
    }
}
