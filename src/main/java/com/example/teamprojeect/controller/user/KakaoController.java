package com.example.teamprojeect.controller.user;

import com.example.teamprojeect.domain.vo.user.UserVO;
import com.example.teamprojeect.service.KakaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
@RequiredArgsConstructor
@Slf4j
public class KakaoController {
    private final KakaoService kakaoService;

    @GetMapping("/login")
    public String kakaoCallback(@RequestParam String code, HttpSession session, Model model) throws Exception {
        log.info(code);
        String token = kakaoService.getKaKaoAccessToken(code);
        log.info("==================================info.toString()");
        session.setAttribute("token", token);
        session.setAttribute("userNumber", kakaoService.getKakaoInfo(token)); // userNumber 세션에 카카오 유저 넘버 넣음
        log.info("kakaoService.getKakaoInfo(token).toString()================================" + kakaoService.getKakaoInfo(token).toString());
        session.setAttribute("sessionCheck", "k");
        return "/main/main";
    }

    @GetMapping("/logout")
    public void kakaoLogout(HttpSession session, Model model) {
        log.info("logout");
        kakaoService.logoutKakao((String) session.getAttribute("token"));
        model.addAttribute("sessionCheck", "0");
        session.invalidate();
    }

//    @GetMapping("/login")
//    public RedirectView kakaoCallback(@RequestParam String code, HttpSession session, RedirectAttributes rttr) throws Exception {
//        log.info(code);
//        String token = kakaoService.getKaKaoAccessToken(code);
//
//
//        HashMap<String, Object> kakaoInfo = kakaoService.getKakaoInfo(token);
//        log.info("###access_Token#### : " + token);
//        log.info("###userInfo#### : " + kakaoInfo.get("email"));
//
//        UserVO userVO = new UserVO();
//        userVO.setUserEmail(kakaoInfo.get("email").toString());
//
//        if(kakaoService.kakaoLogin(userVO) == null){
//            boolean checkEmail=true;
//            log.info("널인가 " + tempUserSerivce.kakaoLogin(userVO));
//            rttr.addFlashAttribute("checkEmail",checkEmail);
//            return new RedirectView("user/login");
//        }else {
//            log.info("null 아닌가" + tempUserSerivce.kakaoLogin(userVO));
//            session.setAttribute("token", token);
//            session.setAttribute("num", userVO.getNum());
//            session.setAttribute("category", userVO.getCategory());
//            return new RedirectView("main/main");
//        }
//        return new RedirectView("main/main");
//    }
//
//    @GetMapping("/logout")
//    public RedirectView kakaoLogout(HttpSession session){
//        log.info("logout");
//        log.info("logout"+session.getAttribute("token"));
//        kakaoService.logoutKakao((String)session.getAttribute("token"));
//        session.invalidate();
//        return new RedirectView("main/main");
//    }
}