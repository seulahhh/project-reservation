package com.project.global.dto.form;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddStoreForm {
    private Long managerId;

    private String name;
    private String number;

    private Double lat;
    private Double lnt;
}
