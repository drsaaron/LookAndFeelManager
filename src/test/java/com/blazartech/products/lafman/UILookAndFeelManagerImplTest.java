/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.lafman;

import com.blazartech.products.lafman.config.UILookAndFeelManagerConfiguration;
import java.awt.Component;
import java.util.Collection;
import java.util.Enumeration;
import java.util.prefs.Preferences;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author AAR1069
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { UILookAndFeelManagerConfiguration.class })
@PropertySource("classpath:lafTypes.properties")
public class UILookAndFeelManagerImplTest {
    
    private static final Logger logger = LoggerFactory.getLogger(UILookAndFeelManagerImplTest.class);
    
    @Autowired
    private UILookAndFeelManagerImpl lafManager;
    
    @Value("${blazartech.lafmanager.lafClasses.size}")
    private int lafClassSize;
    
    public UILookAndFeelManagerImplTest() {
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

    /**
     * Test of getCurrentLookAndFeelClassName method, of class UILookAndFeelManagerImpl.
     */
    @Test
    public void testGetCurrentLookAndFeelClassName() {
        logger.info("getCurrentLookAndFeelClassName");
        
        String expResult = null;
        String result = lafManager.getCurrentLookAndFeelClassName();
        assertEquals(expResult, result);
    }

    private static final String NIMBUS = "Nimbus";
    
    /**
     * Test of initializePreferencesAndMenu method, of class UILookAndFeelManagerImpl.
     */
    @Test
    public void testInitializePreferencesAndMenu() {
        logger.info("initializePreferencesAndMenu");
        
        JMenu preferencesMenu = new JMenu();
        ButtonGroup buttonGroup = new ButtonGroup();
        Preferences preferences = Preferences.userNodeForPackage(UILookAndFeelManagerImplTest.class);
        Component gui = new JFrame();
        
        preferences.put(UILookAndFeelManagerImpl.LAF_KEY, NIMBUS);
        assertEquals(NIMBUS, preferences.get(UILookAndFeelManagerImpl.LAF_KEY, "unknown"));

        lafManager.initializePreferencesAndMenu(preferencesMenu, buttonGroup, preferences, gui);
        
        Enumeration<AbstractButton> buttonIterator = buttonGroup.getElements();
        AbstractButton selectedButton = null;
        while (buttonIterator.hasMoreElements()) {
            AbstractButton b = buttonIterator.nextElement();
            if (b.isSelected()) { selectedButton = b; }
        }

        assertNotNull(selectedButton);
        assertEquals(NIMBUS, selectedButton.getActionCommand());
    }

    /**
     * Test of getLafClassNames method, of class UILookAndFeelManagerImpl.
     */
    @Test
    public void testGetLafClassNames() {
        logger.info("getLafClassNames");

        Collection<String> classNames = lafManager.getLafClassNames();
        assertEquals(lafClassSize, classNames.size());
        
        classNames.forEach((className) -> {
            logger.info("className = " + className);
        });
        
        String laf = lafManager.getLookAndFeelClass(NIMBUS);
        assertNotNull(laf);
    }

    /**
     * Test of getInstalledLookAndFeels method, of class UILookAndFeelManagerImpl.
     */
    @Test
    public void testGetInstalledLookAndFeels() {
        logger.info("getInstalledLookAndFeels");
        
        Collection<String> expResult = null;
        Collection<String> result = lafManager.getInstalledLookAndFeels();
        result.forEach((className) -> {
            logger.info("installed LAF: " + className);
        });   
        assertNotEquals(null, result);
    }


    
}
