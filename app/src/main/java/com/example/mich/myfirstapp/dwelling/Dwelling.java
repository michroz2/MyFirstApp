package com.example.mich.myfirstapp.dwelling;


public abstract class Dwelling {
    private String address;
    private int constructionYear;
    private Tenant[] tenants;
    private int area;

    // Конструктор
    Dwelling(String newAddress, int newYear, int newArea) {
        address = newAddress;
        constructionYear = newYear;
        tenants = new Tenant[]{};
        area = newArea;
    }

    public void moveIn(Tenant tenant) {

        // Заселяем нового жильца

        boolean already = false;
        int oldNumTenants = this.getNumTenants();

        // Проверяем, не живёт ли такой уже:
        if (oldNumTenants > 0) {
            for (int i : this.tenants) {
                already = (tenant == tenants[i]);
                if (already) return;
            }
        }

        // Если нет ещё такого, то заселяем:
        if (!already) {
            Tenant[] oldTenants = new Tenant[oldNumTenants];
            System.arraycopy(tenants, 0, oldTenants, 0, oldNumTenants);
            this.tenants = new Tenant[oldNumTenants + 1];
            System.arraycopy(oldTenants, 0, this.tenants, 0, oldNumTenants);
            this.tenants[tenants.length] = tenant;
        }
    }

    public void moveInGroup(Tenant[] tenants) {
        for (int i : tenants) moveIn(tenants[i]);
    }

    public void moveOut(Tenant tenant) {

    }

    public int getNumTenants() {
        return tenants.length;
    }

    public void setArea(int a) {
        area = a;
    }

    public int getArea() {
        return area;
    }
}
