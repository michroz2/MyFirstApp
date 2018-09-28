package com.example.mich.myfirstapp.dwelling;

import java.util.ArrayList;
import java.util.List;

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

    public static String[] findAddressByName(String name, Dwelling... dwellings) {
        // TODO DONE: найти адреса жильцов в жилищах по имени:)
        List<String> foundAddresses = new ArrayList<>();
        for (Dwelling aDwelling : dwellings) {
            if (aDwelling.livesHereByName(name)) {
                foundAddresses.add(aDwelling.getAddress());
            }
        }
        String[] result = new String[foundAddresses.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = foundAddresses.get(i);
        }
        return result;
    }

    public static Tenant[] findTenatsByAddress(String address, Dwelling... dwellings) {
        // TODO DONE: найти жильцов в жилищах по адресу - 2 :)
        // если предполагается, что может быть несколько жилищ с одинаковыми адресами
        PrivateHouse dTerminal = new PrivateHouse("dTerminal", 1999, 5000); // пофик эти параметры... .. нафиг вообще нужен этот дом ... внизу видно:


        for (Dwelling aDwelling : dwellings) {
            if (aDwelling.getAddress() == address) {
                dTerminal.moveInGroup(aDwelling.getTenants());
                // смысл, что всех найденных "заселяем" в Д-Терминал и они там фильтруются и получается уникальный массив "задержанных"
            }
        }
        return dTerminal.getTenants();
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

    /**
     * Это вообще нормальный подход - возвращать массив объектов?
     *
     * @return массив жильцов этого жилища
     */
    public Tenant[] getTenants() {
        return tenants;
    }

    public void setArea(int area) {
        this.area = area;
    }

    /**
     * @param name - имя по которому ищутся жильцы
     * @return количество совпадений в этом жилище
     */
    public int numTenantsFoundByName(String name) {
        int result = 0;
        for (int i = 0; i < getNumTenants(); i++) {
            if (tenants[i].getName().equals(name)) {
                result++;
            }
        }
        return result;
    }

    /**
     * @param name - имя по которому ищутся жильцы
     * @return есть ли хоть 1 совпадение имени среди жильцов в этом жилище
     */
    public boolean livesHereByName(String name) {
        for (int i = 0; i < getNumTenants(); i++) {
            if (tenants[i].getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param name
     * @return address если Жилец с таким именем тут живёт и null если нет
     */
    public String getAddressByTenantName(String name) {
        if (livesHereByName(name)) {
            return getAddress();
        }
        return null;
    }

}

