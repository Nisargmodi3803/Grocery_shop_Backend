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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CouponCodeService
{
    @Autowired
    private CouponCodeRepository couponCodeRepository;

    // Find All Coupon Service
//    public List<CouponCode> findAllCoupons(String userDate,double amount)
//    {
//        LocalDate date = LocalDate.parse(userDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        List<CouponCode> couponCodes = couponCodeRepository.findAllCouponCode(date,amount);
//        if (couponCodes.isEmpty())
//            throw new objectNotFoundException("No coupons found");
//        return couponCodes;
//    }

//    Find Coupon by Code Service
//    public List<CouponCode> findCouponByCode(String userDate,String code)
//    {
//        LocalDate date = LocalDate.parse(userDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        CouponCode couponCode = couponCodeRepository.findCouponCodeByCode(date,code);
//        if(couponCode==null)
//        throw new objectNotFoundException("No coupons found");
//        return couponCode;
//    }

    // Find All Coupons for Coupon Code Page Service
    //    public List<CouponCode> findAllCoupons(String userDate)
//    {
//        LocalDate date = LocalDate.parse(userDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        CouponCode couponCode = couponCodeRepository.findCouponCodeByCode(date);
//        if(couponCode==null)
//        throw new objectNotFoundException("No coupons found");
//        return couponCode;
//    }

    public List<CouponCode> findAllCoupons(){
        List<CouponCode> couponCodes = couponCodeRepository.findAllCouponCode();
        if(couponCodes.isEmpty())
            throw new objectNotFoundException("No Coupon Found");
        return couponCodes;
    }

    public List<CouponCode> findAllCoupons(double amount)
    {
        List<CouponCode> couponCodes = couponCodeRepository.findAllCouponCode(amount);
        if (couponCodes.isEmpty())
            throw new objectNotFoundException("No coupons found");
        return couponCodes;
    }

    public CouponCode findCouponByCode(String code,double amount){
        CouponCode couponCode = couponCodeRepository.findCouponCodeByCode(code,amount);
        if(couponCode==null)
            throw new objectNotFoundException("No coupons found");
        return couponCode;
    }

    // Find All General Coupons Service
    public List<CouponCode> findAllGeneralCoupons(String userDate)
    {
        LocalDate date = LocalDate.parse(userDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<CouponCode> validCoupons = couponCodeRepository.findAllGeneralCouponCode(date);
        if (validCoupons.isEmpty())
            throw new objectNotFoundException("No coupons found");
        return validCoupons;
    }

    // Find All Secret Coupons Service
    public List<CouponCode> findAllSecretCoupons(String userDate)
    {
        LocalDate date = LocalDate.parse(userDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<CouponCode> validCoupons = couponCodeRepository.findAllSecretCouponCode(date);
        if (validCoupons.isEmpty())
            throw new objectNotFoundException("No coupons found");
        return validCoupons;
    }

    // Find All Fixed Amount Coupons Service
    public List<CouponCode> findAllFixedAmtCoupons(String userDate)
    {
        LocalDate date = LocalDate.parse(userDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<CouponCode> validCoupons = couponCodeRepository.findAllFixedAmtCouponCode(date);
        if (validCoupons.isEmpty())
            throw new objectNotFoundException("No coupons found");
        return validCoupons;
    }

    //Find All Percentage Coupons Service
    public List<CouponCode> findAllPercentageCoupons(String userDate)
    {
        LocalDate date = LocalDate.parse(userDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<CouponCode> validCoupons = couponCodeRepository.findAllPercentageCouponCode(date);
        if (validCoupons.isEmpty())
            throw new objectNotFoundException("No coupons found");
        return validCoupons;
    }

    // Update start-date & end-date Service
    @Transactional
    public CouponCode updateDates(int couponCodeId,CouponDatesUpdateDTO couponDates)
    {
        CouponCode couponCode = couponCodeRepository.findCouponById(couponCodeId);

        if(couponCode!=null)
        {
            if(couponDates!=null)
            {
                if(couponDates.getStartDate()!=null)
                    couponCode.setCouponStartDate(couponDates.getStartDate());
                if (couponDates.getEndDate()!=null)
                    couponCode.setCouponEndDate(couponDates.getEndDate());
            }
            else
            {
                throw new objectNotFoundException("No Update Date Found");
            }
            return couponCodeRepository.save(couponCode);
        }
        else
            throw new objectNotFoundException("No coupons found");
    }

    // Delete Coupon Code Service
    @Transactional
    public void deleteCouponCode(int couponCodeId)
    {
        CouponCode couponCode = couponCodeRepository.findCouponById(couponCodeId);
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
        couponCode.setCouponStatus(1);
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
}
