package com.worldline.sips.helper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.builder.ToStringSummary;

public class SortedReflectionToStringBuilder extends ReflectionToStringBuilder {

    private Comparator<Field> comparator;
    private final Map<Class<?>, Function<Object, String>> customSerializers = new HashMap<>();

    public SortedReflectionToStringBuilder(Object object, ToStringStyle style) {
        super(object, style);
    }

    /**
     * Get all the fields of a given type (include private and inherited fields from the class hierarchy)
     *
     * @param type
     * @return a list containing all the fields of the given type
     */
    private static List<Field> getAllFields(Class<?> type) {
        List<Field> fields = new ArrayList<>();
        List<List<Field>> temp = new ArrayList<>();
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            temp.add(Arrays.asList(c.getDeclaredFields()));
        }
        for (int i = temp.size() - 1; i >= 0; i--) {
            fields.addAll(temp.get(i));
        }
        return fields;
    }

    public void setComparator(Comparator<Field> comparator) {
        this.comparator = comparator;
    }

    @Override
    public String toString() {
        if (this.getObject() == null) {
            return super.toString();
        }
        Class<?> clazz = this.getObject().getClass();
        List<Field> allFields = getAllFields(clazz);
        if (comparator != null) {
            allFields.sort(comparator);
        }
        for (final Field field : allFields) {
            final String fieldName = field.getName();
            if (this.accept(field)) {
                try {
                    field.setAccessible(true);
                    // Warning: Field.get(Object) creates wrappers objects
                    // for primitive types.
                    Object fieldValue = this.getValue(field);
                    Function<Object, String> serializer = customSerializers.get(field.getType());
                    if (serializer != null) {
                      fieldValue = serializer.apply(fieldValue);
                    }
                    if (! isExcludeNullValues() || fieldValue != null) {
                        this.append(fieldName, fieldValue, ! field.isAnnotationPresent(ToStringSummary.class));
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
        getStyle().appendEnd(this.getStringBuffer(), this.getObject());
        return this.getStringBuffer().toString();
    }
    
    public void addSerializer(Class<?> clazz, Function<Object, String> serializer) {
      customSerializers.put(clazz, serializer);
    }
  public SortedReflectionToStringBuilder initFrom(Object value) {
    SortedReflectionToStringBuilder copy = new SortedReflectionToStringBuilder(value, getStyle());
    copy.comparator = this.comparator;
    copy.customSerializers.putAll(customSerializers);
    return copy;
  }
}


