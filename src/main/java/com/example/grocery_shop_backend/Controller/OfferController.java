package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Dto.OfferDTO;
import com.example.grocery_shop_backend.Dto.OfferDatesUpdateDTO;
import com.example.grocery_shop_backend.Entities.Offer;
import com.example.grocery_shop_backend.Service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class OfferController
{
    @Autowired
    private OfferService offerService;

    // GET API {Find All Offers by Brand}
    @GetMapping("/offer-brand")
    public List<Offer> findAllOffersByBrand(@RequestParam String userDate)
    {
        return offerService.findAllOffersByBrand(userDate);
    }

    // GET API {Find All Offers by Category}
    @GetMapping("/offer-category")
    public List<Offer> findAllOffersByCategory(@RequestParam String userDate)
    {
        return offerService.findAllOffersByCategory(userDate);
    }

    // GET API {Find All Offers by SubCategory}
    @GetMapping("/offer-subcategory")
    public List<Offer> findAllOffersBySubCategory(@RequestParam String userDate)
    {
        return offerService.findAllOffersBySubCategory(userDate);
    }

    // GET API {Find All Offers by Products}
    @GetMapping("offer-products")
    public List<Offer> findAllOffersByProducts(@RequestParam String userDate)
    {
        return offerService.findAllOffersByProducts(userDate);
    }

    // GET API {Find All Offers by slug title}
    @GetMapping("/offer-slug-title/{slugTitle}")
    public Offer findOfferBySlugTitle(@PathVariable String slugTitle, @RequestParam String userDate)
    {
        return offerService.findAllOffersBySlugTitle(slugTitle,userDate);
    }

    // PATCH API {Update start-date & end-date}
    @PatchMapping("/update-offer-dates/{offerId}")
    public Offer updateDates(@PathVariable int offerId, @RequestBody OfferDatesUpdateDTO offerDates)
    {
        return offerService.updateDates(offerId,offerDates);
    }

    // PATCH API {Delete Offer}
    @PatchMapping("/delete-offer/{offerId}")
    public ResponseEntity<String> deleteOffer(@PathVariable int offerId)
    {
        offerService.deleteOffer(offerId);
        return ResponseEntity.ok("Deleted offer");
    }

    // POST API {Insert Offer}
    @PostMapping("/insert-offer")
    public ResponseEntity<String> insertOffer(@RequestBody OfferDTO offerDTO)
    {
        try
        {
            offerService.insertOffer(offerDTO);
            return ResponseEntity.ok("Successfully inserted offer");
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PATCH API {Retrieve Offer}
    @PatchMapping("retrieve-offer/{id}")
    public ResponseEntity<String> retrieveOffer(@PathVariable int id)
    {
        try {
            if(offerService.retrieveOffer(id))
                return ResponseEntity.ok("Successfully retrieved offer");
            else
                return ResponseEntity.ofNullable("Already Present[Not Deleted]");
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
