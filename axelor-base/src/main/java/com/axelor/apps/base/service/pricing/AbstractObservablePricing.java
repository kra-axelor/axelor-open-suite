package com.axelor.apps.base.service.pricing;

import com.axelor.apps.base.db.Pricing;
import com.axelor.apps.base.db.PricingRule;
import com.axelor.event.Event;
import com.axelor.event.NamedLiteral;
import com.axelor.inject.Beans;
import com.axelor.meta.db.MetaField;

public abstract class AbstractObservablePricing {

  private Event<PricingComputerOperated> pricingComputerOperatedEvent;

  protected AbstractObservablePricing() {
    this.pricingComputerOperatedEvent = (Event<PricingComputerOperated>) Beans.get(Event.class);
  }

  /**
   * Fires a axelor event of type pricing
   *
   * @param pricing
   */
  public void notifyPricing(Pricing pricing) {

    pricingComputerOperatedEvent
        .select(NamedLiteral.of(PricingComputerOperated.OPERATION_PRICING))
        .fire(PricingComputerOperated.on(pricing));
  }

  /**
   * Fires a axelor event of type classfication pricing rule
   *
   * @param pricingRule
   * @param result
   */
  public void notifyClassificationPricingRule(PricingRule pricingRule, Object result) {
    pricingComputerOperatedEvent
        .select(NamedLiteral.of(PricingComputerOperated.OPERATION_CLASS_PRICING_RULE))
        .fire(PricingComputerOperated.on(pricingRule, result));
  }

  /**
   * Fires a axelor event of type result pricing rule
   *
   * @param pricingRule
   * @param result
   */
  public void notifyResultPricingRule(PricingRule pricingRule, Object result) {
    pricingComputerOperatedEvent
        .select(NamedLiteral.of(PricingComputerOperated.OPERATION_RES_PRICING_RULE))
        .fire(PricingComputerOperated.on(pricingRule, result));
  }

  /**
   * Fires a axelor event of type metafield populated
   *
   * @param pricingRule
   * @param result
   */
  public void notifyFieldToPopulate(MetaField field) {
    pricingComputerOperatedEvent
        .select(NamedLiteral.of(PricingComputerOperated.OPERATION_METAFIELD_POPULATED))
        .fire(PricingComputerOperated.on(field));
  }
}
