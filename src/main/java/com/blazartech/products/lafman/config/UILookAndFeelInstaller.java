/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.lafman.config;

import com.blazartech.products.lafman.UILookAndFeelManagerImpl;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author AAR1069
 */
class UILookAndFeelInstaller implements PropertyChangeListener {

    private static final Logger logger = LoggerFactory.getLogger(UILookAndFeelInstaller.class);
    
    private final UILookAndFeelManagerImpl lafImpl;

    public UILookAndFeelInstaller(UILookAndFeelManagerImpl lafImpl) {
        this.lafImpl = lafImpl;
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(UILookAndFeelManagerImpl.PROP_LAFCLASSNAMES)) {
            logger.info("installing LAF classes.");
            lafImpl.getLafClassNames().stream().forEach((lafClass) -> {
                try {
                    logger.info("instantiating " + lafClass);
                    LookAndFeel laf = (LookAndFeel) Class.forName(lafClass).getDeclaredConstructor().newInstance();
                    UIManager.installLookAndFeel(laf.getName(), lafClass);
                } catch(ClassNotFoundException|IllegalAccessException|InstantiationException|NoSuchMethodException|IllegalArgumentException|InvocationTargetException e) {
                    logger.error("error loading LAF class: " + e.getMessage(), e);
                } 
            });
            
            UIManager.LookAndFeelInfo[] installed = UIManager.getInstalledLookAndFeels();
            for (UIManager.LookAndFeelInfo info : installed) {
                lafImpl.installedLAFMap.put(info.getName(), info.getClassName());
            }
        }
    }
    
}
