package kuit2.server.service;

import kuit2.server.dao.MenuDao;
import kuit2.server.dto.menu.GetMenuResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuDao menuDao;

    public List<GetMenuResponse> getMenus(long storeId) {
        log.info("MenuService.getMenus");
        return menuDao.getMenus(storeId);
    }
}
