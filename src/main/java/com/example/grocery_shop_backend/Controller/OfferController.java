package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Dto.OfferDTO;
import com.example.grocery_shop_backend.Dto.OfferDatesUpdateDTO;
import com.example.grocery_shop_backend.Entities.Offer;
import com.example.grocery_shop_backend.Service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class OfferController
{
    @Autowired
    private OfferService offerService;

    // GET API {Find All Offers by Brand}
    @GetMapping("/offer-brand")
    public ResponseEntity<List<Offer>> findAllOffersByBrand(@RequestParam String userDate)
    {
        try {
            return new ResponseEntity<>(offerService.findAllOffersByBrand(userDate), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // GET API {Find All Offers by Category}
    @GetMapping("/offer-category")
    public ResponseEntity<List<Offer>> findAllOffersByCategory()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime now = LocalDateTime.now();
        String userDate = now.format(formatter);

        try {
            return new ResponseEntity<>(offerService.findAllOffersByCategory(userDate), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // GET API {Find All Offers by SubCategory}
    @GetMapping("/offer-subcategory")
    public ResponseEntity<List<Offer>> findAllOffersBySubCategory()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime now = LocalDateTime.now();
        String userDate = now.format(formatter);

        try {
            return new ResponseEntity<>(offerService.findAllOffersBySubCategory(userDate), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // GET API {Find All Offers by Products}
    @GetMapping("offer-products")
    public ResponseEntity<List<Offer>> findAllOffersByProducts()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime now = LocalDateTime.now();
        String userDate = now.format(formatter);

        try {
            return new ResponseEntity<>(offerService.findAllOffersByProducts(userDate), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // GET API {Find All Offers by slug title}
    @GetMapping("/offer-slug-title/{slugTitle}")
    public ResponseEntity<Offer> findOfferBySlugTitle(@PathVariable String slugTitle) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // Fixed Format
        LocalDateTime now = LocalDateTime.now();
        String userDate = now.format(formatter);

        try {
            Offer offer = offerService.findAllOffersBySlugTitle(slugTitle, userDate); // Fixed argument order
            return ResponseEntity.ok(offer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Handle exception properly
        }
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
