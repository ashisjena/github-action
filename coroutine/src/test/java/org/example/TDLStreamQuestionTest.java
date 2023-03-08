package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TDLStreamQuestionTest {

    private static MobileMaster mobileMaster;
    private static List<Pair<String, Integer>> gsmarenaRatings;
    private static List<Pair<String, Integer>> smartprixRatings;

    @BeforeAll
    public static void setup() {
        gsmarenaRatings = List.of(
                new Pair<>("mi note 5", 9),
                new Pair<>("iphone 15", 11),
                new Pair<>("lg 5", 7),
                new Pair<>("blah blah", 50),
                new Pair<>("realme 10 pro", 13));

        smartprixRatings = List.of(
                new Pair<>("mi note 12", 18),
                new Pair<>("iphone 15", 13),
                new Pair<>("samsung 10", 8));

        mobileMaster = new MobileMasterCache(gsmarenaRatings, smartprixRatings);
    }

    @Test
    public void getTopMobiles() {
        System.out.println(mobileMaster);
        System.out.println("===========================================================");

        List<Mobile> actualResults = getTopNMobiles(List.of(gsmarenaRatings, smartprixRatings), 5);

        List<Mobile> expectedResults = new ArrayList<>(Arrays.asList(
                new Mobile("mi note 12", 18),
                new Mobile("iphone 15", 13),
                new Mobile("realme 10 pro", 13),
                new Mobile("mi note 5", 9),
                new Mobile("samsung 10", 8)
        ));

        // assertEquals(expectedResults, actualResults);
    }

    private List<Mobile> getTopNMobiles(List<List<Pair<String, Integer>>> listOfMobilesAndTheirRatings, int topN) {
        // TODO:: Implement
        return new ArrayList<>();
    }
}

record Pair<K, V>(K key, V score) {
}

record Mobile(String name, Integer score) {
}

interface MobileMaster {
    Mobile getMobile(Pair<String, Integer> pair);

    static String mobileMasterKey(Pair<String, Integer> p) {
        return p.key() + " # " + p.score();
    }
}


// Implemented by Separate Team
class MobileMasterCache implements MobileMaster {

    private final Map<String, Mobile> mobileMap;

    MobileMasterCache(List<Pair<String, Integer>> gsmarenaRatings, List<Pair<String, Integer>> smartprixRatings) {
        mobileMap = Stream.concat(gsmarenaRatings.stream(), smartprixRatings.stream())
                .filter(pair -> !pair.key().equals("blah blah"))
                .collect(Collectors.toMap(
                        MobileMaster::mobileMasterKey,
                        (p) -> new Mobile(p.key(), p.score()),
                        (x, y) -> x.score() > y.score() ? x : y));
    }

    @Override
    public Mobile getMobile(Pair<String, Integer> pair) {
        return mobileMap.get(MobileMaster.mobileMasterKey(pair));
    }

    @Override
    public String toString() {
        return this.mobileMap
                .entrySet()
                .stream()
                .map(String::valueOf)
                .collect(Collectors.joining(System.lineSeparator()));
    }

}