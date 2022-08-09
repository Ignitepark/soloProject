package com.jin.common;

import java.util.Scanner;

import com.jin.member.MemberManager;
import com.jin.member.MemberService;
import com.jin.post.PostService;
import com.jin.reply.ReplyService;

public class Manager {

	Scanner scn = new Scanner(System.in);
	MemberManager mm = MemberManager.getInstance();
	MemberService ms = new MemberService();
	PostService ps = new PostService();
	ReplyService rs = new ReplyService();
	int menu = 0;
	int menu2 = 0;

	public Manager() {
		run();
	}

	int role = 0;

	private void run() {
		while (true) {
			if (role == 0) {
				System.out.println("| 1. 로그인 | 2. 회원가입 | 9.종료 |");
				System.out.print("메뉴 입력 > ");
				menu = Integer.parseInt(scn.nextLine());
			}
			if (menu == 1) {
				if (role == 0) {
					System.out.println("로그인 화면입니다.");
					mm.login();
				}
				if (mm.judgeManager()) {
					while (MemberManager.mem != null) {
						System.out.println("| 1.회원 추가 | 2. 포인트 확인 | 3.포인트 확인 | 8.일반회원메뉴 | 9.로그아웃 |");
						menu2 = Integer.parseInt(scn.nextLine());
						if (menu2 == 1) {
							ms.adminAddMember();
						} else if (menu2 == 2) {
							ms.showAllMemberInfo();
						} else if (menu2 == 3) {
							ms.checkPoint();
						} else if (menu2 == 8) {
							MemberManager.mem.setRole(0);
							role++;
							break;
						} else if (menu2 == 9) {
							mm.logout();
							System.out.println("로그아웃 되었습니다.");
							System.out.println("----------------------------------");
							break;
						}
					}
				} else {
					while (MemberManager.mem != null) {
						System.out.println("| 1.게시판 | 2.포인트 확인 | 3.레벨업 | 4.포인트전송 | 7.미니 게임 | 8.회원정보확인 | 9.로그아웃 |");
						menu2 = Integer.parseInt(scn.nextLine());
						if (menu2 == 1) {
							while (true) {
								ps.postMenu();
								int menuNo = Integer.parseInt(scn.nextLine());
								if (menuNo == 1) {
									ps.showList();
								} else if (menuNo == 2) {
									System.out.println("--------------------게시글 조회--------------------");
									System.out.print("게시글 번호 입력 > ");
									int postNo = Integer.parseInt(scn.nextLine());
									while (true) {
										ps.showPost(postNo);
										rs.showReply(postNo);
										rs.replyMenu();
										System.out.println();
										int menuReply = Integer.parseInt(scn.nextLine());
										if (menuReply == 1) {
											rs.addReply(postNo);
										} else if (menuReply == 2) {
											rs.updateReply(postNo);
										} else if (menuReply == 3) {
											rs.deleteReply();
										} else if (menuReply == 4) {
											ps.goodPost(postNo);
										} else if (menuReply == 5) {
											ps.badPost(postNo);
										} else if (menuReply == 9) {
											break;
										}
									}
								} else if (menuNo == 3) {
									ps.insertPost();
								} else if (menuNo == 4) {
									ps.updatePost();
								} else if (menuNo == 5) {
									ps.deletePost();
								} else if (menuNo == 9) {
									break;
								}
							}
						} else if (menu2 == 2) {
							System.out.println("-------------포인트 확인-------------");
							if (MemberManager.mem != null) {
								mm.showGameInfo(MemberManager.mem);
							} else {
								System.out.println("로그인 하십시오!");
							}
							System.out.println("----------------------------------");
						} else if (menu2 == 3) {
							mm.levelUp();
						} else if (menu2 == 4) {
							ms.sendPoint();
						} else if (menu2 == 5) {

						} else if (menu2 == 6) {

						} else if (menu2 == 7) {
							ms.miniGame();
						} else if (menu2 == 8) {
							ms.showMemberInfo();
						} else if (menu2 == 9) {
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
