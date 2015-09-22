
var qrt = (\dur:1);
var eth = (\dur:1/2);
var sxt = (\dur:1/4);

SkoarTestRunner((

    cats_eat_values: [
        "2 => @two ) =^.^= => @two ) < =^.^= > => @two )",
        [(\two:2),(\two:inf),(\two:[nil])]
    ],

	cats_regex_ambiguity: [
        "<=^.^=,=^.^=> => @foo )",
        [(\foo:[nil,nil])]
    ],

	cats_are_cats: [
        "{? =^.^= == =^.^= ?? 3 ?? 2 ?} => @foo )",
        [(\foo:3)]
    ],

	cats_are_false: [
        "{? =^.^= ?? 3 ?? 2 ?} => @foo )",
        [(\foo:2)]
    ],

	cats_nonexistant_variable: [
        "{? !x == =^.^= ?? 3 ?? 2 ?} => @foo )",
        [(\foo:3)]
    ],

	cats_nonexistant_variable_two: [
        "{? !x ?? 3 ?? 2 ?} => @foo )",
        [(\foo:2)]
    ],

	cats_missing_args: [
        "{! bar<x> !! {? !x ?? 3 ?? 2 ?} !} !bar => @foo )",
        [(\foo:2)]
    ],

));
