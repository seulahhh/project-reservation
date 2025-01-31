package com.project.member.web.view;

import com.project.member.model.dto.StoreDto;
import com.project.member.persistence.entity.Store;
import com.project.member.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class StoreViewController {

    private final ManagerService managerService;
    /**
     * 사용자가 매장 조회 페이지로 이동중,
     * 위치를 먼저 불러 온 후 이동할 수 있도록
     * 로딩중 페이지를 먼저 보여줌
     */
    @GetMapping("/customer/stores/process")
    public String processingPage() {
        return "process-ing";
    }

    /**
     * Manager - 매장 등록하는 페이지 진입
     */
    @GetMapping("/manager/store/new")
    public String customerStoresPage () {
        return "manager/store-new";
    }
}
