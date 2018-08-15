package Game;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TheGameJUnit {

	TheGame theGame = new TheGame();

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Test
	void testEvaluatePlayerResponse() {

		assertTrue("Expected profile ids to match.", theGame.evaluatePlayerResponse("1", "1"));
	}

}
