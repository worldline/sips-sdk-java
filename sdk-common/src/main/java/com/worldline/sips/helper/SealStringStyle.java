package com.worldline.sips.helper;

import java.time.YearMonth;
import java.util.Arrays;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.RecursiveToStringStyle;

/**
 * A {@link RecursiveToStringStyle} parametrized to the seal format
 *
 * @see com.worldline.sips.security.Sealable
 */
public final class SealStringStyle extends RecursiveToStringStyle {
  
    private SortedReflectionToStringBuilder reflectionToStringBuilder;

    public void setReflectionToStringBuilder(SortedReflectionToStringBuilder reflectionToStringBuilder) {
        this.reflectionToStringBuilder = reflectionToStringBuilder;
    }

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
            buffer.append(reflectionToStringBuilder.initFrom(value).toString());
        } else {
          if (value instanceof YearMonth) {
            value = ((YearMonth) value).toString().replace("-", "");
          }
          super.appendDetail(buffer, fieldName, value);
        }
    }

    @Override
    protected void appendDetail(StringBuffer buffer, String fieldName, Object[] array) {
      try {
        Arrays.sort(array);
      } catch (ClassCastException ignored) {
        System.out.println("test");
      }
      super.appendDetail(buffer, fieldName, array);
    }

    @Override
    protected boolean accept(Class<?> clazz) {
        return ! clazz.isEnum() && clazz.getPackage().getName().startsWith("com.worldline");
    }
}
