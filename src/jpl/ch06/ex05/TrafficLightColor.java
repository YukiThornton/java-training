package jpl.ch06.ex05;

import java.awt.Color;

/*
 * Answer to 6.5
 * 定数固有のメソッドを定義することを推奨しない
 * 理由：シンプルなabstractメソッドを各enum定数が実装する場合、コンストラクタによって各Colorオブジェクトを設定する必要がなくなる。
 * しかし、各enum定数内のコード量は多くなる。enum定数の数が多い場合はコード量が肥大するだけなので、getColorの処理が複雑でない限り、
 * 定数固有のメソッドを定義することを推奨しない
 */
public enum TrafficLightColor {
	RED{
		Color getColor() {
			return Color.RED;
		}
	},
	YELLOW{
		Color getColor() {
			return Color.YELLOW;
		}		
	},
	BLUE{
		Color getColor() {
			return Color.BLUE;
		}		
	};
	
	abstract Color getColor();
}
