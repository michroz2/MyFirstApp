package com.example.mich.myfirstapp.dwelling;


import android.os.Build;
import android.support.annotation.RequiresApi;

public abstract class Dwelling {
    private String address;
    private int constructionYear;
    public Tenant[] tenants;
    private int area;

    public Dwelling(String address, int constructionYear, int area) {
        this.address = address;
        this.constructionYear = constructionYear;
        this.tenants = new Tenant[0];
        this.area = area;
    }

    /**
     * Заселение жильца в дом
     *
     * @param tenant
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void moveIn(Tenant tenant) {

        boolean already = false;
        int oldNumTenants = this.getNumTenants();

        // Проверяем, не живёт ли такой уже:
        if (oldNumTenants > 0) {
            for (int i = 0; i < this.tenants.length; i++) {
                already = (tenant.equals(tenants[i]));
                if (already) return;
            }
        }

        // Если нет ещё такого, то заселяем:
        if (!already) {
            Tenant[] oldTenants = new Tenant[oldNumTenants];
            System.arraycopy(tenants, 0, oldTenants, 0, oldNumTenants);
            this.tenants = new Tenant[oldNumTenants + 1];
            System.arraycopy(oldTenants, 0, this.tenants, 0, oldNumTenants);
            this.tenants[tenants.length - 1] = tenant;
        }
    }

    /**
     *
     * @param tenants
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void moveInGroup(Tenant[] tenants) {
        for (int i = 0; i < tenants.length; i++) {
            this.moveIn(tenants[i]);
        }
    }

    /** Выселение Жильца из Жилья, если он там живёт
     * @param tenant
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void moveOut(Tenant tenant) {
        int oldNumTenants = this.getNumTenants();
        for (int i = 0; i < oldNumTenants; i++) {
            if (tenant.equals(tenants[i])) {
               // tenant живёт в данном жилье! - выселить!
                Tenant[] oldTenants = new Tenant[oldNumTenants];
                System.arraycopy(tenants, 0, oldTenants, 0, oldNumTenants);
                this.tenants = new Tenant[oldNumTenants - 1];
                System.arraycopy(oldTenants, 0, this.tenants, 0, i);
                System.arraycopy(oldTenants, i+1, this.tenants, i, oldNumTenants - i - 1);
                return;
            }
        }
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
