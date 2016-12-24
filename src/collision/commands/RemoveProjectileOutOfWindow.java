package collision.commands;

import actors.Projectile;

import java.util.List;

public class RemoveProjectileOutOfWindow implements Command {
    private final List<Projectile> allProjectiles;
    private final Projectile projectileOutOfWindow;

    public RemoveProjectileOutOfWindow(
        List<Projectile> allProjectiles,
        Projectile projectileOutOfWindow){
        this.allProjectiles = allProjectiles;
        this.projectileOutOfWindow = projectileOutOfWindow;
    }

    @Override
    public void Execute() {
        int indexOfDeadProjectile = allProjectiles.indexOf(projectileOutOfWindow);
        allProjectiles.remove(indexOfDeadProjectile);
    }
}
