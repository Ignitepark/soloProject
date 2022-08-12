package com.jin.game;

import java.util.List;
import java.util.Scanner;

import com.jin.horseRace.HorseRace;
import com.jin.member.MemberManager;

public class GameService {
	Scanner scn = new Scanner(System.in);

	public void miniGame() {
		while (true) {
			System.out.println("-------------미니 게임-------------");
			System.out.println("| 1.숫자야구 | 2.가위바위보 하나빼기 | 3.경마 | 9.나가기 |");
			System.out.print("메뉴 입력 > ");
			int selectNo = Integer.parseInt(scn.nextLine());
			if (selectNo == 1) {
				GameManager.getInstance().numberBaseball();
			} else if (selectNo == 2) {
				GameManager.getInstance().chooseLCP();
			} else if (selectNo == 3) {

				HorseRace();
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

	public void adminItemMenu() {
		while (true) {
			System.out.println("--------------------아이템 관리 메뉴--------------------");
			System.out.println("| 1.아이템 목록 | 2.아이템 등록 | 3.아이템 수정 | 9.나가기 |");
			System.out.print("메뉴 입력 > ");
			int selectNo = Integer.parseInt(scn.nextLine());
			if (selectNo == 1) {
				showItem();
			} else if (selectNo == 2) {
				insertItem();
			} else if (selectNo == 3) {
				updateItem();
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

	public void itemMenu() {
		while (true) {
			System.out.println("--------------------아이템 관리 메뉴--------------------");
			System.out.println("| 1.보유 아이템 | 2.아이템 판매 | 9.나가기 |");
			System.out.print("메뉴 입력 > ");
			int selectNo = Integer.parseInt(scn.nextLine());
			if (selectNo == 1) {
				showMemberItem();
			} else if (selectNo == 2) {
				sellItem();
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

	public void insertItem() {
		Game game = new Game();
		System.out.println("--------------------아이템 등록 메뉴--------------------");
		System.out.print("아이템 이름 입력 > ");
		String itemName = scn.nextLine();
		System.out.print("아이템 능력 입력 > ");
		String itemContents = scn.nextLine();
		System.out.print("아이템 유형 입력(1.장비, 2.사용) > ");
		String itemType = Integer.parseInt(scn.nextLine()) == 1 ? "장비" : "사용";
		System.out.print("아이템 등급 입력(F~S) > ");
		String itemGrade = scn.nextLine();
		System.out.print("아이템 가격 입력 > ");
		int itemSellPoint = Integer.parseInt(scn.nextLine());

		game.setItemName(itemName);
		game.setItemContents(itemContents);
		game.setItemType(itemType);
		game.setItemGrade(itemGrade);
		game.setItemSellPoint(itemSellPoint);

		int result = GameManager.getInstance().insertItem(game);
		System.out.println(result == 1 ? "아이템 등록 완료" : "아이템 등록 실패");
	}

	public void showItem() {
		System.out.println("----------------------아이템 목록----------------------");
		List<Game> list = GameManager.getInstance().showItem();
		for (Game game : list) {
			System.out.println("아이템번호 : " + game.getItemNo() + ", 등급 : " + game.getItemGrade() + ", 이름 : "
					+ game.getItemName() + " , 유형 : " + game.getItemType() + "아이템, 효과 : " + game.getItemContents()
					+ ", 가격 : " + game.getItemSellPoint());
		}
	}

	public void showMemberItem() {
		System.out.println("----------------------아이템 목록----------------------");
		List<Game> list = GameManager.getInstance().showMemberItem();
		for (Game game : list) {
			System.out.println("아이템수량 : " + game.getItemQuantity() + ", 등급 : " + game.getItemGrade() + ", 이름 : "
					+ game.getItemName() + " , 유형 : " + game.getItemType() + "아이템, 효과 : " + game.getItemContents()
					+ ", 가격 : " + game.getItemSellPoint());
		}
	}

	public void updateItem() {
		System.out.print("수정할 아이템 번호 입력 > ");
		int itemNo = Integer.parseInt(scn.nextLine());
		System.out.print("수정할 아이템 내용 입력 > ");
		String itemContents = scn.nextLine();
		int result = GameManager.getInstance().updateItem(itemNo, itemContents);
		System.out.println(result == 1 ? "아이템 삭제 완료" : "아이템 삭제 실패");
	}

	public int checkItemQuantity(int itemNo) {
		return GameManager.getInstance().checkItemQuantity(itemNo);
	}

	public boolean checkAllItem() {
		int num = GameManager.getInstance().checkAllItem();
		if (num < 5) {
			return true;
		}
		return false;
	}

	public void getItem() {
		if (MemberManager.getInstance().judgePoint(5)) {
			String itemGrade = "";
			System.out.println("----------------------아이템 목록----------------------");
			System.out.println("5 포인트를 이용해 아이템 뽑기에 도전하시겠습니까? (※주의※ 심각한 손해가 발생 할 수 있습니다.)");
			if (checkAllItem()) {
				System.out.print("입력 (y/n) > ");
				String yesOrNo = scn.nextLine();
				if (yesOrNo.equals("y") || yesOrNo.equals("Y")) {
					MemberManager.getInstance().minusPoint(5);
					MemberManager.getInstance().checkPointUse(5, -1, -6);
					int percentage = Math.round((float) (Math.random() * 100));
					if (percentage <= 50) {
						System.out.println("꽝입니다.");
						itemGrade = "꽝";
					} else if (percentage <= 70) {
						itemGrade = "F";
					} else if (percentage <= 80) {
						itemGrade = "D";
					} else if (percentage <= 88) {
						itemGrade = "C";
					} else if (percentage <= 94) {
						itemGrade = "B";
					} else if (percentage <= 98) {
						itemGrade = "A";
					} else if (percentage <= 100) {
						itemGrade = "S";
					}
					if (itemGrade != "꽝") {
						System.out.println(GameManager.getInstance().getItem(itemGrade) + "(이)가 나왔습니다!");
					}
				}
			} else {
				System.out.println("아이템 최대 보유 가능 갯수는 5개 입니다.");
			}
		}
	}

	public void sellItem() {
		System.out.print("판매할 아이템의 이름을 입력하시오 > ");
		String itemName = scn.nextLine();
		int point = GameManager.getInstance().sellItem(itemName);
		System.out.println(
				point == 0 ? "판매에 실패하였습니다." : point < 0 ? (-point) + " 포인트가 감소하였습니다!" : point + " 포인트를 획득하였습니다!");
	}

	public void HorseRace() {
		if (MemberManager.getInstance().minusPoint(2) == 1) {
			MemberManager.getInstance().checkPointUse(2, -1, -7);
			System.out.println("1~4번 말 중 하나를 선택하시오.");
			int num = Integer.parseInt(scn.nextLine());
			new HorseRace(num);
		}
	}
}
