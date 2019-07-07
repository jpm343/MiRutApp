package com.example.mirutapp.Model;

import androidx.room.TypeConverter;

public class CarTypeConverter {
    public static final String TAG = "CarTypeConverter";

    @TypeConverter
    public static Vehicle.CarType fromInteger(int carType) {
        if(carType == Vehicle.CarType.AUTO.getCode())
            return Vehicle.CarType.AUTO;
        else if(carType == Vehicle.CarType.CAMION.getCode())
            return Vehicle.CarType.CAMION;
        else if(carType == Vehicle.CarType.MOTO.getCode())
            return Vehicle.CarType.MOTO;
        else
            throw new IllegalArgumentException("Unrecognized Car Type. (0, AUTO; 1, CAMION; 2, MOTO;)");
    }

    @TypeConverter
    public static Integer toInteger(Vehicle.CarType carType) {
        return carType.getCode();
    }
}
