package com.example.mich.myfirstapp.dwelling;


public abstract class Dwelling {
    private String address;
    private int constructionYear;
    private Tenant[] tenants;
    private int area;

    Dwelling(String address, int constructionYear, int area) {
        this.address = address;
        this.constructionYear = constructionYear;
        this.tenants = new Tenant[0];
        this.area = area;
    }

    /**
     * Заселение жильца в дом
     *
     * @param tenant -
     */
    public void moveIn(Tenant tenant) {

        boolean already = false;
        int oldNumTenants = tenants.length;

        // Проверяем, не живёт ли такой уже:
        if (oldNumTenants > 0) {
            for (int i = 0; i < tenants.length; i++) {
                already = tenant == tenants[i];
                if (already) return;
            }
        }

        // Если нет ещё такого, то заселяем:
        if (!already) { // TODO: если сюда дошло, то already будет ВСЕГДА false
            Tenant[] oldTenants = new Tenant[oldNumTenants];
            System.arraycopy(tenants, 0, oldTenants, 0, oldNumTenants);
            tenants = new Tenant[oldNumTenants + 1];
            System.arraycopy(oldTenants, 0, tenants, 0, oldNumTenants);
            tenants[tenants.length - 1] = tenant;
        }
    }

    /**
     * двигает что-то в какую-то группу
     *
     * @param tenants
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
}