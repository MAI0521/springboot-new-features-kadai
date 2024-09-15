package com.example.samuraitravel.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.samuraitravel.entity.Favorite;
import com.example.samuraitravel.entity.User;
import com.example.samuraitravel.repository.FavoriteRepository;
import com.example.samuraitravel.repository.HouseRepository;
import com.example.samuraitravel.repository.ReviewRepository;
import com.example.samuraitravel.security.UserDetailsImpl;
import com.example.samuraitravel.service.FavoriteService;
 
 @Controller
 @RequestMapping
 
public class FavoriteController {
	private final HouseRepository houseRepository; 
    private final ReviewRepository reviewRepository;
    private final FavoriteRepository favoriteRepository;
    private final FavoriteService favoriteService;
    
    @Autowired
    public FavoriteController(HouseRepository houseRepository, ReviewRepository reviewRepository, FavoriteRepository favoriteRepository, FavoriteService favoriteService) {
        this.houseRepository = houseRepository;     
        this.reviewRepository = reviewRepository;
        this.favoriteRepository = favoriteRepository;
        this.favoriteService = favoriteService;
    }
    
    @GetMapping("/favorites")
    public String index(@AuthenticationPrincipal UserDetailsImpl userDetails, 
    		@PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Direction.ASC) Pageable pageable, Model model) {
        User user = userDetails.getUser();
        Page<Favorite> favoritesPage = favoriteRepository.findByUserOrderByCreatedAtDesc(user, pageable);
        
        model.addAttribute("favoritesPage", favoritesPage);         
        
        return "favorites/index";
    }

    @PostMapping ("/houses/{houseId}/favorites")
    public String addFavorite(
    		@PathVariable("houseId") Integer houseId,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
    		Model model) {
//    		Favorite favorite = favoriteRepository.findById(userId).orElse(null);
    		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    		String currentUserName = authentication.getName();
    	
	//   	 House house = houseRepository.findById(id).orElse(null);
//   	 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String currentUserName = authentication.getName(); 
//   	 Favorite favorite = favoriteRepository.findByHouseOrderByCreatedAtDesc(house);
//    	House house = houseRepository.findById(id).orElse(null);
//    	FavoriteService favoriteService = new FavoriteService(null, null, null);
    	
    	 System.out.println("Current User Name: " + currentUserName);
    	 System.out.println("Current House Name: " + houseId);
    	 
    	 favoriteService.addFavorite(houseId, userDetails.getUser());

   	 
//        model.addAttribute("house", house); 
//        model.addAttribute("favorites", favorite);
//        model.addAttribute("currentUserName", currentUserName);
        
//        if (house != null) {
//            model.addAttribute("house", house);
//        } else {
            // Handle the case where the house is not found
//            return "redirect:/error";
//        }
        return "redirect:/houses/{houseId}";
    }
    
   
    @PostMapping ("/houses/{houseId}/favorites-delete")
    public String deleteFavorite(
    		@PathVariable("houseId") Integer houseId,
    		@AuthenticationPrincipal UserDetailsImpl userDetails,
            RedirectAttributes redirectAttributes) {
	    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String currentUserName = authentication.getName();
    		
			favoriteService.deleteFavoritesByHouseIdAndUser(houseId, userDetails.getUser());
        return "redirect:/houses/{houseId}";
    }

}
