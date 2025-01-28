package com.project.member.web.api;

import com.project.member.model.dto.StoreDto;
import com.project.member.model.dto.form.AddStoreForm;
import com.project.member.persistence.repository.StoreRepository;
import com.project.member.service.ManagerService;
import jakarta.servlet.ServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerApiController {
    private final ManagerService managerService;
    private final StoreRepository storeRepository;
    private final ServletRequest httpServletRequest;


    /**
     * 매장 등록하기
     */
    @PostMapping("/store/new")
    public String addStore (@ModelAttribute AddStoreForm form) {
        managerService.addStore(form);
        // todo 성공적으로 등록되었다는 alert 처리
        return "redirect:/manager/store";
    }

    /**
     * 내 매장 보기
     * - manager/home에서
     * 등록한 매장이 없을 경우 매장 보기 ui X, 매장 등록하기 ui
     * - 매장의 리뷰도 함께
     */
    @GetMapping("/store")
    public ModelAndView managerStorePage () {
        ModelAndView mv = new ModelAndView();
        StoreDto storeDto = managerService.getManagerStore();
        mv.addObject(storeDto);
        mv.setViewName("store/store-detail");
        return mv;
    }

    /**
     * 리뷰 삭제하기
     */
    @DeleteMapping("/store/reviews/{reviewId}")
    public String deleteReview(@PathVariable Long reviewId) {
        // todo 리뷰를 삭제할거냐는 confirm 처리
        managerService.deleteReview(reviewId);
        System.out.println("삭제완료");
        return "store/store-detail";
    }

    /**
     * 등록한 매장 정보 수정하기
     */
    @PutMapping("/store")
    public void updateStore() {
        // todo 추후 필요시 고려
    }
//    /**
//     * 등록한 매장 삭제하기
//     * - 본인이 등록한 매장에 한해
//     */
//    @DeleteMapping("/store")
//    public ModelAndView deleteStore (@RequestParam Long storeId, HttpServletResponse response, ModelAndView model) {
//        ModelAndView mv = new ModelAndView();
//        Message message = managerService.deleteStore(storeId);
//
//        response.setContentType("text/html; charset=UTF-8");
//        try {
//            PrintWriter writer = response.getWriter();
//            writer.println("'<script>alert('매장이 정상적으로 삭제되었습니다!'); location.href='/manager/home'</script>'");
//            writer.flush();
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return mv;
//    }
    // 리뷰도 다 같이 삭제가 잘 되는지 확인




    // 리뷰도 잘 보이는지 확인


    // 수정은 불가, 작성도 불가 / 삭제만 가능
}
