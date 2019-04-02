package moria.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChildTransaction {
    private String id;
    private int categoryId;
    private BigDecimal amount;
}
