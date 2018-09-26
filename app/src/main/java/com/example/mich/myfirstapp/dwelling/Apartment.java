package com.example.mich.myfirstapp.dwelling;

/**
 * Apartment - это единичная квартира в многоквартирном доме.
 * поэтому она содержит property apartmentNumber
 * В принципе, Квартиры всегда используются только в Кв. Доме.
 при этом используется как массив, и номер в массиве сам по себе может служить номером квартиры
 Данное property может оказаться излишним и, вообще говоря, не совпадать с порядковым номером в массиве.
 */
public class Apartment extends Dwelling {

    private final int apartmentNumber;
    private boolean areaIsSet; // Флаг - установлена ли площадь квартиры "по-настоящему" или взята с потолка как например среднее по дому

    Apartment(String address, int constructionYear, int area, int apartmentNumber, boolean areaIsSet) {
        super(address, constructionYear, area);
        this.apartmentNumber = apartmentNumber;
        this.areaIsSet = areaIsSet;
    }

    /**
     * @return address plus zero-based flat number
     */
    @Override
    public String getAddress() {
        return super.getAddress() + ", #" + apartmentNumber;
    }

    /**
     * @return номер квартиры
     * @see Apartment
     */
    public int getApartmentNumber() {
        return apartmentNumber;
    }

    /**
     * Устанавливает площадь квартиры
     *
     * @param area      - площадь квартиры
     * @param areaIsSet - флаг "реальности" устанавливаемой  площади
     */
    public void setArea(int area, boolean areaIsSet) {
        super.setArea(area);
        this.areaIsSet = areaIsSet;
    }
}
