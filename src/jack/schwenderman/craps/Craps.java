package jack.schwenderman.craps;

import java.util.Calendar;
import java.util.Random;

public class Craps {
	long baseBet = 60;
	int oddsX = 3;
	public static void main(String[] args) {
		Craps craps = new Craps();
		craps.run(Integer.parseInt(args[0]));
	}
	private void run(int iterations) {
		Random rand = new Random(Calendar.getInstance().getTimeInMillis());
		byte die1 = 0;
		byte die2 = 0;
		double[] counts = {0,0,0,0,0,0,0,0,0,0,0};
		boolean[] points = {false,false,false,false,false,false};
		double bankroll = 10000;
		byte point =0;

		System.out.println("Bankroll = " + bankroll);

		for (int a=1;a<=iterations;a++) {
			die1 = (byte) (rand.nextInt(6) + 1);
			die2 = (byte) (rand.nextInt(6) + 1);
			point = (byte) (die1+die2);
			System.out.println(a + " " + die1 + "+" + die2 + "=" + point);
			counts[die1+die2-2]++;
			// Logic for playing the don't
			switch (point) {
				case 2: case 3:
					bankroll = bankroll + baseBet; System.out.println("Won " + baseBet);
					break;
				case 11:
					bankroll = bankroll - baseBet; System.out.println("Lost " + baseBet);
					break;
				case 7:
					bankroll = bankroll - baseBet; System.out.println("Lost " + baseBet);
					double oddsWin = 0;
					for (int p = 0;p < 6; p++) {
						if (points[p]) {
							bankroll = bankroll + baseBet; System.out.println("Won " + baseBet);
							switch (p) {
								case 0: case 5:
									oddsWin = baseBet*(3.0/6.0)*oddsX;
									break;
								case 1: case 4:
									oddsWin = baseBet*(4.0/6.0)*oddsX;
									break;
								case 2: case 3:
									oddsWin = baseBet*(5.0/6.0)*oddsX;
									break;
							}
							bankroll = bankroll + oddsWin; System.out.println("Won " + oddsWin);
						}
						points[p] = false;
					}
					break;
				case 4: case 5: case 6: case 8: case 9: case 10:
					int offset = 4;
					if (point > 7) offset = 5;
					if (points[point-offset]) {
						bankroll = bankroll - (baseBet*(oddsX+1)); System.out.println("Lost " + (baseBet*(oddsX+1)));
					}
					points[point-offset] = true;
					break;
				case 12:
					System.out.println("Push");
					break;
			}
		}
		for (int a=1;a<12;a++) {
			System.out.println((a+1)+" "+counts[a-1]+ "   "+Math.round((counts[a-1]/iterations)*10000)/100.0+"%  "+
					Math.round(((a<7) ? a/36.0 : (12-a)/36.0)*10000)/100.0+"%");
		}

		System.out.println("Bankroll = " + bankroll);
		
	}
}
