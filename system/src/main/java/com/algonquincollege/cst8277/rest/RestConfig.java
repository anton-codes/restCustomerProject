/***************************************************************************f******************u************zz*******y**
 * File: RestConfig.java
 * Course materials (20F) CST 8277
 * @author Mike Norman
 * @date 2020 03
 * @author (Updated) Anton Hrytsyk
 */
package com.algonquincollege.cst8277.rest;

import static com.algonquincollege.cst8277.utils.MyConstants.APPLICATION_API_VERSION;
import static com.algonquincollege.cst8277.utils.MyConstants.ADMIN_ROLE;
import static com.algonquincollege.cst8277.utils.MyConstants.USER_ROLE;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.security.DeclareRoles;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath(APPLICATION_API_VERSION)
@DeclareRoles({USER_ROLE, ADMIN_ROLE})
public class RestConfig extends Application {

    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("jersey.config.jsonFeature", "JacksonFeature");
        return properties;
    }
}
