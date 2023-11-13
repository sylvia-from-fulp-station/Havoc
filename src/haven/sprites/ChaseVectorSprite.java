package haven.sprites;

import haven.*;
import haven.render.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ChaseVectorSprite extends Sprite implements PView.Render2D {


    public static Color MAINCOLOR = OptWnd.myselfChaseVectorColorOptionWidget.currentColor;
    public static Color FOECOLOR = OptWnd.foeChaseVectorColorOptionWidget.currentColor;
    public static Color FRIENDCOLOR = OptWnd.friendChaseVectorColorOptionWidget.currentColor;
    public static Color UNKNOWNCOLOR = OptWnd.unknownChaseVectorColorOptionWidget.currentColor;
    private final Homing homing;

    public ChaseVectorSprite(Gob gob, Homing homing) {
        super(gob, null);
        this.homing = homing;
    }

    private static final String[] IGNOREDCHASEVECTORS = {
            "gfx/terobjs/vehicle/cart",
            "gfx/terobjs/vehicle/wagon",
            "gfx/terobjs/vehicle/wheelbarrow",
    };

    public void draw(GOut g, Pipe state) {
        if (OptWnd.drawChaseVectorsCheckBox.a) {
            try {
                Gob gob = (Gob) owner;
                UI ui = gob.glob.sess.ui;
                if (ui != null) {
                    MapView mv = ui.gui.map;
                    if (mv != null) {
                        Moving lm = gob.getattr(Moving.class);
                        if (lm != null) {
                            Gob target = homing.tgt();
                            if (target != null) {
                                Resource targetRes = target.getres();
			                    if (Arrays.stream(IGNOREDCHASEVECTORS).noneMatch(gob.getres().name::contains) && Arrays.stream(IGNOREDCHASEVECTORS).noneMatch(targetRes.name::contains)) {
                                    if (gob.occupants.size() > 0){
                                        if (gob.getres().name.equals("gfx/terobjs/vehicle/rowboat")) {
                                            for (Gob occupant : gob.occupants) {
                                                if (occupant.getPoses().contains("rowboat-d") || occupant.getPoses().contains("rowing")) {
                                                    if (occupant.occupiedGobID == gob.id) {
                                                        gob = occupant;
                                                    }
                                                }
                                            }
                                        } else if (gob.getres().name.equals("gfx/terobjs/vehicle/knarr")) {
                                            for (Gob occupant : gob.occupants) {
                                                if (occupant.getPoses().contains("knarrman9")) {
                                                    if (occupant.occupiedGobID == gob.id) {
                                                        gob = occupant;
                                                    }
                                                }
                                            }
                                        } else if (gob.getres().name.equals("gfx/terobjs/vehicle/spark")) {
                                            for (Gob occupant : gob.occupants) {
                                                if (occupant.getPoses().contains("sparkan-idle") || occupant.getPoses().contains("sparkan-sparkan")) {
                                                    if (occupant.occupiedGobID == gob.id) {
                                                        gob = occupant;
                                                    }
                                                }
                                            }
                                        } else if (gob.getres().name.equals("gfx/terobjs/vehicle/snekkja")) {
                                            for (Gob occupant : gob.occupants) {
                                                if (occupant.getPoses().contains("snekkjaman0")) {
                                                    if (occupant.occupiedGobID == gob.id) {
                                                        gob = occupant;
                                                    }
                                                }
                                            }
                                        } else {
                                            for (Gob occupant : gob.occupants) {
                                                if (occupant.occupiedGobID == gob.id) {
                                                    gob = occupant;
                                                }
                                            }
                                        }
                                    }
                                    Color chaserColor;
                                    if (gob.isMe()) {
                                        chaserColor = MAINCOLOR;
                                    } else if (gob.isPartyMember() && !gob.isMe()) {
                                        chaserColor = FRIENDCOLOR;
                                    } else if ((gob.getres().name.equals("gfx/borka/body")) && (target.isMe() || target.isPartyMember())) {
                                        chaserColor = FOECOLOR;
                                    } else {
                                        chaserColor = UNKNOWNCOLOR;
                                    }
                                    Coord ChaserCoord = mv.screenxf(gob.getc()).round2();
                                    Coord TargetCoord = mv.screenxf(target.getc()).round2();
                                    g.chcolor(Color.BLACK);
                                    g.line(ChaserCoord, TargetCoord, 5);
                                    g.chcolor(chaserColor);
                                    g.line(ChaserCoord, TargetCoord, 3);
                                    g.chcolor();
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
    }
}
