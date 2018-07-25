package com.company;

import java.awt.Color;
import java.awt.GradientPaint;

public class Enemy extends Player {
    int health;

    public Enemy(Controller var1) {
        super(var1);
        this.gp1 = new GradientPaint((float)this.x, (float)this.y, new Color(0,0,0), (float)this.x, (float)(this.y + this.h), new Color(0, 15, 8), true);
        this.x = 0;
        this.y = 0;
        this.health = 1;
    }

    public int target(int var1) {
        return var1 == 0 ? this.parent.p.x + this.parent.p.cox : this.parent.p.y + this.parent.p.coy;
    }

    public void run() {
        if (this.dead) {
            this.vy += 0.2D;
            this.y = (int)((double)this.y + this.vy);
        } else {
            if (this.parent.p.dead) {
                return;
            }

            int var1 = this.parent.p.x + this.parent.p.cox - (this.x + this.cox);
            int var2 = this.parent.p.y + this.parent.p.coy - (this.y + this.coy);
            if (var1 > 0) {
                this.x += 2;
            } else if (var1 < 0) {
                this.x -= 2;
            }

            if (var2 > 0) {
                this.y += 2;
            } else if (var2 < 0) {
                this.y -= 2;
            }

            if (Math.abs(var1) < 50 && Math.abs(var2) < 50) {
                --this.parent.health;
                this.parent.shaking = 10;
                this.parent.p.damage = 10;
                if (this.parent.health <= 0) {
                    this.parent.p.dead = true;
                    this.parent.p.vy = -10.0D;
                    this.parent.p.vx = 0.0D;
                }
            }

            if (Math.random() < 0.002D * (double)this.parent.level * (double)this.parent.level / 10.0D + 0.005D && !this.parent.p.dead) {
                var1 = this.parent.p.x + this.parent.p.cox - (this.x + this.cox);
                var2 = this.parent.p.y + this.parent.p.coy - (this.y + this.coy);
                double var3 = Math.sqrt((double)(var1 * var1 + var2 * var2));
                this.parent.s.add(new Shot(this.x + this.cox, this.y + this.coy, (int)((double)var1 * (5.0D / var3)), (int)((double)var2 * (5.0D / var3)), false));
            }
        }

    }
}
