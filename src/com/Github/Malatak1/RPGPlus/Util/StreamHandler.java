package com.Github.Malatak1.RPGPlus.Util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public final class StreamHandler implements Runnable {
	
	InputStream in;
	File file;
	
	public StreamHandler(InputStream in, File file) {
		this.in = in;
		this.file = file;
	}

	@Override
	public void run() {
	    try {
	        OutputStream out = new FileOutputStream(file);
	        byte[] buf = new byte[1024];
	        int len;
	        while((len=in.read(buf))>0){
	            out.write(buf,0,len);
	        }
	        out.close();
	        in.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
