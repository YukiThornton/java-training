package jpl.ch01.ex16;

import java.io.FileInputStream;
import java.io.IOException;

public class MyUtilities {
	public double[] getDataSet(String setName) throws BadDataSetException{
		double[] result = new double[1];
		result[0] = 0.0;

		String file = setName + ".dset";
		FileInputStream in = null;
		
		try {
			in = new FileInputStream(file);
			return result;
		} catch (IOException e) {
			BadDataSetException badDataSetException = new BadDataSetException();
			badDataSetException.setDataSetName(file);
			badDataSetException.setIoException(e);
			throw badDataSetException;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			}catch (IOException e) {
				;
			}
		}
	}
}
