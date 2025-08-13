package com.code4cause.qventor.service;

import com.code4cause.qventor.model.*;
import com.code4cause.qventor.repository.AdminRepository;
import com.code4cause.qventor.repository.ExportRecordRepository;
import com.code4cause.qventor.repository.ImportRecordRepository;
import com.code4cause.qventor.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final AdminRepository adminRepository;
    private final ImportRecordRepository importRecordRepository;
    private final ExportRecordRepository exportRecordRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository,
                       AdminRepository adminRepository,
                       ImportRecordRepository importRecordRepository,
                       ExportRecordRepository exportRecordRepository) {
        this.itemRepository = itemRepository;
        this.adminRepository = adminRepository;
        this.importRecordRepository = importRecordRepository;
        this.exportRecordRepository = exportRecordRepository;
    }

    // ✅ Add a new item linked to an admin using supabaseUserId
    public Item addItemToAdmin(String supabaseUserId, Item item) {
        Admin admin = adminRepository.findBySupabaseUserId(supabaseUserId)
                .orElseThrow(() -> new RuntimeException("Admin not found with SupabaseUserId: " + supabaseUserId));

        item.setAdmin(admin);

        // Link imports to this item
        if (item.getImports() != null) {
            item.getImports().forEach(importRecord -> importRecord.setItem(item));
        }

        // Link exports to this item
        if (item.getExports() != null) {
            item.getExports().forEach(exportRecord -> exportRecord.setItem(item));
        }

        return itemRepository.save(item);
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
        Item existingItem = getItemById(itemId);
        existingItem.setName(updatedItem.getName());
        existingItem.setDescription(updatedItem.getDescription());
        existingItem.setPrice(updatedItem.getPrice());
        existingItem.setQuantity(updatedItem.getQuantity());
        return itemRepository.save(existingItem);
    }

    // ✅ Delete an item
    public void deleteItem(Long itemId) {
        if (!itemRepository.existsById(itemId)) {
            throw new RuntimeException("Item not found");
        }
        itemRepository.deleteById(itemId);
    }

    // ✅ Get all import records for an item
    public List<ImportRecord> getImportRecordsByItem(Long itemId) {
        getItemById(itemId); // ensure item exists
        return importRecordRepository.findByItemId(itemId);
    }

    // ✅ Get single import record
    public ImportRecord getSingleImportRecord(Long itemId, Long importId) {
        ImportRecord rec = importRecordRepository.findById(importId)
                .orElseThrow(() -> new RuntimeException("Import record not found"));
        if (!rec.getItem().getId().equals(itemId)) {
            throw new RuntimeException("Import record does not belong to this item");
        }
        return rec;
    }

    // ✅ Get all export records for an item
    public List<ExportRecord> getExportRecordsByItem(Long itemId) {
        getItemById(itemId); // ensure item exists
        return exportRecordRepository.findByItemId(itemId);
    }

    // ✅ Get single export record
    public ExportRecord getSingleExportRecord(Long itemId, Long exportId) {
        ExportRecord rec = exportRecordRepository.findById(exportId)
                .orElseThrow(() -> new RuntimeException("Export record not found"));
        if (!rec.getItem().getId().equals(itemId)) {
            throw new RuntimeException("Export record does not belong to this item");
        }
        return rec;
    }

    // ✅ CREATE: Add a new ImportRecord to an existing item
    public ImportRecord addImportToItem(Long itemId, ImportRecord newImport) {
        Item item = getItemById(itemId);
        newImport.setItem(item);

        // (Optional) Update on-hand quantity by what actually arrived
        if (newImport.getQuantityReceived() > 0) {
            item.setQuantity(item.getQuantity() + newImport.getQuantityReceived());
            itemRepository.save(item);
        }
        return importRecordRepository.save(newImport);
    }

    // ✅ CREATE: Add a new ExportRecord to an existing item
    public ExportRecord addExportToItem(Long itemId, ExportRecord newExport) {
        Item item = getItemById(itemId);
        newExport.setItem(item);

        // (Optional) Reduce on-hand quantity by what shipped
        if (newExport.getQuantityShipped() > 0) {
            int newQty = item.getQuantity() - newExport.getQuantityShipped();
            if (newQty < 0) {
                throw new RuntimeException("Not enough stock to ship");
            }
            item.setQuantity(newQty);
            itemRepository.save(item);
        }
        return exportRecordRepository.save(newExport);
    }
}
