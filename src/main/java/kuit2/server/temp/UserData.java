package kuit2.server.temp;

// JSON으로 받은 data를 변환한 객채 : UserData

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserData {
    private String nickname;
    private int age;
}
