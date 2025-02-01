package com.project.global.dto.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class CreateReviewForm {
    private Long id;

    private Long reservationId;
    private Long customerId;
    private Long storeId;

    private String title;
    private String body;
    private Double rating;
}
