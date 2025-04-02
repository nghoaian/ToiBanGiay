package tdtu.edu.vn.shoes_store.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class CategoriesDto {
    private Long id;
    private String name;

    @Builder
    public CategoriesDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
