package com.peter.rogue.lighting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.peter.rogue.Global;
import com.peter.rogue.Rogue;
import com.peter.rogue.lighting.Line;
import com.peter.rogue.lighting.Point;

public class Light 
{
	private Point position;
	private float size;
	private float range;
	private Color color;
	private float angle;
	public Line[] rays;
	
	public Light(Point new_pos, float new_size, float new_range, Color new_color){
		position = new_pos;
		size = new_size;
		range = new_range;
		color = new_color;
		rays = new Line[3601];
	}
	
	public void update(){
		for(float i = 0; i < rays.length; i++){
			rays[(int)i] = new_ray(i / 10);
		}
		
		position.setXY(Gdx.input.getX(), Gdx.input.getY() * -1 + Global.HEIGHT);
		for(int i = 0; i < rays.length - 1; i++){
			for(int j = 0; j < Main.visible_rects.size(); j++){
				if(rays[i].is_intersected(Main.visible_rects.get(j).getLineTop())){
					rays[i] = new Line(rays[i].begin, rays[i].returnIntersectedPoint(Main.visible_rects.get(j).getLineTop()));
				}
				if(rays[i].is_intersected(Main.visible_rects.get(j).getLineRight())){
					rays[i] = new Line(rays[i].begin, rays[i].returnIntersectedPoint(Main.visible_rects.get(j).getLineRight()));
				}
				if(rays[i].is_intersected(Main.visible_rects.get(j).getLineBottom())){
					rays[i] = new Line(rays[i].begin, rays[i].returnIntersectedPoint(Main.visible_rects.get(j).getLineBottom()));
				}
				if(rays[i].is_intersected(Main.visible_rects.get(j).getLineLeft())){
					rays[i] = new Line(rays[i].begin, rays[i].returnIntersectedPoint(Main.visible_rects.get(j).getLineLeft()));
				}
			}
		}
	}
	
	public Line new_ray(float rotation)
	{
		return new Line(position, new Point((float)Math.cos(Math.toRadians(rotation)) * range, 
				                            (float)Math.sin(Math.toRadians(rotation)) * range));
	}
	
	public void renderRays(ShapeRenderer shapeRenderer){
		for(int i = 0; i < rays.length - 1; i++){
			shapeRenderer.line(rays[i].getX(), rays[i].getY(), rays[i].getX2(), rays[i].getY2());
		}
	}
	
	public Color get_color()
	{
		return color;
	}
	
	public Point get_position()
	{
		return position;
	}
}