package uk.co.wehavecookies56.kk.client.render.chakrams;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.lwjgl.opengl.GL11;
import uk.co.wehavecookies56.kk.client.model.ModelChakram;
import uk.co.wehavecookies56.kk.common.entity.projectiles.ChakramEntity;
import uk.co.wehavecookies56.kk.common.lib.Reference;

public class RenderEntityChakram extends Render implements IRenderFactory<ChakramEntity> {

    private ModelBase model;
    private String name;

    public RenderEntityChakram (RenderManager renderManager, String name) {
        super(renderManager);
        shadowSize = 1;
        this.model = new ModelChakram(name);
        this.name = name;
    }

    @Override
    protected ResourceLocation getEntityTexture (Entity entity) {
        return new ResourceLocation(Reference.MODID, "textures/items/models/" + name + ".png");
    }

    @Override
    public void doRender (Entity entity, double x, double y, double z, float yaw, float pitch) {
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glRotatef(90, 1F, 0.0F, 0.0F);

        GL11.glScalef(0.02f, 0.02f, 0.02f);

        GL11.glRotatef(yaw * 100, 0, 0, 1);

        bindEntityTexture(entity);

        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        this.model.render(entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        GL11.glPopMatrix();
    }


    @Override
    public Render<? super ChakramEntity> createRenderFor (RenderManager manager) {
        return new RenderEntityChakram(manager, name);
    }

}