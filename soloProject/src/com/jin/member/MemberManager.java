package com.jin.member;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.jin.DAO.DAO;
import com.jin.game.GameService;

public class MemberManager extends DAO {
	public static Member mem = null;
	Scanner scn = new Scanner(System.in);
	int result = 0;
	LocalDate now = LocalDate.now();
	MemberService ms = new MemberService();
	GameService gs = new GameService();

	private static MemberManager mm = new MemberManager();

	private MemberManager() {
	}

	public static MemberManager getInstance() {
		return mm;
	}

	public void showGameInfo(Member member) {
		try {
			conn();
			String sql = "select * from memberinfo where member_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, member.getMemberNo());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				mem.setMemberLevel(rs.getInt("member_level"));
				mem.setMemberPoint(rs.getInt("member_point"));
				System.out.println(member.getMemberId() + "유저의 레벨은 " + member.getMemberLevel() + ", 포인트는 "
						+ member.getMemberPoint() + "점 입니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	public int addMember(Member member) {
		mem = new Member();
		try {
			conn();
			String sql1 = "insert into member(member_no,member_id,member_pw) values (member_no_seq.nextval,?,?)";
			pstmt = conn.prepareStatement(sql1);
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPw());
			result = pstmt.executeUpdate();

			if (result == 1) {
				String sql2 = "insert into memberInfo(member_no) values(member_no_seq.currval)";
				stmt = conn.createStatement();
				result += stmt.executeUpdate(sql2);
			}

			if (result == 2) {
				String sql3 = "insert into membergender values(member_no_seq.currval,?,?)";
				pstmt = conn.prepareStatement(sql3);
				pstmt.setString(1, member.getMemberName());
				pstmt.setString(2, member.getMemberGender());
				result += pstmt.executeUpdate();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

		return result;
	}

	public int adminAddMember(Member member) {
		mem = new Member();
		try {
			conn();
			String sql1 = "insert into member values (member_no_seq.nextval,?,?,?)";
			pstmt = conn.prepareStatement(sql1);
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPw());
			pstmt.setInt(3, result);
			result = pstmt.executeUpdate();

			if (result == 1) {
				String sql2 = "insert into memberInfo(member_no) values(member_no_seq.currval)";
				stmt = conn.createStatement();
				result += stmt.executeUpdate(sql2);
			}

			if (result == 2) {
				String sql3 = "insert into membergender values(member_no_seq.currval,?,?)";
				pstmt = conn.prepareStatement(sql3);
				pstmt.setString(1, member.getMemberName());
				pstmt.setString(2, member.getMemberGender());
				result += pstmt.executeUpdate();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

		return result;
	}

	public Member login() {
		int result = 0;
		System.out.print("ID 입력 > ");
		String id = scn.nextLine();
		System.out.print("비밀번호 입력 > ");
		String pw = scn.nextLine();
		int itemPoint = 0;
		int itemPointMulti = 1;
		try {
			conn();
			String sql1 = "select * from member where member_id = ? and member_pw = ?";
			pstmt = conn.prepareStatement(sql1);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				mem = new Member();
				mem.setMemberId(rs.getString("member_id"));
				mem.setMemberPw(rs.getString("member_pw"));
				mem.setMemberNo(rs.getInt("member_no"));
				mem.setRole(rs.getInt("role"));
				result++;
			}

			if (result == 1) {
				String sql2 = "select * from membergender where member_no = ?";
				pstmt = conn.prepareStatement(sql2);
				pstmt.setInt(1, mem.getMemberNo());
				rs = pstmt.executeQuery();

				if (rs.next()) {
					mem.setMemberGender(rs.getString("member_gender"));
					mem.setMemberName(rs.getString("member_name"));
					result++;
				}
			}

			String sql8 = "Select item_quantity from inventory where member_no = ? and item_no = ?";
			pstmt = conn.prepareStatement(sql8);
			pstmt.setInt(1, MemberManager.mem.getMemberNo());
			pstmt.setInt(2, 2);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				itemPoint = rs.getInt("item_quantity");
			}
			String sql9 = "Select item_quantity from inventory where member_no = ? and item_no = ?";
			pstmt = conn.prepareStatement(sql9);
			pstmt.setInt(1, MemberManager.mem.getMemberNo());
			pstmt.setInt(2, 13);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				itemPointMulti = 1 + rs.getInt("item_quantity");
			}

			if (result == 2) {
				int day = now.getDayOfMonth();
				Date compareDay = null;
				System.out.println("로그인 되었습니다.");
				String sql3 = "select last_date from memberInfo where member_no = ?";
				pstmt = conn.prepareStatement(sql3);
				pstmt.setInt(1, mem.getMemberNo());
				rs = pstmt.executeQuery();
				if (rs.next()) {
					compareDay = rs.getDate("last_date");
					if (compareDay.getDate() != day) {
						String sql4 = "update memberinfo set member_point = member_point + ?*((2 * member_level)+?) where member_no = ?";
						pstmt = conn.prepareStatement(sql4);
						pstmt.setInt(1, itemPointMulti + 1);
						pstmt.setInt(2, itemPoint);
						pstmt.setInt(3, mem.getMemberNo());
						result = pstmt.executeUpdate();

						String sql7 = "insert into point(point_member_no,point,point_type,point_contents) values(?,?,?,?)";
						pstmt = conn.prepareStatement(sql7);
						pstmt.setInt(1, mem.getMemberNo());
						pstmt.setInt(2, mem.getMemberLevel() * 2);
						pstmt.setInt(3, 1);
						pstmt.setInt(4, 1);
						result = pstmt.executeUpdate();
					}
					String sql5 = "select * from memberinfo where member_no = ?";
					pstmt = conn.prepareStatement(sql5);
					pstmt.setInt(1, mem.getMemberNo());
					rs = pstmt.executeQuery();
					if (rs.next()) {
						mem.setMemberLevel(rs.getInt("member_level"));
						mem.setMemberPoint(rs.getInt("member_point"));
					}
					String sql6 = "update memberInfo set last_date = sysdate where member_no =?";
					pstmt = conn.prepareStatement(sql6);
					pstmt.setInt(1, mem.getMemberNo());
					result += pstmt.executeUpdate();

				}
			} else {
				System.out.println("로그인에 실패하였습니다.");
				return mem = null;
			}
		} catch (Exception e) {
			System.out.println("로그인에 실패하였습니다.");
		} finally {
			disconnect();
		}
		return mem;
	}

	public void logout() {
		mem = null;
	}

	public boolean judgeMember(Member member) {
		if (member.getRole() == -1) {
			return false;
		} else {
			return true;
		}
	}

	public int judgeManager() {
		if (mem == null) {
			return 0;
		} else {
			if (mem.getRole() == 1) {
				return 1;
			} else if (mem.getRole() == 0) {
				return 0;
			} else {
				return -1;
			}
		}
	}

	public int minusPoint(int point) {
		int result = 0;
		if (judgePoint(point)) {
			try {
				conn();
				String sql = "update memberinfo set member_point = member_point - ? where member_no = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, point);
				pstmt.setInt(2, mem.getMemberNo());
				result = pstmt.executeUpdate();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				disconnect();
			}
		}
		return result;
	}

	public int plusPoint(int point) {
		int result = 0;
		int itemPoint = gs.checkItemQuantity(18);
		if (judgePoint(point)) {
			try {
				conn();
				String sql = "update memberinfo set member_point = member_point + ? where member_no = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, point + itemPoint);
				pstmt.setInt(2, mem.getMemberNo());
				result = pstmt.executeUpdate();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				disconnect();
			}
		}
		return result;
	}

	public Boolean judgePoint(int point) {
		Boolean result = false;
		try {
			conn();
			String sql = "select member_point from memberinfo where member_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mem.getMemberNo());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt("member_point") >= point ? true : false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return result;
	}

	public boolean levelUp() {
		System.out.println("레벨 업 시 " + mem.getMemberLevel() * 100 + "포인트가 소모됩니다.");
		if (mem.getMemberPoint() >= 100 * mem.getMemberLevel()) {
			System.out.print("레벨 업 하시겠습니까? > (y/n)");
			String yesno = scn.nextLine();
			boolean isTrue = yesno.equals("y") ? true : false;
			if (isTrue) {
				try {
					conn();
					String sql1 = "update memberinfo set member_point = member_point - ? , member_level = member_level + 1 where member_no = ? ";
					pstmt = conn.prepareStatement(sql1);
					pstmt.setInt(1, mem.getMemberLevel() * 100);
					pstmt.setInt(2, mem.getMemberNo());
					int result = pstmt.executeUpdate();

					if (result == 1) {
						System.out.println("레벨 업 하였습니다!");
						String sql2 = "select * from memberinfo where member_no=?";
						pstmt = conn.prepareStatement(sql2);
						pstmt.setInt(1, mem.getMemberNo());
						rs = pstmt.executeQuery();

						if (rs.next()) {
							mem.setMemberPoint(rs.getInt("member_point"));
							mem.setMemberLevel(rs.getInt("member_level"));
							System.out
									.println("현재 레벨 : " + mem.getMemberLevel() + ", 현재 포인트 : " + mem.getMemberPoint());
							return true;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					disconnect();
				}
			}
		} else {
			System.out.println("포인트가 부족합니다.");
		}
		return false;
	}

	public int sendPoint(int memberNo, int point) {
		int result = 0;
		try {
			conn();
			String sql = "update memberinfo set member_point = member_point - ? where member_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, point);
			pstmt.setInt(2, MemberManager.mem.getMemberNo());
			result = pstmt.executeUpdate();

			if (result == 1) {
				String sql1 = "update memberinfo set member_point = member_point + ? where member_no = ?";
				pstmt = conn.prepareStatement(sql1);
				pstmt.setInt(1, point);
				pstmt.setInt(2, memberNo);
				result += pstmt.executeUpdate();
				if (result != 2) {
					String sql2 = "update memberinfo set member_point = member_point + ? where member_no = ?";
					pstmt = conn.prepareStatement(sql2);
					pstmt.setInt(1, point);
					pstmt.setInt(2, MemberManager.mem.getMemberNo());
					result = pstmt.executeUpdate();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return result;
	}

	public List<Member> showAllMemberInfo() {
		List<Member> list = new ArrayList<>();
		Member member = null;
		try {
			conn();
			String sql = "select * from member";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				member = new Member();
				member.setMemberId(rs.getString("member_id"));
				member.setMemberNo(rs.getInt("member_no"));
				member.setRole(rs.getInt("role"));
				String sql1 = "select * from membergender where member_no = ?";
				pstmt = conn.prepareStatement(sql1);
				pstmt.setInt(1, member.getMemberNo());
				rs2 = pstmt.executeQuery();
				if (rs2.next()) {
					member.setMemberName(rs2.getString("member_name"));
					member.setMemberGender(rs2.getString("member_gender"));
				}
				String sql2 = "select * from memberinfo where member_no = ?";
				pstmt = conn.prepareStatement(sql2);
				pstmt.setInt(1, member.getMemberNo());
				rs2 = pstmt.executeQuery();
				if (rs2.next()) {
					member.setMemberLevel(rs2.getInt("member_level"));
					member.setMemberPoint(rs2.getInt("member_point"));
				}
				list.add(member);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}

	public int adminDeleteMember(int memberNo) {
		int result = 0;
		try {
			conn();
			String sql = "update member set role = -1 where member_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memberNo);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

		return result;
	}

	public int deleteMember() {
		int result = 0;
		try {
			conn();
			String sql = "update member set role = -1 where member_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mem.getMemberNo());
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return result;
	}

	// pointType = -1 : 게시글 이용, -2 : 게시글 추천, -3 : 레벨업, -4 : 숫자야구(미니게임), -5 : 가위바위보
	// 하나빼기(미니게임), -6 : 아이템 뽑기 , -7 : 경마
	// pointType = 1 : 출석체크, 2 : 게시글 추천, 3 : 숫자야구(미니게임), -4 : 가위바위보 하나빼기(미니게임)
	public void checkPointUse(int point, int pointType, int pointContents) {
		try {
			conn();
			String sql = "insert into point(point_member_no,point,point_type,point_contents) values(?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mem.getMemberNo());
			pstmt.setInt(2, point);
			pstmt.setInt(3, pointType);
			pstmt.setInt(4, pointContents);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	public void showPointFlowByContents() {
		try {
			conn();
			String sql1 = "select sum(point) sum, point_type, point_contents from point group by point_type,point_contents order by point_contents";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql1);
			System.out.println("-------------------사용 내역-------------------");
			while (rs.next()) {
				int sort = rs.getInt("point_contents");
				if (sort == -1) {
					System.out.println("게시글 이용에 사용된 포인트 : " + rs.getInt("sum"));
				}
			}
			rs = stmt.executeQuery(sql1);
			while (rs.next()) {
				int sort = rs.getInt("point_contents");
				if (sort == -2) {
					System.out.println("------------------------------------");
					System.out.println("게시글 추천에 사용된 포인트 : " + rs.getInt("sum"));
				}
			}
			rs = stmt.executeQuery(sql1);
			while (rs.next()) {
				int sort = rs.getInt("point_contents");
				if (sort == -3) {
					System.out.println("------------------------------------");
					System.out.println("레벨업에 사용된 포인트 : " + rs.getInt("sum"));
				}
			}
			rs = stmt.executeQuery(sql1);
			while (rs.next()) {
				int sort = rs.getInt("point_contents");
				if (sort == -4) {
					System.out.println("------------------------------------");
					System.out.println("숫자야구에 사용된 포인트 : " + rs.getInt("sum"));
				}
			}
			rs = stmt.executeQuery(sql1);
			while (rs.next()) {
				int sort = rs.getInt("point_contents");
				if (sort == -5) {
					System.out.println("------------------------------------");
					System.out.println("가위바위보 하나 빼기에 사용된 포인트 : " + rs.getInt("sum"));
				}
			}
			rs = stmt.executeQuery(sql1);
			while (rs.next()) {
				int sort = rs.getInt("point_contents");
				if (sort == -6) {
					System.out.println("------------------------------------");
					System.out.println("아이템 뽑기에 사용된 포인트 : " + rs.getInt("sum"));
				}
			}
			rs = stmt.executeQuery(sql1);
			while (rs.next()) {
				int sort = rs.getInt("point_contents");
				if (sort == -7) {
					System.out.println("------------------------------------");
					System.out.println("경마에 사용된 포인트 : " + rs.getInt("sum"));
				}
			}
			rs = stmt.executeQuery(sql1);
			while (rs.next()) {
				int sort = rs.getInt("point_contents");
				if (sort == 1) {
					System.out.println("------------------------------------");
					System.out.println("출석체크로 획득한 포인트 : " + rs.getInt("sum"));
				}
			}
			rs = stmt.executeQuery(sql1);
			while (rs.next()) {
				int sort = rs.getInt("point_contents");
				if (sort == 2) {
					System.out.println("------------------------------------");
					System.out.println("게시글 추천으로 획득한 포인트 : " + rs.getInt("sum"));
				}
			}
			rs = stmt.executeQuery(sql1);
			while (rs.next()) {
				int sort = rs.getInt("point_contents");
				if (sort == 3) {
					System.out.println("------------------------------------");
					System.out.println("숫자야구로 획득한 포인트 : " + rs.getInt("sum"));
				}
			}
			rs = stmt.executeQuery(sql1);
			while (rs.next()) {
				int sort = rs.getInt("point_contents");
				if (sort == 4) {
					System.out.println("------------------------------------");
					System.out.println("가위바위보 하나 빼기로 획득한 포인트 : " + rs.getInt("sum"));
				}
			}
			rs = stmt.executeQuery(sql1);
			while (rs.next()) {
				int sort = rs.getInt("point_contents");
				if (sort == 5) {
					System.out.println("------------------------------------");
					System.out.println("아이템 판매로 획득한 포인트 : " + rs.getInt("sum"));
				}
			}
			rs = stmt.executeQuery(sql1);
			while (rs.next()) {
				int sort = rs.getInt("point_contents");
				if (sort == 6) {
					System.out.println("------------------------------------");
					System.out.println("경마로 획득한 포인트 : " + rs.getInt("sum"));
				}
			}

			System.out.println("------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

	}

	public void showPointFlowByMember() {
		try {
			conn();
			String sql1 = "select sum(point) sum, point_member_no, point_type from point group by point_member_no,point_type order by point_member_no";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql1);
			System.out.println("포인트 사용 내역");
			while (rs.next()) {
				int sort = rs.getInt("point_type");
				if (sort == -1) {
					System.out.println(rs.getInt("point_member_no") + "번 회원 , 타입 : 사용 , " + rs.getInt("sum"));
				}
			}
			System.out.println("------------------------------------");
			System.out.println("포인트 획득 내역");
			String sql2 = "select sum(point) sum, point_member_no, point_type from point group by point_member_no,point_type order by point_member_no";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql2);
			while (rs.next()) {
				int sort = rs.getInt("point_type");
				if (sort == 1) {
					System.out.println(rs.getInt("point_member_no") + "번 회원 , 타입 : 획득 , " + rs.getInt("sum"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	public void showPointFlowByDay(String year, String month, int lastDay) {
		int[] days = new int[lastDay];
		String[] strDays = new String[lastDay];
		for (int i = 0; i < lastDay; i++) {
			days[i] = i + 1;
		}
		for (int i = 0; i < lastDay; i++) {
			if (days[i] < 10) {
				strDays[i] = "0" + String.valueOf(days[i]);
			} else {
				strDays[i] = String.valueOf(days[i]);
			}
		}
		Date[] dates = new Date[lastDay];
		for (int i = 0; i < lastDay; i++) {
			dates[i] = Date.valueOf(year + "-" + month + "-" + strDays[i]);
		}
		int[] pointSum = new int[lastDay];
		for (int a : pointSum) {
			a = 0;
		}
		int[] pointType = new int[lastDay];
		for (int a : pointType) {
			a = 0;
		}
		Date a = null;
		Date b = null;
		int intMonth = Integer.parseInt(month);
		int intNextMonth = intMonth + 1;
		String nextMonth = "";
		if (intNextMonth < 10) {
			nextMonth = "0" + String.valueOf(intNextMonth);
		} else {
			nextMonth = String.valueOf(intNextMonth);
		}
		int pT = 0;
		int psp = 0;
		int psm = 0;
		try {
			conn();
			for (int i = 0; i < lastDay; i++) {
				a = dates[i];
				if (i == lastDay - 1) {
					b = Date.valueOf(year + "-" + nextMonth + "-01");
				} else {
					b = dates[i + 1];
				}
				String sql = "select * from point where point_date > ? and point_date < ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setDate(1, a);
				pstmt.setDate(2, b);
				rs = pstmt.executeQuery();
				pT = 0;
				psp = 0;
				psm = 0;
				while (rs.next()) {
					pT = rs.getInt("point_type");
					if (pT == 1) {
						psp += rs.getInt("point");
					} else {
						psm += rs.getInt("point");
					}
				}
				if (psp == 0 && psm == 0) {
				} else {
					System.out.println(dates[i] + "의 포인트 획득량 : " + psp);
					System.out.println(dates[i] + "의 포인트 사용량 : " + psm);
					System.out.println();
				}
			}
		} catch (

		Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

	}
}

//try {
//}catch(Exception e) {
//	e.printStackTrace();
//}finally {
//	disconnect();
//}
