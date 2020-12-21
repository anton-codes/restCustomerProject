/*****************************************************************c******************o*******v******id********
 * File: StoreResource.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * 
 * update by : Anton Hrytsyk
 *
 */
package com.algonquincollege.cst8277.rest;

import static com.algonquincollege.cst8277.utils.MyConstants.ADMIN_ROLE;
import static com.algonquincollege.cst8277.utils.MyConstants.RESOURCE_PATH_ID_ELEMENT;
import static com.algonquincollege.cst8277.utils.MyConstants.RESOURCE_PATH_ID_PATH;
import static com.algonquincollege.cst8277.utils.MyConstants.STORE_RESOURCE_NAME;
import static com.algonquincollege.cst8277.utils.MyConstants.USER_ROLE;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.algonquincollege.cst8277.ejb.CustomerService;
import com.algonquincollege.cst8277.models.StorePojo;

@Path(STORE_RESOURCE_NAME)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StoreResource {

    @EJB
    protected CustomerService customerServiceBean;

    @Inject
    protected ServletContext servletContext;
	
    /**
     * response that displays for adding new store
     * @param new store
     * @return Response
     */
    @RolesAllowed({ADMIN_ROLE})
    @POST
    public Response addStore(StorePojo newStore) {
        servletContext.log("add specific store ...");
        Response response = null;
        StorePojo newStoreWithIdTimestamps = customerServiceBean.persistStore(newStore);
        response = Response.ok(newStoreWithIdTimestamps).build();
        return response;
    }

    /**
     * response that displays for updating store by id
     * @param id, store with updates
     * @return Response
     */
    @RolesAllowed({ADMIN_ROLE, USER_ROLE})
    @PUT
    @Path("{id}")
    public Response updateStore(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id, StorePojo storeWithUpdates) {
        servletContext.log("update specific store ...");
        Response response = null;
        StorePojo updatedStore = customerServiceBean.updateStore(id, storeWithUpdates);
        response = Response.ok(updatedStore).build();
        return response;
    }

    @GET
    public Response getStores() {
        servletContext.log("retrieving all stores ...");
        List<StorePojo> stores = customerServiceBean.getAllStores();
        Response response = Response.ok(stores).build();
        return response;
    }

    @GET
    @Path(RESOURCE_PATH_ID_PATH)
    public Response getStoreById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        servletContext.log("try to retrieve specific store " + id);
        StorePojo theStore = customerServiceBean.getStoreById(id);
        Response response = Response.ok(theStore).build();
        return response;
    }

}