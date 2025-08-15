package com.code4cause.qventor.service;

import com.code4cause.qventor.model.Warehouse;
import com.code4cause.qventor.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WarehouseService {


    private final WarehouseRepository warehouseRepository;

    @Autowired
    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    //Update Warehouse info through warehouse id
    public Warehouse updateWarehouse(Long id , Warehouse warehouse){
        Warehouse updateWarehouse = warehouseRepository.findById(id).orElseThrow(
                () -> new RuntimeException("No warehouse found")
        );

        updateWarehouse.setWarehouseName(warehouse.getWarehouseName());
        updateWarehouse.setLocation(warehouse.getLocation());

        return warehouseRepository.save(updateWarehouse);
    }


    /**
     *  Set warehouse active/inActive by Warehouse name
     *  It makes it active if it's inactive or vice versa
     * */
    public Warehouse activateOrDeactivateWarehouse(String warehouseName){
        Warehouse warehouse = warehouseRepository.findByWarehouseName(warehouseName).orElseThrow(
                ()  -> new RuntimeException("Warehouse not found with this name")
        );

        warehouse.setEnabled(!warehouse.isEnabled());

        return warehouseRepository.save(warehouse);
    }

    /**
     *  Set warehouse active/inActive by Warehouse Id
     *  It makes it active if it's inactive or vice versa
     * */
    public Warehouse activateOrDeactivateWarehouse(Long id){
        Warehouse warehouse = warehouseRepository.findById(id).orElseThrow(
                ()  -> new RuntimeException("Warehouse not found with this name")
        );

        warehouse.setEnabled(!warehouse.isEnabled());

        return warehouseRepository.save(warehouse);
    }
}
