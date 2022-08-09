package com.jin.reply;

import java.sql.Date;

import lombok.Data;

@Data
public class Reply {

	private int replyNo;
	private String replyContents;
	private String replyWriter;
	private int replyPostNo;
	private Date replyDate;
}
