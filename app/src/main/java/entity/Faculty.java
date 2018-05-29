package entity;


public enum Faculty {
    WTF("Bодазабеспячэнне\n"),
    IT("Iнфармацыйныя\nтэхналогіі"),
    SF("Будаўніцтва\nАрхітэктура"),
    FTK("Транспартныя\nКамунікацыі"),
    ATF("Эканоміка\n"),
    FES("Энергетычнае\nбудаўніцтва "),
    IPF("Педагогіка\n");


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
