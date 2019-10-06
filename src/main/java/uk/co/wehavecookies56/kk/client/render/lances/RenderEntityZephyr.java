package uk.co.wehavecookies56.kk.client.render.lances;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.lwjgl.opengl.GL11;
import uk.co.wehavecookies56.kk.client.model.lances.ModelZephyr;
import uk.co.wehavecookies56.kk.common.entity.projectiles.lances.EntityZephyr;
import uk.co.wehavecookies56.kk.common.lib.Reference;
import uk.co.wehavecookies56.kk.common.lib.Strings;

public class RenderEntityZephyr extends Render implements IRenderFactory<EntityZephyr> {

    ModelBase model;

    public RenderEntityZephyr (RenderManager renderManager) {
        super(renderManager);
        shadowSize = 1;
        this.model = new ModelZephyr();
    }

    @Override
    protected ResourceLocation getEntityTexture (Entity entity) {
        return new ResourceLocation(Reference.MODID, "textures/items/models/" + Strings.Zephyr + ".png");
    }

    @Override
    public void doRender (Entity entity, double x, double y, double z, float yaw, float partialTicks) {
    	GL11.glPushMatrix();
    	{
	        GL11.glTranslated(x, y, z);
	
			GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
	        GL11.glRotatef(90, 0,0,1);

	        GL11.glScalef(0.02f, 0.02f, 0.02f);
	        
	        bindEntityTexture(entity);
	
	        //this.model.render(entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
			model.render(entity, (float) x, (float) y, (float) z, 0.0F, 0.0F, 0.0625F);

    	}
        GL11.glPopMatrix();
    }

    @Override
    public Render<? super EntityZephyr> createRenderFor (RenderManager manager) {
        return new RenderEntityZephyr(manager);
    }

}
