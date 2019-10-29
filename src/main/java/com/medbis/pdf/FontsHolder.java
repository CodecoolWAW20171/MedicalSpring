package com.medbis.pdf;

import com.itextpdf.text.Font;

class FontsHolder {
    private static FontsHolder ourInstance = new FontsHolder();

    private static Font subFont;
    private static Font largeBoldFont;
    private static Font smallBoldFont;

    private Font getSubFont(){
        if(subFont==null){
            subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16);
        }
        return subFont;
    }

    private Font getSmallBoldFont(){
        if(smallBoldFont==null){
            smallBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
                    Font.BOLD);
        }
        return smallBoldFont;
    }

    private Font getLargeBoldFont(){
        if(largeBoldFont == null){
            largeBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
        }
        return largeBoldFont;
    }

    Font getFont(String font){
        switch (font){
            case "subFont": return getSubFont();
            case "smallBoldFont": return getSmallBoldFont();
            case "largeBoldFont": return getLargeBoldFont();
        }
        return getSubFont();
    }

    static FontsHolder getInstance() {
        return ourInstance;
    }

    private FontsHolder() {
    }
}
