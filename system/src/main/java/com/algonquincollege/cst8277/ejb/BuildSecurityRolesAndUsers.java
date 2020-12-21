/*****************************************************************c******************o*******v******id********
 * File: BuildSecurityRolesAndUsers.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * 
 * update by : Anton Hrytsyk
 *
 */
package com.algonquincollege.cst8277.ejb;

import static com.algonquincollege.cst8277.utils.MyConstants.ADMIN_ROLE;
import static com.algonquincollege.cst8277.utils.MyConstants.USER_ROLE;
import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_ADMIN_USER;
import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_ADMIN_USER_PASSWORD;
import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_KEY_SIZE;
import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_PROPERTY_ALGORITHM;
import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_PROPERTY_ITERATIONS;
import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_SALT_SIZE;
import static com.algonquincollege.cst8277.utils.MyConstants.PROPERTY_ALGORITHM;
import static com.algonquincollege.cst8277.utils.MyConstants.PROPERTY_ITERATIONS;
import static com.algonquincollege.cst8277.utils.MyConstants.PROPERTY_KEYSIZE;
import static com.algonquincollege.cst8277.utils.MyConstants.PROPERTY_SALTSIZE;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

import com.algonquincollege.cst8277.models.SecurityRole;
import com.algonquincollege.cst8277.models.SecurityUser;
import com.algonquincollege.cst8277.security.CustomIdentityStoreJPAHelper;

/**
 * This Stateless Session bean is 'special' because it is also a Singleton and
 * it runs at startup.
 *
 * How do we 'bootstrap' the security system? This EJB checks to see if the default ADMIN user
 * has already been created. If not, it then builds the ADMIN role, the default ADMIN user with
 * ADMIN role of ADMIN and the USER role ... and stores all of them in the database.
 *
 */
@Startup
@Singleton
public class BuildSecurityRolesAndUsers {

    @Inject
    protected CustomIdentityStoreJPAHelper jpaHelper;

    @Inject
    protected Pbkdf2PasswordHash pbAndjPasswordHash;

    @PostConstruct
    public void init() {
        // build default admin user (if needed)
        SecurityUser adminUser = jpaHelper.findUserByName(DEFAULT_ADMIN_USER);
        if (adminUser == null) {
            adminUser = new SecurityUser();
            adminUser.setUsername(DEFAULT_ADMIN_USER);
            Map<String, String> pbjProperties = new HashMap<>();
            pbjProperties.put(PROPERTY_ALGORITHM, DEFAULT_PROPERTY_ALGORITHM);
            pbjProperties.put(PROPERTY_ITERATIONS, DEFAULT_PROPERTY_ITERATIONS);
            pbjProperties.put(PROPERTY_SALTSIZE, DEFAULT_SALT_SIZE);
            pbjProperties.put(PROPERTY_KEYSIZE, DEFAULT_KEY_SIZE);

            pbAndjPasswordHash.initialize(pbjProperties);
            String pwHash = pbAndjPasswordHash.generate(DEFAULT_ADMIN_USER_PASSWORD.toCharArray());
            adminUser.setPwHash(pwHash);

            SecurityRole adminRole = new SecurityRole();
            adminRole.setRoleName(ADMIN_ROLE);
            Set<SecurityRole> roles = adminUser.getRoles();
            if (roles == null) {
                roles = new HashSet<>();

            }
            roles.add(adminRole);
            adminUser.setRoles(roles);
            jpaHelper.saveSecurityUser(adminUser);
            
            // if building Admin User/Role,might as well also build USER_ROLE
            SecurityRole userRole = new SecurityRole();

            userRole.setRoleName(USER_ROLE);
            jpaHelper.saveSecurityRole(userRole);
        }
    }
}