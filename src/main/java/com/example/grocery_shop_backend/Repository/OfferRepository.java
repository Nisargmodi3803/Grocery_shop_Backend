package com.example.grocery_shop_backend.Repository;

import com.example.grocery_shop_backend.Entities.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Integer>
{
    @Query("SELECT offer FROM Offer offer " +
            "WHERE :userDate BETWEEN FUNCTION('STR_TO_DATE', offer.offerStartDate, '%d-%m-%Y') " +
            "AND FUNCTION('STR_TO_DATE', offer.offerEndDate, '%d-%m-%Y') " +
            "AND offer.createBannerFor=1 AND offer.isDeleted=1")
    List<Offer> findAllOffersByBrand(@Param("userDate") LocalDate userDate);

    @Query("SELECT offer FROM Offer offer " +
            "WHERE :userDate BETWEEN FUNCTION('STR_TO_DATE', offer.offerStartDate, '%d-%m-%Y') " +
            "AND FUNCTION('STR_TO_DATE', offer.offerEndDate, '%d-%m-%Y') " +
            "AND offer.createBannerFor=2 AND offer.isDeleted=1")
    List<Offer> findAllOffersByCategory(@Param("userDate") LocalDate userDate);

    @Query("SELECT offer FROM Offer offer " +
            "WHERE :userDate BETWEEN FUNCTION('STR_TO_DATE', offer.offerStartDate, '%d-%m-%Y') " +
            "AND FUNCTION('STR_TO_DATE', offer.offerEndDate, '%d-%m-%Y') " +
            "AND offer.createBannerFor=3 AND offer.isDeleted=1")
    List<Offer> findAllOffersBySubCategory(@Param("userDate") LocalDate userDate);

    @Query("SELECT offer FROM Offer offer " +
            "WHERE :userDate BETWEEN FUNCTION('STR_TO_DATE', offer.offerStartDate, '%d-%m-%Y') " +
            "AND FUNCTION('STR_TO_DATE', offer.offerEndDate, '%d-%m-%Y') " +
            "AND offer.createBannerFor=4 AND offer.isDeleted=1")
    List<Offer> findAllOffersByProducts(@Param("userDate") LocalDate userDate);

    @Query("SELECT offer FROM Offer offer " +
            "WHERE :userDate BETWEEN FUNCTION('STR_TO_DATE', offer.offerStartDate, '%d-%m-%Y') " +
            "AND FUNCTION('STR_TO_DATE', offer.offerEndDate, '%d-%m-%Y') " +
            "AND offer.slugTitle = :slugTitle AND offer.isDeleted=1")
    Offer findOfferBySlugTitle(String slugTitle,@Param("userDate") LocalDate userDate);

    @Query("SELECT offer FROM Offer offer WHERE offer.offerId = :id AND offer.isDeleted=1")
    Offer findOfferById(@Param("id") int id);
}
