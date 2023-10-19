package com.worldline.sips.helper;

import java.lang.reflect.Field;
import java.util.Comparator;

public class AlphabeticalFieldComparator implements Comparator<Field> {
    @Override
    public int compare(Field o1, Field o2) {
        return o1.getName().compareToIgnoreCase(o2.getName());
    }
}
