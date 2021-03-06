package io.github.vipcxj.beanknife.runtime.converters;

import io.github.vipcxj.beanknife.runtime.PropertyConverter;

public class NullDoubleAsZeroConverter implements PropertyConverter<Double, Double> {
    @Override
    public Double convert(Double value) {
        return value != null ? value : 0.0;
    }

    @Override
    public Double convertBack(Double value) {
        return value;
    }
}
