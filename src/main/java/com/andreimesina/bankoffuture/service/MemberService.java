package com.andreimesina.bankoffuture.service;

import com.andreimesina.bankoffuture.model.Member;
import com.andreimesina.bankoffuture.model.MemberDetails;
import com.andreimesina.bankoffuture.model.dao.MemberDao;

public class MemberService {

    private static MemberService instance;

    private MemberDao memberDao;

    private MemberService() {
        memberDao = MemberDao.getInstance();
    }

    public static synchronized MemberService getInstance() {
        if (instance == null) {
            instance = new MemberService();
        }

        return instance;
    }

    public Integer registerNewMember(Member member, MemberDetails memberDetails) {
        // TODO: password encryption
        Integer memberId = memberDao.insertNewMember(member.getUsername(), member.getPassword());

        if (memberId != null) {
            memberDetails.setMemberId(memberId);
            memberDao.insertMemberDetails(memberDetails);
        }

        return memberId;
    }

    public MemberDetails getMemberDetails(int memberId) {
        return memberDao.getMemberDetails(memberId);
    }

    public void updateMemberDetails(MemberDetails memberDetails) {
        memberDao.updateMemberDetails(memberDetails);
    }

    public Integer getMemberIdByCredentials(String username, String password) {
        return memberDao.getMemberIdByCredentials(username, password);
    }

    public void removeMember(int memberId) {
        memberDao.removeMember(memberId);
    }
}
