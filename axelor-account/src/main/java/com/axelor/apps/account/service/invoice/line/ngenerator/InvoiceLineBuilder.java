package com.axelor.apps.account.service.invoice.line.ngenerator;

import com.axelor.apps.account.db.InvoiceLine;
import com.axelor.exception.AxelorException;

public interface InvoiceLineBuilder {

  /**
   * Build the invoiceLine
   *
   * @return invoice line created
   */
  InvoiceLine build() throws AxelorException;
}
