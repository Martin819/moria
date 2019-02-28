package moria.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@AllArgsConstructor
public class Rule {

    @Getter @Setter private String categoryName;
    @Getter @Setter private String ruleName;
    @Getter @Setter private HashMap<String, String> rules;


}
