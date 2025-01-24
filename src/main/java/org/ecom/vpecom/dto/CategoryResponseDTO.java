package org.ecom.vpecom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDTO {
    private List<CategoryDTO> category;
    private Integer pageNumber;
    private Integer dataLimit;
    private Long totalRecords;
    private Integer totalNumberOfPage;
    private boolean lastPage;


}
