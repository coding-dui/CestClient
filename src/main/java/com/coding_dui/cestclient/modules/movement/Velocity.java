package com.coding_dui.cestclient.modules.movement;

import com.coding_dui.cestclient.module.Category;
import com.coding_dui.cestclient.module.Module;

/**
 * Cancels the knockback the server applies to you. The cancellation itself is
 * done by {@code LivingEntityKnockbackMixin} while this module is enabled.
 */
public class Velocity extends Module {
    public Velocity() {
        super("Velocity", "Cancels knockback", Category.MOVEMENT);
    }
}
