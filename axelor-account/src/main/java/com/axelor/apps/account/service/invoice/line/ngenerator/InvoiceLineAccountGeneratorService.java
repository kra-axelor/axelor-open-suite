package com.axelor.apps.account.service.invoice.line.ngenerator;

import com.axelor.apps.account.db.Invoice;
import com.axelor.apps.account.db.InvoiceLine;
import com.axelor.apps.account.db.TaxLine;
import com.axelor.apps.base.db.Product;
import com.axelor.apps.base.db.Unit;
import com.axelor.exception.AxelorException;
import java.math.BigDecimal;

public interface InvoiceLineAccountGeneratorService {
  /**
   * Method that creates the invoiceLine
   *
   * @param invoice
   * @param product
   * @param productName
   * @param price
   * @param inTaxPrice
   * @param priceDiscounted
   * @param description
   * @param qty
   * @param unit
   * @param taxLine
   * @param sequence
   * @param discountAmount
   * @param discountTypeSelect
   * @param exTaxTotal
   * @param inTaxTotal
   * @param isTaxInvoice
   * @return
   * @throws AxelorException
   */
  InvoiceLine create(
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
      throws AxelorException;

  /**
   * This method will only call InvoiceLineBuilder.build method. <br>
   * The purpose of this method is to be able to add new parameters in the invoiceLine without
   * creating a new service,especially if this new service would only be needed for the new
   * parameters and nothing else.
   *
   * @param builder
   * @return invoiceLine
   * @throws AxelorException
   */
  InvoiceLine create(InvoiceLineBuilder builder) throws AxelorException;
}
