package moria.server.controller;

import moria.server.MockData.Item;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping(path = "/items", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Item sendItems() {
        Item item = new Item();
        item.setCount(1);
        item.setName("Bagrinho");
        return item;
    }
}
