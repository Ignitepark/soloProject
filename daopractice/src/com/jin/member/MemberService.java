package com.jin.member;

import java.util.Scanner;

public class MemberService {
	Scanner scn = new Scanner(System.in);
	MemberManager mm = MemberManager.getInstance();
	int menu = 0;

	public MemberService() {
		run();
	}

	private void run() {
		while (true) {
			System.out.println("| 1. 로그인 | 2. 회원가입 | 9.종료 |");
			menu = Integer.parseInt(scn.nextLine());

			if (menu == 1) {
				System.out.println("로그인 화면입니다.");
				mm.login();
				if (mm.judgeManager()) {
					while (MemberManager.mem != null) {
						System.out.println("| 1.회원 추가 | 2. 포인트 확인 | 9.로그아웃 |");
						menu = Integer.parseInt(scn.nextLine());
						if (menu == 1) {

						} else if (menu == 2) {

						} else if (menu == 9) {
							mm.logout();
							System.out.println("로그아웃 되었습니다.");
							System.out.println("----------------------------------");
							break;
						}
					}
				} else {
					while (MemberManager.mem != null) {
						System.out.println("| 1.게시판 | 2.포인트 확인 | 3.레벨업 | 8.미니 게임 | 9.나가기 |");
						menu = Integer.parseInt(scn.nextLine());
						if (menu == 1) {

						} else if (menu == 2) {
							System.out.println("-------------포인트 확인-------------");
							if (MemberManager.mem != null) {
								mm.showGameInfo(MemberManager.mem);
							} else {
								System.out.println("로그인 하십시오!");
							}
							System.out.println("----------------------------------");
						} else if (menu == 3) {
							mm.levelUp();
						} else if (menu == 4) {

						} else if (menu == 5) {

						} else if (menu == 6) {

						} else if (menu == 7) {

						} else if (menu == 8) {

						} else if (menu == 9) {
							mm.logout();
							System.out.println("로그아웃 되었습니다.");
							System.out.println("----------------------------------");
							break;
						}
					}

				}
			} else if (menu == 2) {

			} else if (menu == 9) {
				System.out.println("프로그램 종료");
				break;
			}
		}
	}

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
}
