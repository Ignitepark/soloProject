package com.jin.reply;

import java.util.List;
import java.util.Scanner;

import com.jin.member.MemberManager;

public class ReplyService {
	Scanner scn = new Scanner(System.in);

	public void replyMenu() {
		System.out.println("| 1.댓글달기 | 2.댓글수정 | 3.댓글삭제 | 4.좋아요 | 5.싫어요 | 9.뒤로가기 |");
		System.out.print("메뉴 입력 > ");
	}

	public void addReply(int postNo) {
		Reply reply = new Reply();
		System.out.print("댓글 내용 입력 > ");
		reply.setReplyContents(scn.nextLine());
		reply.setReplyNo(ReplyManager.getInstance().countReply(postNo));
		reply.setReplyWriter(MemberManager.mem.getMemberName());
		reply.setReplyPostNo(postNo);

		int result = ReplyManager.getInstance().addReply(reply);
		System.out.println(result == 1 ? "댓글 등록 완료" : "댓글 등록 실패");
	}

	public void updateReply(int postNo) {
		Reply reply = new Reply();
		System.out.print("수정할 댓글 번호 입력 > ");
		int replyNo = Integer.parseInt(scn.nextLine());
		System.out.print("수정할 내용 입력 > ");
		String replyContents = scn.nextLine();

		reply.setReplyContents(replyContents);
		reply.setReplyNo(replyNo);
		reply.setReplyPostNo(postNo);
		reply.setReplyWriter(MemberManager.mem.getMemberName());
		int result = ReplyManager.getInstance().updateReply(reply);
		System.out.println(result == 1 ? "댓글 수정 완료" : "댓글 수정 실패");
	}

	public void deleteReply() {
		System.out.print("삭제 할 댓글 번호 입력 > ");
		int replyNo = Integer.parseInt(scn.nextLine());
		int result = ReplyManager.getInstance().deleteReply(replyNo);
		System.out.println(result == 1 ? "댓글 삭제 완료" : "댓글 삭제 실패");
	}

	public void showReply(int postNo) {
		List<Reply> list = ReplyManager.getInstance().getReply(postNo);
		if (list != null) {
			for (Reply reply : list) {
				System.out.print(reply.getReplyNo() + "번 댓글 : " + reply.getReplyContents() + " | 작성자 : "
						+ reply.getReplyWriter() + " | 작성일 : " + reply.getReplyDate() + "\n");
			}
		}
		System.out.println("-------------------------------------------");
	}
}
