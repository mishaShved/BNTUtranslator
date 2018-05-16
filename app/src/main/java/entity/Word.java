package entity;

/**
 * Created by misha on 18.2.18.
 */

public class Word {
    private String rus;
    private String bel;
    private String eng;
    public Word() {
    }
    public Word(String rus, String bel, String eng) {
        this.rus = rus;
        this.bel = bel;
        this.eng = eng;
    }
    public String getBel() {
        return capitalize(bel);
    }
    public String getEng() {
        return capitalize(eng);
    }
    public String getRus() {
        return capitalize(rus);
    }
    private String capitalize(String str){
        if(str.length() < 2){
            return "lol";
        }
        return str.substring(0,1).toUpperCase() + str.substring(1).toLowerCase();
    }
    @Override
    public String toString() {
        return getBel() + " " + getEng() + " " + getRus();
    }
}
