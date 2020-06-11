package com.intercom.assignment.controller;

import com.intercom.assignment.service.CalculateService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

/**
 * Customer Controller
 */
@Controller
public class GetCustomerCtl {

    // service module
    @Autowired
    private CalculateService service;
    // Log4j
    public static final Logger LOGGER = LogManager.getLogger(GetCustomerCtl.class);

    /**
     * Get customers
     *
     * @param request : http request;
     * @return Json text;
     */
    @RequestMapping("/customers")
    @ResponseBody
    public String getCustomerList(HttpServletRequest request) {
        LOGGER.info("Service(/customers) Start -> GetCustomerCtl::getCustomerList");
        // get longitude from index
        String longitude = request.getParameter("longitude");
        // get latitude from index
        String latitude = request.getParameter("latitude");
        // get customers from index
        String customers = request.getParameter("customers");
        return service.getCustomers(customers, longitude, latitude);
    }

    /**
     * initial index
     *
     * @param model : data model;
     * @return index;
     */
    @RequestMapping("/index")
    public String showIndex(Model model) {
        LOGGER.info("Service(/index) Start -> GetCustomerCtl::showIndex");
        model.addAttribute("longitude", "-6.257664");
        model.addAttribute("latitude", "53.339428");
        model.addAttribute("customers", "");
        return "index";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        LOGGER.info("Service(/upload) Start -> GetCustomerCtl::upload");
        try {
            String data = new String(file.getBytes());
            // get longitude from index
            String longitude = request.getParameter("longitude");
            // get latitude from index
            String latitude = request.getParameter("latitude");
            return service.getCustomers(data, longitude, latitude);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "Upload error," + e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return "Upload error," + e.getMessage();
        }
    }
}
