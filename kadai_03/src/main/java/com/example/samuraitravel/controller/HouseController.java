package com.example.samuraitravel.controller;

 import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.samuraitravel.entity.Favorite;
import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.Review;
import com.example.samuraitravel.form.ReservationInputForm;
import com.example.samuraitravel.repository.FavoriteRepository;
import com.example.samuraitravel.repository.HouseRepository;
import com.example.samuraitravel.repository.ReviewRepository;
import com.example.samuraitravel.repository.UserRepository;
import com.example.samuraitravel.security.UserDetailsImpl;
 
 @Controller
 @RequestMapping("/houses")
public class HouseController {
     private final HouseRepository houseRepository; 
     private final ReviewRepository reviewRepository;
     private final FavoriteRepository favoriteRepository;
     private final UserRepository userRepository;
     
     public HouseController(HouseRepository houseRepository, ReviewRepository reviewRepository, FavoriteRepository favoriteRepository, UserRepository userRepository) {
         this.houseRepository = houseRepository;     
         this.reviewRepository = reviewRepository;
         this.favoriteRepository = favoriteRepository;
         this.userRepository = userRepository;     }     
   
     @GetMapping
     public String index(@RequestParam(name = "keyword", required = false) String keyword,
                         @RequestParam(name = "area", required = false) String area,
                         @RequestParam(name = "price", required = false) Integer price,   
                         @RequestParam(name = "order", required = false) String order,
                         @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
                         Model model) 
     {
         Page<House> housePage;
                 
         if (keyword != null && !keyword.isEmpty()) {
        	 if (order != null && order.equals("priceAsc")) {
                 housePage = houseRepository.findByNameLikeOrAddressLikeOrderByPriceAsc("%" + keyword + "%", "%" + keyword + "%", pageable);
             } else {
                 housePage = houseRepository.findByNameLikeOrAddressLikeOrderByCreatedAtDesc("%" + keyword + "%", "%" + keyword + "%", pageable);
             }            
         } else if (area != null && !area.isEmpty()) {
        	 if (order != null && order.equals("priceAsc")) {
                 housePage = houseRepository.findByAddressLikeOrderByPriceAsc("%" + area + "%", pageable);
             } else {
                 housePage = houseRepository.findByAddressLikeOrderByCreatedAtDesc("%" + area + "%", pageable);
             }
         } else if (price != null) {
        	 if (order != null && order.equals("priceAsc")) {
                 housePage = houseRepository.findByPriceLessThanEqualOrderByPriceAsc(price, pageable);
             } else {
                 housePage = houseRepository.findByPriceLessThanEqualOrderByCreatedAtDesc(price, pageable);
             }
         } else {
        	 if (order != null && order.equals("priceAsc")) {
                 housePage = houseRepository.findAllByOrderByPriceAsc(pageable);
             } else {
                 housePage = houseRepository.findAllByOrderByCreatedAtDesc(pageable);   
             }    
         }                
         
         model.addAttribute("housePage", housePage);
         model.addAttribute("keyword", keyword);
         model.addAttribute("area", area);
         model.addAttribute("price", price);   
         model.addAttribute("order", order);
         
         
         return "houses/index";
     }
     
     @GetMapping("{houseId}")
     public String getHouseById(@PathVariable("houseId") Integer houseId,
    		 @PageableDefault(size = 10) Pageable pageable, 
             @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
             Model model) {
    	 House house = houseRepository.findById(houseId).orElse(null);
    	 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         String currentUserName = authentication.getName(); 
    	 Page<Review> review = reviewRepository.findByHouseOrderByCreatedAtDesc(house, PageRequest.of(0, 6));
         
    	 System.out.println("Current User Name: " + currentUserName);
    	 System.out.println("Current House Name: " + houseId);
    	 
         model.addAttribute("house", house); 
         model.addAttribute("reservationInputForm", new ReservationInputForm());
         model.addAttribute("review",review);
         model.addAttribute("currentUserName", currentUserName);
         if (userDetailsImpl != null) {
             com.example.samuraitravel.entity.User user = userDetailsImpl.getUser();
             Favorite favorite = favoriteRepository.findByHouseAndUserOrderByCreatedAtDesc(house, user);
             model.addAttribute("favorite", favorite);
         } else {
             model.addAttribute("favorite", null); // Handle the case where userDetailsImpl is null
         }
         
         if (house != null) {
             model.addAttribute("house", house);
         } else {
             // Handle the case where the house is not found
             return "redirect:/error";
         }
         return "houses/show";
     }
  
}