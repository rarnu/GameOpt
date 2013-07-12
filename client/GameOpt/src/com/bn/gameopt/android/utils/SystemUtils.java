package com.bn.gameopt.android.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.os.Debug;

import com.bn.gameopt.android.Global;
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
			// check the importance
			if (appProcessInfo.importance >= ActivityManager.RunningAppProcessInfo.IMPORTANCE_SERVICE) {
				int pid = appProcessInfo.pid;
				int uid = appProcessInfo.uid;
				String processName = appProcessInfo.processName;
				int[] myMempid = new int[] { pid };
				Debug.MemoryInfo[] memoryInfo = am
						.getProcessMemoryInfo(myMempid);
				int memSize = memoryInfo[0].dalvikPrivateDirty;
				ProcessInfo processInfo = new ProcessInfo(pid, uid, memSize,
						processName, appProcessInfo.pkgList);
				processInfoList.add(processInfo);
			}
		}
		return processInfoList;
	}

	public static void killProcess(Context context, String[] pkgNames) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		if (pkgNames != null && pkgNames.length != 0) {
			for (String pkg : pkgNames) {
				if (!pkg.contains(Global.CURRENT_GAME_PACKAGE_NAME)
						&& (!pkg.contains(context.getPackageName()))) {
					am.killBackgroundProcesses(pkg);
				}
			}
		}
	}

	public static void systemkillProcess(Context context, String[] pkgNames) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		if (pkgNames != null && pkgNames.length != 0) {
			for (String pkg : pkgNames) {
				if (!pkg.contains(Global.CURRENT_GAME_PACKAGE_NAME)
						&& (!pkg.contains(context.getPackageName()))) {
					am.forceStopPackage(pkg);
				}
			}
		}

	}

	public static void rootKillProcess(Context context, int pid, String[] pkgNames) {
		boolean canKill = true;

		if (pkgNames != null && pkgNames.length != 0) {
			for (String pkg : pkgNames) {
				if (pkg.equals(Global.CURRENT_GAME_PACKAGE_NAME)&& (!pkg.contains(context.getPackageName()))) {
					canKill = false;
					break;
				}
			}
		}
		if (canKill) {
			RootUtils.runCommand(String.format("kill %d", pid), true, null);
		}
	}

	public static void rootDropCache() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				RootUtils.runCommand("echo 3 > /proc/sys/vm/drop_caches", true,
						null);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
				RootUtils.runCommand("echo 0 > /proc/sys/vm/drop_caches", true,
						null);
			}
		}).start();

	}

	public static void openApp(Context context, String packageName) {
		PackageInfo pi = null;
		try {
			pi = context.getPackageManager().getPackageInfo(packageName, 0);
		} catch (NameNotFoundException e) {
		}

		if (pi == null) {
			return;
		}

		Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
		resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		resolveIntent.setPackage(pi.packageName);

		List<ResolveInfo> apps = context.getPackageManager()

		.queryIntentActivities(resolveIntent, 0);

		ResolveInfo ri = apps.iterator().next();
		if (ri != null) {
			String className = ri.activityInfo.name;
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_LAUNCHER);
			ComponentName cn = new ComponentName(packageName, className);
			intent.setComponent(cn);
			context.startActivity(intent);
		}
	}
}
