/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.lafman.config;

import com.blazartech.products.lafman.UILookAndFeelManager;
import com.blazartech.products.lafman.UILookAndFeelManagerImpl;
import static com.blazartech.products.lafman.UILookAndFeelManagerImpl.PROP_LAFCLASSNAMES;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @author AAR1069
 */
@Configuration
@PropertySource("classpath:lafTypes.properties")
public class UILookAndFeelManagerConfiguration {
    
    @Value("${blazartech.lafmanager.lafClasses}")
    private String lafClasses;
    
    @Bean(name = "uiLookAndFeelManager")
    UILookAndFeelManager getUILookAndFeelManager() {
        UILookAndFeelManagerImpl impl = new UILookAndFeelManagerImpl();
        UILookAndFeelInstaller uiInstaller = new UILookAndFeelInstaller(impl);
        impl.addPropertyChangeListener(PROP_LAFCLASSNAMES, uiInstaller);
        
        String[] lafClassArray = lafClasses.split(":");
        impl.setLafClassNames(Arrays.asList(lafClassArray));
        
        return impl;
    }
}
