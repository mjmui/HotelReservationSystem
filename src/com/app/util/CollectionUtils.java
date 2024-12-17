package com.app.util;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtils {
    
    public static <T> ArrayList<T> initializeList(List<T> list) {
        return list != null ? new ArrayList<>(list) : new ArrayList<>();
    }
}
