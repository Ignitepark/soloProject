package com.jin.member;

import java.util.List;
import java.util.Scanner;

public class MemberService {
	Scanner scn = new Scanner(System.in);
	MemberManager mm = MemberManager.getInstance();
	int menu = 0;

	public void addMember() {
		Member member = new Member();
		System.out.print("회원 ID 입력 > ");
		String id = scn.nextLine();
		System.out.print("회원 비밀번호 입력 > ");
		String pw = scn.nextLine();
		System.out.print("회원 이름 입력 > ");
		String name = scn.nextLine();
		System.out.print("회원 성별 입력 > ");
		String gender = scn.nextLine();

		member.setMemberId(id);
		member.setMemberPw(pw);
		member.setMemberName(name);
		member.setMemberGender(gender);

		mm.addMember(member);

	}

	public void adminAddMember() {
		Member member = new Member();
		System.out.print("회원 ID 입력 > ");
		String id = scn.nextLine();
		System.out.print("회원 비밀번호 입력 > ");
		String pw = scn.nextLine();
		System.out.print("회원 이름 입력 > ");
		String name = scn.nextLine();
		System.out.print("회원 성별 입력 > ");
		String gender = scn.nextLine();
		System.out.println("역할 : 0.일반사용자, 1.관리자");
		System.out.print("회원 역할 입력 > ");
		int role = Integer.parseInt(scn.nextLine());

		member.setMemberId(id);
		member.setMemberPw(pw);
		member.setMemberName(name);
		member.setMemberGender(gender);
		member.setRole(role);

		mm.adminAddMember(member);
	}

	public void checkPoint() {
		System.out.println("-------------포인트 확인-------------");
		if (MemberManager.mem != null) {
			mm.showGameInfo(MemberManager.mem);
		} else {
			System.out.println("로그인 하십시오!");
		}
		System.out.println("----------------------------------");
	}

	public void miniGame() {
		while (true) {
			System.out.println("-------------미니 게임-------------");
			System.out.println("| 1.숫자야구 | 2.가위바위보 | 9.로그아웃 |");
			System.out.print("메뉴 입력 > ");
			int selectNo = Integer.parseInt(scn.nextLine());
			if (selectNo == 1) {
				mm.numberBaseball();
			} else if (selectNo == 2) {

			} else if (selectNo == 3) {

			} else if (selectNo == 4) {

			} else if (selectNo == 5) {

			} else if (selectNo == 6) {

			} else if (selectNo == 7) {

			} else if (selectNo == 8) {

			} else if (selectNo == 9) {
				break;
			}
		}
	}

	public void sendPoint() {
		System.out.println("보낼 포인트를 입력하시오 > ");
		int point = Integer.parseInt(scn.nextLine());
		if (mm.judgePoint(point)) {
			System.out.println("받을 회원의 번호를 입력하시오 > ");
			int memberNo = Integer.parseInt(scn.nextLine());
			int result = mm.sendPoint(memberNo, point);
			System.out.println(result == 2 ? "포인트 전송 완료" : "포인트 전송 실패");
		} else {
			System.out.println("포인트가 부족합니다.");
		}
	}

	public void showMemberInfo() {
		System.out.print(
				"회원번호 : " + MemberManager.mem.getMemberNo() + ", ID : " + MemberManager.mem.getMemberId() + ", 이름 : "
						+ MemberManager.mem.getMemberName() + ", 성별 : " + MemberManager.mem.getMemberGender() + "\n");
	}

	public void showAllMemberInfo() {
		List<Member> list = MemberManager.getInstance().showAllMemberInfo();
		for (Member member : list) {
			System.out.println("회원번호 : " + member.getMemberNo() + ", ID : " + member.getMemberId() + ", 이름 : "
					+ member.getMemberName() + ", 성별 : " + member.getMemberGender() + ", 레벨 : "
					+ member.getMemberLevel() + ", 포인트 : " + member.getMemberPoint());
		}
	}
}
