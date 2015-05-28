package com.mflow.chatmeet.Message;

import java.util.Date;

/**
 * 이 클래스는 메시지의 정보를 전달할때 사용하는 클래스입니다.  메시지 상태,정보(사진,글,등),메시지전송여부
 * 일명 bean 클래스, 정보 저장의 목적으로 만들어진 클래스
 **/

public class Message {
	public final static int MSG_TYPE_TEXT 	= 0;
	public final static int MSG_TYPE_PHOTO 	= 1;
	public final static int MSG_TYPE_FACE 	= 2;
	
	public final static int MSG_STATE_SENDING 	= 0;
	public final static int MSG_STATE_SUCCESS 	= 1;
	public final static int MSG_STATE_FAIL 		= 2;
	
	private Long id;
	private Integer type;		// 0-text | 1-photo | 2-face | more type ...    메시지 타입
	private Integer state; 		// 0-sending | 1-success | 2-fail	메시지 상태
	private String fromUserName;		//보낸 유저 이름
	private String fromUserAvatar;		//보낸 유저 아바타
	private String toUserName;		//받은 유저 이름
	private String toUserAvatar;	//받은 유저 아바
	private String content;		//메시지 내용

	private Boolean isSend;		//true 시 다른사람이 메시지를 보냄 왼편, false 시 내가 메시지를 보냄 오른편
	private Boolean sendSucces;		//메시지 성공적으로 보냄
	private Date time;			//메시지 전송 시간

	public Message(Integer type, Integer state, String fromUserName,
			String fromUserAvatar, String toUserName, String toUserAvatar,
			String content, Boolean isSend, Boolean sendSucces, Date time) {
		super();
		this.type = type;
		this.state = state;
		this.fromUserName = fromUserName;
		this.fromUserAvatar = fromUserAvatar;
		this.toUserName = toUserName;
		this.toUserAvatar = toUserAvatar;
		this.content = content;
		this.isSend = isSend;
		this.sendSucces = sendSucces;
		this.time = time;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getFromUserAvatar() {
		return fromUserAvatar;
	}

	public void setFromUserAvatar(String fromUserAvatar) {
		this.fromUserAvatar = fromUserAvatar;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getToUserAvatar() {
		return toUserAvatar;
	}

	public void setToUserAvatar(String toUserAvatar) {
		this.toUserAvatar = toUserAvatar;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getIsSend() {
		return isSend;
	}

	public void setIsSend(Boolean isSend) {
		this.isSend = isSend;
	}

	public Boolean getSendSucces() {
		return sendSucces;
	}

	public void setSendSucces(Boolean sendSucces) {
		this.sendSucces = sendSucces;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}
