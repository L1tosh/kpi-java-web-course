package org.example.spacecatsmarket.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq")
    @SequenceGenerator(name = "product_id_seq", sequenceName = "product_id_seq", allocationSize = 1)
    Long id;

    String name;
    String description;
    Double price;
    String unit;
    Integer amount;

    @ManyToMany(mappedBy = "products")
    List<OrderEntity> orders;

    @ManyToOne
    @JoinColumn(name = "category_id")
    CategoryEntity category;
}
