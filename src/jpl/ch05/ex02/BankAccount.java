package jpl.ch05.ex02;

import java.util.ArrayList;
import java.util.List;

/*
 * Answer to 5.1
 * HistoryクラスはActionクラスと同様にインスタンスと関連付けられているため、内部クラスとしてネストされるべき。
 */
public class BankAccount {
	
	private long number; // 口座番号
	private long balance; // 現在の残高(単位は、セント)
	private Action lastAct; // 最後に行われた処理
	private History history;
	
	public BankAccount(long number, long balance) {
		this.number = number;
		this.balance = balance;
		history = new History();
	}

	public long number() {
		return number;
	}

	public long balance() {
		return balance;
	}

	public Action lastAct() {
		return lastAct;
	}

	public History history() {
		return (History)history.clone();
	}
	
	public void deposit(long amount) {
		balance += amount;
		Action newAction = new Action("deposit", amount);
		history.queue(newAction);
		lastAct = newAction;
	}
	
	public void withdraw(long amount) {
		balance -= amount;
		Action newAction = new Action("withdraw", amount);
		history.queue(newAction);
		lastAct = newAction;
	}
	
	public class Action {
		private String act;
		private long amount;
		Action(String act, long amount) {
			this.act = act;
			this.amount = amount;
		}
		public String toString() {
			// identify out enclosing account
			return number + ": " + act + " " + amount;
		}
	}
	
	public class History implements Cloneable{
		private final static int HISTORY_MAX = 10;
		private List<Action> actions;
		private int pointer;
		
		private History() {
			actions = new ArrayList<Action>();
			pointer = 0;
		}
		private void queue(Action newAction) { 
			if (actions.size() >= HISTORY_MAX)
				actions.remove(0);
			actions.add(newAction);
		}
		public Action next() {
			if (pointer > actions.size() - 1) {
				return null;				
			} else {
				Action action = actions.get(pointer);
				pointer++;
				return action;				
			}
		}
		
		public Object clone() {
			History clonedHistory = new History();
			clonedHistory.actions = this.actions;
			clonedHistory.pointer = this.pointer;
			return clonedHistory;
		}
	}
	
}
