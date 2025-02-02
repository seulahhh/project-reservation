package com.project.global.dto.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class KioskForm {
    private String email;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long StoreId;
}
