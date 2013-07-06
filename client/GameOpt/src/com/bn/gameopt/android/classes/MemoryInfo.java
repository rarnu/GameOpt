package com.bn.gameopt.android.classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import android.util.Log;

public class MemoryInfo {

	public int memTotal;
	public int memFree;
	public int buffers;
	public int cached;

	public MemoryInfo() {
		getMemoryInfo();
	}

	private void getMemoryInfo() {
		String minfoPath = "/proc/meminfo";
		String line = "";
		try {
			FileReader fr = new FileReader(minfoPath);
			BufferedReader br = new BufferedReader(fr, 8192);
			while ((line = br.readLine()) != null) {

				line = line.replaceAll("\\s+", " ");
				if (line.startsWith("MemTotal:")) {
					memTotal = getNumbericValue(line);
				}
				if (line.startsWith("MemFree:")) {
					memFree = getNumbericValue(line);
				}
				if (line.startsWith("Buffers:")) {
					buffers = getNumbericValue(line);
				}
				if (line.startsWith("Cached:")) {
					cached = getNumbericValue(line);
				}
			}
			br.close();
			fr.close();
		} catch (IOException e) {
		}
	}

	private int getNumbericValue(String line) {
		// MemTotal: 1927772 kB
		Log.e("getNumbericValue", line);
		int ret = 0;
		String[] ss = line.split(" ");
		if (ss.length > 2) {
			ret = Integer.parseInt(ss[1]);
			Log.e("getNumbericValue", String.valueOf(ret));
		}
		return ret;
	}

}
