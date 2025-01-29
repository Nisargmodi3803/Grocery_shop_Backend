package com.example.grocery_shop_backend.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_product")
public class Product
{
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //Product Category ID
    //Many to One Mapping with Category Table
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_cat_id")
    private Category cat;

    //Product Sub-Category ID
    //Many to One Mapping with Sub-Category Table
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_subcat_id")
    private SubCategory subcat;

    //Product Brand ID
    //Many to One Mapping with Brand Table
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_brand_id")
    private Brand brand;

    @Column(name = "product_name")
    private String name;

    @Column(name = "product_variant_name")
    private String variantName;

    @Column(name = "product_description")
    private String description;

    @Column(name = "product_description_long")
    private String long_description;

    @Column(name = "product_image")
    private String image_url;

    @Column(name = "product_is_inclusive_tax")
    private int isInclusiveTax;  //1= yes, 2=no

    @Column(name = "product_base_price")
    private double basePrice;

    @Column(name = "product_cgst")
    private double cgst;

    @Column(name = "product_sgst")
    private double sgst;

    @Column(name = "product_igst")
    private double igst;

    @Column(name = "product_mrp")
    private double mrp;

    @Column(name = "product_discount_amt")
    private double discount_amt;

    @Column(name = "product_wholesaler_amt")
    private double wholesaler_amt;

    @Column(name = "slug_title")
    private String slug_title;

    @Column(name = "product_is_main")
    private int is_main; // 1= yes(Show in list) and 2= no

    @Column(name = "product_no_of_rating")
    private String no_of_rating;

    @Column(name = "product_average_rating")
    private String average_rating;

    @Column(name = "is_deleted")
    private int is_deleted;

    @Column(name = "c_date")
    private String c_date;

    public Product(){}

    public Product(int id, Category cat, SubCategory subcat, Brand brand, String name, String variantName, String description, String long_description, String image_url, int isInclusiveTax, double price, double cgst, double sgst, double igst, double mrp, double discount_amt, double wholesaler_amt, String slug_title, int is_main, String no_of_rating, String average_rating, int is_deleted, String c_date) {
        this.id = id;
        this.cat = cat;
        this.subcat = subcat;
        this.brand = brand;
        this.name = name;
        this.variantName = variantName;
        this.description = description;
        this.long_description = long_description;
        this.image_url = image_url;
        this.isInclusiveTax = isInclusiveTax;
        this.basePrice = price;
        this.cgst = cgst;
        this.sgst = sgst;
        this.igst = igst;
        this.mrp = mrp;
        this.discount_amt = discount_amt;
        this.wholesaler_amt = wholesaler_amt;
        this.slug_title = slug_title;
        this.is_main = is_main;
        this.no_of_rating = no_of_rating;
        this.average_rating = average_rating;
        this.is_deleted = is_deleted;
        this.c_date = c_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Category getCat() {
        return cat;
    }

    public void setCat(Category cat) {
        this.cat = cat;
    }

    public SubCategory getSubcat() {
        return subcat;
    }

    public void setSubcat(SubCategory subcat) {
        this.subcat = subcat;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVariantName() {
        return variantName;
    }

    public void setVariantName(String variantName) {
        this.variantName = variantName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLong_description() {
        return long_description;
    }

    public void setLong_description(String long_description) {
        this.long_description = long_description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getIsInclusiveTax() {
        return isInclusiveTax;
    }

    public void setIsInclusiveTax(int isInclusiveTax) {
        this.isInclusiveTax = isInclusiveTax;
    }

    public double getPrice() {
        return basePrice;
    }

    public void setPrice(double price) {
        this.basePrice = price;
    }

    public double getCgst() {
        return cgst;
    }

    public void setCgst(double cgst) {
        this.cgst = cgst;
    }

    public double getSgst() {
        return sgst;
    }

    public void setSgst(double sgst) {
        this.sgst = sgst;
    }

    public double getIgst() {
        return igst;
    }

    public void setIgst(double igst) {
        this.igst = igst;
    }

    public double getMrp() {
        return mrp;
    }

    public void setMrp(double mrp) {
        this.mrp = mrp;
    }

    public double getDiscount_amt() {
        return discount_amt;
    }

    public void setDiscount_amt(double discount_amt) {
        this.discount_amt = discount_amt;
    }

    public double getWholesaler_amt() {
        return wholesaler_amt;
    }

    public void setWholesaler_amt(double wholesaler_amt) {
        this.wholesaler_amt = wholesaler_amt;
    }

    public String getSlug_title() {
        return slug_title;
    }

    public void setSlug_title(String slug_title) {
        this.slug_title = slug_title;
    }

    public int getIs_main() {
        return is_main;
    }

    public void setIs_main(int is_main) {
        this.is_main = is_main;
    }

    public String getNo_of_rating() {
        return no_of_rating;
    }

    public void setNo_of_rating(String no_of_rating) {
        this.no_of_rating = no_of_rating;
    }

    public String getAverage_rating() {
        return average_rating;
    }

    public void setAverage_rating(String average_rating) {
        this.average_rating = average_rating;
    }

    public int getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(int is_deleted) {
        this.is_deleted = is_deleted;
    }

    public String getC_date() {
        return c_date;
    }

    public void setC_date(String c_date) {
        this.c_date = c_date;
    }
}
