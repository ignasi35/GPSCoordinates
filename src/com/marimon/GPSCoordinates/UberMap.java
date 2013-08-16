package com.marimon.GPSCoordinates;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ignasi
 * Date: 8/16/13
 * Time: 8:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class UberMap {

    private final Map<String, String> _map = new LinkedHashMap<String, String>() ;


    public UberMap put(String key, double value){
        return put(key, Double.toString(value));
    }

    public UberMap put(String key, String value){
        _map.put(key, value);
        return this ;
    }

    public Map<String, String> asMap(){
        return _map ;
    }
}
