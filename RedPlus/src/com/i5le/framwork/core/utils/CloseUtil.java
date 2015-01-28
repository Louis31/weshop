package com.i5le.framwork.core.utils;

import java.io.Closeable;
import java.io.IOException;

public class CloseUtil {
	public static void close(Closeable obj){
		try {
			if(obj!=null) obj.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
