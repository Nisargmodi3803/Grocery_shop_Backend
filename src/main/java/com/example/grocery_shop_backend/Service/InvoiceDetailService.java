package com.example.grocery_shop_backend.Service;

import com.example.grocery_shop_backend.Dto.AddProductOrderDTO;
import com.example.grocery_shop_backend.Dto.ProductOrderListDTO;
import com.example.grocery_shop_backend.Entities.Invoice;
import com.example.grocery_shop_backend.Entities.InvoiceDetail;
import com.example.grocery_shop_backend.Entities.Product;
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
    public List<ProductOrderListDTO> findProductOrderByInvoiceNum(int invoiceId)
    {
        List<ProductOrderListDTO> invoiceDetails = invoiceDetailRepository.findByInvoiceId(invoiceId);
        if(invoiceDetails.isEmpty())
            throw new objectNotFoundException("InvoiceDetail Not Found");
        return invoiceDetails;
    }

   // Insert Product Order Service
   @Transactional
   public void addProductOrder(AddProductOrderDTO addProductOrderDTO) {
       Invoice invoice = invoiceRepository.findByInvoiceNum(addProductOrderDTO.getOrderNum());

       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
       LocalDateTime now = LocalDateTime.now();
       String cDate = now.format(formatter);

       if (invoice != null) {
           int[] productIds = addProductOrderDTO.getProductId();
           for (int productId : productIds) {
               Product product = productRepository.findProductById(productId);
               if (product != null) {
                   InvoiceDetail invoiceDetail = new InvoiceDetail();

                   invoiceDetail.setProduct(product);
                   invoiceDetail.setInvoice(invoice);
                   invoiceDetail.setProductName(product.getName());
                   invoiceDetail.setProductVariantName(product.getVariantName());
                   invoiceDetail.setBasePrice(product.getBasePrice());
                   invoiceDetail.setMrp(product.getMrp());
                   invoiceDetail.setQuantity(addProductOrderDTO.getQuantity());
                   invoiceDetail.setTotalAmount(product.getDiscount_amt());

                   double totalPayable = product.getBasePrice() * addProductOrderDTO.getQuantity();
                   if (totalPayable >= 500)
                       invoiceDetail.setTotalPayable(addProductOrderDTO.getTotalPayable());
                   else
                       invoiceDetail.setTotalPayable(totalPayable + 15.00);

                   invoiceDetail.setIsDeleted(1);
                   invoiceDetail.setcDate(cDate);

                   invoiceDetailRepository.save(invoiceDetail);
               } else {
                   throw new objectNotFoundException("Product Not Found with id: " + productId);
               }
           }
       } else {
           throw new objectNotFoundException("No Invoice found for order BI - " + addProductOrderDTO.getOrderNum());
       }
   }

}
