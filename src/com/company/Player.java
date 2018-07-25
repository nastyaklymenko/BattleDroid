package com.company;



import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D.Double;

public class Player {
    public int x = 200;
    public int y = 100;
    public int w = 75;
    public int h = 75;
    public int cox = 30;
    public int coy = 45;
    public double vx;
    public double vy;
    GradientPaint gp1;
    Ellipse2D e1;
    Ellipse2D e2;
    Ellipse2D e3;
    Rectangle2D r1;
    BasicStroke bs1;
    Line2D l1;
    Line2D l2;
    Line2D l3;
    Line2D l4;
    Line2D l5;
    Line2D l6;
    Controller parent;
    int damage = 0;
    boolean dead;

    public Player(Controller var1) {
        this.parent = var1;
        this.gp1 = new GradientPaint((float)this.x, (float)this.y, new Color(207, 15, 25), (float)this.x, (float)(this.y + this.h), new Color(0, 15, 25), true);
        this.e1 = new Double(0.0D, 0.0D, (double)this.w, (double)this.h);
        this.e2 = new Double(0.0D, 0.0D, 20.0D, 30.0D);
        this.e3 = new Double(0.0D, 0.0D, 8.0D, 8.0D);
        this.r1 = new java.awt.geom.Rectangle2D.Double(-25.0D, -10.0D, 75.0D, 25.0D);
        this.bs1 = new BasicStroke(2.0F);
        this.l1 = new java.awt.geom.Line2D.Double(0.0D, 0.0D, 40.0D, 20.0D);
        this.l2 = new java.awt.geom.Line2D.Double(0.0D, 20.0D, 40.0D, 0.0D);
        this.l3 = new java.awt.geom.Line2D.Double(0.0D, 20.0D, 20.0D, 0.0D);
        this.l4 = new java.awt.geom.Line2D.Double(0.0D, 0.0D, 20.0D, 20.0D);
        this.l5 = new java.awt.geom.Line2D.Double(20.0D, 20.0D, 40.0D, 0.0D);
        this.l6 = new java.awt.geom.Line2D.Double(20.0D, 0.0D, 40.0D, 20.0D);
    }

    public int target(int var1) {
        return var1 == 0 ? this.parent.mouse_pos.x : this.parent.mouse_pos.y;
    }

    public void paint(Graphics2D var1) {
        int var2 = this.x;
        int var3 = this.y;
        int var4 = this.cox;
        int var5 = this.coy;
        var1.translate(var2, var3);
        var1.setPaint(this.gp1);
        var1.fill(this.e1);
        var1.setStroke(this.bs1);
        var1.setPaint(Color.BLACK);
        var1.draw(this.e1);
        var1.translate(17, 10);
        if (this.dead) {
            var1.draw(this.l3);
            var1.draw(this.l4);
            var1.draw(this.l5);
            var1.draw(this.l6);
        } else if (this.damage > 0) {
            --this.damage;
            var1.draw(this.l1);
            var1.draw(this.l2);
        } else {
            var1.setPaint(Color.WHITE);
            var1.fill(this.e2);
            var1.setPaint(Color.BLACK);
            var1.draw(this.e2);
            var1.translate(6, 10);
            var1.fill(this.e3);
            var1.translate(-6, -10);
            var1.translate(22, 0);
            var1.setPaint(Color.WHITE);
            var1.fill(this.e2);
            var1.setPaint(Color.BLACK);
            var1.draw(this.e2);
            var1.translate(6, 10);
            var1.fill(this.e3);
            var1.translate(-6, -10);
            var1.translate(-22, 0);
        }

        var1.translate(-17, -10);
        var1.translate(var4, var5);
        double var6 = (double)(this.target(0) - (var2 + var4));
        double var8 = (double)(this.target(1) - (var3 + var5));
        if (var8 != 0.0D) {
            var1.rotate(Math.atan(var8 / var6));
            if (var6 < 0.0D) {
                var1.rotate(3.141592653589793D);
            }
        }

        var1.setPaint(Color.BLUE);
        var1.fill(this.r1);
        var1.setPaint(Color.BLACK);
        var1.draw(this.r1);
        if (var8 != 0.0D) {
            var1.rotate(-Math.atan(var8 / var6));
            if (var6 < 0.0D) {
                var1.rotate(-3.141592653589793D);
            }
        }

        var1.translate(-var4, -var5);
        var1.translate(-var2, -var3);
    }

    public void run() {
        if (this.dead) {
            this.vy += 0.2D;
        } else if (this.y + this.h < 400) {
            this.vy += 0.2D;
        } else if (this.y + this.h > 400) {
            this.vy = 0.0D;
            this.y = 400 - this.h;
        }

        this.x = (int)((double)this.x + Math.ceil(this.vx));
        this.y = (int)((double)this.y + Math.ceil(this.vy));
        if (this.x > 500 - this.h) {
            this.x = 500 - this.h;
        } else if (this.x < 0) {
            this.x = 0;
        }

    }
}
