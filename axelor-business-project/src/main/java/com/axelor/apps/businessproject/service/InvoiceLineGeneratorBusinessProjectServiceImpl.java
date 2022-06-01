package com.axelor.apps.businessproject.service;

import java.math.BigDecimal;

import com.axelor.apps.account.db.Invoice;
import com.axelor.apps.account.db.InvoiceLine;
import com.axelor.apps.account.db.TaxLine;
import com.axelor.apps.account.service.AccountManagementAccountService;
import com.axelor.apps.account.service.app.AppAccountService;
import com.axelor.apps.account.service.invoice.generator.InvoiceLineGeneratorServiceImpl;
import com.axelor.apps.base.db.Product;
import com.axelor.apps.base.db.Unit;
import com.axelor.apps.base.service.CurrencyService;
import com.axelor.apps.base.service.ProductCompanyService;
import com.axelor.apps.base.service.app.AppBaseService;
import com.axelor.apps.project.db.Project;
import com.axelor.apps.sale.db.SaleOrderLine;
import com.axelor.exception.AxelorException;
import com.google.inject.Inject;

public class InvoiceLineGeneratorBusinessProjectServiceImpl extends InvoiceLineGeneratorServiceImpl {
	
	@Inject
	public InvoiceLineGeneratorBusinessProjectServiceImpl(AccountManagementAccountService accountManagementService,
			ProductCompanyService productCompanyService, AppAccountService appAccountService,
			CurrencyService currencyService, AppBaseService appBaseService) {
		super(accountManagementService, productCompanyService, appAccountService, currencyService, appBaseService);
	}
	
	public InvoiceLine creates(Invoice invoice, Product product, String productName, BigDecimal price,
			BigDecimal inTaxPrice, BigDecimal priceDiscounted, String description, BigDecimal qty, Unit unit,
			TaxLine taxLine, int sequence, BigDecimal discountAmount, int discountTypeSelect, BigDecimal exTaxTotal,
			BigDecimal inTaxTotal, boolean isTaxInvoice, Project project, SaleOrderLine saleOrderLine) throws AxelorException {
		
		InvoiceLine invoiceLine = creates(invoice, product, productName, price, inTaxPrice, priceDiscounted, description, qty, unit, taxLine, sequence, discountAmount, discountTypeSelect, exTaxTotal, inTaxTotal, isTaxInvoice);
		invoiceLine.setProject(project);
		invoiceLine.setSaleOrderLine(saleOrderLine);
		
		return invoiceLine;
	}
	
	
	

}
