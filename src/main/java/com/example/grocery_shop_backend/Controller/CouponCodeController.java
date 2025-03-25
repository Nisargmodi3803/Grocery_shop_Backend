package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Dto.CouponCodeDTO;
import com.example.grocery_shop_backend.Dto.CouponDatesUpdateDTO;
import com.example.grocery_shop_backend.Entities.CouponCode;
import com.example.grocery_shop_backend.Repository.CouponCodeRepository;
import com.example.grocery_shop_backend.Service.CouponCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"}, allowCredentials = "true")
public class CouponCodeController
{
    @Autowired
    private CouponCodeService couponCodeService;

    // GET API {Find All Coupons}
    @GetMapping("/coupons")
    public ResponseEntity<List<CouponCode>> findAllCoupons(@RequestParam double amount) {
        try {
            LocalDate userDate = LocalDate.now();
            return new ResponseEntity<>(couponCodeService.findAllCouponsAmt(userDate, amount), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // GET API {Find All Coupons for Coupon Code Page API}
    @GetMapping("/all-coupons")
    public ResponseEntity<List<CouponCode>> findAllCoupons() {
        try {
            LocalDate userDate = LocalDate.now();
            return new ResponseEntity<>(couponCodeService.findAllCoupons(userDate), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // GET API {Verify the Coupon Code API}
    @GetMapping("/coupon-code")
    public ResponseEntity<CouponCode> verifyCouponCode(@RequestParam String code, @RequestParam double amount) {
        try {
            LocalDate userDate = LocalDate.now();
            return new ResponseEntity<>(couponCodeService.findCouponCodeByCode(userDate, code, amount), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    // GET API {Find All General Coupons}
    @GetMapping("/general-coupons")
    public List<CouponCode> findAllGeneralCoupons(@RequestParam String date)
    {
        return couponCodeService.findAllGeneralCoupons(date);
    }

    // GET API {Find All Secret Coupons}
    @GetMapping("/secret-coupons")
    public List<CouponCode> findAllSecretCoupons(@RequestParam String date)
    {
        return couponCodeService.findAllSecretCoupons(date);
    }

    // GET API {Find All Fixed Amt Coupons}
    @GetMapping("/amount-coupons")
    public List<CouponCode> findAllFixedAmtCoupons(@RequestParam String date)
    {
        return couponCodeService.findAllFixedAmtCoupons(date);
    }

    // GET API {Find All Percentage Coupons}
    @GetMapping("/percentage-coupons")
    public List<CouponCode> findAllPercentageCoupons(@RequestParam String date)
    {
        return couponCodeService.findAllPercentageCoupons(date);
    }

    // PATCH API {Update start-date & end-date}
    @PatchMapping("/update-coupon-code/{id}")
    public ResponseEntity<String> updateDates(@PathVariable int id,@RequestBody CouponCodeDTO couponCodeDTO)
    {
        try {
            couponCodeService.updateCoupon(id, couponCodeDTO);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // PATCH API {Delete Coupon Code}
    @PatchMapping("/delete-coupon-code/{couponId}")
    public ResponseEntity<String> deleteCoupon(@PathVariable int couponId)
    {
        couponCodeService.deleteCouponCode(couponId);
        return ResponseEntity.ok("Successfully deleted coupon code");
    }

    // POST API {Insert Coupon Code}
    @PostMapping("/add-coupon-code")
    public ResponseEntity<String> insertCoupon(@RequestBody CouponCodeDTO couponCodeDTO)
    {
        try {
            couponCodeService.insertCouponCode(couponCodeDTO);
            return ResponseEntity.ok("Successfully inserted coupon code");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PATCH API {Retrieve Coupon Code}
    @PatchMapping("retrieve-coupon-code/{id}")
    public ResponseEntity<String> retrieveOffer(@PathVariable int id)
    {
        try {
            if(couponCodeService.retrieveCoupon(id))
                return ResponseEntity.ok("Successfully retrieved offer");
            else
                return ResponseEntity.ofNullable("Already Present[Not Deleted]");
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET API {Find All Coupon Code for Admin}
    @GetMapping("/admin/coupon-codes")
    public ResponseEntity<List<CouponCode>> findAllCouponCodeForAdmin(){
        try {
            return ResponseEntity.ok(couponCodeService.findAllCouponCodesForAdmin());
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // GET API {Search Coupon Code}
    @GetMapping("/search-coupon-code")
    public ResponseEntity<List<CouponCode>> findAllCouponCodeForSearch(@RequestParam String keyword){
        try {
            return new ResponseEntity<>(couponCodeService.searchCouponCodes(keyword), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // GET API {Check Slug Title}
    @GetMapping("/check-coupon-code")
    public ResponseEntity<Boolean> checkCode(@RequestParam String code){
        return ResponseEntity.ok(couponCodeService.checkCode(code));
    }

    @GetMapping("/admin/coupon/{id}")
    public ResponseEntity<CouponCode> findCouponById(@PathVariable int id){
        try {
            return new ResponseEntity(couponCodeService.findCouponCodeById(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
