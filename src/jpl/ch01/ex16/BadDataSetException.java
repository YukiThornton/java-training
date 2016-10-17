package jpl.ch01.ex16;

import java.io.IOException;

public class BadDataSetException extends Exception {
	private IOException ioException;
	private String dataSetName;
	
	public IOException getIoException() {
		return ioException;
	}
	public void setIoException(IOException ioException) {
		this.ioException = ioException;
	}
	public String getDataSetName() {
		return dataSetName;
	}
	public void setDataSetName(String dataSetName) {
		this.dataSetName = dataSetName;
	}
	
}
