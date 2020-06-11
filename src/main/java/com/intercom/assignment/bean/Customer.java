package com.intercom.assignment.bean;

import com.google.gson.annotations.Expose;

/**
 * Customer Bean
 */
public class Customer {

    // user_id
    @Expose
    private String user_id = null;
    public static String JSON_KEY_USER_ID = "user_id";

    // name
    @Expose
    private String name = null;
    public static String JSON_KEY_NAME = "name";

    // user id for sorting
    private int userId = 0;

    // latitude
    private String latitude = null;
    public static String JSON_KEY_LATITUDE = "latitude";

    // longitude
    private String longitude = null;
    public static String JSON_KEY_LONGITUDE = "longitude";

    // distance from center
    private double distance = 0;

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    private void setUI(int pUserId) {
        userId = pUserId;
    }

    public int getUI() {
        return userId;
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String userId) {
        if (userId != null && userId.length() != 0) {
            try {
                int uId = Integer.valueOf(userId);
                setUI(uId);
            } catch (Exception e) {

            }
        }
        this.user_id = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
