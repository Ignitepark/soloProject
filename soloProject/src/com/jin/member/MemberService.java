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
			System.out.print("회원번호 : " + member.getMemberNo() + ", ID : " + member.getMemberId() + ", 이름 : "
					+ member.getMemberName() + ", 성별 : " + member.getMemberGender() + ", 레벨 : "
					+ member.getMemberLevel() + ", 포인트 : " + member.getMemberPoint());
			if (member.getRole() == -1) {
				System.out.println("  ※ ※ ※ 탈퇴한 회원입니다 ※ ※ ※");
			} else {
				System.out.println();
			}
		}
	}

	public void deleteMember() {
		System.out.println("회원 탈퇴 메뉴입니다");
		System.out.println("정말로 탈퇴하시겠습니까? (y/n)");
		System.out.print("입력 > ");
		String yesOrNo = scn.nextLine();
		if (yesOrNo.equals("y") || yesOrNo.equals("Y")) {
			System.out.println("탈퇴를 원하시면 아이디를 입력하십시오");
			System.out.print("입력 > ");
			String memberId = scn.nextLine();
			if (memberId.equals(MemberManager.mem.getMemberId())) {
				int result = mm.deleteMember();
				System.out.println(result == 1 ? "성공적으로 탈퇴하였습니다" : "회원 탈퇴에 실패하였습니다");
			}
		}
	}

	public void adminDeleteMember() {
		int result = 0;
		System.out.print("탈퇴 시킬 회원 번호를 입력하시오 > ");
		int memberNo = Integer.parseInt(scn.nextLine());
		System.out.println("정말 탈퇴 시키겠습니까? (y/n)");
		System.out.print("입력 > ");
		String yesOrNo = scn.nextLine();

		if (yesOrNo.equals("y") || yesOrNo.equals("Y")) {
			result = mm.adminDeleteMember(memberNo);
			System.out.println(result == 1 ? "성공적으로 탈퇴시켰습니다" : "회원 탈퇴에 실패하였습니다");
		} else if (yesOrNo.equals("n") || yesOrNo.equals("N")) {
			System.out.println("탈퇴를 취소하였습니다");
		} else {
			System.out.println("잘못된 정보를 입력하였습니다");
		}
	}

	// pointType = -1 : 게시글 이용, -2 : 게시글 추천, -3 : 숫자야구, -4 : 가위바위보 하나빼기, -5 : 레벨업
	// pointType = 1 : 출석체크, 2 : 게시글 추천, 3 : 숫자야구(미니게임), -4 : 가위바위보 하나빼기(미니게임)
	public void checkPointUse(int point, int pointType, int pointContents) {
		mm.checkPointUse(point, pointType, pointContents);
	}

	public void pointMenu() {
		System.out.println("| 1.항목별 포인트 | 2.멤버별 포인트 | 3.일자별 포인트 | 9.나가기 |");
		System.out.print("입력 > ");
	}

	public void showPointFlowByDay() {
		System.out.println("확인하고자 하는 년월을 입력하시오. ex) 2022-08");
		System.out.print("입력 > ");
		String day = scn.nextLine();

		String strYear = day.substring(0, 4);
		String strMonth = day.substring(day.length() - 2, day.length());
		int lastDay = 0;
		int year = Integer.parseInt(strYear);
		switch (Integer.parseInt(strMonth)) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			lastDay = 31;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			lastDay = 30;
			break;
		case 2:
			if (year % 4 == 0) {
				if (year % 100 == 0) {
					if (year % 400 == 0) {
						lastDay = 29;
					} else {
						lastDay = 28;
					}
				} else {
					lastDay = 29;
				}
			}
			break;
		}
		mm.showPointFlowByDay(strYear, strMonth, lastDay);
	}
}
