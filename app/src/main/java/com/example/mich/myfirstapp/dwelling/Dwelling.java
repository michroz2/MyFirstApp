package com.example.mich.myfirstapp.dwelling;


public abstract class Dwelling {
    private String address;
    private int constructionYear;
    private Tenant[] tenants;
    private int area;

    Dwelling(String address, int constructionYear, int area) {
        this.address = address;
        this.constructionYear = constructionYear;
        this.area = area;
        tenants = new Tenant[0];
    }

    /**
     * Заселение жильца в жильё
     *
     * @param tenant - жилец
     */
    public void moveIn(Tenant tenant) {
        if (contains(tenant)) {
            return; // Если есть уже такой жилец, то не надо его заселять
        }

        int oldNumTenants = tenants.length;


        Tenant[] oldTenants = new Tenant[oldNumTenants];
        System.arraycopy(tenants, 0, oldTenants, 0, oldNumTenants);
        this.tenants = new Tenant[oldNumTenants + 1];
        System.arraycopy(oldTenants, 0, this.tenants, 0, oldNumTenants);
        this.tenants[tenants.length - 1] = tenant;
    }

    /**
     * заселить целую группу жильцов
     *
     * @param tenants - массив заселяющихся жильцов
     */
    public void moveInGroup(Tenant[] tenants) {
        for (int i = 0; i < tenants.length; i++) {
            this.moveIn(tenants[i]);
        }
    }

    /**
     * Выселение Жильца из Жилья, если он там живёт
     *
     * @param tenant
     */
    public void moveOut(Tenant tenant) {
        int oldNumTenants = tenants.length;
        for (int i = 0; i < oldNumTenants; i++) {
            if (tenant == tenants[i]) {
                // tenant живёт в данном жилье! - выселить!
                Tenant[] oldTenants = new Tenant[oldNumTenants];
                System.arraycopy(tenants, 0, oldTenants, 0, oldNumTenants);
                tenants = new Tenant[oldNumTenants - 1];
                System.arraycopy(oldTenants, 0, tenants, 0, i);
                System.arraycopy(oldTenants, i + 1, tenants, i, oldNumTenants - i - 1);
                return;
            }
        }
    }

    /**
     * есть ли жилец в доме
     *
     * @param tenant - жилец
     * @return <code>true</code> если есть такой жилец в жилище. <code>false</code> в противном случае
     */
    public boolean contains(Tenant tenant) {
        if (tenants == null || tenants.length == 0) {
            return false;
        }
        for (Tenant t : tenants) {
            if (t == tenant) {
                return true;
            }
        }
        return false;
    }


    public int getNumTenants() {
        return tenants.length;
    }


    /**
     * @return returns address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Удалить всех жильцов из этого жилища
     */
    public void deleteTenants() {
        tenants = new Tenant[0];
    }

    /** Это вообще нормальный подход - возвращать массив объектов?
     * @return массив жильцов этого жилища
     */
    public Tenant[] getTenants() {
        return tenants;
    }

    public void setArea(int area) {
        this.area = area;
    }
}
