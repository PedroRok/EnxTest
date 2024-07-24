package com.pedrorok.enx.windcharge;

import com.destroystokyo.paper.ParticleBuilder;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rok, Pedro Lucas nmm. Created on 21/07/2024
 * @project EnxTest
 */
public class WindOptions {

    private final double power;
    private final double size;
    @Getter
    private final double velocity;

    // Sound
    private final float volume;
    private final float pitch;
    private final Sound sound;

    // Particle List
    private final List<ParticleBuilder> particles;

    public WindOptions(double power, double size, double velocity, float volume, float pitch, Sound sound) {
        this.power = power;
        this.size = size;
        this.velocity = velocity;
        this.volume = volume;
        this.pitch = pitch;
        this.sound = sound;
        this.particles = new ArrayList<>();
    }


    public void addParticle(ParticleBuilder particle) {
        particles.add(particle);
    }

    public void explode(Location location) {
        windExplode(location);
        particles.forEach(particle -> particle.clone().location(location).spawn());
        location.getWorld().playSound(location, sound, volume,pitch);
    }


    private void windExplode(Location location) {
        List<Entity> entities = new ArrayList<>(location.getWorld().getNearbyEntities(location, size, size, size));
        for (Entity entity : entities) {
            if (entity instanceof Player && ((Player) entity).getGameMode() == GameMode.SPECTATOR) continue;

            Location entityLoc = entity.getLocation();

            double distance = entity.getLocation().distanceSquared(entityLoc) / power;
            double eyeHeight = entity instanceof LivingEntity ? ((LivingEntity) entity).getEyeHeight() : 1.53D;
            double dx = entityLoc.getX() - location.getX();
            double dy = entityLoc.getY() + eyeHeight - location.getY();
            double dz = entityLoc.getZ() - location.getZ();
            double dq = Math.sqrt(dx * dx + dy * dy + dz * dz);
            if (dq == 0.0D) continue;
            dx /= dq;
            dy /= dq;
            dz /= dq;
            double face = (1.0D - distance) * power;
            Vector vec = new Vector(dx * face, dy * face, dz * face);

            entity.setVelocity(entity.getVelocity().add(vec).multiply(power));
        }
    }
}
