package com.company;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Rectangle2D.Double;
import javax.swing.JPanel;

public class Window extends JPanel {
    Controller parent;
    GradientPaint gp1;
    GradientPaint gp2;
    Rectangle2D r1;
    Rectangle2D r2;
    Rectangle2D r3;
    Rectangle2D r4;
    BasicStroke bs1;
    Line2D s1;
    Line2D s2;
    Font f1;
    Font f2;
    RoundRectangle2D rr1;
    RoundRectangle2D rr2;

    public Window(Controller var1) {
        this.parent = var1;
        this.setPreferredSize(new Dimension(500, 500));
        this.gp1 = new GradientPaint(0.0F, 0.0F, new Color(13, 15, 37), 0.0F, 400.0F, Color.BLACK);
        this.gp2 = new GradientPaint(0.0F, 100.0F, new Color(13, 15, 0), 0.0F, 500.0F, new Color(13, 15, 48));
        this.r1 = new Double(0.0D, 0.0D, 500.0D, 400.0D);
        this.r2 = new Double(0.0D, 400.0D, 500.0D, 100.0D);
        this.r3 = new Double(0.0D, 0.0D, 450.0D, 50.0D);
        this.r4 = new Double(0.0D, 0.0D, 450.0D, 5.0D);
        this.bs1 = new BasicStroke(1.0F);
        this.s1 = new java.awt.geom.Line2D.Double(-16.0D, 0.0D, 16.0D, 0.0D);
        this.s2 = new java.awt.geom.Line2D.Double(0.0D, -16.0D, 0.0D, 16.0D);
        this.f1 = new Font("Arial", 0, 40);
        this.f2 = new Font("Arial", 0, 20);
        this.rr1 = new java.awt.geom.RoundRectangle2D.Double(100.0D, 100.0D, 300.0D, 300.0D, 10.0D, 10.0D);
        this.rr2 = new java.awt.geom.RoundRectangle2D.Double(150.0D, 300.0D, 200.0D, 50.0D, 10.0D, 10.0D);
    }

    public void paint(Graphics var1) {
        this.update(var1);
    }

    public void update(Graphics var1) {
        Graphics2D var2 = (Graphics2D)var1;
        var2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (this.parent.shaking > 0) {
            --this.parent.shaking;
            var2.translate(Math.random() * 5.0D, Math.random() * 5.0D);
        }

        var2.setPaint(this.gp1);
        var2.fill(this.r1);
        var2.setPaint(this.gp2);
        var2.fill(this.r2);

        int var3;
        for(var3 = 0; var3 < this.parent.e.size(); ++var3) {
            ((Enemy)this.parent.e.get(var3)).paint(var2);
        }

        this.parent.p.paint(var2);

        for(var3 = 0; var3 < this.parent.s.size(); ++var3) {
            ((Shot)this.parent.s.get(var3)).paint(var2);
        }

        FontMetrics var6;
        if (!this.parent.p.dead) {
            var2.translate(25, 25);
            this.r3.setRect(0.0D, 0.0D, (double)this.parent.health, 25.0D);
            if (this.parent.health > 225) {
                var2.setPaint(Color.GREEN);
            } else if (this.parent.health > 100) {
                var2.setPaint(Color.YELLOW);
            } else {
                var2.setPaint(Color.RED);
            }

            var2.fill(this.r3);
            var2.setPaint(Color.BLACK);
            var2.draw(this.r3);
            if (this.parent.cooldown > 0) {
                var2.translate(0, 25);
                var2.setPaint(Color.RED);
                this.r4.setRect(0.0D, 0.0D, (double)this.parent.cooldown, 5.0D);
                var2.fill(this.r4);
                var2.setPaint(Color.BLACK);
                var2.draw(this.r4);
                var2.translate(0, -25);
            }

            var2.translate(225, 33);
            var2.setFont(this.f2);
            var6 = var2.getFontMetrics();
            var2.drawString("Score: " + this.parent.score + "   Level: " + this.parent.level, -var6.stringWidth("Score: " + this.parent.score + " : Level: " + this.parent.level) / 2, -var6.getHeight() / 2);
            var2.translate(-225, -37);
            var2.translate(-25, -25);
        }

        if (this.parent.levelup > 0) {
            double var7 = (Math.random() - 0.5D) / 3.0D;
            if (this.parent.levelup < 50) {
                var2.translate(250, 250 - (-this.parent.levelup + 50) * 5);
            } else {
                var2.translate(250, 250);
            }

            var2.setFont(this.f1);
            FontMetrics var5 = var2.getFontMetrics();
            var2.rotate(var7);
            var2.drawString("LEVEL UP", -var5.stringWidth("LEVEL UP") / 2, -var5.getHeight());
            var2.rotate(-var7);
            if (this.parent.levelup < 50) {
                var2.translate(-250, -(250 - (-this.parent.levelup + 50) * 5));
            } else {
                var2.translate(-250, -250);
            }

            --this.parent.levelup;
        }

        if (this.parent.p.y > 2000) {
            this.parent.p.vy = 0.0D;
            this.parent.p.y = 2001;
            var2.setPaint(Color.WHITE);
            var2.fill(this.rr1);
            var2.setPaint(Color.BLACK);
            var2.draw(this.rr1);
            var2.setFont(this.f1);
            var6 = var2.getFontMetrics();
            var2.drawString("GAME OVER!", 250 - var6.stringWidth("GAME OVER!") / 2, 175 - var6.getHeight() / 2);
            var2.drawString("" + this.parent.score, 250 - var6.stringWidth("" + this.parent.score) / 2, 275 - var6.getHeight() / 2);
            var2.setFont(this.f2);
            var6 = var2.getFontMetrics();
            var2.drawString("Final Score:", 250 - var6.stringWidth("final Score:") / 2, 220 - var6.getHeight() / 2);
            var2.drawString("Quit", 250 - var6.stringWidth("Quit") / 2, 345 - var6.getHeight() / 2);
            var2.draw(this.rr2);
        }

        if (this.parent.pause) {
            var2.setPaint(Color.WHITE);
            var2.fill(this.rr2);
            var2.setPaint(Color.BLACK);
            var2.draw(this.rr2);
            var2.setFont(this.f2);
            var6 = var2.getFontMetrics();
            var2.drawString("Pause", 250 - var6.stringWidth("Pause") / 2, 345 - var6.getHeight() / 2);
        }

        var2.translate(this.parent.mouse_pos.x, this.parent.mouse_pos.y);
        var2.setStroke(this.bs1);
        var2.setPaint(Color.BLACK);
        var2.draw(this.s1);
        var2.draw(this.s2);
    }
}
