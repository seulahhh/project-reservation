package com.project.reservation.util;


import com.project.reservation.persistence.entity.QStore;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

// 하버사인 공식 -> QueryDSL을 이용해서 정리
@Component
@RequiredArgsConstructor
public class DistanceCalculator {
    public static NumberExpression<Double> calculate (Double lat, Double lnt, QStore qStore) {
        // :latitude 를 radians 로 계산
        NumberExpression<Double> rLat =
                Expressions.numberTemplate(Double.class, "radians({0})", lat);

        // 계산된 latitude -> 코사인 계산
        NumberExpression<Double> cosLatitude =
                Expressions.numberTemplate(Double.class, "cos({0})", rLat);
        NumberExpression<Double> cosSubwayLatitude =
                Expressions.numberTemplate(Double.class, "cos(radians({0}))", qStore.lat);

        // 계산된 latitude -> 사인 계산
        NumberExpression<Double> sinLatitude =
                Expressions.numberTemplate(Double.class, "sin({0})", rLat);
        NumberExpression<Double> sinSubWayLatitude =
                Expressions.numberTemplate(Double.class, "sin(radians({0}))", qStore.lat);
        // 사이 거리 계산
        NumberExpression<Double> cosLongitude =
                Expressions.numberTemplate(Double.class, "cos(radians({0}) - radians({1}))", qStore.lnt, lnt);

        NumberExpression<Double> acosExpression =
                Expressions.numberTemplate(Double.class, "acos({0})", cosLatitude.multiply(cosSubwayLatitude).multiply(cosLongitude).add(sinLatitude.multiply(sinSubWayLatitude)));

        // 최종 계산
        NumberExpression<Double> distanceExpression =
                Expressions.numberTemplate(Double.class, "6371 * {0}", acosExpression);

        NumberExpression<Double> roundedExpression = Expressions.numberTemplate(Double.class, "round ({0}, 2)",
                                   distanceExpression);
        return roundedExpression;
    }
}

