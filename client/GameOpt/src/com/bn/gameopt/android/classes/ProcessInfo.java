package com.bn.gameopt.android.classes;

public class ProcessInfo {

	public int pid;
	public int uid;
	public int memSize;
	public String processName;
	public String[] pkgNames;

	public ProcessInfo() {

	}

	public ProcessInfo(int pid, int uid, int memSize, String processName, String[] pkgNames) {
		this.pid = pid;
		this.uid = uid;
		this.memSize = memSize;
		this.processName = processName;
		this.pkgNames = pkgNames;
	}

}
