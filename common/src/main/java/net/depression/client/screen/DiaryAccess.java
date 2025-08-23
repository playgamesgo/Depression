package net.depression.client.screen;

import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.stream.IntStream;

public class DiaryAccess {

    public static BookViewScreen.BookAccess fromDiary(ItemStack itemStack) {
        List<Component> pages = extractPagesFromDiary(itemStack);
        return new BookViewScreen.BookAccess(pages);
    }

    private static List<Component> extractPagesFromDiary(ItemStack itemStack) {
        // Use the existing BookAccess.fromItem method to extract pages
        BookViewScreen.BookAccess bookAccess = BookViewScreen.BookAccess.fromItem(itemStack);
        if (bookAccess != null) {
            // Convert the pages to Component list
            return IntStream.range(0, bookAccess.getPageCount())
                    .mapToObj(i -> {
                        FormattedText page = bookAccess.getPage(i);
                        if (page instanceof Component component) {
                            return component;
                        }
                        return Component.literal(page.getString());
                    })
                    .toList();
        }
        return List.of();
    }
}