//DatabaseConfig.java
//Package com.ecom (in src/main/java)
package com.ecom;

public class DatabaseConfig {
    private String username = "root";
    private String password = "my_password";
    private String url = "jdbc:mysql://localhost:3306/ecom";

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }
}
