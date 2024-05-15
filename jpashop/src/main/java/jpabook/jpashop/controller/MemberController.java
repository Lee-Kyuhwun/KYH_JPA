package jpabook.jpashop.controller;


import jakarta.validation.Valid;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createMemberForm(Model model) { // Model은 컨트롤러에서 뷰로 값을 전달할 때 사용
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm memberForm, BindingResult result) {
        // BindREsult는 @Valid로 인해 검증 오류가 발생하면 이 오류를 담아서 컨트롤러에 전달해줌
        if(result.hasErrors()){
           return "members/createMemberForm"; // 에러가 있으면 다시 입력 폼으로
        }
        Address address = new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());
        Member member = new Member();
        member.setUserName(memberForm.getName());
        member.setAddress(address);
        memberService.join(member);
        return "redirect:/";
    }
}
