package com.ofnicon.targetfocus.objects;

import java.util.ArrayList;
import java.util.List;

public class Notice {

    private String text;

    public Notice(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static List<String> getStringListFromNoticeList(List<Notice> notices) {
        List<String> result = new ArrayList<>();
        for (Notice notice : notices) {
            result.add(notice.getText());
        }
        return result;
    }

    public static List<Notice> getNoticeListFromStringList(List<String> strings) {
        List<Notice> result = new ArrayList<>();
        for (String string : strings) {
            result.add(new Notice(string));
        }
        return result;
    }

}
