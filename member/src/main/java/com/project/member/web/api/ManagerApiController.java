package com.project.member.web.api;

import com.project.member.model.dto.form.AddStoreForm;
import com.project.member.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerApiController {
    private final ManagerService managerService;

    @PostMapping("/store/new")
    public ModelAndView addStore (@ModelAttribute AddStoreForm form, ModelAndView mv) {
        String message = String.format("'%s' 매장이 성공적으로 등록되었습니다!", managerService.addStore(form).getBody());
        mv.addObject("submitSuccess", "매장이 정상적으로 등록되었습니다.");
        mv.addObject("redirectUrl", "/manager/store");
        mv.addObject("prevUrl", "/manager/store");
        // todo 등록된 매장이 없으면 매장 등록 버튼 활성화
        // 등록된 매장이 있으면 등록된 매장의 정보
        mv.setViewName("success");
        return mv;
    }


}
