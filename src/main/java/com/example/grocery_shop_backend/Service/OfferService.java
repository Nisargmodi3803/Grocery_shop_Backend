package com.example.grocery_shop_backend.Service;

import com.example.grocery_shop_backend.Dto.CouponDatesUpdateDTO;
import com.example.grocery_shop_backend.Dto.OfferDatesUpdateDTO;
import com.example.grocery_shop_backend.Entities.CouponCode;
import com.example.grocery_shop_backend.Entities.Offer;
import com.example.grocery_shop_backend.Exception.objectNotFoundException;
import com.example.grocery_shop_backend.Repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    public Offer findAllOffersBySlugTitle(String slugTitle,String userDate)
    {
        LocalDate date = LocalDate.parse(userDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Offer offer = offerRepository.findOfferBySlugTitle(slugTitle,date);
        if(offer == null)
            throw new objectNotFoundException("No offers found");
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
}
