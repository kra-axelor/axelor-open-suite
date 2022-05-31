package com.axelor.apps.account.service.invoice.generator;

import java.math.BigDecimal;

import com.axelor.apps.account.db.Invoice;
import com.axelor.apps.account.db.InvoiceLine;
import com.axelor.apps.account.db.TaxLine;
import com.axelor.apps.base.db.Product;
import com.axelor.apps.base.db.Unit;
import com.axelor.exception.AxelorException;

public interface InvoiceLineGeneratorService {

	  public InvoiceLine creates(Invoice invoice,
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
		      boolean isTaxInvoice) throws AxelorException;
	
}
