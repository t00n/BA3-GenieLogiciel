package be.ac.ulb.infof307.g05.model;

import static org.junit.Assert.*;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jme3.math.Vector2f;

public class WallTest {
	private Wall wall = null;

	@Before
	public void setUp() throws Exception {
		Vector2f start = new Vector2f(0,1);
		Vector2f end = new Vector2f(5,7);
		this.wall = new Wall(start, end, 1, 10);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConstructor() {
		assertEquals(0.0,this.wall.getStart().getX(), 0.1);
		assertEquals(1.0,this.wall.getStart().getY(), 0.1);
		assertEquals(5.0,this.wall.getEnd().getX(), 0.1);
		assertEquals(7.0,this.wall.getEnd().getY(), 0.1);
		assertEquals(1, this.wall.getThickness());
		assertEquals(10, this.wall.getHeight());
	}
	
	@Test
	public void testComponent() {
		Window win1 = new Window();
		Window win2 = new Window();
		
		// Test of add
		this.wall.add(win1);
		this.wall.add(win2);
		assertSame(win1, this.wall.getChild(0));
		assertSame(win2, this.wall.getChild(1));
		
		// Test of iterator (next and hasNext)
		IteratorComposite iter = this.wall.makeIterator();
		assertSame(win1,iter.next());
		assertTrue(iter.hasNext());
		assertSame(win2,iter.next());
		assertFalse(iter.hasNext());
		
		// test of remove
		iter = this.wall.makeIterator();
		assertSame(win1,iter.next());
		this.wall.remove(win2);
		assertFalse(iter.hasNext());
	}

}
