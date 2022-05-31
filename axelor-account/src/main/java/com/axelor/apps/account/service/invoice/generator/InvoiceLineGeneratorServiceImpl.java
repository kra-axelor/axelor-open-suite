package com.axelor.apps.account.service.invoice.generator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import com.axelor.apps.account.db.Account;
import com.axelor.apps.account.db.FiscalPosition;
import com.axelor.apps.account.db.Invoice;
import com.axelor.apps.account.db.InvoiceLine;
import com.axelor.apps.account.db.TaxEquiv;
import com.axelor.apps.account.db.TaxLine;
import com.axelor.apps.account.db.repo.InvoiceLineRepository;
import com.axelor.apps.account.exception.IExceptionMessage;
import com.axelor.apps.account.service.AccountManagementAccountService;
import com.axelor.apps.account.service.app.AppAccountService;
import com.axelor.apps.account.service.invoice.InvoiceToolService;
import com.axelor.apps.base.db.Company;
import com.axelor.apps.base.db.Currency;
import com.axelor.apps.base.db.Partner;
import com.axelor.apps.base.db.Product;
import com.axelor.apps.base.db.Unit;
import com.axelor.apps.base.service.CurrencyService;
import com.axelor.apps.base.service.ProductCompanyService;
import com.axelor.apps.base.service.app.AppBaseService;
import com.axelor.apps.base.service.tax.AccountManagementService;
import com.axelor.exception.AxelorException;
import com.axelor.exception.db.repo.TraceBackRepository;
import com.axelor.i18n.I18n;
import com.axelor.inject.Beans;
import com.google.inject.Inject;

public class InvoiceLineGeneratorServiceImpl implements InvoiceLineGeneratorService {
	
	protected AccountManagementAccountService accountManagementService;
	protected ProductCompanyService productCompanyService;
	protected AppAccountService appAccountService;
	protected CurrencyService currencyService;
	protected AppBaseService appBaseService;
	protected Integer typeSelect = 0;
	
	
	@Inject
	public InvoiceLineGeneratorServiceImpl(AccountManagementAccountService accountManagementService, ProductCompanyService productCompanyService, AppAccountService appAccountService,
			CurrencyService currencyService, AppBaseService appBaseService) {
		this.accountManagementService = accountManagementService;
		this.productCompanyService = productCompanyService;
		this.appAccountService = appAccountService;
		this.currencyService = currencyService;
		this.appBaseService = appBaseService;
	}

	@Override
	public InvoiceLine creates(Invoice invoice, Product product, String productName, BigDecimal price,
			BigDecimal inTaxPrice, BigDecimal priceDiscounted, String description, BigDecimal qty, Unit unit,
			TaxLine taxLine, int sequence, BigDecimal discountAmount, int discountTypeSelect, BigDecimal exTaxTotal,
			BigDecimal inTaxTotal, boolean isTaxInvoice) throws AxelorException {
	    InvoiceLine invoiceLine = new InvoiceLine();
	    boolean isPurchase = InvoiceToolService.isPurchase(invoice);
	    Partner partner = invoice.getPartner();
	    Company company = invoice.getCompany();
	    qty.setScale(appBaseService.getNbDecimalDigitForQty(), RoundingMode.HALF_UP);
	    invoiceLine.setInvoice(invoice);

	    invoiceLine.setProduct(product);

	    invoiceLine.setProductName(productName);
	    if (product != null) {
	      invoiceLine.setProductCode((String) productCompanyService.get(product, "code", company));
	      Account account =
	          accountManagementService.getProductAccount(
	              product,
	              company,
	              invoice.getFiscalPosition(),
	              isPurchase,
	              invoiceLine.getFixedAssets());
	      invoiceLine.setAccount(account);
	    }

	    invoiceLine.setDescription(description);
	    invoiceLine.setPrice(price);
	    invoiceLine.setInTaxPrice(inTaxPrice);

	    invoiceLine.setPriceDiscounted(priceDiscounted);
	    invoiceLine.setQty(qty);
	    invoiceLine.setUnit(unit);

	    invoiceLine.setTypeSelect(typeSelect);
	    LocalDate todayDate = appAccountService.getTodayDate(invoice.getCompany());
	    if (taxLine == null) {
	      taxLine = this.determineTaxLine(product, invoice, todayDate);
	    }

	    if (product != null) {
	      TaxEquiv taxEquiv =
	          Beans.get(AccountManagementService.class)
	              .getProductTaxEquiv(product, company, invoice.getFiscalPosition(), isPurchase);

	      invoiceLine.setTaxEquiv(taxEquiv);
	    }

	    invoiceLine.setTaxLine(taxLine);

	    if (taxLine != null) {
	      invoiceLine.setTaxRate(taxLine.getValue());
	      invoiceLine.setTaxCode(taxLine.getTax().getCode());
	    }

	    if ((exTaxTotal == null || inTaxTotal == null)) {
	      this.computeTotal(invoice, invoiceLine, taxLine, qty, exTaxTotal, inTaxTotal, priceDiscounted);
	    }

	    invoiceLine.setExTaxTotal(exTaxTotal);
	    invoiceLine.setInTaxTotal(inTaxTotal);

	    this.computeCompanyTotal(invoice, invoiceLine, todayDate);

	    invoiceLine.setSequence(sequence);

	    invoiceLine.setDiscountTypeSelect(discountTypeSelect);
	    invoiceLine.setDiscountAmount(discountAmount);

	    return invoiceLine;
	}
	
	  public TaxLine determineTaxLine(Product product, Invoice invoice, LocalDate today) throws AxelorException {

		    if (product != null) {

		      Company company = invoice.getCompany();
		      Partner partner = invoice.getPartner();
		      FiscalPosition fiscalPosition = invoice.getFiscalPosition();

		      TaxLine taxLine =
		          accountManagementService.getTaxLine(
		              today, product, company, fiscalPosition, InvoiceToolService.isPurchase(invoice));
		      return taxLine;
		    }
		    
		    return null;
		  }
	  
	  public void computeTotal(Invoice invoice, InvoiceLine invoiceLine, TaxLine taxLine, BigDecimal qty, BigDecimal exTaxTotal, BigDecimal inTaxTotal,  BigDecimal priceDiscounted) {
		  
		    BigDecimal exTaxTot = exTaxTotal;
		    BigDecimal inTaxTot = inTaxTotal;
		    if (typeSelect == InvoiceLineRepository.TYPE_TITLE) {
		      return;
		    }

		    BigDecimal taxRate = BigDecimal.ZERO;
		    if (taxLine != null) {
		      taxRate = taxLine.getValue();
		    }

		    if (!invoice.getInAti()) {
		      
		      exTaxTotal = InvoiceLineToolAmountService.computeAmount(qty, priceDiscounted, 2);
		      inTaxTotal = exTaxTotal.add(exTaxTotal.multiply(taxRate)).setScale(2, RoundingMode.HALF_UP);
		    } else {
		      inTaxTotal = InvoiceLineToolAmountService.computeAmount(qty, priceDiscounted, 2);
		      exTaxTotal = inTaxTotal.divide(taxRate.add(BigDecimal.ONE), 2, BigDecimal.ROUND_HALF_UP);
		    }
		    invoiceLine.setExTaxTotal(exTaxTot);
		    invoiceLine.setInTaxTotal(inTaxTot);
	 }
	  
	  public void computeCompanyTotal(Invoice invoice, InvoiceLine invoiceLine, LocalDate date) throws AxelorException {

		    BigDecimal exTaxTotal = invoiceLine.getExTaxTotal();
		    BigDecimal inTaxTotal = invoiceLine.getInTaxTotal();
		    if (typeSelect == InvoiceLineRepository.TYPE_TITLE) {
		      return;
		    }

		    Company company = invoice.getCompany();

		    Currency companyCurrency = company.getCurrency();

		    if (companyCurrency == null) {
		      throw new AxelorException(
		          TraceBackRepository.CATEGORY_CONFIGURATION_ERROR,
		          I18n.get(IExceptionMessage.INVOICE_LINE_GENERATOR_2),
		          company.getName());
		    }

		    invoiceLine.setCompanyExTaxTotal(
		        currencyService
		            .getAmountCurrencyConvertedAtDate(
		                invoice.getCurrency(), companyCurrency, exTaxTotal, date)
		            .setScale(AppBaseService.DEFAULT_NB_DECIMAL_DIGITS, RoundingMode.HALF_UP));

		    invoiceLine.setCompanyInTaxTotal(
		        currencyService
		            .getAmountCurrencyConvertedAtDate(
		                invoice.getCurrency(), companyCurrency, inTaxTotal, date)
		            .setScale(AppBaseService.DEFAULT_NB_DECIMAL_DIGITS, RoundingMode.HALF_UP));
		  }

}
