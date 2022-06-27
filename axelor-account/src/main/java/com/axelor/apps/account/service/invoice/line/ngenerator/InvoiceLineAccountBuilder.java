package com.axelor.apps.account.service.invoice.line.ngenerator;

import com.axelor.apps.account.db.Account;
import com.axelor.apps.account.db.Invoice;
import com.axelor.apps.account.db.InvoiceLine;
import com.axelor.apps.account.db.TaxEquiv;
import com.axelor.apps.account.db.TaxLine;
import com.axelor.apps.account.db.repo.InvoiceLineRepository;
import com.axelor.apps.account.exception.IExceptionMessage;
import com.axelor.apps.account.service.AccountManagementAccountService;
import com.axelor.apps.account.service.app.AppAccountService;
import com.axelor.apps.account.service.invoice.InvoiceToolService;
import com.axelor.apps.account.service.invoice.generator.InvoiceLineGenerator;
import com.axelor.apps.account.service.invoice.line.InvoiceLineToolService;
import com.axelor.apps.base.db.Company;
import com.axelor.apps.base.db.Currency;
import com.axelor.apps.base.db.Product;
import com.axelor.apps.base.db.Unit;
import com.axelor.apps.base.db.repo.PriceListLineRepository;
import com.axelor.apps.base.service.CurrencyService;
import com.axelor.apps.base.service.ProductCompanyService;
import com.axelor.apps.base.service.app.AppBaseService;
import com.axelor.exception.AxelorException;
import com.axelor.exception.db.repo.TraceBackRepository;
import com.axelor.i18n.I18n;
import com.google.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class InvoiceLineAccountBuilder implements InvoiceLineBuilder {

  private Invoice invoice = null;
  private Product product = null;
  private String productName = null;
  private BigDecimal price = null;
  private BigDecimal inTaxPrice = null;
  private BigDecimal priceDiscounted = null;
  private String description = null;
  private BigDecimal qty = null;
  private Unit unit = null;
  private TaxLine taxLine = null;
  private int sequence = InvoiceLineGenerator.DEFAULT_SEQUENCE;
  private BigDecimal discountAmount = null;
  private int discountTypeSelect = PriceListLineRepository.AMOUNT_TYPE_NONE;
  private BigDecimal exTaxTotal = null;
  private BigDecimal inTaxTotal = null;
  private BigDecimal companyExTaxTotal = null;
  private BigDecimal companyInTaxTotal = null;
  private String productCode = null;
  private Account account = null;
  private TaxEquiv taxEquiv = null;
  private BigDecimal taxRate = BigDecimal.ZERO;
  private String taxCode = null;
  private int typeSelect = 0;
  private LocalDate date;

  @Inject protected ProductCompanyService productCompanyService;
  @Inject protected AccountManagementAccountService accountManagementService;
  @Inject protected AppAccountService appAccountService;
  @Inject protected CurrencyService currencyService;

  public InvoiceLineAccountBuilder(Invoice invoice) {
    this.invoice = invoice;
    this.date = appAccountService.getTodayDate(invoice.getCompany());
  }

  public InvoiceLineAccountBuilder setDate(LocalDate date) {
    this.date = date;
    return this;
  }

  public InvoiceLineAccountBuilder setProduct(Product product) {
    this.product = product;
    return this;
  }

  public InvoiceLineAccountBuilder setProductName(String productName) {
    this.productName = productName;
    return this;
  }

  public InvoiceLineAccountBuilder setPrice(BigDecimal price) {
    this.price = price;
    return this;
  }

  public InvoiceLineAccountBuilder setInTaxPrice(BigDecimal inTaxPrice) {
    this.inTaxPrice = inTaxPrice;
    return this;
  }

  public InvoiceLineAccountBuilder setPriceDiscounted(BigDecimal priceDiscounted) {
    this.priceDiscounted = priceDiscounted;
    return this;
  }

  public InvoiceLineAccountBuilder setDescription(String description) {
    this.description = description;
    return this;
  }

  public InvoiceLineAccountBuilder setQty(BigDecimal qty) {
    this.qty = qty;
    return this;
  }

  public InvoiceLineAccountBuilder setUnit(Unit unit) {
    this.unit = unit;
    return this;
  }

  public InvoiceLineAccountBuilder setTaxLine(TaxLine taxLine) {
    this.taxLine = taxLine;
    return null;
  }

  public InvoiceLineAccountBuilder setSequence(int sequence) {
    this.sequence = sequence;
    return this;
  }

  public InvoiceLineAccountBuilder setDiscountAmount(BigDecimal discountAmount) {
    this.discountAmount = discountAmount;
    return this;
  }

  public InvoiceLineAccountBuilder setDiscountTypeSelect(int discountTypeSelect) {
    this.discountTypeSelect = discountTypeSelect;
    return this;
  }

  public InvoiceLineAccountBuilder setExTaxTotal(BigDecimal exTaxTotal) {
    this.exTaxTotal = exTaxTotal;
    return this;
  }

  public InvoiceLineAccountBuilder setInTaxTotal(BigDecimal inTaxTotal) {
    this.inTaxTotal = inTaxTotal;
    return this;
  }

  public InvoiceLineAccountBuilder setCompanyExTaxTotal(BigDecimal companyExTaxTotal) {
    this.companyExTaxTotal = companyExTaxTotal;
    return this;
  }

  public InvoiceLineAccountBuilder setCompanyInTaxTotal(BigDecimal companyInTaxTotal) {
    this.companyInTaxTotal = companyInTaxTotal;
    return this;
  }

  public InvoiceLineAccountBuilder setProductCode(String productCode) {
    this.productCode = productCode;
    return this;
  }

  public InvoiceLineAccountBuilder setAccount(Account account) {
    this.account = account;
    return this;
  }

  public InvoiceLineAccountBuilder setTaxEquiv(TaxEquiv taxEquiv) {
    this.taxEquiv = taxEquiv;
    return this;
  }

  public InvoiceLineAccountBuilder setTypeSelect(int typeSelect) {
    this.typeSelect = typeSelect;
    return this;
  }

  public InvoiceLine build() throws AxelorException {

    completeBuilder();
    InvoiceLine invoiceLine = new InvoiceLine();

    invoiceLine.setInvoice(invoice);
    invoiceLine.setProduct(product);
    invoiceLine.setProductName(productName);
    invoiceLine.setProductCode(productCode);
    invoiceLine.setAccount(account);
    invoiceLine.setDescription(description);
    invoiceLine.setPrice(price);
    invoiceLine.setInTaxPrice(inTaxPrice);
    invoiceLine.setPriceDiscounted(priceDiscounted);
    invoiceLine.setQty(qty);
    invoiceLine.setUnit(unit);
    invoiceLine.setTypeSelect(typeSelect);
    invoiceLine.setTaxEquiv(taxEquiv);
    invoiceLine.setTaxLine(taxLine);
    invoiceLine.setTaxRate(taxRate);
    invoiceLine.setTaxCode(taxCode);
    invoiceLine.setExTaxTotal(exTaxTotal);
    invoiceLine.setInTaxTotal(inTaxTotal);
    invoiceLine.setCompanyExTaxTotal(companyExTaxTotal);
    invoiceLine.setCompanyInTaxTotal(companyInTaxTotal);
    invoiceLine.setSequence(sequence);
    invoiceLine.setDiscountTypeSelect(discountTypeSelect);
    invoiceLine.setDiscountAmount(discountAmount);

    return invoiceLine;
  }

  protected void completeBuilder() throws AxelorException {

    boolean isPurchase = InvoiceToolService.isPurchase(invoice);

    Company company = invoice.getCompany();

    if (product != null) {
      if (productCode == null) {
        productCode = (String) productCompanyService.get(product, "code", company);
      }
      if (account == null) {
        account =
            accountManagementService.getProductAccount(
                product, company, invoice.getFiscalPosition(), isPurchase, false);
      }
      if (taxEquiv == null) {
        taxEquiv =
            accountManagementService.getProductTaxEquiv(
                product, company, invoice.getFiscalPosition(), isPurchase);
      }
      if (taxLine == null) {
        taxLine =
            accountManagementService.getTaxLine(
                date,
                product,
                invoice.getCompany(),
                invoice.getFiscalPosition(),
                InvoiceToolService.isPurchase(invoice));
      }
    }
    if (taxLine != null) {
      taxRate = taxLine.getValue();
      taxCode = taxLine.getTax().getCode();
    }

    completeTotals(company);
  }

  protected void completeTotals(Company company) throws AxelorException {
    if (typeSelect != InvoiceLineRepository.TYPE_TITLE) {
      if (!invoice.getInAti() && (exTaxTotal == null || inTaxTotal == null)) {
        exTaxTotal = InvoiceLineToolService.computeAmount(this.qty, this.priceDiscounted, 2);
        inTaxTotal = exTaxTotal.add(exTaxTotal.multiply(taxRate)).setScale(2, RoundingMode.HALF_UP);
      } else {
        inTaxTotal = InvoiceLineToolService.computeAmount(this.qty, this.priceDiscounted, 2);
        exTaxTotal = inTaxTotal.divide(taxRate.add(BigDecimal.ONE), 2, BigDecimal.ROUND_HALF_UP);
      }
      Currency companyCurrency = getCurrency(company);

      companyExTaxTotal =
          currencyService
              .getAmountCurrencyConvertedAtDate(
                  invoice.getCurrency(), companyCurrency, exTaxTotal, date)
              .setScale(AppBaseService.DEFAULT_NB_DECIMAL_DIGITS, RoundingMode.HALF_UP);

      companyInTaxTotal =
          currencyService
              .getAmountCurrencyConvertedAtDate(
                  invoice.getCurrency(), companyCurrency, inTaxTotal, date)
              .setScale(AppBaseService.DEFAULT_NB_DECIMAL_DIGITS, RoundingMode.HALF_UP);
    }
  }

  protected Currency getCurrency(Company company) throws AxelorException {
    Currency companyCurrency = company.getCurrency();

    if (companyCurrency == null) {
      throw new AxelorException(
          TraceBackRepository.CATEGORY_CONFIGURATION_ERROR,
          I18n.get(IExceptionMessage.INVOICE_LINE_GENERATOR_2),
          company.getName());
    }
    return companyCurrency;
  }
}
