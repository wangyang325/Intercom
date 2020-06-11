package com.intercom.assignment;

import com.intercom.assignment.service.CalculateService;
import org.junit.Assert;
import org.junit.Test;

public class TestCalculateService extends AssignmentApplicationTests {

    // Get the service
    private CalculateService service = new CalculateService();

    /*
     * Test 001
     * Type  : Error
     * input : data = null, lon = normal, lat = normal
     * expect: error
     */
    @Test
    public void Test001() {
        service.getCustomers(null, "-6.257664", "53.339428");
        String error = service.getError();
        Assert.assertNotEquals(error, null);
    }

    /*
     * Test 002
     * Type  : Error
     * input : data = "", lon = normal, lat = normal
     * expect: error
     */
    @Test
    public void Test002() {
        service.getCustomers("", "-6.257664", "53.339428");
        String error = service.getError();
        Assert.assertNotEquals(error, null);
    }

    /*
     * Test 003
     * Type  : Error
     * input : data = "{a}" (format is wrong), lon = normal, lat = normal
     * expect: error
     */
    @Test
    public void Test003() {
        service.getCustomers("{a}", "-6.257664", "53.339428");
        String error = service.getError();
        Assert.assertNotEquals(error, null);
    }

    /*
     * Test 004
     * Type  : Normal
     * input : data = "{xx}" (one record), lon = normal, lat = normal
     * expect: no error
     */
    @Test
    public void Test004() {
        service.getCustomers("{\"latitude\": \"51.92893\", \"user_id\": 1, \"name\": \"Alice Cahill\", \"longitude\": \"-10.27699\"}", "-6.257664", "53.339428");
        String error = service.getError();
        Assert.assertSame(error, null);
    }

    /*
     * Test 005
     * Type  : Error
     * input : data = "{xx}" (Two records, no line break), lon = normal, lat = normal
     * expect: no error
     */
    @Test
    public void Test005() {
        service.getCustomers("{\"latitude\": \"52.986375\", \"user_id\": 12, \"name\": \"Christina McArdle\", \"longitude\": \"-6.043701\"}{\"latitude\": \"51.92893\", \"user_id\": 1, \"name\": \"Alice Cahill\", \"longitude\": \"-10.27699\"}", "-6.257664", "53.339428");
        String error = service.getError();
        Assert.assertNotEquals(error, null);
    }

    /*
     * Test 006
     * Type  : Normal
     * input : data = "{xx}{xx}" (multiple records), lon = normal, lat = normal
     * expect: no error and only the record is within 100km by user id order (increment)
     */
    @Test
    public void Test006() {
        String data = service.readFileToStr("customer.txt");
        String rs = service.getCustomers(data, "-6.257664", "53.339428");
        String error = service.getError();
        Assert.assertSame(error, null);
        System.out.print(rs);
    }

    // -----------------------------
    // The same test for lon and lat (null -> "" -> illegal -> normal).
    // If need to get 100% coverage (white box), the private methods also need to test. (need the mock plugin)
}
