package moria.server.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;

@NoArgsConstructor @AllArgsConstructor
public class Category {

    @Getter @Setter
    private HashMap<String, ArrayList<String>> categories;
}
