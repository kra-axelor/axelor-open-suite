package com.axelor.apps.account.service.invoice.line.ngenerator;

import com.axelor.apps.account.db.Invoice;
import com.axelor.apps.account.db.InvoiceLine;
import com.axelor.apps.account.db.TaxLine;
import com.axelor.apps.account.service.invoice.generator.InvoiceLineGenerator;
import com.axelor.apps.base.db.Product;
import com.axelor.apps.base.db.Unit;
import com.axelor.exception.AxelorException;
import java.math.BigDecimal;
import java.util.Objects;

public class InvoiceLineAccountGeneratorServiceImpl implements InvoiceLineAccountGeneratorService {

  /**
   * {@inheritDoc} </br> This implementation uses InvoiceLineAccountBuilder to create the invoice
   * line. It is also possible to directly use the InvoiceLineAccountBuilder. This method is a
   * remaining (it has the same parameters) of the constructor of this service predecessor {@link
   * InvoiceLineGenerator} and it only exists to be able to create a invoice line in a classic way
   * (calls a method of a injected service)
   */
  @Override
  public InvoiceLine create(
      Invoice invoice,
      Product product,
      String productName,
      BigDecimal price,
      BigDecimal inTaxPrice,
      BigDecimal priceDiscounted,
      String description,
      BigDecimal qty,
      Unit unit,
      TaxLine taxLine,
      int sequence,
      BigDecimal discountAmount,
      int discountTypeSelect,
      BigDecimal exTaxTotal,
      BigDecimal inTaxTotal,
      boolean isTaxInvoice)
      throws AxelorException {

    InvoiceLineAccountBuilder builder = new InvoiceLineAccountBuilder(invoice);

    return builder
        .setProduct(product)
        .setProductName(productName)
        .setPrice(price)
        .setInTaxPrice(inTaxPrice)
        .setPriceDiscounted(priceDiscounted)
        .setDescription(description)
        .setQty(qty)
        .setUnit(unit)
        .setTaxLine(taxLine)
        .setSequence(sequence)
        .setDiscountAmount(discountAmount)
        .setDiscountTypeSelect(discountTypeSelect)
        .setExTaxTotal(exTaxTotal)
        .setInTaxTotal(inTaxTotal)
        .build();
  }

  @Override
  public InvoiceLine create(InvoiceLineBuilder builder) throws AxelorException {
    Objects.requireNonNull(builder);

    return builder.build();
  }
}
