import java.util.ArrayList;
import java.util.Scanner;

public class SlidingBlocks {

	static ArrayList<Node> fringe = new ArrayList<Node>();
	static ArrayList<String> closed = new ArrayList<String>();
	static int pathCost = 0;
	static int stepCount = 0;

	public static void insertionSort(ArrayList<Node> array) {
		int i, j;
		Node key;
		for (i = 1; i < array.size(); i++) {
			key = array.get(i);
			j = i - 1;

			while (j >= 0 && array.get(j).getCost() > key.getCost()) {
				array.set(j + 1, array.get(j));
				j--;
			}
			array.set(j + 1, key);
		}
	}

	public static boolean isValidGoalState(String state) {
		int count = 0;// count number of white tiles on the left of any black tile
		for (int i = 0; i < state.length(); i++) {
			if (state.charAt(i) == 'W')
				count++;
			else if (state.charAt(i) == 'B') {
				if (count != 3)
					return false;
			}
		}
		return true;
	}

	private static void printRecursive(Node child) {
		if (child == null) {
			System.out.println("\nPath from initial to goal state:");
			return;
		}

		printRecursive(child.getParent());
		System.out.println("+---+---+---+---+---+---+---+");
		for (char c : child.getState().toCharArray())
			System.out.print("| " + c + " ");
		System.out.print("|");
		if (child.getParent() != null) {
			System.out.print("\t Step cost = " + (child.getG() - child.getParent().getG()));
			stepCount++;
			pathCost += child.getG() - child.getParent().getG();
		}
		System.out.println("\n+---+---+---+---+---+---+---+");
		System.out.println("              |");
		System.out.println("              V");
	}

	public static void printPath(Node child) {
		printRecursive(child);
		System.out.println("          Goal State");
		System.out.println("\t\t\t\t Number of steps = " + stepCount);
		System.out.println("\t\t\t\t Path cost = " + pathCost);
	}

	public static void algo(String initial, String goal) {
		fringe.add(new Node(initial, null));

		while (!fringe.isEmpty()) {
			Node next = fringe.remove(0);
			// System.out.println(next.getState());
			if (next.isGoal(goal)) {
				printPath(next);
				return;
			}
			updateFringe(next);
			insertionSort(fringe);
			closed.add(next.getState());
		}
		System.out.println("No solution!");
	}

	static void updateFringe(Node next) {
		ArrayList<Node> successors = next.getSuccesors();
		for (Node x : successors) {
			if (closed.contains(x.getState()))
				continue;
			int index = fringe.indexOf(x);
			if (index != -1) {
				if (fringe.get(index).getCost() > x.getCost())
					fringe.set(index, x);
			} else
				fringe.add(x);
		}

	}

	static boolean isValidState(String s) {
		if (s == null || s.length() != 7)
			return false;
		int countW = 0, countE = 0, countB = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == 'B')
				countB++;
			if (s.charAt(i) == 'W')
				countW++;
			if (s.charAt(i) == 'E')
				countE++;
		}
		if (countB == 3 && countW == 3 && countE == 1)
			return true;
		return false;
	}

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		boolean flag;
		String initial, goal;

		if (args.length == 2) {
			initial = args[0].toUpperCase();
			goal = args[1].toUpperCase();

			if (!(isValidState(initial) && isValidState(goal) && isValidGoalState(goal))) {
				System.out.println("Invalid input.");
				System.exit(-1);
			}
		} else {
			do {
				System.out.print("Give the initial state (eg.BBBEWWW): ");
				initial = in.nextLine().toUpperCase();

				flag = isValidState(initial);
				if (!flag) {
					System.out.println("Invalid input. Try again!");
				}
			} while (!flag);

			do {
				System.out.print("Give the goal state (eg.WWWEBBB): ");
				goal = in.nextLine().toUpperCase();

				flag = isValidState(goal);
				if (!flag) {
					System.out.println("Invalid input. Try again!");
				}

				else {
					flag = isValidGoalState(goal);
					if (!flag) {
						System.out.println("Not accepted goal state. Try again!");
					}
				}

			} while (!flag);
		}
		in.close();
		algo(initial, goal);
	}

}
