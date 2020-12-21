/*****************************************************************c******************o*******v******id********
 * File: CustomIdentityStoreJPAHelper.java
 * Course materials (20F) CST 8277
 * @author Mike Norman
 * @author (Updated) Anton Hrytsyk
 *
 */
package com.algonquincollege.cst8277.security;

import static com.algonquincollege.cst8277.models.SecurityUser.SECURITY_USER_BY_NAME_QUERY;
import static com.algonquincollege.cst8277.utils.MyConstants.PARAM1;
import static com.algonquincollege.cst8277.utils.MyConstants.PU_NAME;
import static java.util.Collections.emptySet;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.ServletContext;
import javax.transaction.Transactional;
import javax.ws.rs.core.Context;

import com.algonquincollege.cst8277.models.SecurityRole;
import com.algonquincollege.cst8277.models.SecurityUser;

@Singleton
public class CustomIdentityStoreJPAHelper {

    @PersistenceContext(name=PU_NAME)
    protected EntityManager em;

    @Context
    protected ServletContext servletContext;

    private Connection unwrapConnectionHelper(Connection connection) throws
        IllegalAccessException, IllegalArgumentException, ClassNotFoundException, NoSuchMethodException, SecurityException,  InvocationTargetException {
        Connection unwrappedConnection = null;
        Class<?> loadClass = connection.getClass().getClassLoader().loadClass("com.sun.gjc.spi.base.ConnectionHolder");
        if(loadClass.isInstance(connection) ) {
            Method unwrapMethod = loadClass.getDeclaredMethod("getConnection");
            unwrappedConnection = (Connection)unwrapMethod.invoke(connection);
        }
        return unwrappedConnection;
    }

    @PostConstruct
    public void init() {
        try {
            Connection connection = em.unwrap(Connection.class);
            Connection unwrappedCon = unwrapConnectionHelper(connection);
            String dbUrl = unwrappedCon.getMetaData().getURL();
            if (dbUrl.contains("jdbc:h2:mem:20fGroupProject")) {
                org.h2.tools.Server bridge = org.h2.tools.Server.createTcpServer("-tcpPort", "54321", "-tcpAllowOthers").start();
            }
        }
        catch (Throwable t) {
            t.printStackTrace();

        }
    }

    @Transactional
    public void saveSecurityUser(SecurityUser user) {
        em.persist(user);
    }

    @Transactional
    public void saveSecurityRole(SecurityRole role) {
        em.persist(role);
    }
    
    public SecurityUser findUserByName(String username) {
        SecurityUser user = null;
        try {
            TypedQuery<SecurityUser> q = em.createNamedQuery(SECURITY_USER_BY_NAME_QUERY, SecurityUser.class);
            q.setParameter(PARAM1, username);
            user = q.getSingleResult();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public Set<String> findRoleNamesForUser(String username) {
        Set<String> roleNames = emptySet();
        SecurityUser securityUser = findUserByName(username);
        if (securityUser != null) {
            roleNames = securityUser.getRoles().stream().map(SecurityRole::getRoleName).collect(Collectors.toSet());
        }
        return roleNames;
    }


}