package com.challenge.client;

import com.challenge.dto.RetornoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "potterAPIHousesClient", url = "${potterApi.url}")
public interface PotterAPIHousesClient {

    @RequestMapping(value = "/houses")
    RetornoDTO getHouses();
}
