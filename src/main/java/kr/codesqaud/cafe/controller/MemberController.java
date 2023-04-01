package kr.codesqaud.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.NoSuchElementException;

import kr.codesqaud.cafe.dto.ProfileEditRequestDto;
import kr.codesqaud.cafe.dto.SignUpRequestDto;
import kr.codesqaud.cafe.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public String findAll(Model model){
        model.addAttribute("memberResponsesDto",memberService.findAll());
        return "/members";
    }

    @PostMapping("/signUp")
    public String signUp(@ModelAttribute("signUpRequestDto") @Validated SignUpRequestDto signUpRequestDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "member/signUp";
        }
        memberService.signUp(signUpRequestDto);
        return "redirect:/member";
    }


    @GetMapping("/{id}")
    public String profile(@PathVariable String id, Model model){
        try {
            model.addAttribute("memberResponsesDto",memberService.findById(id));
            return "/profile";
        }catch (NoSuchElementException e){
            return "error/404";
        }
    }

    @PutMapping("/{id}")
    public String editProfile(@ModelAttribute @Validated ProfileEditRequestDto profileEditRequestDto, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "member/profiledEdit";
        }

        memberService.update(profileEditRequestDto);
        redirectAttributes.addAttribute("id",profileEditRequestDto.getId());
        return "redirect:/member/{id}";
    }

    @GetMapping("/{id}/edit")
    public String profileEditForm(@PathVariable String id, Model model){
        model.addAttribute("profileEditRequest", ProfileEditRequestDto.of(memberService.findById(id)));
        return "/profileEdit";
    }

}
