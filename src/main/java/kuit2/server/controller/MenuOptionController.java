package kuit2.server.controller;

import kuit2.server.common.response.BaseResponse;
import kuit2.server.dto.menuOption.MenuOptionResponse;
import kuit2.server.service.MenuOptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/stores/{storeId}/menus/{menuId}/menuOptions")
public class MenuOptionController {

    private final MenuOptionService menuOptionService;

    /** *
     * 메뉴 옵션 조회
     */
    @GetMapping("")
    public BaseResponse<List<MenuOptionResponse>> getMenuOptions(@PathVariable long storeId,
                                                                 @PathVariable long menuId) {
        log.info("[MenuOptionController.getMenuOptions]");
        return new BaseResponse<>(menuOptionService.getMenuOptions(storeId, menuId));
    }
}
