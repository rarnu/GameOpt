package com.bn.gameopt.android.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;

import com.bn.gameopt.android.classes.MemoryInfo;
import com.bn.gameopt.android.classes.ProcessInfo;
import com.rarnu.command.RootUtils;

public class SystemUtils {

	public static MemoryInfo getMemoryInfo() {
		return new MemoryInfo();
	}

	public static List<ProcessInfo> getRunningAppProcessInfo(Context context) {

		List<ProcessInfo> processInfoList = new ArrayList<ProcessInfo>();
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> appProcessList = am
				.getRunningAppProcesses();

		for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessList) {
			int pid = appProcessInfo.pid;
			int uid = appProcessInfo.uid;
			String processName = appProcessInfo.processName;
			int[] myMempid = new int[] { pid };
			Debug.MemoryInfo[] memoryInfo = am.getProcessMemoryInfo(myMempid);
			int memSize = memoryInfo[0].dalvikPrivateDirty;
			ProcessInfo processInfo = new ProcessInfo(pid, uid, memSize,
					processName, appProcessInfo.pkgList);
			processInfoList.add(processInfo);
		}
		return processInfoList;
	}

	public static void killProcess(Context context, String[] pkgNames) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		if (pkgNames != null && pkgNames.length != 0) {
			for (String pkg : pkgNames) {
				am.forceStopPackage(pkg);
			}
		}

	}

	public static void rootKillProcess(int pid) {
		RootUtils.runCommand(String.format("kill %d", pid), true, null);
	}

	public static void rootDropCache() {
		RootUtils.runCommand("echo 3 > /proc/sys/vm/drop_caches", true, null);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		RootUtils.runCommand("echo 0 > /proc/sys/vm/drop_caches", true, null);
	}

}
