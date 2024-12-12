package com.uday.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uday.Entity.Bill;
import com.uday.Entity.Item;
import com.uday.Repository.ItemRepository;
import com.uday.Service.CheckoutService;
import com.uday.Service.RegisterService;
import com.uday.dto.BillDTO;
import com.uday.dto.ItemDTO;

//@RestController
//@RequestMapping("/api/register")
//public class RegisterController {
//    private final RegisterService registerService;
//    @Autowired
//    private ItemRepository itemRepository;


//    @Autowired
//    public RegisterController(RegisterService registerService) {
//        this.registerService = registerService;
//    }
//
//    @PostMapping("/startCheckout")
//    public ResponseEntity<String> startCheckout() {
//        registerService.startCheckout();
//
//        return ResponseEntity.ok("Checkout started.");
//    }
//
//    @PostMapping("/finishCheckout")
//    public ResponseEntity<BillDTO> finishCheckout() {
//        Bill bill = registerService.finishCheckout();
//        return ResponseEntity.ok(new BillDTO(bill.getItems(), bill.getTotalAmount()));
//
//    }
//    
//    @PostMapping("/addItems")
//    public ResponseEntity<String> addItems(@RequestBody List<Item> items) {
//        itemRepository.saveAll(items); // Use saveAll for bulk operations
//        return ResponseEntity.ok("Items added successfully.");
//    }


@RestController
@RequestMapping("/api/register")
public class RegisterController {
	 @Autowired
	    private CheckoutService checkoutService;
	 
	 
	 
	 
	 
	  private List<ItemDTO> currentItems = new ArrayList<>(); // Store items temporarily
	    private Double totalAmount = 0.0; // Store the total amount

	    // Start the bill - Initialize the list and reset the total amount
	    @PostMapping("/start")
	    public ResponseEntity<String> start() {
	        currentItems.clear(); // Clear any previous items
	        totalAmount = 0.0; // Reset the total amount
	        return ResponseEntity.ok("Bill started. Ready to add items.");
	    }

	    // Add item(s) to the bill (accepts a list of items)
	    @PostMapping("/addItem")
	    public ResponseEntity<String> addItem(@RequestBody List<ItemDTO> items) {
	        try {
	            currentItems.addAll(items); // Add the list of items to the current items
	            for (ItemDTO item : items) {
	                totalAmount += item.getPrice(); // Add the price of each item to the total
	            }
	            return ResponseEntity.ok("Items added successfully.");
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().body("Error adding items: " + e.getMessage());
	        }
	    }
   
	    
	 // After adding the offers
//	    // Finish checkout and return the items and total amount
//	    @PostMapping("/finishCheckout")
//	    public ResponseEntity<BillDTO> finishCheckout() {
//	        try {
//	            BillDTO billDTO = new BillDTO(currentItems, totalAmount);
//	            return ResponseEntity.ok(billDTO);
//	        } catch (Exception e) {
//	            return ResponseEntity.badRequest().body(new BillDTO(new ArrayList<>(), 0.0));
//	        }
	    
	    // After adding the offers
	    @PostMapping("/finishCheckout")
	    public ResponseEntity<Map<String, Object>> finishCheckout(@RequestBody List<ItemDTO> items) {
	        try {
	            // Calculate the total amount (sum of item prices)
	            double totalAmount = items.stream().mapToDouble(ItemDTO::getPrice).sum();

	            // Call the instance method of CheckoutService to apply offers and get checkout details
	            Map<String, Object> checkoutDetails = checkoutService.applyOffersToCart(items, totalAmount);

	            // Get the final amount after applying offers
	            double finalAmount = (double) checkoutDetails.get("finalAmount");

	            // Prepare the response in the desired order: totalAmount, appliedOffers, finalAmount, items
	            Map<String, Object> response = Map.of(
	                "items", checkoutDetails.get("items"),
	                "totalAmount", totalAmount,
	                "appliedOffers", checkoutDetails.get("appliedOffers"),
	                "finalAmount", finalAmount
	            );

	            // Return the response
	            return ResponseEntity.ok(response);
	        } catch (Exception e) {
	            // Handle any exceptions and return an error response
	            return ResponseEntity.badRequest().body(Map.of("error", "Error during checkout: " + e.getMessage()));
	        }
	    }
	}