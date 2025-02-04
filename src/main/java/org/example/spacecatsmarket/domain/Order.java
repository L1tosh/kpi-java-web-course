package org.example.spacecatsmarket.domain;

import lombok.Builder;
import lombok.Data;
import org.example.spacecatsmarket.common.OrderStatus;

import java.util.List;

@Data
@Builder
public class Order {

    Long id;
    List<Product> products;
    OrderStatus status;

}
