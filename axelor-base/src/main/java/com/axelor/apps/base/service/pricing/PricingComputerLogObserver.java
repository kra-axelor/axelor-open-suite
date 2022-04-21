package com.axelor.apps.base.service.pricing;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.axelor.event.Observes;
import com.google.inject.name.Named;

public class PricingComputerLogObserver {

  private final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  /**
   * Update the observer for the pricing used
   *
   * @param pricing
   */
  public void updatePricing(
      @Observes @Named(PricingComputerOperated.OPERATION_PRICING) PricingComputerOperated event) {
    log.info(String.format("Identified pricing scale: %s", event.getPricing().getName()));
  }

  /**
   * Update the observer that classification pricing rule is used with result
   *
   * @param pricingRule
   * @param result
   */
  public void updateClassificationPricingRule(
      @Observes @Named(PricingComputerOperated.OPERATION_CLASS_PRICING_RULE)
          PricingComputerOperated event) {
    log.info(
        String.format(
            "Classfication rule used: %s, Evaluation of the classification rule: %s",
            event.getPricingRule().getName(), event.getResult().toString()));
  }

  /**
   * Update the observer that result pricing rule is used with result
   *
   * @param pricingRule
   * @param result
   */
  public void updateResultPricingRule(
      @Observes @Named(PricingComputerOperated.OPERATION_RES_PRICING_RULE)
          PricingComputerOperated event) {
    log.info(
        String.format(
            "Evaluation of result rule used: %s, Result of evaluation of the result rule: %s",
            event.getPricingRule().getName(), event.getResult().toString()));
  }

  /**
   * Update the observer the field to populate
   *
   * @param field
   */
  public void updateFieldToPopulate(
      @Observes @Named(PricingComputerOperated.OPERATION_METAFIELD_POPULATED)
          PricingComputerOperated event) {
    log.info(String.format("Populated field: %s", event.getFieldPopulated().getName()));
  }
}
