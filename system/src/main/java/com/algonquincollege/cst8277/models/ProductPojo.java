/*****************************************************************c******************o*******v******id********
 * File: OrderPojo.java
 * Course materials (20F) CST 8277
 * (Original Author) Mike Norman
 * 
 * (Modified) @author Anton Hrytsyk
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
*
* Description: model for the Customer object
*/
@Entity(name = "Product")
@Table(name = "PRODUCT")
@AttributeOverride(name = "id", column = @Column(name="PRODUCT_ID"))
public class ProductPojo extends PojoBase implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String description;
    protected String serialNo;
    protected Set<StorePojo> stores = new HashSet<>();

    public ProductPojo() {
    }
    
    /**
     * @return the value for firstName
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description new value for description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonInclude(Include.NON_NULL)
    @ManyToMany(mappedBy = "products", cascade = CascadeType.PERSIST)  // Note: could be List<StorePojo>, but JPA generates more efficient SQL for M:N Sets
    public Set<StorePojo> getStores() {
        return stores;
    }
    public void setStores(Set<StorePojo> stores) {
        this.stores = stores;
    }

    @Column(name = "SERIALNUMBER")
    public String getSerialNo() {
        return serialNo;
    }
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }


}