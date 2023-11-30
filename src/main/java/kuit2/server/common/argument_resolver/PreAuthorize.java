package kuit2.server.common.argument_resolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)              // 적용 target : 파라미터
@Retention(RetentionPolicy.RUNTIME)         // 유효시간 : runtime
public @interface PreAuthorize {
}
