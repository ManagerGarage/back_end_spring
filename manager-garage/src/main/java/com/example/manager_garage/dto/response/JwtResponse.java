package com.example.manager_garage.dto.response;

public class JwtResponse {
    private String token;
    private UserInfo user;

    public JwtResponse(String token, UserInfo user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public static class UserInfo {
        // private Long id; // Ẩn id, không trả về nữa
        private String username;
        private String email;
        private String fullName;
        private String role;
        private String phone;
        private String address;
        private String dayBirth;
        private String license;
        private String statusDriver;

        public UserInfo(/* Long id, */ String username, String email, String fullName, String role, String phone,
                String address, String dayBirth, String license, String statusDriver) {
            // this.id = id;
            this.username = username;
            this.email = email;
            this.fullName = fullName;
            this.role = role;
            this.phone = phone;
            this.address = address;
            this.dayBirth = dayBirth;
            this.license = license;
            this.statusDriver = statusDriver;
        }

        // Xóa getter/setter cho id
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getDayBirth() {
            return dayBirth;
        }

        public void setDayBirth(String dayBirth) {
            this.dayBirth = dayBirth;
        }

        public String getLicense() {
            return license;
        }

        public void setLicense(String license) {
            this.license = license;
        }

        public String getStatusDriver() {
            return statusDriver;
        }

        public void setStatusDriver(String statusDriver) {
            this.statusDriver = statusDriver;
        }

    }
}