package com.worldline.sips.helper;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;

public class SortedReflectionToStringBuilder extends ReflectionToStringBuilder {

    private Comparator<Field> comparator;

    public SortedReflectionToStringBuilder(Object object, ToStringStyle style) {
        super(object, style);
    }


    public void setComparator(Comparator<Field> comparator) {
        this.comparator = comparator;
    }

    @Override
    protected void appendFieldsIn(Class<?> clazz) {
        if (clazz.isArray()) {
            this.reflectionAppendArray(this.getObject());
            return;
        }

        Field[] fields = clazz.getDeclaredFields();

        if (comparator != null) {
            Arrays.sort(fields, comparator);
        }
        AccessibleObject.setAccessible(fields, true);
        for (final Field field : fields) {
            final String fieldName = field.getName();
            if (this.accept(field)) {
                try {
                    // Warning: Field.get(Object) creates wrappers objects
                    // for primitive types.
                    final Object fieldValue = this.getValue(field);
                    if (!isExcludeNullValues() || fieldValue != null) {
                        this.append(fieldName, fieldValue);
                    }
                } catch (final IllegalAccessException ex) {
                    //this can't happen. Would get a Security exception
                    // instead
                    //throw a runtime exception in case the impossible
                    // happens.
                    throw new InternalError("Unexpected IllegalAccessException: " + ex.getMessage());
                }
            }
        }
    }


}


