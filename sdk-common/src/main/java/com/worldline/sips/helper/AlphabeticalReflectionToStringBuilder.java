package com.worldline.sips.helper;

import org.apache.commons.lang3.builder.ToStringStyle;

public class AlphabeticalReflectionToStringBuilder extends SortedReflectionToStringBuilder {

    private AlphabeticalReflectionToStringBuilder(Object object, ToStringStyle style) {
        super(object, style);
        setComparator(new AlphabeticalFieldComparator());
    }

    public static AlphabeticalReflectionToStringBuilder newInstance(Object object, SealStringStyle sealStringStyle) {
        AlphabeticalReflectionToStringBuilder res = new AlphabeticalReflectionToStringBuilder(object, sealStringStyle);
        sealStringStyle.setReflectionToStringBuilder(res);
        return res;
    }
}
