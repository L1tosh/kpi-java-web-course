package org.example.spacecatsmarket.domain;

import lombok.*;
import org.example.spacecatsmarket.common.OrderStatus;

import java.util.List;

@Data
@Builder
public class Order {

    Long id;
    List<Product> products;
    Double price;
    OrderStatus status;

}
