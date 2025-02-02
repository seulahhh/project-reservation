package com.project.member.client;

import com.project.global.dto.LocationDto;
import com.project.global.dto.StoreDto;
import com.project.global.dto.form.AddStoreForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreApiClient {
    private final WebClient webClient;

    /**
     * 거리순으로 매장 리스트 가져오는 api 호출
     */
    public List<StoreDto> getStoreListOrderByDistance (
            LocationDto locationDto) {
        List<StoreDto> res = webClient.get()
                                      .uri(uriBuilder ->
                                                   uriBuilder.path("/api/stores/sort-by-distance")
                                                             .queryParam("lat"
                                                                     , locationDto.getLat())
                                                             .queryParam("lnt"
                                                                     , locationDto.getLnt())
                                                             .build())
                                      .retrieve()
                                      .bodyToFlux(StoreDto.class)
                                      .collectList()
                                      .block();
        return res;
    }

    /**
     * 이름순으로 매장 리스트 가져오는 api 호출
     */
    public List<StoreDto> getStoreListOrderByName (
            LocationDto locationDto) {
        List<StoreDto> res = webClient.get()
                                      .uri(uriBuilder ->
                                                   uriBuilder.path("/api/stores/sort-by-name")
                                                             .queryParam("lat"
                                                                     , locationDto.getLat())
                                                             .queryParam("lnt"
                                                                     , locationDto.getLnt())
                                                             .build())
                                      .retrieve()
                                      .bodyToFlux(StoreDto.class)
                                      .collectList()
                                      .block();
        return res;
    }

    /**
     * 별점순으로 매장 리스트 가져오는 api 호출
     */
    public List<StoreDto> getStoreListOrderByRating (
            LocationDto locationDto) {
        List<StoreDto> res = webClient.get()
                                      .uri(uriBuilder ->
                                                   uriBuilder.path("/api/stores/sort-by-rating")
                                                             .queryParam("lat"
                                                                     , locationDto.getLat())
                                                             .queryParam("lnt"
                                                                     , locationDto.getLnt())
                                                             .build())
                                      .retrieve()
                                      .bodyToFlux(StoreDto.class)
                                      .collectList()
                                      .block();
        return res;
    }

    /**
     * 매장 상세 조회 api 호출
     */
    public StoreDto getStoreDetail (Long storeId) {
        StoreDto res = webClient.get()
                                .uri("/api/stores/"+storeId)
                                .retrieve()
                                .bodyToMono(StoreDto.class)
                                .block();
        return res;
    }

    /**
     * 매장 등록하기 api 호출
     */
    public String registerStore (AddStoreForm form) {
        String res = webClient.post()
                                .uri("/api/stores/")
                                .retrieve()
                                .bodyToMono(String.class)
                                .block();
        return res;
    }
}
