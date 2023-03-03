// This is basic data structure for the dataBase

package com.example.roombasic;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Word {
    // id will be generate automatically
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "english-word")
    private String word;

    @ColumnInfo(name = "chinese_meaning")
    private String chineseMeaning;

    @ColumnInfo(name = "chinese_invisible")
    private boolean chineseInvisible;

    // the constructor used to set the initial value of 'word' and 'chineseMeaning'
    public Word(String word, String chineseMeaning) {
        this.word = word;
        this.chineseMeaning = chineseMeaning;
    }

    public int getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public String getChineseMeaning() {
        return chineseMeaning;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setChineseMeaning(String chineseMeaning) {
        this.chineseMeaning = chineseMeaning;
    }

    public boolean isChineseInvisible() {
        return chineseInvisible;
    }

    public void setChineseInvisible(boolean chineseInvisible) {
        this.chineseInvisible = chineseInvisible;
    }
}
