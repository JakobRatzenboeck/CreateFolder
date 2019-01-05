package Model;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class FolderManipulation {

	public void openDirectory(String path) {
		try {
			Runtime.getRuntime().exec("explorer.exe /select," + path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean createFolder(String fullpath) {
		// Create a directory; all non-existent ancestor directories are
		// automatically created
//		File folder = new File(fullpath);
//		if(!folder.exists()) {
			return (new File(fullpath)).mkdirs();
//		} else {
//			
//		}
	}

	public void createDirectory(String path, String[] nameing) {
		for (String string : nameing) {
			System.out.println(path + string);
			createFolder(path + string);
		}
	}

	public String[] NumberFolderNames(Integer from , Integer to, String description) {
		ArrayList<String> names = new ArrayList<String>();
		while(from <= to) {
			names.add(from+""+description);
			from++;
		}
		String[] namearray = new String[names.toArray().length];
		int i = 0;
		for (String s : names) {
			namearray[i] = s;
			i++;
		}
		return namearray;
	}
	
	public String[] dateFolderNames(boolean full, LocalDate from, LocalDate to, int repeating, int intervall) {
		ArrayList<String> names = new ArrayList<String>();
		while (from.getYear() <= to.getYear()) {
			while (from.getMonth().getValue() <= to.getMonth().getValue()) {
				while (from.getDayOfMonth() <= to.getDayOfMonth()) {
					if (repeating == 1) {
						names.add(getDate(full, from));
						from = from.plusDays(intervall);
					}
					if (repeating == 2) {
						names.add(getDate(full, from));
						from = from.plusWeeks(intervall);
					}
				}
				if (repeating == 3) {
					names.add(getDate(full, from));
					from = from.plusMonths(intervall);
				} else {
					from = from.plusMonths(1);
				}
			}
			if (repeating == 4) {
				names.add(getDate(full, from));
				from = from.plusYears(intervall);
			} else {
				from = from.plusYears(1);
			}
		}
		String[] namearray = new String[names.toArray().length];
		int i = 0;
		for (String s : names) {
			namearray[i] = s;
			i++;
		}
		return namearray;
	}

	public String getDate(boolean full, LocalDate date) {
		if (full) {
			return date.getYear() + "." + date.getMonthValue() + "." + date.getDayOfMonth() + "";
		} else {
			return String.valueOf(date.getYear()).substring(2, 4) + "." + date.getMonthValue() + "."
					+ date.getDayOfMonth() + "";
		}
	}

}
