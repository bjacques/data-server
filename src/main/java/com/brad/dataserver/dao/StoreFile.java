package com.brad.dataserver.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class StoreFile implements Store {
	
	private static final String FILE_NAME = "store.txt";
	
	private static final boolean APPEND_ONLY = true;
	
	private File file;

	public StoreFile(String dir) throws IOException {
		file = new File(dir + "\\" + FILE_NAME);
		file.createNewFile();
	}

	@Override
	public void store(String value) {
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(file, APPEND_ONLY);
			bw = new BufferedWriter(fw);
			bw.write(value);
			bw.newLine();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			close(bw);
			close(fw);
		}
	}

	@Override
	public String readLastValue() {
		FileReader fr = null;
		BufferedReader br = null;
		String value = null;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			value = br.readLine();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			close(br);
			close(fr);
		}
		return value;
	}

	public File getFile() {
		return file;
	}

	private void close(Closeable resource) {
		if (resource != null) {
			try {
				resource.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
