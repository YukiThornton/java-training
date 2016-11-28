package jpl.ch06.ex05;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

public class TrafficLightColorTest {

	@Test
	public void testGetColor() {
		assertEquals(Color.RED, TrafficLightColor.RED.getColor());
		assertEquals(Color.YELLOW, TrafficLightColor.YELLOW.getColor());
		assertEquals(Color.BLUE, TrafficLightColor.BLUE.getColor());
	}

}
