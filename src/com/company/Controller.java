package com.company;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;
import javax.swing.JFrame;

public class Controller implements KeyListener, MouseListener, MouseMotionListener {
    Window w = new Window(this);
    Player p = new Player(this);
    Vector<Enemy> e = new Vector();
    Vector<Shot> s = new Vector();
    JFrame f;
    Point mouse_pos = new Point(0, 0);
    int health = 450;
    int shaking = 0;
    int level = 1;
    int xp = 0;
    int levelup = 0;
    int score = 0;
    int cooldown = 0;
    int waittime = 0;
    boolean pause;

    Controller(JFrame var1) {
        this.f = var1;
        this.f.getContentPane().add(this.w, "Center");
        this.f.pack();
        this.f.addMouseListener(this);
        this.f.addMouseMotionListener(this);
        this.f.addKeyListener(this);
        this.run();
    }

    void run() {
        while(true) {
            if (this.pause) {
                this.w.repaint();

                try {
                    Thread.sleep(100L);
                } catch (Exception var5) {
                    ;
                }
            } else {
                if (this.cooldown > 0) {
                    --this.cooldown;
                }

                if (Math.random() < 0.002D * (double)this.level * (double)this.level / 10.0D + 1.0E-4D * (double)this.waittime && !this.p.dead) {
                    this.e.add(new Enemy(this));
                    this.waittime = 0;
                    if (Math.random() < 0.66D) {
                        ((Enemy)this.e.get(this.e.size() - 1)).x = Math.random() < 0.5D ? -50 : 550;
                        ((Enemy)this.e.get(this.e.size() - 1)).y = (int)(Math.random() * 400.0D);
                    } else {
                        ((Enemy)this.e.get(this.e.size() - 1)).x = (int)(Math.random() * 400.0D);
                        ((Enemy)this.e.get(this.e.size() - 1)).y = -50;
                    }

                    ((Enemy)this.e.get(this.e.size() - 1)).health = 5 * this.level * this.level;
                } else {
                    ++this.waittime;
                }

                this.p.run();

                int var1;
                for(var1 = 0; var1 < this.e.size(); ++var1) {
                    ((Enemy)this.e.get(var1)).run();
                    if (((Enemy)this.e.get(var1)).dead && ((Enemy)this.e.get(var1)).y > 500) {
                        this.e.remove(var1);
                        --var1;
                    }
                }

                for(var1 = 0; var1 < this.s.size(); ++var1) {
                    ((Shot)this.s.get(var1)).run();
                    if (((Shot)this.s.get(var1)).x <= 500 && ((Shot)this.s.get(var1)).y <= 500 && ((Shot)this.s.get(var1)).x >= 0 && ((Shot)this.s.get(var1)).y >= 0) {
                        int var2;
                        int var3;
                        if (!((Shot)this.s.get(var1)).friendly) {
                            var2 = this.p.x + this.p.cox - ((Shot)this.s.get(var1)).x;
                            var3 = this.p.y + this.p.coy - ((Shot)this.s.get(var1)).y;
                            if (Math.abs(var2) < 50 && Math.abs(var3) < 50) {
                                this.s.remove(var1);
                                --var1;
                                this.p.damage = 20;
                                this.health -= 25;
                                this.shaking = 20;
                                if (this.health <= 0) {
                                    this.p.dead = true;
                                    this.p.vy = -10.0D;
                                    this.p.vx = 0.0D;
                                }
                            }
                        } else {
                            for(var2 = 0; var2 < this.e.size(); ++var2) {
                                if (!((Enemy)this.e.get(var2)).dead) {
                                    var3 = ((Enemy)this.e.get(var2)).x + ((Enemy)this.e.get(var2)).cox - ((Shot)this.s.get(var1)).x;
                                    int var4 = ((Enemy)this.e.get(var2)).y + ((Enemy)this.e.get(var2)).coy - ((Shot)this.s.get(var1)).y;
                                    if (Math.abs(var3) < 50 && Math.abs(var4) < 50) {
                                        this.s.remove(var1);
                                        --var1;
                                        ((Enemy)this.e.get(var2)).damage = 20;
                                        Enemy var10000 = (Enemy)this.e.get(var2);
                                        var10000.health -= 10;
                                        if (((Enemy)this.e.get(var2)).health <= 0) {
                                            ((Enemy)this.e.get(var2)).dead = true;
                                            ((Enemy)this.e.get(var2)).vy = -10.0D;
                                            this.score += 250 * this.level;
                                            ++this.xp;
                                            if (this.xp > this.level * 6) {
                                                this.xp -= this.level * 6;
                                                ++this.level;
                                                this.levelup = 150;
                                                this.score += 10000 * this.level;
                                            }
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                    } else {
                        this.s.remove(var1);
                        --var1;
                    }
                }

                this.w.repaint();

                try {
                    Thread.sleep(20L);
                } catch (Exception var6) {
                    ;
                }
            }
        }
    }

    public void keyReleased(KeyEvent var1) {
        if (!this.p.dead && (var1.getKeyCode() == 65 || var1.getKeyCode() == 68)) {
            this.p.vx = 0.0D;
        }

    }

    public void keyPressed(KeyEvent var1) {
        if (!this.p.dead) {
            if (var1.getKeyCode() == 65) {
                this.p.vx = -4.0D;
            } else if (var1.getKeyCode() == 68) {
                this.p.vx = 4.0D;
            } else if (var1.getKeyCode() == 32) {
                if (this.p.y == 400 - this.p.h) {
                    this.p.y -= 10;
                    this.p.vy = -10.0D;
                }
            } else if (var1.getKeyCode() == 80) {
                if (this.pause) {
                    this.pause = false;
                } else {
                    this.pause = true;
                }
            }
        }

    }

    public void keyTyped(KeyEvent var1) {
    }

    public void mouseExited(MouseEvent var1) {
    }

    public void mouseEntered(MouseEvent var1) {
    }

    public void mouseClicked(MouseEvent var1) {
    }

    public void mouseReleased(MouseEvent var1) {
        if (this.p.y > 2000 && var1.getButton() == 1 && this.mouse_pos.x > 150 && this.mouse_pos.x < 350 && this.mouse_pos.y > 300 && this.mouse_pos.y < 350) {
            System.exit(0);
        }

    }

    public void mousePressed(MouseEvent var1) {
        if (!this.p.dead && !this.pause) {
            int var2;
            if (var1.getButton() == 1) {
                var2 = this.mouse_pos.x - (this.p.x + this.p.cox);
                int var3 = this.mouse_pos.y - (this.p.y + this.p.coy);
                double var4 = Math.sqrt((double)(var2 * var2 + var3 * var3));
                this.s.add(new Shot(this.p.x + this.p.cox, this.p.y + this.p.coy, (int)((double)var2 * (5.0D / var4)), (int)((double)var3 * (5.0D / var4)), true));
            } else if (var1.getButton() == 3 && this.cooldown == 0) {
                for(var2 = 0; var2 < 360; var2 += 9) {
                    this.s.add(new Shot(this.p.x + this.p.cox, this.p.y + this.p.coy, (int)(Math.cos((double)var2) * 10.0D), (int)(Math.sin((double)var2) * 10.0D), true));
                }

                this.cooldown = 450 - this.level * 15;
            }
        }

    }

    public void mouseMoved(MouseEvent var1) {
        this.mouse_pos.setLocation(var1.getX(), var1.getY());
    }

    public void mouseDragged(MouseEvent var1) {
        this.mouse_pos.setLocation(var1.getX(), var1.getY());
    }
}
