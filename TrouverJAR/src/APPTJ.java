import java.io.File;

public class APPTJ {

	public static void main(String[] argv) {

		File f = new File("openimaj-master");
		Tjar(f);
	}

	private static void Tjar(File r) {
		for(File file : r.listFiles()) {
			if(file.isDirectory())
				Tjar(file);

			else if (file.getName().endsWith("jar"))
				System.out.println(file.getAbsolutePath());

		}
	}
}
