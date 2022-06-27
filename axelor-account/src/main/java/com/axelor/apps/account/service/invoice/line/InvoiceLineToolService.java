package com.axelor.apps.account.service.invoice.line;

import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.axelor.apps.base.service.app.AppBaseService;

public class InvoiceLineToolService {
	
	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	  /**
	   * Compute the quantity per the unit price
	   *
	   * @param quantity
	   * @param price The unit price.
	   * @return The Excluded tax total amount.
	   */
	  public static BigDecimal computeAmount(BigDecimal quantity, BigDecimal price) {

	    BigDecimal amount =
	        quantity
	            .multiply(price)
	            .setScale(AppBaseService.DEFAULT_NB_DECIMAL_DIGITS, RoundingMode.HALF_UP);

	    LOG.debug(
	        "Calcul du montant HT avec une quantité de {} pour {} : {}",
	        new Object[] {quantity, price, amount});

	    return amount;
	  }

	  /**
	   * Compute the quantity per the unit price
	   *
	   * @param quantity
	   * @param price The unit price.
	   * @param scale Scale to apply on the result
	   * @return The Excluded tax total amount.
	   */
	  public static BigDecimal computeAmount(BigDecimal quantity, BigDecimal price, int scale) {

	    BigDecimal amount = quantity.multiply(price).setScale(scale, RoundingMode.HALF_UP);

	    LOG.debug(
	        "Calcul du montant HT avec une quantité de {} pour {} : {}",
	        new Object[] {quantity, price, amount});

	    return amount;
	  }

}
