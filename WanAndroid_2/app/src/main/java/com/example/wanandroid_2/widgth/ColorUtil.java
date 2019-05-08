package com.example.wanandroid_2.widgth;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张十八 on 2019/5/5.
 */

public class ColorUtil {
    public static String getRandomColor() {
        List<String> colorList = new ArrayList<String>();
        colorList.add("#E78F8F");
        colorList.add("#F6BC7E");
        colorList.add("#90C5F0");
        colorList.add("#67CCB7");
        colorList.add("#F88F55");
        colorList.add("#91CED5");
        colorList.add("#C0AFD0");

        return colorList.get((int) (Math.random() * colorList.size()));
    }
}
