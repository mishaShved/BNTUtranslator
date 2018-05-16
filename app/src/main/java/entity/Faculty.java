package entity;


public enum Faculty {
    WTF("Водоснабжение\n"),
    IT("Информационные\nтехнологии"),
    SF("Строительство\nАрхитектура"),
    FTK("Транспортные\nкоммуникации"),
    ATF("Экономика\n"),
    FES("Энергетическое\nстроительство "),
    IPF("Педагогика\n");


    private String nameFac;

    Faculty(String nameFac) {
        this.nameFac = nameFac;
    }

    public static Faculty getFacultyByName(String name){

        for (Faculty fac: Faculty.values() ) {
            if(fac.nameFac.equalsIgnoreCase(name)){
                return fac;
            }
        }
        return null;
    }

    public String getNameFac() {
        return nameFac;
    }
}
