package com.project.member.model.dto.form;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddStoreForm {
    private String name;

    private String number;

    private Double lat;
    private Double lnt;
}
