package com.project.member.service;

import com.project.global.dto.ReservationDto;
import com.project.member.persistence.entity.QManager;
import com.querydsl.core.QueryFactory;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final WebClient webClient;
    private final JPAQueryFactory queryFactory;
    QManager qmanager = QManager.manager;
    public ReservationDto sendConnectToServer() {
        String email = SecurityContextHolder.getContext()
                                              .getAuthentication()
                                              .getName();
        Long id = queryFactory.selectFrom(qmanager)
                             .select(qmanager.id)
                             .where(qmanager.email.eq(email))
                             .fetchFirst();

        ReservationDto res = webClient.get()
                                        .uri("/api/sse-connect/" + id)
                                        .header("AUTH-CODE", email)
                                        .retrieve()
                                        .bodyToMono(ReservationDto.class)
                                        .block();

        return res;
    }
}
