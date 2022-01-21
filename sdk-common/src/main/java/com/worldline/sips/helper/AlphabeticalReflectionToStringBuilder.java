package com.worldline.sips.helper;

import org.apache.commons.lang3.builder.ToStringStyle;

public class AlphabeticalReflectionToStringBuilder extends SortedReflectionToStringBuilder {

    public AlphabeticalReflectionToStringBuilder(Object object, ToStringStyle style) {
        super(object, style);
        setComparator(new AlphabeticalFieldComparator());
    }

}
