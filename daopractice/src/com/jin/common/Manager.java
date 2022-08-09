package com.jin.common;

import java.util.Scanner;

import com.jin.member.Member;
import com.jin.member.MemberManager;
import com.jin.member.MemberService;

public class Manager {

	Scanner scn = new Scanner(System.in);
	MemberManager mm = MemberManager.getInstance();
	MemberService ms = new MemberService();
	int menu = 0;

	public Manager() {
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
							ms.adminAddMember();
						} else if (menu == 2) {
							ms.checkPoint();
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
							ms.miniGame();
						} else if (menu == 9) {
							mm.logout();
							System.out.println("로그아웃 되었습니다.");
							System.out.println("----------------------------------");
							break;
						}
					}

				}
			} else if (menu == 2) {
				ms.addMember();
			} else if (menu == 9) {
				System.out.println("프로그램 종료");
				break;
			}
		}
	}
}
