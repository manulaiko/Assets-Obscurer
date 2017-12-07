package com.manulaiko.tabitha.utils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Inflector class.
 * ================
 *
 * This class offers helpers for pluralizing and singularizing
 * english words.
 *
 * To pluralize a word use the method `plural`, it accepts
 * as parameter the word to pluralize.
 *
 * To singularize a word use the method `singular`, it accepts
 * as parameter the word to singularize.
 *
 * This class contains code from the [Doctrine project](http://www.doctrine-project.org) coded
 * by {@author Konsta Vesterinen <kvesteri@cc.hut.fi>} and {@author Jonathan H. Wage <jonwage@gmail.com>}.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Inflector {
    /**
     * Plural inflector rules.
     */
    private static Map<String, Map<String, String>> _plural = new HashMap<>();

    /**
     * Singular inflector rules.
     */
    private static Map<String, Map<String, String>> _singular = new HashMap<>();

    /**
     * Words that should not be inflected.
     */
    private static List<String> _uncontable = new ArrayList<>();

    /**
     * Initializes the collections.
     */
    private static void _initialize() {
        Inflector._initializePlural();
        Inflector._initializeSingular();
        Inflector._initializeUncontable();
    }

    /**
     * Initializes the plural collection.
     */
    private static void _initializePlural() {
        Map<String, String> rules = new HashMap<>();
        rules.put("/(s)tatus$/i", "\1\2tatuses");
        rules.put("/(quiz)$/i", "\1zes");
        rules.put("/^(ox)$/i", "\1\2en");
        rules.put("/([m|l])ouse$/i", "\1ice");
        rules.put("/(matr|vert|ind)(ix|ex)$/i", "\1ices");
        rules.put("/(x|ch|ss|s)]$/i", "\1es");
        rules.put("/([^aeiouy]|qu)y$/i", "\1ies");
        rules.put("/(hive)$/i", "\1s");
        rules.put("/(?:([^f])fe|([lr])f)$/i", "\1\2ves");
        rules.put("/sis$/i", "ses");
        rules.put("/([ti])um$/i", "\1a");
        rules.put("/(p)erson$/i", "\1eople");
        rules.put("/(m)an$/i", "\1en");
        rules.put("/(c)hild$/i", "\1hildren");
        rules.put("/(f)oot$/i", "\1eet");
        rules.put("/(buffal|her|potat|tomat|volcan)o$/i", "\1\2oes");
        rules.put("/(alumn|bacill|cact|foc|fung|nucle|radi|stimul|syllab|termin|vir)us$/i", "\1i");
        rules.put("/us$/i", "uses");
        rules.put("/(alias)$/i", "\1es");
        rules.put("/(analys|ax|cris|test|thes)is$/i", "\1es");
        rules.put("/s$/", "s");
        rules.put("/^$/", "");
        rules.put("/$/", "s");

        Map<String, String> uninflected = new HashMap<>();
        uninflected.put(".*[nrlm]ese", ".*[nrlm]ese");
        uninflected.put(".*deer", ".*deer");
        uninflected.put(".*fish", ".*fish");
        uninflected.put(".*measles", ".*measles");
        uninflected.put(".*ois", ".*ois");
        uninflected.put(".*pox", ".*pox");
        uninflected.put(".*sheep", ".*sheep");
        uninflected.put("people", "people");
        uninflected.put("cookie", "cookie");

        Map<String, String> irregular = new HashMap<>();

        irregular.put("atlas", "atlases");
        irregular.put("axe", "axes");
        irregular.put("beef", "beefs");
        irregular.put("brother", "brothers");
        irregular.put("cafe", "cafes");
        irregular.put("chateau", "chateaux");
        irregular.put("child", "children");
        irregular.put("cookie", "cookies");
        irregular.put("corpus", "corpuses");
        irregular.put("cow", "cows");
        irregular.put("criterion", "criteria");
        irregular.put("curriculum", "curricula");
        irregular.put("demo", "demos");
        irregular.put("domino", "dominoes");
        irregular.put("echo", "echoes");
        irregular.put("foot", "feet");
        irregular.put("fungus", "fungi");
        irregular.put("ganglion", "ganglions");
        irregular.put("genie", "genies");
        irregular.put("genus", "genera");
        irregular.put("graffito", "graffiti");
        irregular.put("hippopotamus", "hippopotami");
        irregular.put("hoof", "hoofs");
        irregular.put("human", "humans");
        irregular.put("iris", "irises");
        irregular.put("leaf", "leaves");
        irregular.put("loaf", "loaves");
        irregular.put("man", "men");
        irregular.put("medium", "media");
        irregular.put("memorandum", "memoranda");
        irregular.put("money", "monies");
        irregular.put("mongoose", "mongooses");
        irregular.put("motto", "mottoes");
        irregular.put("move", "moves");
        irregular.put("mythos", "mythoi");
        irregular.put("niche", "niches");
        irregular.put("nucleus", "nuclei");
        irregular.put("numen", "numina");
        irregular.put("occiput", "occiputs");
        irregular.put("octopus", "octopuses");
        irregular.put("opus", "opuses");
        irregular.put("ox", "oxen");
        irregular.put("penis", "penises");
        irregular.put("person", "people");
        irregular.put("plateau", "plateaux");
        irregular.put("runner-up", "runners-up");
        irregular.put("sex", "sexes");
        irregular.put("soliloquy", "soliloquies");
        irregular.put("son-in-law", "sons-in-law");
        irregular.put("syllabus", "syllabi");
        irregular.put("testis", "testes");
        irregular.put("thief", "thieves");
        irregular.put("tooth", "teeth");
        irregular.put("tornado", "tornadoes");
        irregular.put("trilby", "trilbys");
        irregular.put("turf", "turfs");
        irregular.put("volcano", "volcanoes");

        Inflector._plural.put("rules", rules);
        Inflector._plural.put("uninflected", uninflected);
        Inflector._plural.put("irregular", irregular);
    }

    /**
     * Initializes the singular collection.
     */
    private static void _initializeSingular() {
        Map<String, String> rules = new HashMap<>();

        rules.put("/(s)tatuses$/i", "\1\2tatus");
        rules.put("/^(.*)(menu)s$/i", "\1\2");
        rules.put("/(quiz)zes$/i", "\\1");
        rules.put("/(matr)ices$/i", "\1ix");
        rules.put("/(vert|ind)ices$/i", "\1ex");
        rules.put("/^(ox)en/i", "\1");
        rules.put("/(alias)(es)*$/i", "\1");
        rules.put("/(buffal|her|potat|tomat|volcan)oes$/i", "\1o");
        rules.put("/(alumn|bacill|cact|foc|fung|nucle|radi|stimul|syllab|termin|viri?)i$/i", "\1us");
        rules.put("/([ftw]ax)es/i", "\1");
        rules.put("/(analys|ax|cris|test|thes)es$/i", "\1is");
        rules.put("/(shoe|slave)s$/i", "\1");
        rules.put("/(o)es$/i", "\1");
        rules.put("/ouses$/", "ouse");
        rules.put("/([^a])uses$/", "\1us");
        rules.put("/([m|l])ice$/i", "\1ouse");
        rules.put("/(x|ch|ss|sh)es$/i", "\1");
        rules.put("/(m)ovies$/i", "\1\2ovie");
        rules.put("/(s)eries$/i", "\1\2eries");
        rules.put("/([^aeiouy]|qu)ies$/i", "\1y");
        rules.put("/([lr])ves$/i", "\1f");
        rules.put("/(tive)s$/i", "\1");
        rules.put("/(hive)s$/i", "\1");
        rules.put("/(drive)s$/i", "\1");
        rules.put("/([^fo])ves$/i", "\1fe");
        rules.put("/(^analy)ses$/i", "\1sis");
        rules.put("/(analy|diagno|^ba|(p)arenthe|(p)rogno|(s)ynop|(t)he)ses$/i", "\1\2sis");
        rules.put("/([ti])a$/i", "\1um");
        rules.put("/(p)eople$/i", "\1\2erson");
        rules.put("/(m)en$/i", "\1an");
        rules.put("/(c)hildren$/i", "\1\2hild");
        rules.put("/(f)eet$/i", "\1oot");
        rules.put("/(n)ews$/i", "\1\2ews");
        rules.put("/eaus$/", "eau");
        rules.put("/^(.*us)$/", "\\1");
        rules.put("/s$/i", "");

        Map<String, String> uninflected = new HashMap<>();
        uninflected.put(".*[nrlm]ese", ".*[nrlm]ese");
        uninflected.put(".*deer", ".*deer");
        uninflected.put(".*fish", ".*fish");
        uninflected.put(".*measles", ".*measles");
        uninflected.put(".*ois", ".*ois");
        uninflected.put(".*pox", ".*pox");
        uninflected.put(".*sheep", ".*sheep");
        uninflected.put(".*ss", ".*ss");

        Map<String, String> irregular = new HashMap<>();
        irregular.put("criteria", "criterion");
        irregular.put("curves", "curve");
        irregular.put("emphases", "emphasis");
        irregular.put("foes", "foe");
        irregular.put("hoaxes", "hoax");
        irregular.put("media", "medium");
        irregular.put("neuroses", "neurosis");
        irregular.put("waves", "wave");
        irregular.put("oases", "oasis");

        Inflector._singular.put("rules", rules);
        Inflector._singular.put("uninflected", uninflected);
        Inflector._singular.put("irregular", irregular);
    }

    /**
     * Initializes the uncontable list.
     */
    private static void _initializeUncontable() {
        String[] uncontable = new String[]{
                "Amoyese",
                "bison",
                "Borghese",
                "bream",
                "breeches",
                "britches",
                "buffalo",
                "cantus",
                "carp",
                "chassis",
                "clippers",
                "cod",
                "coitus",
                "Congoese",
                "contretemps",
                "corps",
                "debris",
                "diabetes",
                "djinn",
                "eland",
                "elk",
                "equipment",
                "Faroese",
                "flounder",
                "Foochowese",
                "gallows",
                "Genevese",
                "Genoese",
                "Gilbertese",
                "graffiti",
                "headquarters",
                "herpes",
                "hijinks",
                "Hottentotese",
                "information",
                "innings",
                "jackanapes",
                "Kiplingese",
                "Kongoese",
                "Lucchese",
                "mackerel",
                "Maltese",
                "mews",
                "moose",
                "mumps",
                "Nankingese",
                "news",
                "nexus",
                "Niasese",
                "Pekingese",
                "Piedmontese",
                "pincers",
                "Pistoiese",
                "pliers",
                "Portuguese",
                "proceedings",
                "rabies",
                "rice",
                "rhinoceros",
                "salmon",
                "Sarawakese",
                "scissors",
                "series",
                "Shavese",
                "shears",
                "siemens",
                "species",
                "staff",
                "swine",
                "testes",
                "trousers",
                "trout",
                "tuna",
                "Vermontese",
                "Wenchowese",
                "whiting",
                "wildebeest",
                "Yengeese",
                "audio",
                "compensation",
                "coreopsis",
                "data",
                "deer",
                "education",
                "fish",
                "gold",
                "knowledge",
                "love",
                "rain",
                "money",
                "nutrition",
                "offspring",
                "plankton",
                "police",
                "sheep",
                "traffic"
        };

        Inflector._uncontable = Arrays.asList(uncontable);
    }

    /**
     * Returns the plural form of the word.
     *
     * @param word Word to pluralize.
     *
     * @return The plural form of `word`.
     */
    public static String plural(String word) {
        if (Inflector._uncontable.isEmpty()) {
            Inflector._initialize();
        }

        if (Inflector._uncontable.contains(word)) {
            return word;
        }

        Map<String, String> irregular = Inflector._plural.get("irregular");
        Map<String, String> uninflected = Inflector._plural.get("uninflected");
        Map<String, String> rules = Inflector._plural.get("rules");

        return Inflector._inflect(irregular, uninflected, rules, word);
    }

    /**
     * Returns the singular form of the word.
     *
     * @param word Word to singularize.
     *
     * @return The singular form of `word`.
     */
    public static String singular(String word) {
        if (Inflector._uncontable.isEmpty()) {
            Inflector._initialize();
        }

        if (Inflector._uncontable.contains(word)) {
            return word;
        }

        Map<String, String> irregular = Inflector._singular.get("irregular");
        Map<String, String> uninflected = Inflector._singular.get("uninflected");
        Map<String, String> rules = Inflector._singular.get("rules");

        return Inflector._inflect(irregular, uninflected, rules, word);
    }

    /**
     * Inflects a word with given collections.
     *
     * @param irregular   Irregular inflections.
     * @param uninflected Uninflected matches.
     * @param rules       Inflection rules.
     * @param word        Word to inflect.
     *
     * @return Inflected word.
     */
    private static String _inflect(
            Map<String, String> irregular, Map<String, String> uninflected, Map<String, String> rules, String word
    ) {
        if (irregular.containsKey(word)) {
            return irregular.get(word);
        }

        for (String rule : uninflected.keySet()) {
            Pattern p = Pattern.compile("/^" + rule + "$/");
            Matcher m = p.matcher(word); // get a matcher object

            if (m.matches()) {
                return word;
            }
        }

        for (Map.Entry<String, String> entry : rules.entrySet()) {
            Pattern p = Pattern.compile("/^" + entry.getKey() + "$/");
            Matcher m = p.matcher(word); // get a matcher object

            if (!m.matches()) {
                continue;
            }

            return m.replaceAll(entry.getValue());
        }

        // Well, right now the word couldn't be pluralized
        // so return it as it was (or throw an exception?)
        return word;
    }
}
