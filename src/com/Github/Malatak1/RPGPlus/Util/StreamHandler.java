package com.Github.Malatak1.RPGPlus.Util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.Github.Malatak1.RPGPlus.RPGPlus;

public final class StreamHandler {
	
//	InputStream in;
//	File file;
//	
//	public StreamHandler(InputStream in, File file) {
//		this.in = in;
//		this.file = file;
//	}
	
	public void run(File file) {
		
		final BufferedInputStream in = new BufferedInputStream(RPGPlus.getBaseFile());
//		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		try (FileOutputStream out = new FileOutputStream(file)) {
			in.mark(100);
			int len;
			while ((len = in.read()) != -1) {
			    out.write(len);
			}
			in.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** another attempt */
//	public void run(File file) {
//		final BufferedInputStream in = new BufferedInputStream(RPGPlus.getBaseFile());
//		try (FileOutputStream out = new FileOutputStream(file)) {
//			in.mark(100);
//			byte[] buffer = new byte[1024];
//			int len = in.read(buffer);
//			while (len != -1) {
//			    out.write(buffer, 0, len);
//			    len = in.read(buffer);
//			}
//			in.reset();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	/** Third */
//	public void run(File file) {
//		BufferedInputStream in = new BufferedInputStream(RPGPlus.getBaseFile());
//		try (FileOutputStream out = new FileOutputStream(file)) {
//			in.mark(100);
//			int c;
//			while ((c = in.read()) != -1) {
//				out.write(c);
//			}
//			in.reset();
//			out.flush();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				in.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//				System.exit(1);
//			}
//		}
//	}
	/** attempt 2 */	
//		Path source = Paths.get(getClass().getResource("/baseFile.yml").getFile());
//		Bukkit.getLogger().info(source.toString());
//		Path target = Paths.get(file.getAbsolutePath());
//		
//		Charset charset = Charset.forName("US-ASCII");
//		ArrayList<String> lines = new ArrayList<>();
//		try (BufferedReader reader = Files.newBufferedReader(source, charset)) {
//			String line = null;
//			while ((line = reader.readLine()) != null) {
//				lines.add(line);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		try (BufferedWriter writer = Files.newBufferedWriter(target, charset)){
//			Iterator<String> iterator = lines.iterator();
//			while(iterator.hasNext()) {
//				String s = iterator.next();
//				writer.append(s, 0, s.length());
//				writer.newLine();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		/** original code */
//		InputStream in = stream;
//	    try {
//	        OutputStream out = new FileOutputStream(file);
//	        byte[] buf = new byte[1024];
//	        int len;
//	        while((len=in.read(buf))>0){
//	            out.write(buf,0,len);
//	        }
//	        out.close();
//	        in.close();
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	    }
}
