package com.appicenter.demo.AppicenterInventoryService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InventoryController {

    @Value("${server.port}")
    private String port;

    @Value("${service.info}")
    private String info;

    @RequestMapping("/")
    public String getInv(){
        return info+" running at "+port;
    }
}
