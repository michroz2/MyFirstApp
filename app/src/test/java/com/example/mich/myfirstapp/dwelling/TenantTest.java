package com.example.mich.myfirstapp.dwelling;

import org.junit.Assert;
import org.junit.Test;

public class TenantTest {
    @Test
    public void equals() {
        Tenant tenant1 = new Tenant("Вася Пупкин");
        Tenant tenant2 = new Tenant("Вася Пупкин");
        Assert.assertTrue(tenant1.equals(tenant2));
        Assert.assertTrue(tenant1 == tenant2);
    }

}