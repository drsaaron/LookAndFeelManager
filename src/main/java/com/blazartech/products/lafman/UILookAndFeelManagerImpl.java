/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.lafman;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;
import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author aar1069
 * @version $Id: UILookAndFeelManagerImpl.java 24 2015-04-23 19:42:21Z aar1069 $
 */

/* $Log$
 *******************************************************************************/
public class UILookAndFeelManagerImpl implements UILookAndFeelManager {

    public static final String LAF_KEY = "LookAndFeel";
    
    public UILookAndFeelManagerImpl() {
    }

    private static final Logger logger = LoggerFactory.getLogger(UILookAndFeelManagerImpl.class);

    private String currentLookAndFeelName ;    

    /**
     * Get the value of currentLookAndFeelName
     *
     * @return the value of currentLookAndFeelName
     */
    @Override
    public String getCurrentLookAndFeelClassName() {
        return currentLookAndFeelName;
    }

    /**
     * Set the value of currentLookAndFeelName
     *
     * @param currentLookAndFeelName new value of currentLookAndFeelName
     */
    public void setCurrentLookAndFeelClassName(String currentLookAndFeelName) {
        String oldCurrentLookAndFeelName = this.currentLookAndFeelName;
        this.currentLookAndFeelName = currentLookAndFeelName;
        propertyChangeSupport.firePropertyChange(PROP_CURRENTLOOKANDFEELNAME, oldCurrentLookAndFeelName, currentLookAndFeelName);
    }

    @Override
    public void initializePreferencesAndMenu(JMenu preferencesMenu, ButtonGroup buttonGroup, Preferences preferences, Component gui) {
        final Preferences appPreferences = preferences;
        final Component window = gui;
        
        String currentLAF = appPreferences.get(LAF_KEY, UIManager.getLookAndFeel().getName());
        logger.info("currentLAF = " + currentLAF);
        setCurrentLookAndFeelClassName(currentLAF);
        try {
            UIManager.setLookAndFeel(getLookAndFeelClass(currentLAF));
            SwingUtilities.updateComponentTreeUI(gui);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException dfslaf) {}
        getInstalledLookAndFeels().stream().map((lafType) -> lafType).map((key) -> {
            JRadioButtonMenuItem typeMenu = new JRadioButtonMenuItem();
            typeMenu.setText(key);
            typeMenu.setSelected(key.equals(currentLAF));
            buttonGroup.add(typeMenu);

            // add the event handler.
            typeMenu.addActionListener((ActionEvent evt) -> {
                String className = getLookAndFeelClass(key);
                try {
                    UIManager.setLookAndFeel(className);
                    setCurrentLookAndFeelClassName(className);
                    SwingUtilities.updateComponentTreeUI(window);
                    appPreferences.put(LAF_KEY, key);
                } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException uslaf) {}
            });
            return typeMenu;            
        }).forEachOrdered((typeMenu) -> {
            // add it to the menu.
            preferencesMenu.add(typeMenu);
        });
    }

    @Override
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
    }
    
    
    public Map<String, String> installedLAFMap = new HashMap<>();
    
    private Collection<String> lafClassNames;
    public static final String PROP_LAFCLASSNAMES = "lafClassNames";

    /**
     * Get the value of lafClassNames
     *
     * @return the value of lafClassNames
     */
    public Collection<String> getLafClassNames() {
        return lafClassNames;
    }

    /**
     * Set the value of lafClassNames
     *
     * @param lafClassNames new value of lafClassNames
     */
    public void setLafClassNames(Collection<String> lafClassNames) {
        Collection<String> oldLafClassNames = this.lafClassNames;
        this.lafClassNames = lafClassNames;
        propertyChangeSupport.firePropertyChange(PROP_LAFCLASSNAMES, oldLafClassNames, lafClassNames);
    }
    
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    /**
     * Add PropertyChangeListener.
     *
     * @param listener
     */
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove PropertyChangeListener.
     *
     * @param listener
     */
    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    @Override
    public Collection<String> getInstalledLookAndFeels() {
        return installedLAFMap.keySet();
    }

    @Override
    public String getLookAndFeelClass(String lafName) {
        return installedLAFMap.get(lafName);
    }
}
