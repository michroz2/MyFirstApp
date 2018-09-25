package com.example.mich.myfirstapp.dwelling;

import org.junit.Assert;
import org.junit.Test;


public class ApartmentHouseTest {

    @Test
    public void moveIn() {
        ApartmentHouse apartmentHouse = new ApartmentHouse("Pajustiku 9", 2000, 123, 20);
        Tenant tenant = new Tenant("Mich1");
        Tenant tenant1 = new Tenant("Mich2");
        Tenant tenant2 = new Tenant("Mich3");

        apartmentHouse.moveIn(tenant);
        apartmentHouse.moveIn(tenant);
        apartmentHouse.moveIn(tenant);
        apartmentHouse.moveIn(tenant1);
        apartmentHouse.moveIn(tenant2);


        Assert.assertEquals("aaa", apartmentHouse.getNumTenants(), 3);
        Assert.assertTrue("bbb", apartmentHouse.contains(tenant2));
    }

    @Test
    public void moveInNull() {
        ApartmentHouse apartmentHouse = new ApartmentHouse("Pajustiku 9", 2000, 123, 20);

        try {
            apartmentHouse.moveIn(null);
            Assert.assertTrue("tenant can't be null", false);
        } catch (Exception e) {
            Assert.assertTrue(true);
        }




    }
}