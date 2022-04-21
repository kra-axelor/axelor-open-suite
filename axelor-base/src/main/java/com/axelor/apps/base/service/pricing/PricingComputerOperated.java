package com.axelor.apps.base.service.pricing;

import com.axelor.apps.base.db.Pricing;
import com.axelor.apps.base.db.PricingRule;
import com.axelor.meta.db.MetaField;
import java.util.Objects;

public class PricingComputerOperated {

  public static final String OPERATION_PRICING = "pricing";
  public static final String OPERATION_CLASS_PRICING_RULE = "classification_pricing_rule";
  public static final String OPERATION_RES_PRICING_RULE = "result_pricing_rule";
  public static final String OPERATION_METAFIELD_POPULATED = "metafield_populated";

  private Pricing pricing;
  private PricingRule pricingRule;
  private Object result;
  private MetaField fieldPopulated;

  protected PricingComputerOperated(
      Pricing pricing, PricingRule pricingRule, Object result, MetaField fieldPopulated) {
    this.pricing = pricing;
    this.pricingRule = pricingRule;
    this.result = result;
    this.fieldPopulated = fieldPopulated;
  }

  public static PricingComputerOperated on(Pricing pricing) {
    Objects.requireNonNull(pricing);
    return new PricingComputerOperated(pricing, null, null, null);
  }

  public static PricingComputerOperated on(PricingRule pricingRule, Object result) {
    Objects.requireNonNull(pricingRule);
    Objects.requireNonNull(result);

    return new PricingComputerOperated(null, pricingRule, result, null);
  }

  public static PricingComputerOperated on(MetaField fieldPopulated) {
    Objects.requireNonNull(fieldPopulated);

    return new PricingComputerOperated(null, null, null, fieldPopulated);
  }

  public Pricing getPricing() {
    return pricing;
  }

  public PricingRule getPricingRule() {
    return pricingRule;
  }

  public Object getResult() {
    return result;
  }

  public MetaField getFieldPopulated() {
    return fieldPopulated;
  }
}
