package com.appicenter.demo.AppicenterInventoryService;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RefreshScope
@RequestMapping("")
public class InventoryController {

    @Value("${server.port}")
    private String port;

    @Value("${service.info}")
    private String info;

    @GetMapping("/getServiceInfo")
    public String getServiceInfo(){
        return info+" running at "+port;
    }

    @Autowired
    EurekaClient eurekaClient;

    @GetMapping("/products")
    public List<Products> getProducts() {
        //String usersUrl = "http://localhost:8080/users";

        InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka("product-service", true);
        String productUrl  = instanceInfo.getHomePageUrl()+"products";

        System.out.println("Product URL="+productUrl);
        RestTemplate restTemplate = new RestTemplate();
        // Use ParameterizedTypeReference to specify the type of the response
        ResponseEntity<List<Products>> response = restTemplate.exchange(
                productUrl,
                HttpMethod.GET,
                null,  // No request body
                new ParameterizedTypeReference<List<Products>>() {}
        );

        // Get the list of products from the response
        List<Products> products = response.getBody();

        // Now you can work with the list of products
        if (products != null) {
            for (Products product : products) {
                System.out.println(product.getName());
            }
        }

        return products;
    }

    @GetMapping("/productsByCategory/{category}")
    public List<Products> getProductsByCategory(@PathVariable String category) {

        InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka("product-service", true);
        String productUrl  = instanceInfo.getHomePageUrl()+"productsByCategory/"+category;

        System.out.println("Product Cat URL="+productUrl);
        RestTemplate restTemplate = new RestTemplate();
        // Use ParameterizedTypeReference to specify the type of the response
        ResponseEntity<List<Products>> response = restTemplate.exchange(
                productUrl,
                HttpMethod.GET,
                null,  // No request body
                new ParameterizedTypeReference<List<Products>>() {}
        );

        // Get the list of products from the response
        List<Products> products = response.getBody();

        // Now you can work with the list of products
        if (products != null) {
            for (Products product : products) {
                System.out.println(product.getName());
            }
        }

        return products;
    }
}
