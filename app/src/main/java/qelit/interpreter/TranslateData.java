package qelit.interpreter;

import java.util.ArrayList;

/**
 * Created by Qelit on 07.04.2017.
 */

public class TranslateData {
    private int code;
    private String lang;
    private ArrayList<String> text;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public ArrayList<String> getText() {
        return text;
    }

    public void setText(ArrayList<String> text) {
        this.text = text;
    }
}
