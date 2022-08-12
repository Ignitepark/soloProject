package com.jin.post;

import java.util.Scanner;

import com.jin.member.MemberManager;

public class PostService {
	Scanner scn = new Scanner(System.in);

	PostManager pm = PostManager.getInstance();

	public void showList() {
		System.out.println("------------------------목록------------------------");
		for (Post post : pm.showList()) {
			System.out.println(post.getPostNo() + "번 글 | 제목 : " + post.getPostTitle() + " | 작성자 : "
					+ post.getPostWriter() + " | 작성일 : " + post.getPostDate() + " | 좋아요 : " + post.getPostGood()
					+ " 싫어요 : " + post.getPostBad() + " |");
		}
		System.out.println("---------------------------------------------------");
	}

	public void showDetailList(Post post) {
		System.out.println("---------------------------------------------------");
		System.out.println(
				"| " + post.getPostNo() + "번 글" + " 좋아요 : " + post.getPostGood() + " 싫어요 : " + post.getPostBad() + " 조회수 : "+post.getPostView());
		System.out.println("| 제목 : " + post.getPostTitle());
		System.out.println("| 내용 ");
		System.out.print("| ");
		for (int i = 0; i < post.getPostContents().length(); i++) {
			System.out.print(post.getPostContents().charAt(i));
			if (i != 0 && i % 38 == 0) {
				System.out.println();
				System.out.print("| ");
			}
		}
		System.out.println();
		System.out.println("| 작성자 : " + post.getPostWriter());
		System.out.println("| 작성일 : " + post.getPostDate());
		System.out.println("---------------------------------------------------");
	}

	public void postMenu() {
		System.out.println("----------------------게시판----------------------");
		System.out.println("| 1.목록 | 2.조회 | 3.등록 | 4.수정 | 5.삭제 | 9.나가기 |");
		System.out.print("메뉴 입력 > ");
	}

	public void insertPost() {
		if (MemberManager.getInstance().judgePoint(5 - (MemberManager.mem.getMemberLevel() / 4))) {
			MemberManager.getInstance().minusPoint(5 - (MemberManager.mem.getMemberLevel() / 4));
			Post post = new Post();
			System.out.println("--------------------게시글 등록--------------------");
			System.out.print("제목 입력 > ");
			String title = scn.nextLine();
			System.out.print("내용 입력 > ");
			String contents = scn.nextLine();
			post.setPostTitle(title);
			post.setPostContents(contents);
			post.setPostWriter(MemberManager.mem.getMemberName());
			post.setPostMemberNo(MemberManager.mem.getMemberNo());
			int result = pm.insertPost(post);
			System.out.println(result == 1 ? "게시글이 등록되었습니다" : "게시글 등록에 실패하였습니다");
		} else {
			System.out.println("포인트가 부족합니다");
		}
	}

	public void updatePost() {
		System.out.println("--------------------게시글 수정--------------------");
		System.out.print("게시글 번호 입력 > ");
		int postNo = Integer.parseInt(scn.nextLine());
		pm.showPost(postNo);
		System.out.println("| 1.제목 수정 | 2.내용 수정 |");
		int updateNo = Integer.parseInt(scn.nextLine());
		Post post = new Post();
		post.setPostNo(postNo);
		post.setPostWriter(MemberManager.mem.getMemberName());
		if (updateNo == 1) {
			System.out.print("수정할 제목을 입력하시오 > ");
			String title = scn.nextLine();
			post.setPostTitle(title);
		} else if (updateNo == 2) {
			System.out.print("수정할 내용을 입력하시오 > ");
			String contents = scn.nextLine();
			post.setPostTitle(null);
			post.setPostContents(contents);
		}
		int result = pm.updatePost(post);
		System.out.println(result == 1 ? "정상적으로 수정되었습니다." : "수정에 실패하였습니다.");
	}

	public void showPost(int postNo) {
		showDetailList(pm.showPost(postNo));
		System.out.println();
	}

	public void deletePost() {
		System.out.println("--------------------게시글 삭제--------------------");
		System.out.print("게시글 번호 입력 > ");
		int postNo = Integer.parseInt(scn.nextLine());
		int result = pm.deletePost(postNo);
		System.out.println(result == 3 ? "성공적으로 삭제하였습니다." : "삭제에 실패하였습니다.");
	}

	public void adminDeletePost() {
		System.out.println("--------------------게시글 삭제--------------------");
		System.out.print("게시글 번호 입력 > ");
		int postNo = Integer.parseInt(scn.nextLine());
		int result = pm.adminDeletePost(postNo);
		System.out.println(result == 2 ? "성공적으로 삭제하였습니다." : "삭제에 실패하였습니다.");
	}

	public void goodPost(int postNo) {
		if (MemberManager.getInstance().judgePoint(1)) {
			pm.goodPost(postNo);
		} else {
			System.out.println("포인트가 부족합니다");
		}
	}

	public void badPost(int postNo) {
		pm.badPost(postNo);
	}
}
