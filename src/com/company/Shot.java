package com.company;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;

public class Shot {
    public int x;
    public int y;
    double vx;
    double vy;
    boolean friendly;
    Ellipse2D e;

    public Shot(int var1, int var2, int var3, int var4, boolean var5) {
        this.x = var1;
        this.y = var2;
        this.vx = (double)var3;
        this.vy = (double)var4;
        this.friendly = var5;
        this.e = new Double(-8.0D, -8.0D, 16.0D, 16.0D);
    }

    public void paint(Graphics2D var1) {
        var1.translate(this.x, this.y);
        if (this.friendly) {
            var1.setPaint(Color.RED);
        } else {
            var1.setPaint(Color.BLUE);
        }

        var1.fill(this.e);
        var1.translate(-this.x, -this.y);
    }

    public void run() {
        this.x = (int)((double)this.x + this.vx);
        this.y = (int)((double)this.y + this.vy);
    }
}
