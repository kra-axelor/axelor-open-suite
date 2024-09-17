/*
 * Axelor Business Solutions
 *
 * Copyright (C) 2005-2024 Axelor (<http://axelor.com>).
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.axelor.apps.sale.service.cartline;

import com.axelor.apps.base.AxelorException;
import com.axelor.apps.base.db.Product;
import com.axelor.apps.base.db.repo.ProductRepository;
import com.axelor.apps.sale.db.Cart;
import com.axelor.apps.sale.db.CartLine;
import com.axelor.apps.sale.db.repo.CartLineRepository;
import com.axelor.apps.sale.service.saleorderline.product.SaleOrderLineProductService;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

public class CartLineCreateServiceImpl implements CartLineCreateService {

  protected ProductRepository productRepository;
  protected SaleOrderLineProductService saleOrderLineProductService;
  protected CartLineProductService cartLineProductService;
  protected CartLineRepository cartLineRepository;

  @Inject
  public CartLineCreateServiceImpl(
      ProductRepository productRepository,
      SaleOrderLineProductService saleOrderLineProductService,
      CartLineProductService cartLineProductService,
      CartLineRepository cartLineRepository) {
    this.productRepository = productRepository;
    this.saleOrderLineProductService = saleOrderLineProductService;
    this.cartLineProductService = cartLineProductService;
    this.cartLineRepository = cartLineRepository;
  }

  @Override
  @Transactional(rollbackOn = Exception.class)
  public CartLine createCartLine(Cart cart, Product product) throws AxelorException {
    CartLine cartLine = new CartLine();
    cartLine.setProduct(productRepository.find(product.getId()));
    cartLine.setUnit(saleOrderLineProductService.getSaleUnit(cartLine.getProduct()));
    cartLine.setPrice(cartLineProductService.getSalePrice(cart, cartLine));
    cartLine.setCart(cart);
    return cartLineRepository.save(cartLine);
  }
}