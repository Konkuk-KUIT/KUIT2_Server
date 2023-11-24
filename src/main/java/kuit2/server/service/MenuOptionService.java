package kuit2.server.service;

import kuit2.server.dao.MenuOptionDao;
import kuit2.server.dto.menuOption.MenuOptionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuOptionService {

    private final MenuOptionDao menuOptionDao;

    public List<MenuOptionResponse> getMenuOptions(long storeId, long menuId) {
        log.info("[MenuOptionService.getMenuOptions]");
        return menuOptionDao.getMenuOptions(storeId, menuId);
    }
}
