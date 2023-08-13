package com.jhonn.game.managers;

import com.badlogic.gdx.utils.Array;
import com.jhonn.game.entities.items.BaseItem;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InventoryManager {
    private final Array<BaseItem> items = new Array<>();
    private final ConcurrentHashMap<Integer, BaseItem> itemMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Integer, AtomicInteger> itemQuantities = new ConcurrentHashMap<>();

    public void addItem(BaseItem item, int quantity) {
        items.add(item);
        itemMap.put(item.getId(), item);
        itemQuantities.put(item.getId(), new AtomicInteger(quantity));
    }

    public void removeItem(int id, int quantityToRemove) {
        AtomicInteger quantity = itemQuantities.get(id);
        if (quantity != null) {
            int newQuantity = quantity.get() - quantityToRemove;
            if (newQuantity <= 0) {
                BaseItem item = itemMap.remove(id);
                items.removeValue(item, true);
                itemQuantities.remove(id);
            } else {
                quantity.set(newQuantity);
            }
        }
    }

    public int getItemQuantity(int id) {
        AtomicInteger quantity = itemQuantities.get(id);
        return (quantity != null) ? quantity.get() : 0;
    }

    public BaseItem getItemById(int id) {
        return itemMap.get(id);
    }
}

