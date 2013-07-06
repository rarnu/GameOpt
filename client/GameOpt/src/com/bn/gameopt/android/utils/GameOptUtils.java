package com.bn.gameopt.android.utils;

import java.util.List;

import android.content.Context;

import com.bn.gameopt.android.Global;
import com.bn.gameopt.android.classes.ProcessInfo;
import com.rarnu.command.RootUtils;

public class GameOptUtils {

	public static int getSystemStatus(Context context) {
		// MemoryInfo mi = SystemUtils.getMemoryInfo();
		// List<ProcessInfo> list =
		// SystemUtils.getRunningAppProcessInfo(context);

		// TODO: system analysis and giving a number count, if you want to get
		// the system info, uncomment the lines above

		return 0;
	}

	public static void killBackgroundServices(Context context) {
		// killBackgroundServices
		List<ProcessInfo> list = SystemUtils.getRunningAppProcessInfo(context);
		boolean ignoreKill = false;
		for (ProcessInfo pi : list) {
			ignoreKill = false;
			if (pi.pkgNames != null && pi.pkgNames.length != 0) {
				for (String pkg : pi.pkgNames) {
					if (Global.listIgnorePackages.indexOf(pkg) != -1) {
						ignoreKill = true;
						break;
					}
				}
			}
			if (!ignoreKill) {
				if (RootUtils.hasRoot() == RootUtils.LEVEL_ROOTED) {
					SystemUtils.rootKillProcess(pi.pid);
				} else {
					SystemUtils.killProcess(context, pi.pkgNames);
				}
			}
		}
	}

	public static void cleanMemory() {
		// cleanMemory, root only
		if (RootUtils.hasRoot() == RootUtils.LEVEL_ROOTED) {
			SystemUtils.rootDropCache();
		}
	}

	public static void cleanAll(Context context) {
		// do this in background, every 10 minutes
		killBackgroundServices(context);
		cleanMemory();
	}
}
