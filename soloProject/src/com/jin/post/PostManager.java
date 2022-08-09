package com.jin.post;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.jin.DAO.DAO;
import com.jin.member.MemberManager;
import com.jin.reply.ReplyManager;

public class PostManager extends DAO {
	Scanner scn = new Scanner(System.in);
	private static PostManager pm = new PostManager();

	private PostManager() {
	}

	public static PostManager getInstance() {
		return pm;
	}

	public int insertPost(Post post) {
		int result = 0;
		try {
			conn();
			String sql = "insert into post(post_no,post_title,post_contents,post_writer,post_member_no) values(post_no_seq.nextval,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, post.getPostTitle());
			pstmt.setString(2, post.getPostContents());
			pstmt.setString(3, post.getPostWriter());
			pstmt.setInt(4, post.getPostMemberNo());
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return result;
	}

	public List<Post> showList() {
		List<Post> list = new ArrayList<>();
		Post post = null;
		try {
			conn();
			String sql = "select * from post order by post_no";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				post = new Post();
				post.setPostNo(rs.getInt("post_no"));
				post.setPostTitle(rs.getString("post_title"));
				post.setPostContents(rs.getString("post_contents"));
				post.setPostDate(rs.getDate("post_date"));
				post.setPostWriter(rs.getString("post_writer"));

				String sql1 = "select count(*) as count from post_good where post_no = ? and post_good = 1";
				pstmt = conn.prepareStatement(sql1);
				pstmt.setInt(1, post.getPostNo());
				rs2 = pstmt.executeQuery();
				if (rs2.next()) {
					post.setPostGood(rs2.getInt("count"));
					String sql2 = "select count(*) as count from post_good where post_no = ? and post_bad = 1";
					pstmt = conn.prepareStatement(sql2);
					pstmt.setInt(1, post.getPostNo());
					rs2 = pstmt.executeQuery();
					if (rs2.next()) {
						post.setPostBad(rs2.getInt("count"));
					}
				}
				list.add(post);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}

	public Post showPost(int postNo) {
		Post post = null;
		try {
			conn();
			String sql1 = "update post set post_view= post_view+1 where post_no = ?";
			pstmt = conn.prepareStatement(sql1);
			pstmt.setInt(1, postNo);
			int result = pstmt.executeUpdate();
			if (result == 1) {
				String sql2 = "select * from post where post_no = ?";
				pstmt = conn.prepareStatement(sql2);
				pstmt.setInt(1, postNo);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					post = new Post();
					post.setPostNo(rs.getInt("post_no"));
					post.setPostTitle(rs.getString("post_title"));
					post.setPostContents(rs.getString("post_contents"));
					post.setPostDate(rs.getDate("post_date"));
					post.setPostWriter(rs.getString("post_writer"));
					post.setPostMemberNo(rs.getInt("post_member_no"));
					post.setPostView(rs.getInt("post_view"));
					String sql3 = "select count(*) as count from post_good where post_no = ? and post_good = 1";
					pstmt = conn.prepareStatement(sql3);
					pstmt.setInt(1, post.getPostNo());
					rs = pstmt.executeQuery();
					if (rs.next()) {
						post.setPostGood(rs.getInt("count"));
						String sql4 = "select count(*) as count from post_good where post_no = ? and post_bad = 1";
						pstmt = conn.prepareStatement(sql4);
						pstmt.setInt(1, post.getPostNo());
						rs = pstmt.executeQuery();
						if (rs.next()) {
							post.setPostBad(rs.getInt("count"));
						}

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return post;
	}

	public int adminDeletePost(int postNo) {
		int result = 0;
		if (ReplyManager.getInstance().deleteAllReply(postNo) == 2) {
			try {
				conn();
				String sql = "delete from post where post_no = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, postNo);
				result = pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				disconnect();
			}
		}
		return result;
	}

	public int deletePost(int postNo) {
		int result = 0;
		try {
			conn();
			String sql1 = "select post_member_no from post where post_no = ?";
			pstmt = conn.prepareStatement(sql1);
			pstmt.setInt(1, postNo);
			rs = pstmt.executeQuery();
			int isTrue = 0;
			if (rs.next()) {
				isTrue = rs.getInt("post_member_no");
			}
			if (isTrue == MemberManager.mem.getMemberNo()) {
				String sql2 = "select count(*) as count from reply where reply_post_no = ?";
				pstmt = conn.prepareStatement(sql2);
				pstmt.setInt(1, postNo);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					result = rs.getInt("count");
				}
				if (result > 0) {
					System.out.println("확인");
					String sql3 = "delete from reply where reply_post_no = ?";
					pstmt = conn.prepareStatement(sql3);
					pstmt.setInt(1, postNo);
					result += pstmt.executeUpdate();
				} else if (result == 0) {
					result = 2;
				}
				if (result == 2) {
					String sql4 = "delete from post where post_no = ? and post_member_no = ?";
					pstmt = conn.prepareStatement(sql4);
					pstmt.setInt(1, postNo);
					pstmt.setInt(2, MemberManager.mem.getMemberNo());
					int res = pstmt.executeUpdate();
					if (result == 1) {
						String sql5 = "delete from post_good where post_no = ?";
						pstmt = conn.prepareStatement(sql5);
						pstmt.setInt(1, postNo);
						res = pstmt.executeUpdate();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

		return result;
	}

	public int updatePost(Post post) {
		int result = 0;
		try {
			conn();
			String sql = "update post set ? where post_no = ? and post_member_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, post.getPostTitle() == null ? "post_contents" : "post_title");
			pstmt.setInt(2, post.getPostNo());
			pstmt.setInt(3, MemberManager.mem.getMemberNo());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return result;
	}

	public int goodPost(int postNo) {
		int result = 0;
		try {
			conn();
			String sql1 = "select * from post_good where member_no = ? and (post_good = 1 or post_bad = 1)";
			pstmt = conn.prepareStatement(sql1);
			pstmt.setInt(1, MemberManager.mem.getMemberNo());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String sql2 = "delete from post_good where member_no = ?";
				pstmt = conn.prepareStatement(sql2);
				pstmt.setInt(1, MemberManager.mem.getMemberNo());
				result = pstmt.executeUpdate();
			} else {
				String sql3 = "insert into post_good(member_no,post_good,post_no) values(?,1,?)";
				pstmt = conn.prepareStatement(sql3);
				pstmt.setInt(1, MemberManager.mem.getMemberNo());
				pstmt.setInt(2, postNo);
				result = pstmt.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return result;
	}

	public int badPost(int postNo) {
		int result = 0;
		try {
			conn();
			String sql1 = "select * from post_good where member_no = ? and (post_good = 1 or post_bad = 1)";
			pstmt = conn.prepareStatement(sql1);
			pstmt.setInt(1, MemberManager.mem.getMemberNo());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String sql2 = "delete from post_good where member_no = ?";
				pstmt = conn.prepareStatement(sql2);
				pstmt.setInt(1, MemberManager.mem.getMemberNo());
				result = pstmt.executeUpdate();
			} else {
				String sql3 = "insert into post_good(member_no,post_bad,post_no) values(?,1,?)";
				pstmt = conn.prepareStatement(sql3);
				pstmt.setInt(1, MemberManager.mem.getMemberNo());
				pstmt.setInt(2, postNo);
				result = pstmt.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return result;
	}

}
