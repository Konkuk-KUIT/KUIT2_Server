package kuit2.server.util;

import lombok.Data;

@Data
public class Page {
    private int view = 10;      // 화면에 출력할 데이터 개수
    private int first;          // 페이지 첫번째 데이터
    private int last;           // 페이지 마지막 데이터

    public Page(int movePage, int view){
        moveNextPage(movePage, view);
    }

    public void moveNextPage(int movePage, int view){
        this.first = (view * movePage) - view + 1;
        this.last = movePage * view;
    }
}
