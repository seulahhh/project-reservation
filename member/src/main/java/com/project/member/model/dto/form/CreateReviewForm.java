package com.project.member.model.dto.form;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateReviewForm {
    private Long id;
    private Long reservationId;
    private Long customerId;
    private Long storeId;

    private String title;
    private String body;
    private Double rating;
}
