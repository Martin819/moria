package moria.server.controller;

import moria.server.MockData.Item;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @RequestMapping("/items")
    public Item sendItems() {
        Item item = new Item();
        item.setCount(1);
        item.setName("Bagrinho");
        return item;
    }
}
