package com.project.reservation.model.dto.form;

import com.project.reservation.persistence.entity.Review;
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
