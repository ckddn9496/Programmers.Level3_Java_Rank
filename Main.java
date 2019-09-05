import java.util.ArrayList;
import java.util.HashSet;

public class Main {

	public static void main(String[] args) {
		int n = 5;
		int[][] results = {{4, 3}, {4, 2}, {3, 2}, {1, 2}, {2, 5}}; // return 2
	
		System.out.println(new Solution().solution(n, results));
	}
}

class Solution {
	class Boxer {
		int name;
		int rank;
		HashSet<Integer> stronger, weaker;
		
		public Boxer(int name) {
			this.name = name;
			this.stronger = new HashSet<Integer>();
			this.weaker = new HashSet<Integer>();
		}
		
		public void addStrongBoxer(int name) {
			this.stronger.add(name);
		}
		
		public void addWeakBoxer(int name) {
			this.weaker.add(name);
		}
		
		// n: number of boxers
		public int getRank(int n) {
			if (n-1 == (stronger.size()+weaker.size()))
				return stronger.size() + 1;
			else
				return 0;
		}
		@Override
		public String toString() {
			return name + " " + stronger.toString() + " " + weaker.toString();
		}
	}
	
    public int solution(int n, int[][] results) {
        int answer = 0;
        ArrayList<Boxer> boxers = new ArrayList<>();
        boxers.add(new Boxer(0));
        for (int i = 1; i <= n; i++)
        	boxers.add(new Boxer(i));
        
        for (int[] result : results) {
        	int winner = result[0];
        	int loser = result[1];
        	boxers.get(winner).addWeakBoxer(loser);
        	boxers.get(loser).addStrongBoxer(winner);
        }
        int num = 0;
        while (num++ < 2) {
	        for (int i = 1; i <= n; i++) {
	        	Boxer boxer = boxers.get(i);
	        	// 승자의 승자를 찾아 승자 리스트에 추가
	        	HashSet<Integer> winnersWinner = new HashSet<>();
	        	for (Integer otherBoxerName : boxer.stronger) {
	        		for (Integer winner: boxers.get(otherBoxerName).stronger)
	        			winnersWinner.add(winner);
	        	}
	        	boxer.stronger.addAll(winnersWinner);
	        	
	        	HashSet<Integer> losersLoser = new HashSet<>();
	        	for (Integer otherBoxerName : boxer.weaker) {
	        		for (Integer loser: boxers.get(otherBoxerName).weaker)
	        			losersLoser.add(loser);
	        	}
	        	boxer.weaker.addAll(losersLoser);
	        }
        }
        
        for (Boxer boxer : boxers) {
        	int rank = boxer.getRank(n);
        	if (rank != 0)
        		answer++;
        }
        
        return answer;
    }
}