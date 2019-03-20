package moria.utils;

public enum Categories {

    // odchozi
    FOOD(111),
    ALCOHOL(112),
    APPAREL_AND_FASHION(113),
    HOME_EQUIPMENT(114),
    FUEL(115),
    UTILITIES(116),
    TOBACCO_AND_PRESS(117),
    PHONE_TV_INTERNET_ETC(118),
    FARE(119),
    RENT(120),
    REAL_ESTATE(121),
    SPORT_AND_LEISURE(122),
    HEALTH_AND_BEAUTY(123),
    ENTERTAINMENT(124),
    TRAVELLING_AND_ACCOMMODATION(125),
    ELECTRONICS(126),
    GAMBLING(127),
    LOANS_AND_MORTGAGES(128),
    INSURANCE(129),
    GIFTS(130),
    OTHER(131),

    // prichozi
    I_SALARY_OR_WAGE(11),
    I_PENSION(12),
    I_SOCIAL_ASSISTANCE(13),
    I_BUSINESS(14),
    I_GAMBLING(15),
    I_RENT(16),
    I_LOANS(17),
    I_POCKET_MONEY(18),
    I_GIFTS(19),
    I_OTHER(20),

    UNCATEGORIZED(0);

    private final int value;

    Categories(final int newValue) {
        value = newValue;
    }

    public int getValue() { return value; }

    // vytahne jmeno kategorie podle id
    public static String getCategoryById(int value){
        for(Categories e : Categories.values()){
            if(value == e.value) return e.name();
        }
        return null;
    }

}
