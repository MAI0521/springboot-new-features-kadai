package com.example.samuraitravel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.samuraitravel.entity.Favorite;
import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.User;
import com.example.samuraitravel.repository.FavoriteRepository;
import com.example.samuraitravel.repository.HouseRepository;
import com.example.samuraitravel.repository.UserRepository;

@Service
public class FavoriteService {
	private final FavoriteRepository favoriteRepository;
    private final HouseRepository houseRepository;
    private final UserRepository userRepository;

    @Autowired
    public  FavoriteService(FavoriteRepository favoriteRepository, HouseRepository houseRepository, UserRepository userRepository) {
        this.favoriteRepository = favoriteRepository;
        this.houseRepository = houseRepository;
        this.userRepository = userRepository;
    }

    public Favorite getFavoritesForHouse(House house, User user) {
    	return favoriteRepository.findByHouseAndUserOrderByCreatedAtDesc(house,user);
    }

    @Transactional
    public void addFavorite(Integer houseId, User user) {
    	House house = houseRepository.findById(houseId).orElse(null);
    	

        // Create a new Favorites entity
        Favorite favorite = new Favorite();
        favorite.setHouse(house);
        favorite.setUser(user);
       

        // Save the Favorites entity
        favoriteRepository.save(favorite);
    }
    
    @Transactional
	public void deleteFavoritesByHouseIdAndUser(Integer houseId, User user) {
    	House house = houseRepository.findById(houseId).orElse(null);
    	
    	List<Favorite> favorites = favoriteRepository.findByHouseAndUser(house, user);
        for (Favorite favorite : favorites) {
            favoriteRepository.delete(favorite);
		
	}
}
}