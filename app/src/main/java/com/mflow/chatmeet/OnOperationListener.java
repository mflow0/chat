package com.mflow.chatmeet;


/**
 * 이 인터페이스는 메시지를 보내거나 face를 선택했을때를 정의하기 위한 인터페이스입니다.
 *
 **/


public interface OnOperationListener {

	public void send(String content);
	
	public void selectedFace(String content);
	
	public void selectedFuncation(int index);
	

}
