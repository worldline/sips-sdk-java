package com.worldline.sips.helper;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.RecursiveToStringStyle;

import java.util.Arrays;

/**
 * A {@link RecursiveToStringStyle} parametrized to the seal format
 *
 * @see com.worldline.sips.security.Sealable
 */
public final class SealStringStyle extends RecursiveToStringStyle {
    public SealStringStyle() {
        super();
        setUseClassName(false);
        setUseIdentityHashCode(false);
        setUseFieldNames(false);
        setNullText(StringUtils.EMPTY);
        setContentStart(StringUtils.EMPTY);
        setContentEnd(StringUtils.EMPTY);
        setFieldSeparator(StringUtils.EMPTY);
        setArrayStart(StringUtils.EMPTY);
        setArrayEnd(StringUtils.EMPTY);
        setArraySeparator(StringUtils.EMPTY);
    }

    @Override
    public void appendDetail(StringBuffer buffer, String fieldName, Object value) {
        if (! ClassUtils.isPrimitiveWrapper(value.getClass()) &&
            ! String.class.equals(value.getClass()) &&
            accept(value.getClass())) {
            buffer.append(AlphabeticalReflectionToStringBuilder.toString(value, this));
        } else {
            super.appendDetail(buffer, fieldName, value);
        }
    }

    @Override
    protected void appendDetail(StringBuffer buffer, String fieldName, Object[] array) {
        Arrays.sort(array);
        super.appendDetail(buffer, fieldName, array);
    }

    @Override
    protected boolean accept(Class<?> clazz) {
        return ! clazz.isEnum() && clazz.getPackage().getName().startsWith("com.worldline");
    }
}
