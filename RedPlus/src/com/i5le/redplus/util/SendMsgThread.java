package com.i5le.redplus.util;

public	class SendMsgThread extends Thread {

	private String msg;
	private String tel;

	public SendMsgThread(String msg, String tel) {
		super();
		this.msg = msg;
		this.tel = tel;
	}

	@Override
	public void run() {
		HttpUtil.sendMsg(tel, msg);
		// TODO Auto-generated method stub

	}

}