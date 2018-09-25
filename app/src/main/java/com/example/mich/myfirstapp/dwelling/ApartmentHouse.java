package com.example.mich.myfirstapp.dwelling;

/**
 * ApartmentHouse - Это многоквартирный дом (например 9-этажка)
 */
public class ApartmentHouse extends Dwelling {

    private final Apartment[] apartments;

    ApartmentHouse(String address, int constructionYear, int area, int totalFlats) {
        super(address, constructionYear, area);
        apartments = new Apartment[totalFlats];

        for (int i = 0; i < apartments.length; i++) {
            apartments[i] = new Apartment(address, constructionYear, area / totalFlats, i);
        }
    }

    /**
     * Заселение жильца в квартирный дом ("регистрация"), при условии, что он уже заселен в какую-то квартиру этого дома
     *
     * @param tenant - жилец
     */
    @Override
    public void moveIn(Tenant tenant) {
        if (tenant == null) {
            throw new RuntimeException("tenant can't be null");
        }

        for (Apartment apartment : apartments) {
            if (apartment.contains(tenant)) {
                super.moveIn(tenant);
                break;
            }
        }
    }

    /**
     * метод заселения жильца в квартирный дом в конкретную квартиру (и в дом в целом)
     *
     * @param tenant  - заселяемый жилец
     * @param flatNum - номер квартиры в которую
     *                (жилец реально заселяется при условии, что ещё там не живёт)
     */
    public void moveInFlat(Tenant tenant, int flatNum) {
        apartments[flatNum].moveIn(tenant);
        this.moveIn(tenant);
    }

    /**
     * метод для "синхронизации" списков жильцов отдельных квартир и списка жильцов всего дома
     */
    public void tenantsRegistration() {
        this.deleteTenants(); // очистка списка жильцов этого дома
        for (Apartment apartment : apartments) {
            for (Tenant tenant : apartment.getTenants()) {
                this.moveIn(tenant);
            }
        }
    }

    /**
     * Выселение жильца из квартирного дома ("выписка"), а также из всех квартир, в которых он жил
     *
     * @param tenant - выселяемый жилец
     */
    @Override
    public void moveOut(Tenant tenant) {
        super.moveOut(tenant);
        for (Apartment apartment : apartments) {
            if (apartment.contains(tenant)) {
                apartment.moveOut(tenant);
            }
        }
    }
}
