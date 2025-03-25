package com.example.grocery_shop_backend.Repository;

import com.example.grocery_shop_backend.Entities.CouponCode;
import com.example.grocery_shop_backend.Entities.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CouponCodeRepository extends JpaRepository<CouponCode, Integer>
{
          // For testing
//    @Query("SELECT coupon FROM CouponCode coupon WHERE coupon.couponMinimumBillAmount <= :amount AND coupon.isDeleted=1")
//    List<CouponCode> findAllCouponCode(double amount);
//
//    @Query("SELECT coupon FROM CouponCode coupon WHERE coupon.couponCode = :code AND coupon.couponMinimumBillAmount <= :amount AND coupon.isDeleted=1")
//    CouponCode findCouponCodeByCode(String code,double amount);
//
//    @Query("SELECT coupon FROM CouponCode coupon WHERE coupon.isDeleted=1")
//    List<CouponCode> findAllCouponCode();

    @Query("SELECT coupon FROM CouponCode coupon " +
            "WHERE :userDate BETWEEN FUNCTION('STR_TO_DATE', coupon.couponStartDate, '%Y-%m-%d') " +
            "AND FUNCTION('STR_TO_DATE', coupon.couponEndDate, '%Y-%m-%d') " +
            "AND coupon.couponMinimumBillAmount <= :amount AND coupon.isDeleted = 1")
    List<CouponCode> findAllCouponCode(LocalDate userDate, double amount);

    @Query("SELECT coupon FROM CouponCode coupon " +
            "WHERE :userDate BETWEEN FUNCTION('STR_TO_DATE', coupon.couponStartDate, '%Y-%m-%d') " +
            "AND FUNCTION('STR_TO_DATE', coupon.couponEndDate, '%Y-%m-%d') " +
            "AND coupon.couponMinimumBillAmount <= :amount AND coupon.couponCode = :code AND coupon.isDeleted = 1")
    CouponCode findCouponCodeByCode(LocalDate userDate, String code, double amount);

    @Query("SELECT coupon FROM CouponCode coupon " +
            "WHERE :userDate BETWEEN FUNCTION('STR_TO_DATE', coupon.couponStartDate, '%Y-%m-%d') " +
            "AND FUNCTION('STR_TO_DATE', coupon.couponEndDate, '%Y-%m-%d') " +
            "AND coupon.isDeleted = 1")
    List<CouponCode> findAllCouponCode(LocalDate userDate);



    @Query("SELECT coupon FROM CouponCode coupon " +
            "WHERE :userDate BETWEEN FUNCTION('STR_TO_DATE', coupon.couponStartDate, '%Y-%m-%d') " +
            "AND FUNCTION('STR_TO_DATE', coupon.couponEndDate, '%d-%m-%Y') " +
            "AND coupon.couponStatus = 1 AND coupon.isDeleted = 1 AND coupon.couponCodeFor=2")
    List<CouponCode> findAllGeneralCouponCode(@Param("userDate") LocalDate userDate);

    @Query("SELECT coupon FROM CouponCode coupon " +
            "WHERE :userDate BETWEEN FUNCTION('STR_TO_DATE', coupon.couponStartDate, '%Y-%m-%d') " +
            "AND FUNCTION('STR_TO_DATE', coupon.couponEndDate, '%d-%m-%Y') " +
            "AND coupon.couponStatus = 1 AND coupon.isDeleted = 1 AND coupon.couponCodeFor=1")
    List<CouponCode> findAllSecretCouponCode(@Param("userDate") LocalDate userDate);

    @Query("SELECT coupon FROM CouponCode coupon " +
            "WHERE :userDate BETWEEN FUNCTION('STR_TO_DATE', coupon.couponStartDate, '%Y-%m-%d') " +
            "AND FUNCTION('STR_TO_DATE', coupon.couponEndDate, '%d-%m-%Y') " +
            "AND coupon.couponStatus = 1 AND coupon.isDeleted = 1 AND coupon.couponType=1")
    List<CouponCode> findAllFixedAmtCouponCode(@Param("userDate") LocalDate userDate);

    @Query("SELECT coupon FROM CouponCode coupon " +
            "WHERE :userDate BETWEEN FUNCTION('STR_TO_DATE', coupon.couponStartDate, '%Y-%m-%d') " +
            "AND FUNCTION('STR_TO_DATE', coupon.couponEndDate, '%d-%m-%Y') " +
            "AND coupon.couponStatus = 1 AND coupon.isDeleted = 1 AND coupon.couponType = 2")
    List<CouponCode> findAllPercentageCouponCode(@Param("userDate") LocalDate userDate);

    @Query("SELECT coupon FROM CouponCode coupon WHERE coupon.couponId = :couponId AND coupon.isDeleted=1")
    CouponCode findCouponById(int couponId);

    @Query("SELECT coupons FROM CouponCode coupons WHERE coupons.isDeleted=1")
    List<CouponCode> findAllCouponCodeForAdmin();

    @Query("SELECT c FROM CouponCode c " +
            "WHERE c.isDeleted = 1 AND (" +
            "LOWER(c.couponCode) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.couponTitle) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "CAST(c.couponCodeFor AS string) LIKE CONCAT('%', :keyword, '%') OR " +
            "CAST(c.couponValue AS string) LIKE CONCAT('%', :keyword, '%') OR " +
            "CAST(c.couponMinimumBillAmount AS string) LIKE CONCAT('%', :keyword, '%') OR " +
            "CAST(c.couponMaxDiscount AS string) LIKE CONCAT('%', :keyword, '%') OR " +
            "CAST(c.couponType AS string) LIKE CONCAT('%', :keyword, '%') OR " +
            "LOWER(c.couponStartDate) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.couponEndDate) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
            ")")
    List<CouponCode> searchCouponCodeByKeyword(@Param("keyword") String keyword);

    @Query("SELECT code.couponCode FROM CouponCode code WHERE code.couponCode= :code AND code.isDeleted=1")
    String findCouponCodeByCode(@Param("code") String code);

    @Query("SELECT code FROM CouponCode code WHERE code.couponId= :id AND code.isDeleted=1")
    CouponCode findCouponCodeById(int id);
}
