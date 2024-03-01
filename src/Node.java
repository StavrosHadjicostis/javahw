import java.util.ArrayList;

public class Node {
	private String state;
	private Node parent;
	private int g;
	private int h;

	public Node(String state, Node parent) {
		this.state = state;
		this.parent = parent;
		if (parent != null)// initial state does not have a parent
			this.g = parent.getG() + calcG();
		else
			this.g = 0;
		this.h = calcH();
	}

	private int calcG() {
		int p = 0; // index of empty space in parent state
		for (int i = 0; i < parent.getState().length(); i++)
			if (parent.getState().charAt(i) == 'E')
				p = i;

		int c = 0; // index of empty space in child state(this)
		for (int i = 0; i < getState().length(); i++)
			if (getState().charAt(i) == 'E')
				c = i;

		if (Math.abs(p - c) == 1) // a tile is moved to a neighboring empty space
			return 1;
		else if (Math.abs(p - c) == 2 || Math.abs(p - c) == 3)
			return Math.abs(p - c) - 1;
		return 100; // algo should never come at this point
	}

	private int calcH() {
		int res = 0;
		for (int i = 0; i < state.length(); i++)
			if (state.charAt(i) == 'W')
				for (int j = 0; j < i; j++)
					if (state.charAt(j) == 'B')
						res++;
		return res;
	}

	boolean isGoal(String goal) {
		if (goal.equals(state))
			return true;
		return false;
	}

	public ArrayList<Node> getSuccesors() {
		ArrayList<Node> successors = new ArrayList<Node>();
		String currState = this.getState();
		
		int posE = currState.indexOf('E');
		StringBuilder sb = null;

		if (posE - 1 >= 0) {
			sb = new StringBuilder(currState);
			sb.setCharAt(posE, currState.charAt(posE - 1));
			sb.setCharAt(posE - 1, 'E');
			successors.add(new Node(sb.toString(), this));
		}

		if (posE - 2 >= 0) {
			sb = new StringBuilder(currState);
			sb.setCharAt(posE, currState.charAt(posE - 2));
			sb.setCharAt(posE - 2, 'E');
			successors.add(new Node(sb.toString(), this));
		}

		if (posE - 3 >= 0) {
			sb = new StringBuilder(currState);
			sb.setCharAt(posE, currState.charAt(posE - 3));
			sb.setCharAt(posE - 3, 'E');
			successors.add(new Node(sb.toString(), this));
		}

		if (posE + 1 <= 6) {
			sb = new StringBuilder(currState);
			sb.setCharAt(posE, currState.charAt(posE + 1));
			sb.setCharAt(posE + 1, 'E');
			successors.add(new Node(sb.toString(), this));
		}

		if (posE + 2 <= 6) {
			sb = new StringBuilder(currState);
			sb.setCharAt(posE, currState.charAt(posE + 2));
			sb.setCharAt(posE + 2, 'E');
			successors.add(new Node(sb.toString(), this));
		}

		if (posE + 3 <= 6) {
			sb = new StringBuilder(currState);
			sb.setCharAt(posE, currState.charAt(posE + 3));
			sb.setCharAt(posE + 3, 'E');
			successors.add(new Node(sb.toString(), this));
		}

		return successors;
	}

	public String getState() {
		return state;
	}

	public Node getParent() {
		return parent;
	}

	public int getG() {
		return g;
	}

	public int getH() {
		return h;
	}
	
	public int getCost() {
		return g+h;
	}

	@Override
	public boolean equals(Object obj) {
		Node x = (Node) obj;
		return this.state.equals((x.state));
	}
	
}
