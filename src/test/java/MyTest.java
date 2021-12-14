import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MyTest {
	static DB_Solver2 solver;
	
	@Test
	void findZeroTest1() {
		int[] x = {1,2,3,0,4,5,6,7,9,10,11,12,15,6,3,4};
		Node n = new Node(x);
		solver = new DB_Solver2(n, "heuristicOne");
		
		
		assertEquals(3, solver.findZero(x), "Wrong Value");
	}

	@Test
	void findZeroTest2() {
		int[] x = {1,2,3,1,4,5,6,7,9,10,11,12,15,6,3,4};
		Node n = new Node(x);
		solver = new DB_Solver2(n, "heuristicOne");
		
		
		assertEquals(-1, solver.findZero(x), "Wrong Value");
	}
	
	@Test
	void goalTestTest1() {
		int[] x = {1,2,3,1,4,5,6,7,9,10,11,12,15,6,3,4};
		Node n = new Node(x);
		solver = new DB_Solver2(n, "heuristicOne");
		
		
		assertEquals(false, solver.goalTest(x), "Wrong Value");
	}
	
	@Test
	void goalTestTest2() {
		int[] x = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
		Node n = new Node(x);
		solver = new DB_Solver2(n, "heuristicOne");
		
		
		assertEquals(true, solver.goalTest(x), "Wrong Value");
	}
	
	@Test
	void copyArrayTest1() {
		int[] x = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
		Node n = new Node(x);
		solver = new DB_Solver2(n, "heuristicOne");
		
		
		assertArrayEquals(x, solver.copyArray(x), "Wrong Value");
	}
	
	@Test
	void copyArrayTest2() {
		int[] x = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
		Node n = new Node(x);
		solver = new DB_Solver2(n, "heuristicOne");
		
		int[] y = {};
		
		assertArrayEquals(y, solver.copyArray(y), "Wrong Value");
	}
	
	@Test
	void moveZeroTest1() {
		int[] x = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
		Node n = new Node(x);
		solver = new DB_Solver2(n, "heuristicOne");
		
		solver.moveZero(x, 0, 5);
		
		assertEquals(5, solver.findZero(x), "Wrong Value");
	}
	
	@Test
	void moveZeroTest2() {
		int[] x = {3,1,2,3,4,5,6,7,8,9,10,11,12,13,14,0};
		Node n = new Node(x);
		solver = new DB_Solver2(n, "heuristicOne");
		
		solver.moveZero(x, 15, 5);
		
		assertEquals(5, solver.findZero(x), "Wrong Value");
	}
	
	@Test
	void getH1Test1() {
		int[] x = {3,1,2,3,4,5,6,7,8,9,10,11,12,13,14,0};
		Node n = new Node(x);
		solver = new DB_Solver2(n, "heuristicOne");
		
		
		
		assertEquals(2, solver.getH1(x), "Wrong Value");
	}
	
	@Test
	void getH1Test2() {
		int[] x = {15,14,13,12,11,10,9,8,7,6,5,4,3,2,1,0};
		Node n = new Node(x);
		solver = new DB_Solver2(n, "heuristicOne");
		
		
		
		assertEquals(16, solver.getH1(x), "Wrong Value");
	}
	
	
}
