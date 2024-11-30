package ch.epfl.rigel.astronomy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.US_ASCII;

/**
 * Loader for Asterisms.
 *
 * @author Mounir Raki (310287)
 */
public enum AsterismLoader implements StarCatalogue.Loader{
    INSTANCE;

    /**
     * Loads a file containing the information of the asterisms from a text file,
     * and builds the list of asterisms in a StarCatalogue.
     *
     * @param inputStream
     *          the content of the file to read, obtained by an InputStream
     * @param builder
     *          the type of builder, used to build the list of asterisms
     * @throws IOException
     *          if the file doesn't exist or the path is incorrect
     */
    @Override
    public void load(InputStream inputStream, StarCatalogue.Builder builder) throws IOException {
        try(BufferedReader r = new BufferedReader(new InputStreamReader(inputStream, US_ASCII))){
            String line;
            Map<Integer, Star> hipparcosStarMap = new HashMap<>();

            for(Star s : builder.stars()){
                hipparcosStarMap.put(s.hipparcosId(), s);
            }

            while((line = r.readLine()) != null) {
                String[] strArray = line.split(",");
                List<Star> stars = new ArrayList<>();

                for(String hipId : strArray){
                    int hipparcosId = Integer.parseInt(hipId);
                    Star s = hipparcosStarMap.get(hipparcosId);
                    stars.add(s);
                }

                builder.addAsterism(new Asterism(stars));
            }
        }
    }
}
