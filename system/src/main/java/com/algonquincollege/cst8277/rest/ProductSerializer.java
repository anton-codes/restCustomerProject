/*****************************************************************c******************o*******v******id********
 * File: ProductSerializer.java
 * Course materials (20F) CST 8277
 * @author Mike Norman
 * @author (updated) Anton Hrytsyk
 *
 */
package com.algonquincollege.cst8277.rest;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.algonquincollege.cst8277.models.ProductPojo;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class ProductSerializer extends StdSerializer<Set<ProductPojo>> implements Serializable {
    private static final long serialVersionUID = 1L;

    public ProductSerializer() {
        this(null);
    }

    public ProductSerializer(Class<Set<ProductPojo>> t) {
        super(t);
    }

    @Override
    public void serialize(Set<ProductPojo> originalProducts, JsonGenerator generator, SerializerProvider provider)
        throws IOException {
        
        Set<ProductPojo> hollowProducts = new HashSet<>();
        for (ProductPojo originalProduct : originalProducts) {
            ProductPojo productPojo = new ProductPojo();
            productPojo.setId(originalProduct.getId());
            productPojo.setDescription(originalProduct.getDescription());
            productPojo.setCreatedDate(originalProduct.getCreatedDate());
            productPojo.setUpdatedDate(originalProduct.getUpdatedDate());
            productPojo.setVersion(originalProduct.getVersion());
            productPojo.setSerialNo(originalProduct.getSerialNo());
            productPojo.setStores(null);
            hollowProducts.add(productPojo);
        }
        generator.writeObject(hollowProducts);
    }
}