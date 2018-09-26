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
    public void moveInAppartment() {
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

    @Test
    public void tenantsRegistration() {
        ApartmentHouse apartmentHouse = new ApartmentHouse("streetname, housenumber", 1997, 100, 5);

        Tenant tenant0 = new Tenant("Вася 0");
        Tenant tenant1 = new Tenant("Вася 1");
        Tenant tenant2 = new Tenant("Вася 2");

        Apartment apartment0 = apartmentHouse.getApartment(0);
        Apartment apartment1 = apartmentHouse.getApartment(1);
        Apartment apartment2 = apartmentHouse.getApartment(2);

        apartmentHouse.moveInApartment(tenant0, 0);

        Assert.assertEquals(apartmentHouse.getNumTenants(), 1);
        Assert.assertEquals(apartment0.getNumTenants(), 1);

        //partly illegal move-In: (flat-only)
        apartment1.moveIn(tenant1);
        Assert.assertEquals(apartmentHouse.getNumTenants(), 1);
        //illegal move-In: (building-only)
        apartmentHouse.moveIn(tenant2);
        Assert.assertEquals(apartmentHouse.getNumTenants(), 2);

        apartmentHouse.tenantsRegistration();
        //Expected: Вася 0 и Вася 1 - живут и зарегистрированы, а Вася 2 выселен
        Assert.assertEquals(apartmentHouse.getNumTenants(), 2);
        Assert.assertTrue(apartmentHouse.livesHereByName("Вася 0"));
        Assert.assertTrue(apartmentHouse.livesHereByName("Вася 1"));
        Assert.assertFalse(apartmentHouse.livesHereByName("Вася 2"));


    }

    @Test
    public void moveOut() {
    }
}