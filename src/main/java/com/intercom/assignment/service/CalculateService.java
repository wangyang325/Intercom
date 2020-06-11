package com.intercom.assignment.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.intercom.assignment.bean.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Business service
 * deal with the customer list
 */
@Service
public class CalculateService {
    // radius of earth
    private static final double EARTH_RADIUS = 6378137;
    // Log4j
    public static final Logger LOGGER = LogManager.getLogger(CalculateService.class);
    // system log
    private List<String> errorList = new ArrayList<String>();

    /**
     * Get customers
     *
     * @param data : data from web page;
     * @param lon  : longitude of center;
     * @param lat  : latitude of center;
     * @return Json text;
     */
    public String getCustomers(String data, String lon, String lat) {
        LOGGER.info("Method Start -> CalculateService::getCustomers");
        // Clear error
        clearError();
        // Call main
        String rs = customertoJson(getCustomerList(data, lon, lat, 100));
        // error check
        String error = getError();
        if (error != null) {
            rs = error;
        }
        return rs;
    }

    /**
     * Get Customer list From Json String
     *
     * @param jsonStr    Json Data;
     * @param cLongitude longitude;
     * @param cLatitude  latitude;
     * @param distance;
     * @return customer list;
     */
    private List<Customer> getCustomerList(String jsonStr, String cLongitude, String cLatitude, double distance) {
        // customer list
        List<Customer> customerList = new ArrayList<Customer>();
        // no data
        if (jsonStr == null || jsonStr.length() == 0) {
            addError("Input data is empty!");
            LOGGER.warn("Input data is empty!");
            return customerList;
        } else {
            try {
                // read to array
                String customers[] = jsonStr.split(System.lineSeparator());
                // read by line
                for (String line : customers) {
                    if (line != null && line.length() != 0) {
                        // change line to json format
                        JsonObject convertedObject = new Gson().fromJson(line, JsonObject.class);
                        if (convertedObject.isJsonObject()) {
                            // new a customer object
                            Customer customer = new Customer();
                            // latitude
                            String latitude = convertedObject.get(Customer.JSON_KEY_LATITUDE).getAsString();
                            customer.setLatitude(latitude);
                            // longitude
                            String longitude = convertedObject.get(Customer.JSON_KEY_LONGITUDE).getAsString();
                            customer.setLongitude(longitude);
                            // user_id
                            customer.setUserId(convertedObject.get(Customer.JSON_KEY_USER_ID).getAsString());
                            // name
                            customer.setName(convertedObject.get(Customer.JSON_KEY_NAME).getAsString());
                            // distance
                            double distanceTwo = getDistance(longitude, latitude, cLongitude, cLatitude);
                            customer.setDistance(distanceTwo);

                            // add a new customer info
                            if (distanceTwo <= distance) {
                                customerList.add(customer);
                            }
                        } else {
                            LOGGER.error("CalculateService::getCustomerList :" + "Json is wrong : " + line);
                            addError("CalculateService::getCustomerList :" + "Json is wrong : " + line);
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.error("CalculateService::getCustomerList :" + "Cannot change to string from Json : " + jsonStr + " exception:" + e.getMessage());
                addError("CalculateService::getCustomerList :" + "Cannot change to string from Json : " + jsonStr + " exception:" + e.getMessage());
                return null;
            }
        }
        return customerList;
    }

    /**
     * calculate distance by gps
     *
     * @param pLon1 : longitude of point one;
     * @param pLat1 : latitude of point one;
     * @param pLon2 : longitude of point two;
     * @param pLat2 : latitude of point two;
     * @return distance by km;
     */
    private double getDistance(String pLon1, String pLat1, String pLon2, String pLat2) {
        double s = 0;
        try {
            double lon1 = Double.valueOf(pLon1);
            double lat1 = Double.valueOf(pLat1);
            double lon2 = Double.valueOf(pLon2);
            double lat2 = Double.valueOf(pLat2);
            double radLat1 = rad(lat1);
            double radLat2 = rad(lat2);
            double a = radLat1 - radLat2;
            double b = rad(lon1) - rad(lon2);
            s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
            s = s * EARTH_RADIUS / 1000;
        } catch (Exception e) {
            LOGGER.error("CalculateService::getDistance :" + "Cannot change the GPS" + e.getMessage());
            addError("CalculateService::getDistance :" + "Cannot change the GPS" + e.getMessage());
        }
        return s;
    }

    /**
     * error clear
     *
     */
    private void clearError() {
        if (errorList == null) {
            errorList = new ArrayList<String>();
        }
        errorList.clear();
    }

    /**
     * add message
     *
     * @param msg : message;
     */
    private void addError(String msg) {
        errorList.add(msg);
    }

    /**
     * get error
     *
     * @return lad;
     */
    public String getError() {
        if (errorList == null || errorList.size() == 0) {
            return null;
        }
        else {
            StringBuilder sb = new StringBuilder();
            sb.append("****  Error Information:");
            sb.append("</br>");
            sb.append("</br>");
            sb.append(System.lineSeparator());
                // order by id and change list to string
            errorList.stream().forEach((e) -> {
                    sb.append(e);
                sb.append("</br>");
                    sb.append(System.lineSeparator());
                });
            return sb.toString();
        }
    }

    /**
     * calculate rad
     *
     * @param d : longitude of point one;
     * @return lad;
     */
    private double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * Change the customer list to Json string
     *
     * @param pCustomer customer list;
     * @return Json string;
     */
    private String customertoJson(List<Customer> pCustomer) {
        StringBuilder sb = new StringBuilder();
        // no customer
        if (pCustomer != null && pCustomer.size() != 0) {
            // order by id and change list to string
            pCustomer.stream().sorted(Comparator.comparing(Customer::getUI)).forEach((e) -> {
                GsonBuilder builder = new GsonBuilder();
                builder.excludeFieldsWithoutExposeAnnotation();
                Gson gson = builder.create();
                String customerStr = gson.toJson(e);
                sb.append(customerStr);
                sb.append("</br>");
                sb.append(System.lineSeparator());
            });
        }
        else {
            LOGGER.warn("All data is away from the center!");
            addError("All data is away from the center!");
        }
        return sb.toString();
    }

    // ------------------------------- the following methods are for a bach, no used for microservice
    /**
     * Read Json file to String
     *
     * @param pPath : customer file;
     * @return customer string;
     */
    public String readFileToStr(String pPath) {
        String text = null;
        String filePath = null;
        try {
            // the same path as Jar
            File basePath = new File(ResourceUtils.getURL("classpath:").getPath());
            filePath = basePath.getAbsolutePath() + "/" + pPath;
            LOGGER.info("The file path: " + filePath);
            // read file to string
            text = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            LOGGER.error("CalculateService::readFileToStr :" + "Cannot fine the path : " + filePath + " " + e.getMessage());
            return null;
        }
        return text;
    }

    /**
     * write the string to file
     *
     * @param fileFullPath file path;
     * @param content      customer list;
     */
    private void writeFile(String fileFullPath, String content) {
        FileOutputStream fos = null;
        try {
            // open stream
            fos = new FileOutputStream(fileFullPath, true);
            // write
            fos.write(content.getBytes());
        } catch (IOException e) {
            LOGGER.error("CalculateService::writeFile :" + "Cannot open the file : " + fileFullPath + " exception:" + e.getMessage());
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    LOGGER.error("CalculateService::writeFile :" + "Cannot close the file : " + fileFullPath + " exception:" + e.getMessage());
                }
            }
        }
    }
}
