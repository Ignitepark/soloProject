package com.jin.horseRace;

import lombok.Data;

@Data
public class Horse {
	private int x, y;

	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void move(int x) {
		this.x += x;
	}
}
