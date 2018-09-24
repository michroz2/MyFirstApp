package com.example.mich.myfirstapp.dwelling;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class DwellingTest {

    @Test
    public void moveIn() {
        PrivateHouse testPH = new PrivateHouse("Pajustiku 9", 2000, 123);
        Tenant testMich1 = new Tenant("Mich1");
        Tenant testMich2 = new Tenant("Mich2");
        Tenant testMich3 = new Tenant("Mich3");

        Tenant testMich4 = new Tenant("Mich1");
        Tenant testMich5 = new Tenant("Mich2");

        testPH.moveIn(testMich1);
        testPH.moveIn(testMich2);
        testPH.moveIn(testMich3);
        // Следующие жильцы уже живут и не должны попасть в жильё
        testPH.moveIn(testMich4);
        testPH.moveIn(testMich5);

        Assert.assertEquals(testPH.getNumTenants(), 3);

        Assert.assertEquals(testPH.tenants[0].getName(), "Mich1");
        Assert.assertEquals(testPH.tenants[1].getName(), "Mich2");
        Assert.assertEquals(testPH.tenants[2].getName(), "Mich3");
    }

    @Test
    public void moveInGroup() {
        PrivateHouse testPH = new PrivateHouse("Pajustiku 9", 2000, 123);
        Tenant[] testMich = new Tenant[3];

        testMich[0] = new Tenant("Mich1");
        testMich[1] = new Tenant("Mich2");
        testMich[2] = new Tenant("Mich3");

        testPH.moveInGroup(testMich);

        Assert.assertEquals(testPH.getNumTenants(), 3);

        Assert.assertEquals(testPH.tenants[0].getName(), "Mich1");
        Assert.assertEquals(testPH.tenants[1].getName(), "Mich2");
        Assert.assertEquals(testPH.tenants[2].getName(), "Mich3");

    }

    @Test
    public void moveOut() {
        PrivateHouse testPH = new PrivateHouse("Pajustiku 9", 2000, 123);
        Tenant testMich1 = new Tenant("Mich1");
        Tenant testMich2 = new Tenant("Mich2");
        Tenant testMich3 = new Tenant("Mich3");

        testPH.moveIn(testMich1);
        testPH.moveIn(testMich2);
        testPH.moveIn(testMich3);

        Assert.assertEquals(testPH.getNumTenants(), 3);

        testPH.moveOut(testMich2);

        Assert.assertEquals(testPH.getNumTenants(), 2);
        Assert.assertEquals(testPH.tenants[0].getName(), "Mich1");
        Assert.assertEquals(testPH.tenants[1].getName(), "Mich3");

        testPH.moveOut(testMich3);

        Assert.assertEquals(testPH.getNumTenants(), 1);
        Assert.assertEquals(testPH.tenants[0].getName(), "Mich1");

        testPH.moveOut(testMich1);

        Assert.assertEquals(testPH.getNumTenants(), 0);
    }
}