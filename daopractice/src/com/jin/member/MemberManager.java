package com.jin.member;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Scanner;

import com.jin.DAO.DAO;

public class MemberManager extends DAO {
	public static Member mem = null;
	Scanner scn = new Scanner(System.in);
	int result = 0;
	LocalDate now = LocalDate.now();

	private static MemberManager mm = new MemberManager();

	private MemberManager() {
	}

	public static MemberManager getInstance() {
		return mm;
	}

	public void showGameInfo(Member member) {
		try {
			conn();
			String sql1 = "select member_no from member where member_id = ?";

			String sql2 = "select * from memberinfo where member_no = ?";
			pstmt = conn.prepareStatement(sql2);
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
		System.out.print("ID 입력 > ");
		String id = scn.nextLine();
		System.out.print("비밀번호 입력 > ");
		String pw = scn.nextLine();

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
			}

			if (mem != null) {
				String sql2 = "select * from membergender where member_no = ?";
				pstmt = conn.prepareStatement(sql2);
				pstmt.setInt(1, mem.getMemberNo());
				rs = pstmt.executeQuery();

				if (rs.next()) {
					mem.setMemberGender(rs.getString("member_gender"));
				}
			}

			if (mem != null) {
				int result = 0;
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
						String sql4 = "update memberinfo set member_point = member_point + (2*member_level) where member_no = ?";
						pstmt = conn.prepareStatement(sql4);
						pstmt.setInt(1, mem.getMemberNo());
						result = pstmt.executeUpdate();
						if (result == 1) {
							String sql5 = "select * from memberinfo where member_no = ?";
							pstmt = conn.prepareStatement(sql5);
							pstmt.setInt(1, mem.getMemberNo());
							rs = pstmt.executeQuery();
							if (rs.next()) {
								mem.setMemberLevel(rs.getInt("member_level"));
								mem.setMemberPoint(rs.getInt("member_point"));
							}
						} else {
							System.out.println("포인트 지급 에러 발생.");
						}
					}
				}

				String sql6 = "update memberInfo set last_date = sysdate where member_no =?";
				pstmt = conn.prepareStatement(sql6);
				pstmt.setInt(1, mem.getMemberNo());
				result += pstmt.executeUpdate();

			} else {
				System.out.println("로그인에 실패하였습니다.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return mem;
	}

	public void logout() {
		mem = null;
	}

	public boolean judgeManager() {
		if (mem.getRole() == 1) {
			return true;
		} else {
			return false;
		}
	}

	public void numberBaseball() {
		System.out.print("투자할 포인트를 입력하시오 > ");
		int point = Integer.parseInt(scn.nextLine());
		int result = minusPoint(point);
		if (result == 1) {
			System.out.print("숫자 개수를 입력하시오 > ");
			int baseballNo = Integer.parseInt(scn.nextLine());
			int[] numberBaseball = new int[baseballNo];
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
				if (answerNum.length() != baseballNo) {
					System.out.println("숫자를 다시 입력하시오.");
				} else {
					char[] charAry = answerNum.toCharArray();
					int cntStrike = 0;
					int cntBall = 0;
					for (int i = 0; i < baseballNo; i++) {
						for (int j = 0; j < baseballNo; j++) {
//						System.out.println(charAry[j]);
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
			if (cntPlay <= 15) {
				System.out.println("2배 포인트를 획득하였습니다!");
				plusPoint(point * 2);
			} else if (cntPlay <= 20) {
				System.out.println("투자한 포인트를 돌려받았습니다!");
				plusPoint(point);
			} else if (cntPlay <= 25) {
				System.out.println("투자한 포인트의 반을 획득하였습니다!");
				plusPoint(point / 2);
			} else {
				System.out.println("포인트 획득에 실패하였습니다.");
			}
		} else {
			System.out.println("포인트가 부족합니다.");
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
		if (judgePoint(point)) {
			try {
				conn();
				String sql = "update memberinfo set member_point = member_point + ? where member_no = ?";
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

	public void levelUp() {
		System.out.println("레벨 업 시 " + mem.getMemberLevel() * 100 + "포인트가 소모됩니다.");
		if (mem.getMemberPoint() >= 100) {
			System.out.print("레벨 업 하시겠습니까? > (y/n)");
			String yesno = scn.nextLine();
			boolean isTrue = yesno.equals("y") ? true : false;
			if (isTrue) {

				try {
					conn();
					String sql1 = "update memberinfo set member_point = member_point - 100 , member_level = member_level + 1 where member_no = ? ";
					pstmt = conn.prepareStatement(sql1);
					pstmt.setInt(1, mem.getMemberNo());
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
	}

	public void postMenu() {
		System.out.println("--------------------게시판--------------------");
		System.out.println("| 1.목록 | 2.등록 | 3.수정 | 4.삭제 | 9.나가기 |");
	}

	public void insertPost() {
		System.out.println("--------------------게시글 등록--------------------");
		System.out.print("제목 입력 > ");
		String title = scn.nextLine();
		System.out.print("내용 입력 > ");
		String contents = scn.nextLine();
		
		
		
		
	}

}
