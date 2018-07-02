/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.lafman;

import java.awt.Component;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.prefs.Preferences;
import javax.swing.ButtonGroup;
import javax.swing.JMenu;

/**
 * Interface for a component to manage look and feel in an application.  This 
 * manager will allow the application to supply a list of desired look and feel
 * classes, and will manage the application preferences and a menu to choose
 * the desired look and feel.
 * 
 * @author aar1069
 * @version $Id: UILookAndFeelManager.java 24 2015-04-23 19:42:21Z aar1069 $
 */
public interface UILookAndFeelManager {
    
    /**
     * get a list of the installed look and feel names.
     * @return 
     */
    public Collection<String> getInstalledLookAndFeels();
    
    /**
     * get the specific LAF class for the given name.
     * @param lafName
     * @return 
     */
    public String getLookAndFeelClass(String lafName);
    
    /**
     * Initialize the GUI from the preferences, and create the necessary menu options to update
     * the preferences.
     * 
     * @param preferencesMenu the look and feel preferences menu
     * @param buttonGroup a button group to ensure unique-ness in the LAF options.
     * @param preferences the application preferences.
     * @param gui the application GUI
     */
    public void initializePreferencesAndMenu(JMenu preferencesMenu, ButtonGroup buttonGroup, Preferences preferences, Component gui);

    /**
     * the property name for the current LAF.
     */
    public static final String PROP_CURRENTLOOKANDFEELNAME = "currentLookAndFeelClassName";
    
    /**
     * get the current look and feel class name.
     * 
     * @return 
     */
    public String getCurrentLookAndFeelClassName();
    
    /**
     * add a property change listener.  whenever the look and feel is updated via
     * the options menu, a property change event will be fired.
     * 
     * @param listener 
     */
    public void addPropertyChangeListener(PropertyChangeListener listener);
    
    /**
     * add a property change listener for a specific property.
     * 
     * @param propertyName
     * @param listener 
     */
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);
    
    /**
     * remove the property change listener.
     * 
     * @param listener 
     */
    public void removePropertyChangeListener(PropertyChangeListener listener);
}
