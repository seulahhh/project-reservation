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
     * Customer - 매장 페이지 진입
     */
    @GetMapping("/customer/stores")
    public String customerStoresPage (Model model) {
        // todo 기본 이름순 정렬이 된 상태에서 보여줄 것 인지, 빈 리스트를 보여줄 것인지 검토
        // 정렬 기준이면 해당컨트롤러위치 옮겨야함
        return "customer/store";
    }

    /**
     * Manager - 매장 등록하는 페이지 진입
     */
    @GetMapping("/manager/store/new")
    public String customerStoresPage () {
        return "manager/store-new";
    }
}
