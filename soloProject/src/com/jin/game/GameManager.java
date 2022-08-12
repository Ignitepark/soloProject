package com.jin.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.jin.DAO.DAO;
import com.jin.member.MemberManager;
import com.jin.member.MemberService;

public class GameManager extends DAO {
	private static GameManager gm = null;

	private GameManager() {
	}

	public static GameManager getInstance() {
		if (gm == null) {
			return gm = new GameManager();
		}
		return gm;
	}

	Scanner scn = new Scanner(System.in);
	MemberService ms = new MemberService();
	GameService gs = new GameService();

	public void numberBaseball() {
		int result = MemberManager.getInstance().minusPoint(2);
		if (result == 1) {
			ms.checkPointUse(2, -1, -4);
			int[] numberBaseball = new int[4];
			int cntPlay = 0;

			for (int i = 0; i < numberBaseball.length; i++) {
				int randomNumber = (int) (Math.random() * 10);
				boolean istrue = true;
				for (int j = 0; j < i; j++) {
					if (randomNumber == numberBaseball[j]) {
						i--;
						istrue = false;
					}
				}
				if (istrue) {
					numberBaseball[i] = randomNumber;
				}
			}
			String num = "";
			for (int i = 0; i < numberBaseball.length; i++) {
				num += String.valueOf(numberBaseball[i]);
			}
			String answerNum = "";
			while (!answerNum.equals(num)) {
				System.out.print((++cntPlay) + "번째 시도 | 숫자를 입력하시오 > ");
				answerNum = scn.nextLine();
				if (answerNum.length() != 4) {
					System.out.println("숫자를 다시 입력하시오.");
				} else {
					char[] charAry = answerNum.toCharArray();
					int cntStrike = 0;
					int cntBall = 0;
					for (int i = 0; i < 4; i++) {
						for (int j = 0; j < 4; j++) {
							if (Integer.parseInt(answerNum.substring(i, i + 1)) == numberBaseball[j]) {
								if (j == i) {
									cntStrike++;
								} else {
									cntBall++;
								}
							}
						}
					}
					System.out.println(cntPlay + "번째 시도 : " + cntStrike + "S " + cntBall + "B");
				}
			}
			if (cntPlay <= 10) {
				System.out.println((2 + gs.checkItemQuantity(3) + 2 * gs.checkItemQuantity(18)) + " 포인트를 획득하였습니다!");
				MemberManager.getInstance().plusPoint(4 + gs.checkItemQuantity(3) + 2 * gs.checkItemQuantity(18));
				ms.checkPointUse(4 + gs.checkItemQuantity(3) + 2 * gs.checkItemQuantity(18), 1, 3);
			} else if (cntPlay <= 15) {
				if (gs.checkItemQuantity(3) + gs.checkItemQuantity(18) == 0) {
					System.out.println("투자한 포인트를 돌려받았습니다!");
					MemberManager.getInstance().plusPoint(2);
					ms.checkPointUse(2 + gs.checkItemQuantity(3) + 2 * gs.checkItemQuantity(18), 1, 3);
				} else {
					System.out.println((gs.checkItemQuantity(3) + 2 * gs.checkItemQuantity(18)) + " 포인트를 획득하였습니다!");
					MemberManager.getInstance().plusPoint(4 + gs.checkItemQuantity(3) + 2 * gs.checkItemQuantity(18));
					ms.checkPointUse(2 + gs.checkItemQuantity(3) + 2 * gs.checkItemQuantity(18), 1, 3);
				}
			} else if (cntPlay <= 20) {
				System.out.println((1 + gs.checkItemQuantity(3) + 2 * gs.checkItemQuantity(18)) + " 포인트를 획득하였습니다!");
				MemberManager.getInstance().plusPoint(1 + gs.checkItemQuantity(3) + 2 * gs.checkItemQuantity(18));
				ms.checkPointUse(1 + gs.checkItemQuantity(3) + 2 * gs.checkItemQuantity(18), 1, 3);

			} else {
				System.out.println("포인트 획득에 실패하였습니다.");
			}
		} else {
			System.out.println("포인트가 부족합니다.");
		}
	}

	public void chooseLCP() {
		int result = MemberManager.getInstance().minusPoint(2);
		if (result == 1) {
			ms.checkPointUse(2, -1, -5);
			int[] intComputerLCP = new int[2];
			String[] computerLCP = new String[2];
			String[] userLCP = new String[2];
			String chooseLCP = "";
			int intChooseLCP = 0;
			String chooseComputerLCP = "";
			int intChooseComputerLCP = 0;
			boolean judge = true;
			int win = 0;
			System.out.println("가위, 바위, 보 중 2가지를 입력하십시오");
			System.out.print("입력 > ");
			userLCP[0] = scn.nextLine();
			System.out.print("입력 > ");
			userLCP[1] = scn.nextLine();

			for (int i = 0; i < intComputerLCP.length; i++) {
				int randomNumber = Math.round((float) (Math.random() * 2));
				boolean istrue = true;
				for (int j = 0; j < i; j++) {
					if (randomNumber == intComputerLCP[j]) {
						i--;
						istrue = false;
					}
				}
				if (istrue) {
					intComputerLCP[i] = randomNumber;
				}
			}

			for (int i = 0; i < intComputerLCP.length; i++) {
				switch (intComputerLCP[i]) {
				case 0:
					computerLCP[i] = "가위";
					break;
				case 1:
					computerLCP[i] = "바위";
					break;
				case 2:
					computerLCP[i] = "보";
					break;
				}
			}

			System.out.print("상대의 선택 : ");
			for (int i = 0; i < 2; i++) {
				System.out.print(computerLCP[i]);
				if (i == 0) {
					System.out.print(", ");
				}
			}
			System.out.println();
			for (int i = 0; i < 2; i++) {
				System.out.print(userLCP[i]);
				if (i == 0) {
					System.out.print("와 ");
				}
			}
			System.out.print("중 어떤 것을 선택하시겠습니까?");
			System.out.println();
			while (judge) {
				System.out.print("입력 > ");
				chooseLCP = scn.nextLine();
				for (String a : userLCP) {
					if (chooseLCP.equals(a)) {
						judge = false;
					}
				}
				if (judge) {
					System.out.println("다시 입력 하십시오.");
				}
			}
			int chooseRandom = Math.round((float) Math.random());
			chooseComputerLCP = computerLCP[chooseRandom];
			switch (chooseComputerLCP) {
			case "가위":
				intChooseComputerLCP = 0;
				break;
			case "바위":
				intChooseComputerLCP = 1;
				break;
			case "보":
				intChooseComputerLCP = 2;
				break;
			}
			switch (chooseLCP) {
			case "가위":
				intChooseLCP = 0;
				break;
			case "바위":
				intChooseLCP = 1;
				break;
			case "보":
				intChooseLCP = 2;
				break;
			}
			if (intChooseLCP == intChooseComputerLCP) {
				win = 1;
			} else if (intChooseLCP == 0) {
				if (intChooseComputerLCP == 1) {
					win = 0;
				} else if (intChooseComputerLCP == 2) {
					win = 2;
				}
			} else if (intChooseLCP == 1) {
				if (intChooseComputerLCP == 0) {
					win = 2;
				} else if (intChooseComputerLCP == 2) {
					win = 0;
				}
			} else if (intChooseLCP == 2) {
				if (intChooseComputerLCP == 0) {
					win = 0;
				} else if (intChooseComputerLCP == 1) {
					win = 2;
				}
			}
			switch (win) {
			case 0:
				System.out.println("졌습니다");
				System.out.println("포인트 획득에 실패하였습니다.");
				break;
			case 1:
				if (2 * gs.checkItemQuantity(18) + gs.checkItemQuantity(16) - gs.checkItemQuantity(4) == 0) {
					System.out.println("비겼습니다");
					System.out.println("투자한 포인트를 돌려받았습니다!");
				} else {
					System.out.println("비겼습니다");
					System.out.println(2 * gs.checkItemQuantity(18) + gs.checkItemQuantity(16) - gs.checkItemQuantity(4)
							+ " 포인트를 돌려받았습니다!");
				}
				MemberManager.getInstance().plusPoint(
						2 + 2 * gs.checkItemQuantity(18) + gs.checkItemQuantity(16) - gs.checkItemQuantity(4));
				ms.checkPointUse(2 + 2 * gs.checkItemQuantity(18) + gs.checkItemQuantity(16) - gs.checkItemQuantity(4),
						1, 4);
				break;
			case 2:
				if (4 + 2 * gs.checkItemQuantity(18) + gs.checkItemQuantity(16) - gs.checkItemQuantity(4) == 0) {
					System.out.println("이겼습니다");
					System.out.println("투자한 포인트를 돌려받았습니다!");
				} else {
					System.out.println("이겼습니다");
					System.out.println(
							(2 + 2 * gs.checkItemQuantity(18) + gs.checkItemQuantity(16) - gs.checkItemQuantity(4))
									+ " 포인트를 획득하였습니다!");
				}
				MemberManager.getInstance().plusPoint(
						4 + 2 * gs.checkItemQuantity(18) + gs.checkItemQuantity(16) - gs.checkItemQuantity(4));
				ms.checkPointUse(4 + 2 * gs.checkItemQuantity(18) + gs.checkItemQuantity(16) - gs.checkItemQuantity(4),
						1, 4);
				break;
			}
		} else {
			System.out.println("포인트가 부족합니다.");
		}
	}

	public int insertItem(Game game) {
		int result = 0;

		try {
			conn();
			String sql = "insert into item values(?,?,?,item_no_seq.nextval,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, game.getItemName());
			pstmt.setString(2, game.getItemContents());
			pstmt.setString(3, game.getItemType());
			pstmt.setString(4, game.getItemGrade());
			pstmt.setInt(5, game.getItemSellPoint());
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return result;
	}

	public int updateItem(int itemNo, String itemContents) {
		int result = 0;

		try {
			conn();
			String sql = "update item set item_contents = ? where item_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, itemContents);
			pstmt.setInt(2, itemNo);
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return result;
	}

	public List<Game> showItem() {
		List<Game> list = new ArrayList<>();
		Game game = null;
		try {
			conn();
			String sql = "select * from item order by item_no";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				game = new Game();
				game.setItemName(rs.getString("item_name"));
				game.setItemContents(rs.getString("item_contents"));
				game.setItemType(rs.getString("item_type"));
				game.setItemNo(rs.getInt("item_no"));
				game.setItemGrade(rs.getString("item_grade"));
				game.setItemSellPoint(rs.getInt("item_sell_point"));
				list.add(game);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}

	public List<Game> showMemberItem() {
		List<Game> list = new ArrayList<>();
		Game game = null;
		try {
			conn();
			String sql = "select i.item_name item_name,i.item_contents item_contents,\r\n"
					+ "i.item_type item_type,i.item_no item_no,i.item_grade item_grade,i.item_sell_point item_sell_point,\r\n"
					+ "v.item_quantity item_quantity\r\n" + "from item i,member m, inventory v \r\n"
					+ "where i.item_no = v.item_no and v.member_no = m.member_no \r\n" + "and m.member_no = ?"
					+ "order by item_grade";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, MemberManager.mem.getMemberNo());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				game = new Game();
				game.setItemName(rs.getString("item_name"));
				game.setItemContents(rs.getString("item_contents"));
				game.setItemType(rs.getString("item_type"));
				game.setItemNo(rs.getInt("item_no"));
				game.setItemGrade(rs.getString("item_grade"));
				game.setItemSellPoint(rs.getInt("item_sell_point"));
				game.setItemQuantity(rs.getInt("item_quantity"));
				list.add(game);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}

	public int sellItem(String itemName) {
		int result = 0;
		int memberPoint = 0;
		int itemSellPoint = 0;
		int itemQuantity = 0;
		try {
			conn();
			String sql = "select member_point from memberinfo where member_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, MemberManager.mem.getMemberNo());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				memberPoint = rs.getInt("member_point");
			}

			String sql3 = "select item_sell_point from item where item_name = ?";
			pstmt = conn.prepareStatement(sql3);
			pstmt.setString(1, itemName);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				itemSellPoint = rs.getInt("item_sell_point");
			}

			String sql4 = "select item_quantity from inventory where item_no = (select item_no from item where item_name = ?)";
			pstmt = conn.prepareStatement(sql4);
			pstmt.setString(1, itemName);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				itemQuantity = rs.getInt("item_quantity");
			}

			if ((memberPoint + itemSellPoint) >= 0) {
				String sql1 = "update memberinfo set member_point = member_point + ?*(select item_sell_point from item where item_name = ?) where member_no = ?";
				pstmt = conn.prepareStatement(sql1);
				pstmt.setInt(1, itemQuantity);
				pstmt.setString(2, itemName);
				pstmt.setInt(3, MemberManager.mem.getMemberNo());
				result = pstmt.executeUpdate();

				String sql2 = "delete from inventory where item_no = (select item_no from item where item_name = ?) and member_no = ?";
				pstmt = conn.prepareStatement(sql2);
				pstmt.setString(1, itemName);
				pstmt.setInt(2, MemberManager.mem.getMemberNo());
				result += pstmt.executeUpdate();

				String sql7 = "insert into point(point_member_no,point,point_type,point_contents) values(?,?,?,?)";
				pstmt = conn.prepareStatement(sql7);
				pstmt.setInt(1, MemberManager.mem.getMemberNo());
				pstmt.setInt(2, itemSellPoint);
				pstmt.setInt(3, 1);
				pstmt.setInt(4, 5);
				result = pstmt.executeUpdate();
			} else {
				System.out.println("포인트가 부족합니다");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return itemQuantity * itemSellPoint;
	}

	public int checkItemQuantity(int itemNo) {
		int result = 0;
		try {
			conn();
			String sql = "Select item_quantity from inventory where member_no = ? and item_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, MemberManager.mem.getMemberNo());
			pstmt.setInt(2, itemNo);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt("item_quantity");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return result;
	}

	public int checkAllItem() {
		int result = 0;
		try {
			conn();
			String sql = "select item_quantity from inventory where member_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, MemberManager.mem.getMemberNo());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result += rs.getInt("item_quantity");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return result;
	}

	public String getItem(String itemGrade) {
		int result = 0;
		int i = 0;
		int itemNo = 0;
		int[] intAry = null;
		int length = 0;
		String itemName = "";
		String[] strAry = null;
		try {
			conn();
			String sql1 = "select count(item_no) count from item where item_grade = ?";
			pstmt = conn.prepareStatement(sql1);
			pstmt.setString(1, itemGrade);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				length = rs.getInt("count");
				intAry = new int[length];
				strAry = new String[length];
			}
			String sql2 = "select item_no,item_name from item where item_grade = ?";
			pstmt = conn.prepareStatement(sql2);
			pstmt.setString(1, itemGrade);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				intAry[i] = rs.getInt("item_no");
				strAry[i] = rs.getString("item_name");
				i++;
			}
			int randomNo = (int) (Math.random() * (length - 1));
			itemNo = intAry[randomNo];
			itemName = strAry[randomNo];
			String sql3 = "select item_quantity from inventory where member_no = ? and item_no = ?";
			pstmt = conn.prepareStatement(sql3);
			pstmt.setInt(1, MemberManager.mem.getMemberNo());
			pstmt.setInt(2, itemNo);
			result = pstmt.executeUpdate();
			if (result == 0) {
				String sql4 = "insert into inventory values(?,?,1)";
				pstmt = conn.prepareStatement(sql4);
				pstmt.setInt(1, MemberManager.mem.getMemberNo());
				pstmt.setInt(2, itemNo);
				result += pstmt.executeUpdate();
			} else {
				String sql5 = "update inventory set item_quantity = item_quantity +1 where item_no = ? and member_no = ?";
				pstmt = conn.prepareStatement(sql5);
				pstmt.setInt(1, itemNo);
				pstmt.setInt(2, MemberManager.mem.getMemberNo());
				result = pstmt.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return itemName;
	}

	
	
}
//try {
//}catch(Exception e) {
//	e.printStackTrace();
//}finally {
//	disconnect();
//}
