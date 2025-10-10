package com.product.delivery.system.interfaces;

public interface Criteria<T>
{
    boolean isApplicable(T t);
}
