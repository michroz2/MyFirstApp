package com.example.mich.myfirstapp.dwelling;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PrivateHouseTest {

    @Before
    public void createInstance() {

        PrivateHouse privateHouse = new PrivateHouse("vanasauna 7", 2017, 199);

        privateHouse.moveIn(new Tenant("Andrei"));
        privateHouse.setRoofColor(0xff0000ff);


        ApartmentHouse apartmentHouse = new ApartmentHouse("vikerlase 13", 1990, 5000, 20);

        apartmentHouse.moveIn(new Tenant("Peter"));


    }



    @Test
    public void moveIn() {
        PrivateHouse privateHouse = new PrivateHouse("Pajustiku 9", 2000, 123);
        Tenant tenant = new Tenant("Mich1");
        Tenant tenant1 = new Tenant("Mich2");
        Tenant tenant2 = new Tenant("Mich3");



        privateHouse.moveIn(tenant);
        privateHouse.moveIn(tenant1);
        privateHouse.moveIn(tenant2);
        privateHouse.moveIn(tenant1);
        privateHouse.moveIn(tenant2);

        Assert.assertEquals(privateHouse.getNumTenants(), 3);

        Assert.assertTrue(privateHouse.contains(tenant));
        Assert.assertTrue(privateHouse.contains(tenant1));
        Assert.assertTrue(privateHouse.contains(tenant2));
    }

    @Test
    public void moveOut() {
        PrivateHouse privateHouse = new PrivateHouse("Pajustiku 9", 2000, 123);
        Tenant tenant1 = new Tenant("Mich1");
        Tenant tenant2 = new Tenant("Mich2");
        Tenant tenant3 = new Tenant("Mich3");

        privateHouse.moveIn(tenant1);
        privateHouse.moveIn(tenant2);
        privateHouse.moveIn(tenant3);

        Assert.assertEquals(privateHouse.getNumTenants(), 3);

        privateHouse.moveOut(tenant2);

        Assert.assertEquals(privateHouse.getNumTenants(), 2);

        Assert.assertTrue(privateHouse.contains(tenant1));
        Assert.assertTrue(privateHouse.contains(tenant3));

        privateHouse.moveOut(tenant3);

        Assert.assertEquals(privateHouse.getNumTenants(), 1);
        Assert.assertFalse(privateHouse.contains(tenant3));

        privateHouse.moveOut(tenant1);

        Assert.assertEquals(privateHouse.getNumTenants(), 0);
    }
}