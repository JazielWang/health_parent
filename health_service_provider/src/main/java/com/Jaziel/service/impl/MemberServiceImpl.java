package com.Jaziel.service.impl;

import com.Jaziel.dao.MemberDao;
import com.Jaziel.pojo.Member;
import com.Jaziel.service.MemberService;
import com.Jaziel.utils.MD5Utils;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王杰
 * @date 2021/2/11 15:43
 */
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    @Override
    public void add(Member member) {
        if (member.getPassword() != null){
            member.setPassword(MD5Utils.md5(member.getPassword()));
        }
        memberDao.add(member);
    }

    @Override
    public List<Integer> countMemberByMonths(List<String> months) {
        List<Integer> memberCount = new ArrayList<>();

        for (String month : months) {
            String s  = month + ".31";
            Integer count = memberDao.findMemberCountBeforeDate(s);
            memberCount.add(count);
        }

        return memberCount;
    }
}
