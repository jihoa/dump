package org.springframework.samples.petclinic.hello;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Api("HELLO")
@Controller("helloMemberController")
public class MemberController {

    private final MemberService memberService;

    //@Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/hello/members/new")
	@ApiOperation("HELLO_GET")
    public String createForm(Model model) {

		model.addAttribute("memberForm", new MemberForm());
		return "members/createMemberForm";
    }

    @PostMapping("/hello/members/new")
	@ApiOperation("HELLO_POST")
    public String create(@Valid MemberForm form) {

		Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

		Member member = new Member();
        member.setName(form.getName());
		member.setAddress(address);

        memberService.join(member);

        return "redirect:/hello/members";
    }

    @GetMapping("/hello/members")
	@ApiOperation("HELLO_MEMBERS")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

}
