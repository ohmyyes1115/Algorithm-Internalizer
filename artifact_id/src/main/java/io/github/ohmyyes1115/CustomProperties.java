package io.github.ohmyyes1115;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

class CustomProperties {
    private String m_filePath_prop;

    // public static Optional<CustomProperties> create(String filePath_prop) {
    //     try {
    //         return Optional.of(new CustomProperties(filePath_prop));
    //     } 
    //     catch (IOException e) {
    //         return Optional.empty();
    //     }
    // }

    public CustomProperties(String filePath_prop) throws IOException {
        m_filePath_prop = filePath_prop;

        File prop_file = new File(filePath_prop);
        if (!prop_file.exists()) {
            prop_file.createNewFile();
        }
    }

    public String get(String key) throws IOException {
        return loadProperties(m_filePath_prop).getProperty(key);
    }

    public String get(String key, String default_val) throws IOException {
        Properties prop = loadProperties(m_filePath_prop);
        return prop.getProperty(key, default_val);
    }

    public boolean set(String key, String value) {
        try {
            Properties prop = loadProperties(m_filePath_prop);
            prop.setProperty(key, value);
            saveProperties(prop, m_filePath_prop);

            return true;
        }
        catch (IOException e) {
            return false;
        }
    }
    
    public boolean contains(String key) throws IOException {
        return loadProperties(m_filePath_prop).containsKey(key);
    }

    private Properties loadProperties(String file_path) throws IOException {

        try (InputStream is = new FileInputStream(file_path)) {
            Properties prop = new Properties();
            prop.load(is);

            return prop;
        }
    }

    private void saveProperties(Properties prop, String file_path) throws IOException {

        try (OutputStream os = new FileOutputStream(file_path)) {
            prop.store(os, null);
        }
    }
}