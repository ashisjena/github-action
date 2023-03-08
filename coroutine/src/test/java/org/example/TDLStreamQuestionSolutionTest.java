package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TDLStreamQuestionSolutionTest {

    private static MobileMasterA mobileMaster;
    private static List<PairA<String, Integer>> gsmarenaRatings;
    private static List<PairA<String, Integer>> smartprixRatings;

    @BeforeAll
    public static void setup() {
        gsmarenaRatings = List.of(
                new PairA<>("mi note 5", 9),
                new PairA<>("iphone 15", 11),
                new PairA<>("lg 5", 7),
                new PairA<>("blah blah", 50),
                new PairA<>("realme 10 pro", 13));

        smartprixRatings = List.of(
                new PairA<>("mi note 12", 18),
                new PairA<>("iphone 15", 13),
                new PairA<>("samsung 10", 8));

        mobileMaster = new MobileMasterA(gsmarenaRatings, smartprixRatings);
    }

    @Test
    public void getTopMobiles() {
        System.out.println(mobileMaster);
        System.out.println("=========================================");

        List<MobileA> actualResults = getTopNMobiles(List.of(gsmarenaRatings, smartprixRatings), 5);

        List<MobileA> expectedResults = List.of(
                new MobileA("mi note 12", 18),
                new MobileA("iphone 15", 13),
                new MobileA("realme 10 pro", 13),
                new MobileA("mi note 5", 9),
                new MobileA("samsung 10", 8)
        );

        assertEquals(expectedResults, actualResults);
    }

    public List<MobileA> getTopNMobiles(List<List<PairA<String, Integer>>> listOfMobilesAndTheirRatings, int topN) {
        return new LinkedHashSet<>(listOfMobilesAndTheirRatings.stream()
                .flatMap(List::stream)
                .collect(
                        Collectors.toCollection(() ->
                                new TreeSet<>((p1, p2) ->
                                        Objects.equals(p1.score(), p2.score()) ?
                                                p1.key().compareTo(p2.key()) :
                                                p2.score().compareTo(p1.score())))))
                .stream()
                .map(pair -> mobileMaster.get(pair))
                .filter(Objects::nonNull)
                .limit(topN)
                .collect(Collectors.toList());
    }
}

record PairA<K, V>(K key, V score) {

    @Override
    public int hashCode() {
        return this.key.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass())
            return false;

        return ((PairA<?, ?>) obj).key.equals(this.key);
    }
}

record MobileA(String name, Integer score) {
}

class MobileMasterA {

    private final Map<String, MobileA> mobileMap;

    MobileMasterA(List<PairA<String, Integer>> gsmarenaRatings, List<PairA<String, Integer>> smartprixRatings) {
        mobileMap = Stream.concat(gsmarenaRatings.stream(), smartprixRatings.stream())
                .filter(pair -> !pair.key().equals("blah blah"))
                .collect(Collectors.toMap(
                        MobileMasterA::mobileMasterKey,
                        (p) -> new MobileA(p.key(), p.score()),
                        (x, y) -> x.score() > y.score() ? x : y));
    }

    private static String mobileMasterKey(PairA<String, Integer> p) {
        return p.key() + " # " + p.score();
    }

    public MobileA get(PairA<String, Integer> pair) {
        return mobileMap.get(mobileMasterKey(pair));
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
