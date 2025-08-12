package com.code4cause.qventor.controller;

import com.code4cause.qventor.model.Item;
import com.code4cause.qventor.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/items")
public class ItemController {
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    // ✅ Add item to admin by supabaseUserId
    @PostMapping("/{supabaseUserId}")
    public ResponseEntity<Item> addItemToAdmin(@PathVariable String supabaseUserId, @RequestBody Item item) {
        return ResponseEntity.ok(itemService.addItemToAdmin(supabaseUserId, item));
    }

    // ✅ Get all items of a specific admin
    @GetMapping("/admin/{supabaseUserId}")
    public ResponseEntity<List<Item>> getItemsByAdmin(@PathVariable String supabaseUserId) {
        return ResponseEntity.ok(itemService.getItemsByAdmin(supabaseUserId));
    }

    // ✅ Get single item by ID
    @GetMapping("/{itemId}")
    public ResponseEntity<Item> getItemById(@PathVariable Long itemId) {
        return ResponseEntity.ok(itemService.getItemById(itemId));
    }

    // ✅ Update item
    @PutMapping("/{itemId}")
    public ResponseEntity<Item> updateItem(@PathVariable Long itemId, @RequestBody Item item) {
        return ResponseEntity.ok(itemService.updateItem(itemId, item));
    }

    // ✅ Delete item
    @DeleteMapping("/{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
        return ResponseEntity.ok("Item deleted successfully");
    }
}
