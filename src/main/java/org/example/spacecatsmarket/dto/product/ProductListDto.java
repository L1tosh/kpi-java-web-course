package org.example.spacecatsmarket.dto.product;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class ProductListDto {

    List<ProductEntry> productEntries;

}
