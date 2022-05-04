package com.blank.ecommerce.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "通用id对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TableId {
    @Schema(description = "数据表主键列表")
    private List<Id> ids;

    @Schema(description = "数据表记录主键对象")
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Id {
        @Schema(description = "数据表记录主键")
        private Long id;
    }
}
