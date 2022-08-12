package com.jin.horseRace;

import com.jin.member.MemberManager;

public class HorseRace extends Horse {
	public HorseRace(int num) {
		run(num);
	}

	private void run(int num) {
		int length = 100;
		int[] xAxis = createAxis(length);
		int[] yAxis = createAxis(6);

		Horse[] horse = new Horse[4];
		int intExpress = 0;
		for (int i = 0; i < horse.length; i++) {
			horse[i] = new Horse();
			horse[i].set(1, i + 1);
		}
		boolean runHorse = true;
		while (runHorse) {
			for (int i = 0; i < horse.length; i++) {
				if (horse[i].getX() >= length - 5) {
					System.out.println((i + 1) + "번 말이 우승하였습니다.");
					runHorse = false;
					if (num == (i + 1)) {
						MemberManager.getInstance().plusPoint(10);
						MemberManager.getInstance().checkPointUse(10, 1, 6);
					}
					break;
				}
			}
			if (!runHorse) {
				break;
			}
			clear();
			for (int i = 0; i < yAxis.length; i++) {
				for (int j = 0; j < xAxis.length; j++) {
					for (int k = 0; k < horse.length; k++) {
						if (horse[k].getX() == xAxis[j] && horse[k].getY() == yAxis[i]) {
							intExpress++;
//						System.out.println(intExpress);
						}
					}
					System.out.print(intExpress == 1 ? MemberManager.mem.getMemberGender().equals("남") ? "♂" : "♀"
							: j == xAxis.length - 7 ? "|" : "_");
					if (intExpress == 1) {
						intExpress = 0;
					}
				}
				System.out.println();
			}
			int randomNo = 0;
			for (int i = 0; i < horse.length; i++) {
				randomNo = Math.round((float) Math.random() * 3);
				horse[i].move(randomNo);
			}
			try {
				Thread.sleep(150);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void clear() {
		for (int i = 0; i < 10; i++) {
			System.out.println();
		}
	}

	private int[] createAxis(int length) {
		int[] axis = new int[length];
		for (int i = 0; i < length; i++) {
			axis[i] = i;
		}
		return axis;
	}

}
