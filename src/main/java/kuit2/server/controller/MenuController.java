package kuit2.server.controller;

import kuit2.server.common.response.BaseResponse;
import kuit2.server.dto.GetMenuResponse;
import kuit2.server.service.MenuService;
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
@RequestMapping("/stores/{category}")
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/{storeId}")
    public BaseResponse<List<GetMenuResponse>> getMenus(@PathVariable String category, @PathVariable long storeId) {
        log.info("MenuController.getMenus");
        return new BaseResponse<>(menuService.getMenus(category, storeId));
    }
}
