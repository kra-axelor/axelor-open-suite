package com.axelor.apps.supplychain.service.invoice.line.ngenerator;

import com.axelor.apps.account.db.Invoice;
import com.axelor.apps.account.db.InvoiceLine;
import com.axelor.apps.account.service.invoice.line.ngenerator.InvoiceLineAccountBuilder;
import com.axelor.apps.purchase.db.PurchaseOrderLine;
import com.axelor.apps.sale.db.SaleOrderLine;
import com.axelor.exception.AxelorException;

public class InvoiceLineAccountSupplyChainBuilder extends InvoiceLineAccountBuilder {

  private PurchaseOrderLine purchaseOrderLine = null;
  private SaleOrderLine saleOrderLine = null;

  public InvoiceLineAccountSupplyChainBuilder(Invoice invoice) {
    super(invoice);
  }

  public InvoiceLineAccountSupplyChainBuilder setSaleOrderLine(SaleOrderLine saleOrderLine) {
    this.saleOrderLine = saleOrderLine;
    return this;
  }

  public InvoiceLineAccountSupplyChainBuilder setPurchaseOrderLine(
      PurchaseOrderLine purchaseOrderLine) {
    this.purchaseOrderLine = purchaseOrderLine;
    return this;
  }

  @Override
  public InvoiceLine build() throws AxelorException {
    InvoiceLine invoiceLine = super.build();

    invoiceLine.setPurchaseOrderLine(purchaseOrderLine);
    invoiceLine.setSaleOrderLine(saleOrderLine);
    return invoiceLine;
  }
}
