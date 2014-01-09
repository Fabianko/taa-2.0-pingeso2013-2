/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cl.usach.pingeso.taa2.managedbeans.login;

import cl.usach.pingeso.taa2.entityclasses.User;
import cl.usach.pingeso.taa2.sessionbeans.UserFacadeLocal;
import java.util.Date;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Nico
 */
public class SessionValidatorTest {
    public SessionValidatorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
@Test
    public void testGetUsername() {
        SessionValidator instance = new SessionValidator();
        String expResult = null;
        String result = instance.getUsername();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetUsername() {
        String username = "";
        SessionValidator instance = new SessionValidator();
        instance.setUsername(username);
    }

    @Test
    public void testGetPassword() {
        SessionValidator instance = new SessionValidator();
        String expResult = null;
        String result = instance.getPassword();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetPassword() {
        String password = "";
        SessionValidator instance = new SessionValidator();
        instance.setPassword(password);
    }

    @Test
    public void testGetDate() {
        SessionValidator instance = new SessionValidator();
        Date expResult = null;
        Date result = instance.getDate();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetDate() {
        Date date = null;
        SessionValidator instance = new SessionValidator();
        instance.setDate(date);
    }

    @Test
    public void testGetU() {
        SessionValidator instance = new SessionValidator();
        User expResult = null;
        User result = instance.getU();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetU() {
        User u = null;
        SessionValidator instance = new SessionValidator();
        instance.setU(u);
    }

    @Test
    public void testGetUserFacade() {
        SessionValidator instance = new SessionValidator();
        UserFacadeLocal expResult = null;
        UserFacadeLocal result = instance.getUserFacade();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetUserFacade() {
        UserFacadeLocal userFacade = null;
        SessionValidator instance = new SessionValidator();
        instance.setUserFacade(userFacade);
    }

    @Test
    public void testGetMyURL() {
        SessionValidator instance = new SessionValidator();
        String expResult = null;
        String result = instance.getMyURL();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetMyURL() {
        String myURL = null;
        SessionValidator instance = new SessionValidator();
        instance.setMyURL(myURL);
    }
    
}
