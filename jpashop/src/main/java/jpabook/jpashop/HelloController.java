package jpabook.jpashop;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class HelloController {


    @GetMapping("/")
    public String home(){ // Model은 컨트롤러에서 뷰로 값을 전달할 때 사용
        log.info("home controller");
        return "home"; // 화면 이름
    }
}
