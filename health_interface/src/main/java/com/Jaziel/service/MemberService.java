package com.Jaziel.service;

import com.Jaziel.pojo.Member;

import java.util.List;

/**
 * @author 王杰
 * @date 2021/2/11 15:32
 */
public interface MemberService {

    Member findByTelephone(String telephone);

    void add(Member member);

    List<Integer> countMemberByMonths(List<String> months);
}
