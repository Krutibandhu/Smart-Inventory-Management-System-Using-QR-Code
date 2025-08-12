package com.code4cause.qventor.service;

import com.code4cause.qventor.model.Admin;
import com.code4cause.qventor.model.Item;
import com.code4cause.qventor.repository.AdminRepository;
import com.code4cause.qventor.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final AdminRepository adminRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository, AdminRepository adminRepository) {
        this.itemRepository = itemRepository;
        this.adminRepository = adminRepository;
    }

    // ✅ Add a new item linked to an admin using supabaseUserId
    public Item addItemToAdmin(String supabaseUserId, Item newItem) {
        Admin admin = adminRepository.findBySupabaseUserId(supabaseUserId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        newItem.setAdmin(admin);
        return itemRepository.save(newItem);
    }

    // ✅ Get all items of a specific admin
    public List<Item> getItemsByAdmin(String supabaseUserId) {
        Admin admin = adminRepository.findBySupabaseUserId(supabaseUserId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        return admin.getItems();
    }

    // ✅ Get a single item by ID
    public Item getItemById(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));
    }

    // ✅ Update an item
    public Item updateItem(Long itemId, Item updatedItem) {
        Item existingItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        existingItem.setName(updatedItem.getName());
        existingItem.setDescription(updatedItem.getDescription());
        existingItem.setPrice(updatedItem.getPrice());
        existingItem.setQuantity(updatedItem.getQuantity());
        existingItem.setExportAmount(updatedItem.getExportAmount());
        existingItem.setImportAmount(updatedItem.getImportAmount());
        existingItem.setRecentExportDate(updatedItem.getRecentExportDate());
        existingItem.setRecentImportDate(updatedItem.getRecentImportDate());

        return itemRepository.save(existingItem);
    }

    // ✅ Delete an item
    public void deleteItem(Long itemId) {
        if (!itemRepository.existsById(itemId)) {
            throw new RuntimeException("Item not found");
        }
        itemRepository.deleteById(itemId);
    }
}
