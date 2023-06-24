package com.sample.utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileReader {
    private Properties properties;
    private final String propertyFilePath = "src//test//resources//configuration.properties";

    public ConfigFileReader() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(propertyFilePath));
            properties = new Properties();
            try {
                properties.load(reader);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Properties file not found at path : " + propertyFilePath);
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (IOException ignore) {
            }
        }
    }

    public String getApplicationUrl() {
        String url = properties.getProperty("baseUrl");
        if (url != null) return url;
        else
            throw new RuntimeException("Application Url not specified in the Configuration.properties file for the Key:url");
    }
    public String getAdminUrl() {
        String url = properties.getProperty("adminUrl");
        if (url != null) return url;
        else
            throw new RuntimeException("Admin Url not specified in the Configuration.properties file for the Key:url");
    }

    public String getAdminUsername() {
        String url = properties.getProperty("admin_userName");
        if (url != null) return url;
        else
            throw new RuntimeException("admin_userName  not specified in the Configuration.properties file for the Key:url");
    }
    public String getAdminPassword() {
        String url = properties.getProperty("admin_passWord");
        if (url != null) return url;
        else
            throw new RuntimeException("admin_passWord  not specified in the Configuration.properties file for the Key:url");
    }

    public String getDashboardUrl() {
        String url = properties.getProperty("dashboardUrl");
        if (url != null) return url;
        else
            throw new RuntimeException("Dashboard Url not specified in the Configuration.properties file for the Key:url");
    }

    public String getBrowser() {
        String browserName = properties.getProperty("browserName");
        if (browserName != null) return browserName;
        else
            throw new RuntimeException("Dashboard Url not specified in the Configuration.properties file for the Key:url");

    }

    public String getPropertyValue(String propertyFieldName) {
        String url = properties.getProperty(propertyFieldName);
        if (url != null) return url;
        else
            throw new RuntimeException(""+propertyFieldName+"  not specified in the Configuration.properties file ");
    }


}
