import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
* Hey, a comment
*/
public final class FileNamer {

	public static void main(String[] args) {
		validate(args);
		
		String titlesList = args[0];
		String targetDirectory = args[1];

		System.out.println("titles file = " + titlesList);
		System.out.println("targetDirectory = " + targetDirectory);
		
		System.out.println(" ");

		String sCurrentLine;
		BufferedReader br;
		ArrayList<String> titles = new ArrayList<String>();
		ArrayList<Integer> chosen = new ArrayList<Integer>();
		Random generator = new Random();
		
		try {
			// grab book titles
			br = new BufferedReader(
					new FileReader(
//							"C:\\working\\java\\fileNamer\\src\\main\\resources\\Booklist.txt"));
							titlesList));

			while ((sCurrentLine = br.readLine()) != null) {
				String tempLine = sCurrentLine.substring(0,
						sCurrentLine.indexOf(" by"));

				//System.out.println(" reading [" + tempLine + "]");
				titles.add(tempLine);

			}

			// grab directory listing
			File file = new File(
//					"C:\\working\\java\\fileNamer\\src\\main\\resources\\after");
					targetDirectory);

			// Reading directory contents
			File[] files = file.listFiles();
			for (int ii = 0; ii < files.length; ii++) {
				//System.out.println("file[" + ii + "] = " + files[ii].getPath());
				String newName = getNewFileName(titles, chosen, generator);
				String newFullPath = files[ii].getParent() + "\\" + newName + ".pdf";
				//System.out.println("file[" + ii + "] = " + newFullPath);
				
				File oldOne = files[ii];
				File newOne = new File(newFullPath);
				System.out.println("Renaming [" + oldOne.getName() + "] to [[" + newOne.getName() + "]]");
				oldOne.renameTo(newOne);
			}

		} catch (IOException ioe) {
			// TODO Auto-generated catch block
			ioe.printStackTrace();

		}

	}
	
	/**
	* Hey, another comment
	*/
	public static String getNewFileName(ArrayList<String> titles, ArrayList<Integer> chosen, Random generator) {
		boolean duplicateFound = true;
		String newName = null;
		do {
			Integer pick = Integer.valueOf(generator.nextInt(titles.size()));
			if (chosen.indexOf(pick) == -1) {
				duplicateFound = false;
				chosen.add(pick);
				newName = titles.get(pick.intValue());
				//System.out.println("Will use : " + pick);
			} else {
				System.out.println("Duplicate found : " + pick);
			}
		} while (duplicateFound);
		
		return newName;
	}
	
	public static void validate(String[] args) {
		System.out.println(" ");

		if (args.length != 2) {
			System.out.println("Usage:");
			System.out.println("    FileName path_to_titles path_to_target_directory");
			System.exit(-1);
		}	
		
		File titles = new File(args[0]);
		if (!titles.exists()) {
			System.out.println("Unable to find Titles file " + args[0]);
			System.exit(-1);
		}
		
		if (!titles.isFile()) {
			System.out.println("Titles file " + args[0] + " isn't really a file");
			System.exit(-1);
		}
		
		File directory = new File(args[1]);
		if (!directory.exists()) {
			System.out.println("Unable to find Directory " + args[1]);
			System.exit(-1);
		}
		
		if (!directory.isDirectory()) {
			System.out.println("Directory " + args[1] + " isn't really a directory");
			System.exit(-1);
		}
	}
}