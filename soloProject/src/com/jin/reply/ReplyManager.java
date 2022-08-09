package com.jin.reply;

import java.util.ArrayList;
import java.util.List;

import com.jin.DAO.DAO;
import com.jin.member.MemberManager;

public class ReplyManager extends DAO {
	private static ReplyManager rm = new ReplyManager();

	private ReplyManager() {
	}

	public static ReplyManager getInstance() {
		return rm;
	}

	public List<Reply> getReply(int postNo) {
		List<Reply> list = new ArrayList<>();
		Reply reply = null;
		try {
			conn();
			String sql = "select * from reply where reply_post_no = ? order by reply_no";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, postNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				reply = new Reply();
				reply.setReplyContents(rs.getString("reply_contents"));
				reply.setReplyNo(rs.getInt("reply_no"));
				reply.setReplyWriter(rs.getString("reply_writer"));
				reply.setReplyDate(rs.getDate("reply_date"));
				list.add(reply);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}

	public int countReply(int postNo) {
		int result = 0;
		try {
			conn();
			String sql = "select count(*) as count from reply where reply_post_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, postNo);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt("count") + 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return result;
	}

	public int addReply(Reply reply) {
		int result = 0;
		try {
			conn();
			String sql = "insert into reply(reply_no,reply_contents,reply_writer,reply_post_no,reply_member_no) values(?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reply.getReplyNo());
			pstmt.setString(2, reply.getReplyContents());
			pstmt.setString(3, reply.getReplyWriter());
			pstmt.setInt(4, reply.getReplyPostNo());
			pstmt.setInt(5, MemberManager.mem.getMemberNo());
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return result;
	}

	public int updateReply(Reply reply) {
		int result = 0;
		try {
			conn();
			String sql = "update reply set reply_contents = ? where reply_no = ? and reply_member_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, reply.getReplyContents());
			pstmt.setInt(2, reply.getReplyNo());
			pstmt.setInt(3, MemberManager.mem.getMemberNo());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return result;
	}

	public int deleteReply(int replyNo) {
		int result = 0;
		try {
			conn();
			String sql = "update reply set reply_contents = ? where reply_no = ? and reply_member_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "삭제된 댓글입니다.");
			pstmt.setInt(2, replyNo);
			pstmt.setInt(3, MemberManager.mem.getMemberNo());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return result;
	}

	public int deleteAllReply(int postNo) {
		int result = 0;
		try {
			conn();
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return result;
	}
}
