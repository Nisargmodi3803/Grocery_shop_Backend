package com.example.grocery_shop_backend.Service;

import com.example.grocery_shop_backend.Dto.AddProductOrderDTO;
import com.example.grocery_shop_backend.Dto.ProductOrderListDTO;
import com.example.grocery_shop_backend.Entities.*;
import com.example.grocery_shop_backend.Exception.objectNotFoundException;
import com.example.grocery_shop_backend.Repository.InvoiceDetailRepository;
import com.example.grocery_shop_backend.Repository.InvoiceRepository;
import com.example.grocery_shop_backend.Repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class InvoiceDetailService
{
    @Autowired
    private InvoiceDetailRepository invoiceDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    // Find Product Order List by Invoice Num Service
    public List<InvoiceDetail> findProductOrderByInvoiceNum(int invoiceId)
    {
        List<InvoiceDetail> invoiceDetails = invoiceDetailRepository.findByInvoiceId(invoiceId);
        if(invoiceDetails.isEmpty())
            throw new objectNotFoundException("InvoiceDetail Not Found");
        return invoiceDetails;
    }

   // Insert Product Order Service
   @Transactional
   public void addProductOrder(Cart[] carts,int orderNum,double totalPayable) {
       Invoice invoice = invoiceRepository.findByInvoiceNum(orderNum);

       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
       LocalDateTime now = LocalDateTime.now();
       String cDate = now.format(formatter);

       if (invoice != null) {
           for (Cart cart : carts) {
               Product product = productRepository.findProductById(cart.getProduct().getId());
               if (product != null) {
                   InvoiceDetail invoiceDetail = new InvoiceDetail();

                   invoiceDetail.setProduct(product);
                   invoiceDetail.setInvoice(invoice);
                   invoiceDetail.setProductName(product.getName());
                   invoiceDetail.setProductVariantName(product.getVariantName());
                   invoiceDetail.setBasePrice(product.getBasePrice());
                   invoiceDetail.setMrp(product.getMrp());
                   invoiceDetail.setQuantity(cart.getProductQuantity());
                   invoiceDetail.setTotalAmount(product.getDiscount_amt());

                   invoiceDetail.setTotalPayable(totalPayable);

                   invoiceDetail.setIsDeleted(1);
                   invoiceDetail.setcDate(cDate);

                   invoiceDetailRepository.save(invoiceDetail);
               } else {
                   throw new objectNotFoundException("Product Not Found with id: " + cart.getProduct().getId());
               }
           }
       } else {
           throw new objectNotFoundException("No Invoice found for order BI - " + orderNum);
       }
   }

   // Delete Products here after canceling the order
   @Transactional
    public void deleteProductOrder(int orderNum) {

   }

    // Search Customer Service
    public List<InvoiceDetail> searchInvoice(String searchText) {
        List<InvoiceDetail> invoiceDetails = invoiceDetailRepository.searchInvoiceByKeyword(searchText);
        if(invoiceDetails.isEmpty()) {
            throw new objectNotFoundException("Customer list is empty");
        }
        return invoiceDetails;
    }

}
