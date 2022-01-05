package eu.spigotui.utils;

import java.awt.Dimension;
import java.awt.Point;

public class BoundingBox {

	int x = 0;
	int y = 0;
	int z = 0;
	int width = 1;
	int height = 1;

	public BoundingBox() {
	}

	public BoundingBox(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public BoundingBox(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public BoundingBox(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public BoundingBox(int x, int y, int z, int width, int height) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.width = width;
		this.height = height;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setSize(Dimension d) {
		this.width = d.width;
		this.height = d.height;
	}
	
	public Dimension getSize() {
		return new Dimension(this.width, this.height);
	}
	
	public Point getPos() {
		return new Point(x,y);
	}

}
