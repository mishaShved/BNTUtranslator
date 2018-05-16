package entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by misha on 18.2.18.
 */

public class Dictionary implements Iterable<Word>{
    private List<Word> dictionary;
    private String name;
    public Dictionary() {
        this.dictionary = new ArrayList<>();
    }
    public Dictionary(String name) {
        this.dictionary = new ArrayList<>();
        this.name = name;
    }
    public void addWord(Word word){
        dictionary.add(word);
    }
    public Word getWord(int i){
        if ( i >= 0 && i < dictionary.size()){
            return dictionary.get(i);
        }else{
            return null;
        }
    }
    public List<Word> getDictionary(){
        return this.dictionary;
    }
    @Override
    public String toString() {
        String res = name;
        for(Word el: dictionary){
            res += "\n" + el;
        }
        return res;
    }
    @Override
    public Iterator<Word> iterator() {
        return dictionary.iterator();
    }
}
