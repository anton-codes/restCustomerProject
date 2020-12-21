/**************************************************************************************************
 * File: PBKDF2HashGenerator.java
 * Course materials (20W) CST 8277
 * @author Mike Norman
 * @author Anton Hrytsyk (Updated)
 */
package com.algonquincollege.cst8277.utils;

import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_KEY_SIZE;
import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_PROPERTY_ALGORITHM;
import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_PROPERTY_ITERATIONS;
import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_SALT_SIZE;
import static com.algonquincollege.cst8277.utils.MyConstants.PROPERTY_ALGORITHM;
import static com.algonquincollege.cst8277.utils.MyConstants.PROPERTY_ITERATIONS;
import static com.algonquincollege.cst8277.utils.MyConstants.PROPERTY_KEYSIZE;
import static com.algonquincollege.cst8277.utils.MyConstants.PROPERTY_SALTSIZE;

import java.util.HashMap;
import java.util.Map;

import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

import org.glassfish.soteria.identitystores.hash.Pbkdf2PasswordHashImpl;

public class PBKDF2HashGenerator {
    // the nickname of this Hash algorithm is 'PBandJ' (Peanut-Butter-And-Jam, like the sandwich!)
    // I would like to use the constants from org.glassfish.soteria.identitystores.hash.Pbkdf2PasswordHashImpl
    // but they are not visible, so type in them all over again :-( Hope there are no typos!

    public static void main(String[] args) {

        Pbkdf2PasswordHash pbAndjPasswordHash = new Pbkdf2PasswordHashImpl();

        Map<String, String> properties = new HashMap<>();
        properties.put(PROPERTY_ALGORITHM, DEFAULT_PROPERTY_ALGORITHM);
        properties.put(PROPERTY_ITERATIONS, DEFAULT_PROPERTY_ITERATIONS);
        properties.put(PROPERTY_SALTSIZE, DEFAULT_SALT_SIZE);
        properties.put(PROPERTY_KEYSIZE, DEFAULT_KEY_SIZE);
        pbAndjPasswordHash.initialize(properties);
        String pwHash = pbAndjPasswordHash.generate(args[0].toCharArray());

        System.out.printf("Hash for %s is %s%n", args[0], pwHash);
    }
}
