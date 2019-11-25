package com.feture.learnfilter.openapi;

import com.feture.learnfilter.model.SearchMembersReq;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/openapi/Member")
public class MemberController {

    @RequestMapping(value = "/SearchMembers",method = RequestMethod.POST)
    public String SearchMembers(SearchMembersReq searchMembersReq){
        System.out.println("查询会员："+searchMembersReq.toString());

        return "sucess";
    }
}
