package jpl.ch01.ex14;

// import java.io.File;

public class Song {
	private String title;
	private String data;
	// 音声データを使って動作確認することはできないので、今回は文字列に音声データを入れる。
	// private File data;
	
	public Song(String title, String data) {
		this.title = title;
		this.data = data;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getData() {
		return this.data;
	}
}
