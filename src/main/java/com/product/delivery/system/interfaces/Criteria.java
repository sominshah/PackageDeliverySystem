package com.product.delivery.system.interfaces;

public interface Criteria<T>
{
    public boolean isApplicable(T t);
}
