package ccx.gamestudio.masterminigolf.Manager;

import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;

import java.util.ArrayList;
import java.util.List;

public class AssetTextureLoader {

    public static List<TextureRegion> loadTexturesFromFolder(
            String folderPath,
            int atlasWidth,
            int atlasHeight,
            TextureOptions options
    ) {
        List<TextureRegion> list = new ArrayList<>();

        String previousPath = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath();
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(folderPath);

        try {
            String[] files = ResourceManager.getActivity().getAssets().list(folderPath);

            if (files != null) {
                for (String fileName : files) {

                    if (fileName.endsWith(".png")) {

                        BitmapTextureAtlas atlas = new BitmapTextureAtlas(
                                ResourceManager.getActivity().getTextureManager(),
                                atlasWidth,
                                atlasHeight,
                                options
                        );

                        TextureRegion region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                                atlas,
                                ResourceManager.getActivity(),
                                fileName,
                                0, 0
                        );

                        atlas.load();
                        list.add(region);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(previousPath);

        return list;
    }
}
