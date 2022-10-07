package com.intuit.benten;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */

public class JenkinsProperties {

        private static String baseurl;
        private static String username;
        private static String password;

        public JenkinsProperties(){

        }

        public JenkinsProperties(String base, String user, String pass){
            this.setBaseurl(base);
            this.setUsername(user);
            this.setPassword(pass);
        }

        public String getBaseurl() {
            return baseurl;
        }

        public void setBaseurl(String baseurl) {
            this.baseurl = baseurl;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
