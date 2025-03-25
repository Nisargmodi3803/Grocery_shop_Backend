package com.example.grocery_shop_backend.Service;

import com.example.grocery_shop_backend.Dto.CouponCodeDTO;
import com.example.grocery_shop_backend.Dto.CouponDatesUpdateDTO;
import com.example.grocery_shop_backend.Entities.CouponCode;
import com.example.grocery_shop_backend.Entities.Offer;
import com.example.grocery_shop_backend.Exception.objectNotFoundException;
import com.example.grocery_shop_backend.Repository.CouponCodeRepository;
import jakarta.persistence.Temporal;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.plaf.SpinnerUI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CouponCodeService
{
    @Autowired
    private CouponCodeRepository couponCodeRepository;

    public List<CouponCode> findAllCouponsAmt(LocalDate userDate, double amt) {
        List<CouponCode> couponCodes = couponCodeRepository.findAllCouponCode(userDate, amt);
        if (couponCodes.isEmpty()) {
            throw new objectNotFoundException("couponCode");
        }
        return couponCodes;
    }

    public List<CouponCode> findAllCoupons(LocalDate userDate) {
        List<CouponCode> couponCodes = couponCodeRepository.findAllCouponCode(userDate);
        if (couponCodes.isEmpty()) {
            throw new objectNotFoundException("couponCode");
        }
        return couponCodes;
    }

    public CouponCode findCouponCodeByCode(LocalDate userDate, String code, double amount) {
        CouponCode couponCode = couponCodeRepository.findCouponCodeByCode(userDate, code, amount);
        if (couponCode == null) {
            throw new objectNotFoundException("couponCode");
        }
        return couponCode;
    }


    // Find All General Coupons Service
    public List<CouponCode> findAllGeneralCoupons(String userDate)
    {
        LocalDate date = LocalDate.parse(userDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        List<CouponCode> validCoupons = couponCodeRepository.findAllGeneralCouponCode(date);
        if (validCoupons.isEmpty())
            throw new objectNotFoundException("No coupons found");
        return validCoupons;
    }

    // Find All Secret Coupons Service
    public List<CouponCode> findAllSecretCoupons(String userDate)
    {
        LocalDate date = LocalDate.parse(userDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        List<CouponCode> validCoupons = couponCodeRepository.findAllSecretCouponCode(date);
        if (validCoupons.isEmpty())
            throw new objectNotFoundException("No coupons found");
        return validCoupons;
    }

    // Find All Fixed Amount Coupons Service
    public List<CouponCode> findAllFixedAmtCoupons(String userDate)
    {
        LocalDate date = LocalDate.parse(userDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        List<CouponCode> validCoupons = couponCodeRepository.findAllFixedAmtCouponCode(date);
        if (validCoupons.isEmpty())
            throw new objectNotFoundException("No coupons found");
        return validCoupons;
    }

    //Find All Percentage Coupons Service
    public List<CouponCode> findAllPercentageCoupons(String userDate)
    {
        LocalDate date = LocalDate.parse(userDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        List<CouponCode> validCoupons = couponCodeRepository.findAllPercentageCouponCode(date);
        if (validCoupons.isEmpty())
            throw new objectNotFoundException("No coupons found");
        return validCoupons;
    }

    // Update start-date & end-date Service
    @Transactional
    public void updateCoupon(int couponCodeId,CouponCodeDTO couponCodeDTO)
    {
        CouponCode couponCode = couponCodeRepository.findCouponCodeById(couponCodeId);

        if(couponCode!=null)
        {
            couponCode.setCouponCode(couponCodeDTO.getCode());
            couponCode.setCouponTitle(couponCodeDTO.getTitle());
            couponCode.setCouponStartDate(couponCodeDTO.getStartDate());
            couponCode.setCouponEndDate(couponCodeDTO.getEndDate());
            couponCode.setCouponType(couponCodeDTO.getType());
            couponCode.setCouponCodeFor(couponCodeDTO.getCodeFor());
            couponCode.setCouponValue(couponCodeDTO.getValue());
            couponCode.setCouponMinimumBillAmount(couponCodeDTO.getMinValue());
            couponCode.setCouponMaxDiscount(couponCodeDTO.getMaxDiscount());
            couponCode.setCouponStatus(couponCodeDTO.getStatus());
            couponCodeRepository.save(couponCode);
        }
        else
            throw new objectNotFoundException("No coupons found");
    }

    // Delete Coupon Code Service
    @Transactional
    public void deleteCouponCode(int couponCodeId)
    {
        CouponCode couponCode = couponCodeRepository.findCouponCodeById(couponCodeId);
        if(couponCode!=null)
        {
            couponCode.setIsDeleted(2);
            couponCodeRepository.save(couponCode);
        }
        else
            throw new objectNotFoundException("No coupons found");
    }

    // Insert Coupon Code Service
    @Transactional
    public void insertCouponCode(CouponCodeDTO couponCodeDTO)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String cDate = now.format(formatter);

        CouponCode couponCode = new CouponCode();
        couponCode.setCouponCode(couponCodeDTO.getCode());
        couponCode.setCouponTitle(couponCodeDTO.getTitle());
        couponCode.setCouponStartDate(couponCodeDTO.getStartDate());
        couponCode.setCouponEndDate(couponCodeDTO.getEndDate());
        couponCode.setCouponType(couponCodeDTO.getType());
        couponCode.setCouponCodeFor(couponCodeDTO.getCodeFor());
        couponCode.setCouponValue(couponCodeDTO.getValue());
        couponCode.setCouponMinimumBillAmount(couponCodeDTO.getMinValue());
        couponCode.setCouponMaxDiscount(couponCodeDTO.getMaxDiscount());
        couponCode.setCouponStatus(couponCodeDTO.getStatus());
        couponCode.setIsDeleted(1);
        couponCode.setcDate(cDate);

        couponCodeRepository.save(couponCode);
    }

    // Retrieve Coupon Code Service
    @Transactional
    public boolean retrieveCoupon(int couponCodeId)
    {
        CouponCode couponCode = couponCodeRepository.findById(couponCodeId)
                .orElseThrow(() -> new objectNotFoundException("Offer id " + couponCodeId + " not found"));

        if(couponCode.getIsDeleted()==1)
            return false;
        else
        {
            couponCode.setIsDeleted(1);
            return true;
        }
    }

    // Find All Coupon Codes for Admin
    public List<CouponCode> findAllCouponCodesForAdmin(){
        List<CouponCode> couponCodes = couponCodeRepository.findAllCouponCodeForAdmin();
        if(couponCodes.isEmpty())
            throw new objectNotFoundException("No coupons found");
        return couponCodes;
    }

    //Search Coupon Code Service
    public List<CouponCode> searchCouponCodes(String keyword){
        List<CouponCode> couponCodes = couponCodeRepository.searchCouponCodeByKeyword(keyword);
        if(couponCodes.isEmpty())
            throw new objectNotFoundException("No coupons found");
        return couponCodes;
    }

    // Check Code Service
    public boolean checkCode(String code){
        String existingCode = couponCodeRepository.findCouponCodeByCode(code);
        if(existingCode != null && !existingCode.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    public CouponCode findCouponCodeById(int id){
        CouponCode couponCode = couponCodeRepository.findCouponCodeById(id);
        if(couponCode==null)
            throw new objectNotFoundException("No coupons found");

        return couponCode;
    }
}
