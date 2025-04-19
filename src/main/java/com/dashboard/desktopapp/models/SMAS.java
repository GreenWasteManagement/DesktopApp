package com.dashboard.desktopapp.models;

public class SMAS {
        private Integer id;
        private String name;
        private String username;
        private String email;
        private String phone;
        private Double cc;
        private Double nif;
        private String userType;
        private String address;
        private String workerId;
        private String position;

        public SMAS(Integer id, String name, String username, String email, String phone,
                    Double cc, Double nif, String userType, String address,
                    String workerId, String position) {
            this.id = id;
            this.name = name;
            this.username = username;
            this.email = email;
            this.phone = phone;
            this.cc = cc;
            this.nif = nif;
            this.userType = userType;
            this.address = address;
            this.workerId = workerId;
            this.position = position;
        }

        public Integer getId() { return id; }
        public String getName() { return name; }
        public String getUsername() { return username; }
        public String getEmail() { return email; }
        public String getPhone() { return phone; }
        public Double getCc() { return cc; }
        public Double getNif() { return nif; }
        public String getUserType() { return userType; }
        public String getAddress() { return address; }
        public String getWorkerId() { return workerId; }
        public String getPosition() { return position; }
}
