package com.jin.common;

import java.util.Scanner;

import com.jin.game.GameService;
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
	GameService gs = new GameService();
	int menu = 0;
	int menu2 = 0;
	int menu3 = 0;

	public Manager() {
		run();
	}

	int role = 0;

	private void run() {
		while (true) {
			if (role == 0) {
				System.out.println("| 1. 로그인 | 2. 회원가입 | 9.종료 |");
				System.out.print("메뉴 입력 > ");
				try {
					menu = Integer.parseInt(scn.nextLine());
				} catch (Exception e) {
					System.out.println("메뉴 입력 오류");
				}
			}
			if (menu == 1) {
				if (role == 0) {
					System.out.println("로그인 화면입니다.");
					mm.login();
				}
				if (mm.judgeManager() == 1 && role == 0) {
					while (MemberManager.mem != null) {
						System.out.println("| 1.회원 관리 | 2.포인트 관리 | 3.아이템 관리 | 8.일반회원메뉴 | 9.로그아웃 |");
						System.out.print("메뉴 입력 > ");
						menu2 = Integer.parseInt(scn.nextLine());
						if (menu2 == 1) {
							while (true) {
								System.out.println("| 1.회원 추가 | 2. 회원 확인 | 3.회원 삭제 | 9.나가기 |");
								System.out.print("메뉴 입력 > ");
								menu3 = Integer.parseInt(scn.nextLine());
								if (menu3 == 1) {
									ms.adminAddMember();
								} else if (menu3 == 2) {
									ms.showAllMemberInfo();
								} else if (menu3 == 3) {
									ms.adminDeleteMember();
								} else if (menu3 == 8) {
								} else if (menu3 == 9) {
									break;
								}
							}
						} else if (menu2 == 2) {
							while (true) {
								ms.pointMenu();
								int pointMenu = Integer.parseInt(scn.nextLine());
								if (pointMenu == 1) {
									mm.showPointFlowByContents();
								} else if (pointMenu == 2) {
									mm.showPointFlowByMember();
								} else if (pointMenu == 3) {
									ms.showPointFlowByDay();
								} else if (pointMenu == 9) {
									break;
								}
							}
						} else if (menu2 == 3) {
							gs.adminItemMenu();
						} else if (menu2 == 8) {
							role++;
							break;
						} else if (menu2 == 9) {
							mm.logout();
							System.out.println("로그아웃 되었습니다.");
							System.out.println("----------------------------------");
							break;
						}
					}
				} else if (mm.judgeManager() == 0 || role == 1) {
					while (MemberManager.mem != null) {
						System.out.println(
								"| 1.게시판 | 2.포인트 확인 | 3.레벨업 | 4.포인트전송 | 5.아이템 뽑기 | 6.미니 게임 | 7.인벤토리 | 8.계정관리 | 9.로그아웃 |");
						System.out.print("메뉴 입력 > ");
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
											mm.checkPointUse(1, -1, -2);
											mm.checkPointUse(1, 1, 2);
										} else if (menuReply == 5) {
											ps.badPost(postNo);
										} else if (menuReply == 8) {

										} else if (menuReply == 9) {
											break;
										}
									}
								} else if (menuNo == 3) {
									ps.insertPost();
									mm.checkPointUse(5 - (MemberManager.mem.getMemberLevel() / 4), -1, -1);
								} else if (menuNo == 4) {
									ps.updatePost();
								} else if (menuNo == 5) {
									if (mm.judgeManager() == 0) {
										ps.deletePost();
									} else if (mm.judgeManager() == 1) {
										ps.adminDeletePost();
									}
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
							boolean isTrue = mm.levelUp();

							if (isTrue) {
								mm.checkPointUse(MemberManager.mem.getMemberLevel() * 100, -1, -3);
							}
						} else if (menu2 == 4) {
							ms.sendPoint();
						} else if (menu2 == 5) {
							gs.getItem();
						} else if (menu2 == 6) {
							gs.miniGame();
						} else if (menu2 == 7) {
							gs.itemMenu();
						} else if (menu2 == 8) {
							while (true) {
								System.out.println("--------------------계정 관리--------------------");
								System.out.println("| 1.회원정보 확인 | 2.회원 탈퇴 | 9.나가기 |");
								System.out.print("입력 > ");
								int memberMenu = Integer.parseInt(scn.nextLine());
								if (memberMenu == 1) {
									ms.showMemberInfo();
								} else if (memberMenu == 2) {
									ms.deleteMember();
								} else if (memberMenu == 9) {
									break;
								}
							}
						} else if (menu2 == 9) {
							role = 0;
							mm.logout();
							System.out.println("로그아웃 되었습니다.");
							System.out.println("----------------------------------");
							break;
						}
					}

				} else if (mm.judgeManager() == -1) {
					System.out.println("탈퇴한 회원입니다");
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
