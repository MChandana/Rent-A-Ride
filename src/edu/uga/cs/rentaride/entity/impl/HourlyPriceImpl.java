package edu.uga.cs.rentaride.entity.impl;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.HourlyPrice;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;
import edu.uga.cs.rentaride.persistence.impl.Persistence;
//import edu.uga.cs.rentaride.persistence.impl.Persistent;

import java.util.List;

public class HourlyPriceImpl extends Persistence implements HourlyPrice {

    long ID;
    String vehicleTypeName;
    int maxHrs;
    int price;
    VehicleType vehicleType;
    String vehicleType1;

    ObjectLayer objectLayer=null;

    public HourlyPriceImpl() {
        super(-1);
        this.vehicleTypeName = null;
        this.maxHrs = 0;
        this.price = 0;    }

    public HourlyPriceImpl(int maxHrs, int priceString, VehicleType  vehicleType) {
        super(-1);
        this.vehicleTypeName = vehicleType.getName();
        this.vehicleType=vehicleType;
        this.maxHrs = maxHrs;
        this.price = priceString;
    }
    
    public HourlyPriceImpl(int maxHrs, int priceString, String vehicleType) {
        super(-1);
        this.vehicleTypeName = vehicleType;
        this.maxHrs = maxHrs;
        this.price = priceString;
    }

    @Override
    public int getMaxHours() {
        return maxHrs;
    }

    @Override
    public void setMaxHours(int maxHours) throws RARException {
        this.maxHrs = maxHours;

    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public void setPrice(int price) throws RARException {
        this.price = price;

    }
    
    public String getVehicleTypeName() {
    	return vehicleTypeName;
    }

    @Override
    public VehicleType getVehicleType() {

        /*
        VehicleType vehicleType=null;
        VehicleType modelvehicleType=new VehicleTypeImpl();
        try {
            modelvehicleType.setName(vehicleTypeName);
            List<VehicleType> vehicleTypeList=getPersistenceLayer().restoreVehicleType(modelvehicleType);
            vehicleType=vehicleTypeList.get(0);
        } catch (RARException e) {
            e.printStackTrace();
        }
        */
        return vehicleType;
    }

    @Override
    public void setVehicleType(VehicleType vehicleType) throws RARException {

        /*List<VehicleType> vehicleTypeList=getPersistenceLayer().restoreVehicleType(vehicleType);
        vehicleType=vehicleTypeList.get(0);*/
        this.vehicleType=vehicleType;
        this.vehicleTypeName=vehicleType.getName();

    }

    @Override
    public long getId() {
        return ID;
    }

    @Override
    public void setId(long id) {
        this.ID=id;
    }

    @Override
    public boolean isPersistent() {
        return ID>0;
    }
}
