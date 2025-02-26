package com.example.grocery_shop_backend.Service;

import com.example.grocery_shop_backend.Dto.CouponDatesUpdateDTO;
import com.example.grocery_shop_backend.Dto.OfferDTO;
import com.example.grocery_shop_backend.Dto.OfferDatesUpdateDTO;
import com.example.grocery_shop_backend.Entities.CouponCode;
import com.example.grocery_shop_backend.Entities.Offer;
import com.example.grocery_shop_backend.Exception.objectNotFoundException;
import com.example.grocery_shop_backend.Repository.OfferRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class OfferService
{
    @Autowired
    private OfferRepository offerRepository;

    // Find All Offers by Brand Service
    public List<Offer> findAllOffersByBrand(String userDate)
    {
        LocalDate date = LocalDate.parse(userDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<Offer> offers = offerRepository.findAllOffersByBrand(date);
        if(offers.isEmpty())
            throw new objectNotFoundException("No offers found");
        return offers;
    }

    // Find All Offers by Category Service
    public List<Offer> findAllOffersByCategory(String userDate)
    {
        LocalDate date = LocalDate.parse(userDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<Offer> offers = offerRepository.findAllOffersByCategory(date);
        if (offers.isEmpty())
            throw new objectNotFoundException("No offers found");
        return offers;
    }

    // Find All Offers by Subcategory Service
    public List<Offer> findAllOffersBySubCategory(String userDate)
    {
        LocalDate date = LocalDate.parse(userDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<Offer> offers = offerRepository.findAllOffersBySubCategory(date);
        if(offers.isEmpty())
            throw new objectNotFoundException("No offers found");
        return offers;
    }

    // Find All Offers by Slug title
    public Offer findAllOffersBySlugTitle(String slugTitle, String userDate) { // Fixed argument order
        LocalDate date = LocalDate.parse(userDate, DateTimeFormatter.ofPattern("dd-MM-yyyy")); // Fixed Format
        Offer offer = offerRepository.findOfferBySlugTitle(slugTitle, date);

        if (offer == null) {
            throw new objectNotFoundException("No offers found");
        }
        return offer;
    }


    // Find All Offers by Products
    public List<Offer> findAllOffersByProducts(String userDate)
    {
        LocalDate date = LocalDate.parse(userDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<Offer> offers = offerRepository.findAllOffersByProducts(date);
        if(offers.isEmpty())
            throw new objectNotFoundException("No offers found");
        return offers;
    }

    // Update start-date & end-date Service
    @Transactional
    public Offer updateDates(int offerId, OfferDatesUpdateDTO offerDates)
    {
        Offer offer = offerRepository.findOfferById(offerId);

        if(offer!=null)
        {
            if(offerDates!=null)
            {
                if(offerDates.getStartDate()!=null)
                    offer.setOfferStartDate(offerDates.getStartDate());
                if (offerDates.getEndDate()!=null)
                    offer.setOfferEndDate(offerDates.getEndDate());
            }
            else
            {
                throw new objectNotFoundException("No Update Date Found");
            }
            return offerRepository.save(offer);
        }
        else
            throw new objectNotFoundException("No Offer Found");
    }

    // Delete Offer Service
    @Transactional
    public void deleteOffer(int offerId)
    {
        Offer offer = offerRepository.findOfferById(offerId);

        if(offer!=null)
        {
            offer.setIsDeleted(2);
            offerRepository.save(offer);
        }
        else
            throw new objectNotFoundException("No Offer Found");
    }

    // Insert Offer Service
    @Transactional
    public void insertOffer(OfferDTO offerDTO)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String cDate = now.format(formatter);

        Offer offer = new Offer();
        offer.setCreateBannerFor(offerDTO.getBanner_for());
        offer.setOfferTitle(offerDTO.getTitle());
        offer.setOfferDescription(offerDTO.getDescription());
        offer.setOfferImage(offerDTO.getImage());
        offer.setOfferImageWeb(offerDTO.getImage_web());
        offer.setOfferStartDate(offerDTO.getStartDate());
        offer.setOfferEndDate(offerDTO.getEndDate());
        offer.setSlugTitle(offerDTO.getSlugTitle());
        offer.setOfferIsInBanner(1);
        offer.setIsDeleted(1);
        offer.setcDate(cDate);
        offerRepository.save(offer);
    }

    // Retrieve Offer Service
    @Transactional
    public boolean retrieveOffer(int offerId)
    {
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new objectNotFoundException("Offer id " + offerId + " not found"));

        if(offer.getIsDeleted()==1)
            return false;
        else
        {
            offer.setIsDeleted(1);
            return true;
        }
    }
}
