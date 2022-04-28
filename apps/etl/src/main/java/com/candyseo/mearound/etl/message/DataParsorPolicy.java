package com.candyseo.mearound.etl.message;

public interface DataParsorPolicy<T, S> {
    
    T parseString(S line);

}
