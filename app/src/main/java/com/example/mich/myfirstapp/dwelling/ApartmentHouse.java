package com.example.mich.myfirstapp.dwelling;

/**
 * ApartmentHouse - Это многоквартирный дом (например 9-этажка)
 */
public class ApartmentHouse extends Dwelling {

    private final Apartment[] apartments;

    ApartmentHouse(String address, int constructionYear, int area, int totalApartments) {
        super(address, constructionYear, area);
        apartments = new Apartment[totalApartments];

        for (int i = 0; i < apartments.length; i++) {
            apartments[i] = new Apartment(address, constructionYear, area / totalApartments, i, false);
        }
    }

    /**
     * Заселение жильца в квартирный дом ("регистрация"), но не в квартиру этого дома
     * Малополезный метод, доставшийся в наследство от Жилища.
     * Использовать лучше: moveInApartment(Tenant tenant, int apartmentNum)
     *
     * @param tenant - жилец
     */
    @Override
    public void moveIn(Tenant tenant) {
        if (tenant == null) {
            throw new RuntimeException("tenant can't be null");
        }

        for (Apartment apartment : apartments) {
            if (!apartment.contains(tenant)) {
                super.moveIn(tenant);
                break;
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


    /**
     * метод заселения жильца в квартирный дом в конкретную квартиру (а также в общий список дома)
     * ЭТО ОСНОВНОЙ ПРАВИЛЬНЫЙ МЕТОД
     *
     * @param tenant        - заселяемый жилец
     * @param appartmentNum - номер квартиры в которую заселяем
     */
    public void moveInApartment(Tenant tenant, int appartmentNum) {
        if (existsApartment(appartmentNum)) {
            apartments[appartmentNum].moveIn(tenant);
            this.moveIn(tenant); // Надёжнее (но наверняка медленнее) сделать тут tenantsRegistration()
        }
    }

    /**
     * @param apartmentNum - некий номер квартиры (zero-based)
     * @return можно ли туда заселить жильца
     * пока проверяется только что такой номер квартиры есть, но может добавиться что-то ещё
     */
    public boolean existsApartment(int apartmentNum) {
        return (apartmentNum < getNumApartments());

    }

    /**
     * Наверное важно для каких-то вопросов обращаться к общему списку жильцов дома. Но он может отличаться, если в квартиру вселили жильца
     * как в "отдельное жилище"
     * метод используется для "синхронизации" списков жильцов отдельных квартир и общего списка жильцов всего дома
     */
    public void tenantsRegistration() {
        this.deleteTenants(); // очистка общего списка жильцов дома
        for (Apartment apartment : apartments) {
            for (Tenant tenant : apartment.getTenants()) {
                this.moveIn(tenant); // Занесение в общий список жильцов из каждой квартиры
            }
        }
    }

    /**
     * @return массив квартир этого дома
     */
    public Apartment[] getApartments() {
        return apartments;
    }

    /**
     * @return количество квартир этого дома
     */
    public int getNumApartments() {
        return apartments.length;
    }

    /**
     * @param apartmentNum - номер квартиры, которую надо получить
     * @return одну квартиру этого дома по номеру
     */
    public Apartment getApartment(int apartmentNum) {
        return apartments[apartmentNum];
    }

    /**
     * Установить площадь отдельной квартиры
     *
     * @param appartmentArea - площадь
     * @param appartmentNum  - номер квартиры
     * @param areaIsSet      - устанавливается "по-настоящему" или с потолка
     */
    public void setAppartmentArea(int appartmentArea, int appartmentNum, boolean areaIsSet) {
        apartments[appartmentNum].setArea(appartmentArea, areaIsSet);
    }

}
