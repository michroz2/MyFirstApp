package com.example.mich.myfirstapp.dwelling;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;


public class ApartmentHouseTest {


    // todo -!NOT DONE!: 27-Sep-18 написать тест для поиска адреса человека
    // не понимаю как лист засунуть в функцию... Или что-то другое не понимаю...
    @Test
    public void findAddressByName() {
        PrivateHouse eraMaja1 = new PrivateHouse("Address 1", 2001, 150);
        PrivateHouse eraMaja2 = new PrivateHouse("Address 2", 2001, 150);
        PrivateHouse eraMaja3 = new PrivateHouse("Address 3", 2001, 150);
        PrivateHouse eraMaja4 = new PrivateHouse("Address 4", 2001, 150);
        PrivateHouse eraMaja5 = new PrivateHouse("Address 5", 2001, 150);


        eraMaja1.moveIn(new Tenant("Refugee 1"));
        eraMaja2.moveIn(new Tenant("Refugee 2"));
        eraMaja2.moveIn(new Tenant("Refugee 3"));
        eraMaja3.moveIn(new Tenant("Refugee 3"));
//        eraMaja4.moveIn(new Tenant("Refugee 4"));
        eraMaja5.moveIn(new Tenant("Refugee 3"));

        ArrayList<Dwelling> eraMajad = new ArrayList<>();

        eraMajad.add(eraMaja1);
        eraMajad.add(eraMaja2);
        eraMajad.add(eraMaja3);
        eraMajad.add(eraMaja4);
        eraMajad.add(eraMaja5);


        PrivateHouse aHouse = new PrivateHouse("doesn't matter", 3333, 2222);
        String[] addressByName = aHouse.findAddressByName("Refugee 3", eraMajad < > );

        Assert.assertTrue("3 Addresses", addressByName.length == 3);

    }


    // todo - !NOT DONE! 27-Sep-18 написать тест для получения всех жильцов проживающих по адресу
    public void findTennatsByAddress() {
        PrivateHouse eraMaja1 = new PrivateHouse("Address 1", 2001, 150);
        PrivateHouse eraMaja2 = new PrivateHouse("Address 2", 2002, 150);
        PrivateHouse eraMaja3 = new PrivateHouse("Address 1", 2003, 150);
        PrivateHouse eraMaja4 = new PrivateHouse("Address 4", 2004, 150);
        PrivateHouse eraMaja5 = new PrivateHouse("Address 1", 2005, 150);


        eraMaja1.moveIn(new Tenant("Refugee 1"));
        eraMaja2.moveIn(new Tenant("Refugee 2"));
        eraMaja3.moveIn(new Tenant("Refugee 3"));
        eraMaja3.moveIn(new Tenant("Refugee 4"));
//        eraMaja4.moveIn(new Tenant("Refugee 4"));
        eraMaja5.moveIn(new Tenant("Refugee 1"));
        eraMaja5.moveIn(new Tenant("Refugee 3"));

        ArrayList<Dwelling> eraMajad = new ArrayList<>();

        eraMajad.add(eraMaja1);
        eraMajad.add(eraMaja2);
        eraMajad.add(eraMaja3);
        eraMajad.add(eraMaja4);
        eraMajad.add(eraMaja5);

        // первый способ - остановится на 1-м свпадении адреса:
        PrivateHouse aHouse = new PrivateHouse("doesn't matter", 3333, 2222);
        Tenant[] tenantsByAddress = aHouse.findTennatsByAddress("Address 1", eraMajad < > );
        Assert.assertTrue("1 tenant: №1", tenantsByAddress.length == 1);


        aHouse = new PrivateHouse("doesn't matter", 3333, 2222);
        tenantsByAddress = aHouse.findTennatsByAddress2("Address 1", eraMajad < > );
        Assert.assertTrue("3 tenants: 1,3,4", tenantsByAddress.length == 3);

    }

    @Test
    public void getAddressByName() {
        ApartmentHouse apartmentHouse = new ApartmentHouse("Pajustiku 9", 2000, 123, 20);
        Tenant tenant1 = new Tenant("Negro1");
        Tenant tenant2 = new Tenant("Negro2");
        Tenant tenant3 = new Tenant("Negro3");

        apartmentHouse.moveIn(tenant1); //1
        apartmentHouse.moveIn(tenant2); //2
        apartmentHouse.moveIn(tenant3); //3


        if (apartmentHouse.livesHereByName("Negro2")) {
            Assert.assertTrue("Yes, lives here by this address", apartmentHouse.getAddress() == "Pajustiku 9");
        }
    }


    @Test
    public void getTenants() {  // может требовалось написать getTenantsByAddress() ?
        ApartmentHouse apartmentHouse = new ApartmentHouse("Pajustiku 9", 2000, 123, 20);
        Tenant tenant1 = new Tenant("Negro1");
        Tenant tenant2 = new Tenant("Negro2");
        Tenant tenant3 = new Tenant("Negro3");
        Tenant tenant4 = new Tenant("Negro4");
        Tenant tenant5 = new Tenant("Negro5");
        Tenant tenant6 = new Tenant("Negro6");

        apartmentHouse.moveIn(tenant1); //1
        apartmentHouse.moveIn(tenant2); //2
        apartmentHouse.moveIn(tenant5); //3

        if (apartmentHouse.getAddress() == "Pajustiku 9") {

            Tenant[] tenants;

            tenants = apartmentHouse.getTenants();

            Assert.assertTrue(apartmentHouse.getNumTenants() == 3);
            Assert.assertTrue(tenants[0].getName().equals("Negro1"));
            Assert.assertTrue(tenants[1].getName().equals("Negro2"));
            Assert.assertTrue(tenants[2].getName().equals("Negro5"));
        }
    }

    @Test
    public void moveOut() {
        ApartmentHouse apartmentHouse = new ApartmentHouse("Pajustiku 9", 2000, 123, 20);
        Tenant tenant = new Tenant("Negro1");
        Tenant tenant1 = new Tenant("Negro2");
        Tenant tenant2 = new Tenant("Negro3");
        Tenant tenantKiller = new Tenant("Killer1");

        Tenant[] tenants = new Tenant[3];
        tenants[0] = tenant;
        tenants[1] = tenant1;
        tenants[2] = tenant2;

        apartmentHouse.moveInGroup(tenants);
        Assert.assertTrue(apartmentHouse.getApartment(0).getNumTenants() == 0);
        apartmentHouse.getApartment(0).moveInGroup(tenants);
        apartmentHouse.getApartment(1).moveInGroup(tenants);
        apartmentHouse.getApartment(2).moveInGroup(tenants);

        apartmentHouse.getApartment(0).moveIn(tenantKiller);

        Assert.assertEquals("Жили были 3 негритёнка...", apartmentHouse.getNumTenants(), 3);
        apartmentHouse.moveOut(tenant1); //Один сунул пальцы в розетку...
        Assert.assertEquals("Жили были 2 негритёнка...", apartmentHouse.getNumTenants(), 2);
        apartmentHouse.moveOut(tenant); //Один упал с балкона...
        Assert.assertEquals("Жили были 1 негритёнка...", apartmentHouse.getNumTenants(), 1);
        apartmentHouse.moveOut(tenant2); //Один заснул в ванной...
        Assert.assertEquals("Жили были 0 негритёнка...", apartmentHouse.getNumTenants(), 0);
        apartmentHouse.tenantsRegistration(); // Но пришла полиция...
        Assert.assertEquals("И нашла убийцу!", apartmentHouse.getNumTenants(), 1);
    }

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
}