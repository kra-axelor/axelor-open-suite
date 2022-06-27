package com.axelor.apps.businessproject.service.invoice.line.ngenerator;

import com.axelor.apps.account.db.Invoice;
import com.axelor.apps.account.db.InvoiceLine;
import com.axelor.apps.project.db.Project;
import com.axelor.apps.supplychain.service.invoice.line.ngenerator.InvoiceLineAccountSupplyChainBuilder;
import com.axelor.exception.AxelorException;

public class InvoiceLineAccountBusinessProjectBuilder extends InvoiceLineAccountSupplyChainBuilder {

  private Project project = null;

  public InvoiceLineAccountBusinessProjectBuilder(Invoice invoice) {
    super(invoice);
  }

  public InvoiceLineAccountBusinessProjectBuilder setProject(Project project) {
    this.project = project;
    return this;
  }

  @Override
  public InvoiceLine build() throws AxelorException {
    InvoiceLine invoiceLine = super.build();

    invoiceLine.setProject(project);
    return invoiceLine;
  }
}
