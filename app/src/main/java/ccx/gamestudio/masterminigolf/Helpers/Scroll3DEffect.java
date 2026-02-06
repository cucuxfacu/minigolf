package ccx.gamestudio.masterminigolf.Helpers;

import org.andengine.entity.IEntity;
import org.andengine.entity.sprite.Sprite;

public class Scroll3DEffect {

    private float maxDist;
    private float maxScale;
    private float minScale;
    private float minAlpha;
    private float centerX;

    public Scroll3DEffect(float centerX, float maxDist, float maxScale, float minScale, float minAlpha) {
        this.centerX = centerX;
        this.maxDist = maxDist;
        this.maxScale = maxScale;
        this.minScale = minScale;
        this.minAlpha = minAlpha;
    }

    public void apply(IEntity container) {

        for (int i = 0; i < container.getChildCount(); i++) {

            Sprite s = (Sprite) container.getChildByIndex(i);

            float spriteCenter = s.getX() + container.getX();
            float dist = Math.abs(spriteCenter - centerX);

            // -------------------------
            // ALPHA
            // -------------------------
            float alpha;
            if (dist >= maxDist) {
                alpha = minAlpha;
            } else {
                float t = dist / maxDist;
                alpha = 1f - ((1f - minAlpha) * t);
            }
            s.setAlpha(alpha);

            // -------------------------
            // SCALE
            // -------------------------
            float scale;
            if (dist >= maxDist) {
                scale = minScale;
            } else {
                float t = dist / maxDist;
                scale = maxScale - (t * (maxScale - minScale));
            }
            s.setScale(scale);
        }
    }
}
